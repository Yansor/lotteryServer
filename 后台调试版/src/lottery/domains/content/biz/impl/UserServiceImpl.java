package lottery.domains.content.biz.impl;

import admin.web.WebJSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javautils.StringUtil;
import javautils.array.ArrayUtils;
import javautils.date.Moment;
import javautils.encrypt.PasswordUtil;
import javautils.jdbc.PageList;
import javautils.math.MathUtil;
import javautils.redis.JedisTemplate;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserDailySettleService;
import lottery.domains.content.biz.UserDividendService;
import lottery.domains.content.biz.UserService;
import lottery.domains.content.dao.UserBetsDao;
import lottery.domains.content.dao.UserCardDao;
import lottery.domains.content.dao.UserCodeQuotaDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserRegistLinkDao;
import lottery.domains.content.dao.UserSecurityDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.vo.config.CodeConfig;
import lottery.domains.content.vo.config.LotteryConfig;
import lottery.domains.content.vo.config.WithdrawConfig;
import lottery.domains.content.vo.user.UserBaseVO;
import lottery.domains.content.vo.user.UserOnlineVO;
import lottery.domains.content.vo.user.UserProfileVO;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.UserCodePointUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl
  implements UserService
{
  private static final String USER_LOGOUT_MSG = "USER:LOGOUT:MSG:";
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserBetsDao uBetsDao;
  @Autowired
  private UserCardDao uCardDao;
  @Autowired
  private UserCodeQuotaDao uCodeQuotaDao;
  @Autowired
  private UserSecurityDao uSecurityDao;
  @Autowired
  private UserRegistLinkDao uRegistLinkDao;
  @Autowired
  private UserDividendService uDividendService;
  @Autowired
  private UserDailySettleService uDailySettleService;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  @Autowired
  private UserCodePointUtil uCodePointUtil;
  @Autowired
  private UserBillService uBillService;
  @Autowired
  private JedisTemplate jedisTemplate;
  @Autowired
  private LotteryDataFactory dataFactory;
  
  @Transactional(readOnly=true)
  public User getById(int id)
  {
    return this.uDao.getById(id);
  }
  
  @Transactional(readOnly=true)
  public User getByUsername(String username)
  {
    return this.uDao.getByUsername(username);
  }
  
  public boolean aStatus(String username, int status, String message)
  {
    User uBean = this.uDao.getByUsername(username);
    if ((uBean != null) && (uBean.getAStatus() != -2))
    {
      if (uBean.getId() == 72) {
        return false;
      }
      boolean updated = this.uDao.updateAStatus(uBean.getId(), status, message);
      if ((status != 0) && (updated) && (StringUtils.isNotEmpty(uBean.getSessionId()))) {
        kickOutUser(uBean.getId(), uBean.getSessionId());
      }
      return updated;
    }
    return false;
  }
  
  public boolean bStatus(String username, int status, String message)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null) {
      if (uBean.getAStatus() >= 0) {
        return this.uDao.updateBStatus(uBean.getId(), status, message);
      }
    }
    return false;
  }
  
  public boolean modifyLoginPwd(String username, String password)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null)
    {
      String md5Pwd = PasswordUtil.generatePasswordByMD5(password);
      boolean updated = this.uDao.updateLoginPwd(uBean.getId(), md5Pwd);
      if ((updated) && (StringUtils.isNotEmpty(uBean.getSessionId()))) {
        kickOutUser(uBean.getId(), uBean.getSessionId());
      }
      return updated;
    }
    return false;
  }
  
  public boolean modifyWithdrawPwd(String username, String password)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null)
    {
      String md5Pwd = PasswordUtil.generatePasswordByMD5(password);
      boolean flag = this.uDao.updateWithdrawPassword(uBean.getId(), md5Pwd);
      
      return flag;
    }
    return false;
  }
  
  public boolean modifyWithdrawName(String username, String withdrawName)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null)
    {
      boolean flag = this.uDao.updateWithdrawName(uBean.getId(), withdrawName);
      if (flag)
      {
        String lockTime = new Moment().add(3, "days").toSimpleTime();
        this.uDao.updateLockTime(uBean.getId(), lockTime);
        
        this.uCardDao.updateCardName(uBean.getId(), withdrawName);
      }
      return flag;
    }
    return false;
  }
  
  public boolean resetImagePwd(String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null) {
      return this.uDao.updateImgPwd(uBean.getId(), null);
    }
    return false;
  }
  
  public UserProfileVO getUserProfile(String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null)
    {
      List<User> lowerUsers = this.uDao.getUserLower(uBean.getId());
      UserProfileVO pBean = new UserProfileVO(uBean, lowerUsers, this.lotteryDataFactory, this.uCodePointUtil);
      return pBean;
    }
    return null;
  }
  
  public boolean changeLine(int type, String aUser, String bUser)
  {
    User aBean = this.uDao.getByUsername(aUser);
    User bBean = this.uDao.getByUsername(bUser);
    if ((aBean != null) && (bBean != null))
    {
      if (aBean.getId() == 72) {
        return false;
      }
      if ((type == 0) && 
        (aBean.getCode() <= bBean.getCode()))
      {
        List<User> uList = this.uDao.getUserLower(aBean.getId());
        int succ = 0;
        for (User tmpUser : uList)
        {
          int upid = tmpUser.getUpid();
          if (tmpUser.getUpid() == aBean.getId()) {
            upid = bBean.getId();
          }
          String upids = ArrayUtils.deleteInsertIds(tmpUser.getUpids(), aBean.getId(), true);
          if (StringUtil.isNotNull(upids)) {
            upids = upids + ",";
          } else {
            upids = "";
          }
          upids = upids + "[" + bBean.getId() + "]";
          if (bBean.getUpid() != 0) {
            upids = upids + "," + bBean.getUpids();
          }
          boolean flag = this.uDao.updateProxy(tmpUser.getId(), upid, upids);
          if (flag) {
            succ++;
          }
        }
        boolean updated = succ == uList.size();
        if (updated)
        {
          this.uDividendService.checkDividend(aBean.getUsername());
          this.uDailySettleService.checkDailySettle(aBean.getUsername());
        }
        return updated;
      }
      if ((type == 1) && 
        (aBean.getCode() <= bBean.getCode()))
      {
        List<User> uList = this.uDao.getUserLower(aBean.getId());
        uList.add(aBean);
        int succ = 0;
        for (User tmpUser : uList)
        {
          int upid = tmpUser.getUpid();
          if (tmpUser.getId() == aBean.getId()) {
            upid = bBean.getId();
          }
          String upids = ArrayUtils.deleteInsertIds(tmpUser.getUpids(), aBean.getUpid(), true);
          if (StringUtil.isNotNull(upids)) {
            upids = upids + ",";
          } else {
            upids = "";
          }
          upids = upids + "[" + bBean.getId() + "]";
          if (bBean.getUpid() != 0) {
            upids = upids + "," + bBean.getUpids();
          }
          boolean flag = this.uDao.updateProxy(tmpUser.getId(), upid, upids);
          if (flag) {
            succ++;
          }
        }
        boolean updated = succ == uList.size();
        if (updated)
        {
          this.uDividendService.checkDividend(aBean.getUsername());
          this.uDailySettleService.checkDailySettle(aBean.getUsername());
        }
        return updated;
      }
    }
    return false;
  }
  
  public boolean modifyLotteryPoint(String username, int code, double locatePoint, double notLocatePoint)
  {
    if ((code > this.dataFactory.getCodeConfig().getSysCode()) || (code < 1800)) {
      return false;
    }
    if ((code != this.dataFactory.getCodeConfig().getSysCode()) && (code % 2 != 0)) {
      return false;
    }
    if ((code < 1800) || (code > this.dataFactory.getCodeConfig().getSysCode())) {
      return false;
    }
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null)
    {
      if (uBean.getId() == 72) {
        return false;
      }
      int BStatus = uBean.getBStatus();
      if (code <= this.dataFactory.getLotteryConfig().getNotBetPointAccount()) {
        BStatus = 0;
      } else {
        BStatus = -1;
      }
      boolean flag = this.uDao.updateLotteryPoint(uBean.getId(), code, locatePoint, BStatus, notLocatePoint);
      
      return flag;
    }
    return false;
  }
  
  public boolean downLinePoint(String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null)
    {
      if (uBean.getId() == 72) {
        return false;
      }
      List<User> teamList = this.uDao.getUserLower(uBean.getId());
      teamList.add(uBean);
      for (User tmpBean : teamList)
      {
        int code = tmpBean.getCode();
        double locatePoint = tmpBean.getLocatePoint();
        double notLocatePoint = tmpBean.getNotLocatePoint();
        if (locatePoint > 0.0D)
        {
          code -= 2;
          locatePoint -= 0.1D;
          if (notLocatePoint > 0.0D) {
            notLocatePoint -= 0.1D;
          }
          if (code < 1800)
          {
            code = 1800;
            locatePoint = this.uCodePointUtil.getLocatePoint(code);
            notLocatePoint = this.uCodePointUtil.getNotLocatePoint(code);
          }
        }
        int BStatus = tmpBean.getBStatus();
        if (locatePoint <= this.dataFactory.getLotteryConfig().getNotBetPointAccount()) {
          BStatus = 0;
        } else {
          BStatus = -1;
        }
        boolean flag = this.uDao.updateLotteryPoint(tmpBean.getId(), code, locatePoint, BStatus, notLocatePoint);
        if (!flag) {}
      }
      return true;
    }
    return false;
  }
  
  public boolean modifyExtraPoint(String username, double point)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null) {
      return this.uDao.updateExtraPoint(uBean.getId(), point);
    }
    return false;
  }
  
  public boolean modifyEqualCode(String username, int status)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null) {
      return this.uDao.updateAllowEqualCode(uBean.getId(), status);
    }
    return false;
  }
  
  public boolean modifyUserVipLevel(String username, int vipLevel)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null) {
      return this.uDao.updateVipLevel(uBean.getId(), vipLevel);
    }
    return false;
  }
  
  public boolean modifyTransfers(String username, int status)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null) {
      return this.uDao.updateAllowTransfers(uBean.getId(), status);
    }
    return false;
  }
  
  public boolean modifyPlatformTransfers(String username, int status)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null) {
      return this.uDao.updateAllowPlatformTransfers(uBean.getId(), status);
    }
    return false;
  }
  
  public boolean modifyWithdraw(String username, int status)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null) {
      return this.uDao.updateAllowWithdraw(uBean.getId(), status);
    }
    return false;
  }
  
  public boolean changeProxy(String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null)
    {
      if (uBean.getId() == 72) {
        return false;
      }
      if (uBean.getType() == 2)
      {
        boolean updated = this.uDao.updateType(uBean.getId(), 1);
        if (updated)
        {
          this.uDividendService.checkDividend(uBean.getUsername());
          this.uDailySettleService.checkDailySettle(uBean.getUsername());
        }
        return updated;
      }
    }
    return false;
  }
  
  public boolean unbindGoogle(String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null)
    {
      if (uBean.getId() == 72) {
        return false;
      }
      if ((StringUtil.isNotNull(uBean.getSecretKey())) || (uBean.getIsBindGoogle() == 1)) {
        return this.uDao.unbindGoogle(uBean.getId());
      }
    }
    return false;
  }
  
  public boolean resetLockTime(String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if ((uBean != null) && 
      (StringUtil.isNotNull(uBean.getLockTime()))) {
      return this.uDao.resetLockTime(uBean.getId());
    }
    return false;
  }
  
  public boolean modifyQuota(String username, int count1, int count2, int count3)
  {
    return false;
  }
  
  public User recover(String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null)
    {
      if (uBean.getId() == 72) {
        return null;
      }
      uBean.setPassword(PasswordUtil.generatePasswordByPlainString("a123456"));
      uBean.setTotalMoney(0.0D);
      uBean.setLotteryMoney(0.0D);
      uBean.setBaccaratMoney(0.0D);
      uBean.setFreezeMoney(0.0D);
      uBean.setDividendMoney(0.0D);
      uBean.setWithdrawName(null);
      uBean.setWithdrawPassword(null);
      uBean.setMessage("该账户已被管理员回收！");
      uBean.setAStatus(0);
      uBean.setBStatus(0);
      uBean.setLoginValidate(0);
      uBean.setBindStatus(0);
      this.uDao.delete(uBean.getId());
      this.uBetsDao.delete(uBean.getId());
      this.uCardDao.delete(uBean.getId());
      this.uCodeQuotaDao.delete(uBean.getId());
      this.uSecurityDao.delete(uBean.getId());
      this.uRegistLinkDao.delete(uBean.getId());
      if (StringUtils.isNotEmpty(uBean.getSessionId())) {
        kickOutUser(uBean.getId(), uBean.getSessionId());
      }
      this.uDailySettleService.deleteByTeam(uBean.getUsername());
      this.uDividendService.deleteByTeam(uBean.getUsername());
    }
    return uBean;
  }
  
  public boolean addNewUser(WebJSONObject json, String username, String nickname, String password, int upid, String upids, int type, int code, double locatePoint, double notLocatePoint, String relatedUsers)
  {
    if ((code < 1800) || (code > this.dataFactory.getCodeConfig().getSysCode()))
    {
      json.set(Integer.valueOf(2), "2-2026");
      return false;
    }
    if ((code != this.dataFactory.getCodeConfig().getSysCode()) && (code % 2 != 0))
    {
      json.set(Integer.valueOf(2), "2-2026");
      return false;
    }
    String relatedUserIds = null;
    if (type == 3)
    {
      if (StringUtils.isEmpty(relatedUsers))
      {
        json.set(Integer.valueOf(2), "2-2204");
        return false;
      }
      relatedUserIds = convertRelatedUsers(json, relatedUsers);
      if (StringUtils.isEmpty(relatedUserIds)) {
        return false;
      }
    }
    double totalMoney = 0.0D;
    double lotteryMoney = 0.0D;
    double baccaratMoney = 0.0D;
    double freezeMoney = 0.0D;
    double dividendMoney = 0.0D;
    int codeType = 0;
    double extraPoint = 0.0D;
    String registTime = new Moment().toSimpleTime();
    int AStatus = 0;
    int BStatus = 0;
    if (code > this.dataFactory.getLotteryConfig().getNotBetPointAccount()) {
      BStatus = -1;
    }
    int allowEqualCode = 1;
    if (code >= this.dataFactory.getCodeConfig().getNotCreateAccount()) {
      allowEqualCode = -1;
    }
    int onlineStatus = 0;
    int allowTransfers = 1;
    int loginValidate = 0;
    int bindStatus = 0;
    int allowWithdraw = 1;
    int allowPlatformTransfers = 1;
    password = PasswordUtil.generatePasswordByMD5(password);
    int vipLevel = 0;
    double integral = 0.0D;
    User entity = new User(username, password, nickname, totalMoney, lotteryMoney, baccaratMoney, freezeMoney, 
      dividendMoney, type, upid, code, locatePoint, notLocatePoint, codeType, extraPoint, registTime, AStatus, 
      BStatus, onlineStatus, allowEqualCode, allowTransfers, loginValidate, bindStatus, vipLevel, integral, 
      allowWithdraw, allowPlatformTransfers);
    if ((StringUtil.isNotNull(relatedUserIds)) && (type == 3)) {
      entity.setRelatedUsers(relatedUserIds);
    }
    if (this.dataFactory.getWithdrawConfig().getRegisterHours() > 0) {
      entity.setLockTime(
        new Moment().add(this.dataFactory.getWithdrawConfig().getRegisterHours(), "hours").toSimpleTime());
    }
    if (StringUtil.isNotNull(upids)) {
      entity.setUpids(upids);
    }
    boolean flag = this.uDao.add(entity);
    if (flag)
    {
      this.uDividendService.checkDividend(entity.getUsername());
      
      this.uDailySettleService.checkDailySettle(entity.getUsername());
    }
    return flag;
  }
  
  public boolean addLowerUser(WebJSONObject json, User uBean, String username, String nickname, String password, int type, int code, double locatePoint, double notLocatePoint, String relatedUsers)
  {
    if ((code < 1800) || (code > this.dataFactory.getCodeConfig().getSysCode()))
    {
      json.set(Integer.valueOf(2), "2-2026");
      return false;
    }
    if ((code != this.dataFactory.getCodeConfig().getSysCode()) && (code % 2 != 0)) {
      return false;
    }
    int upid = uBean.getId();
    String upids = "[" + upid + "]";
    if (StringUtil.isNotNull(uBean.getUpids())) {
      upids = upids + "," + uBean.getUpids();
    }
    return addNewUser(json, username, nickname, password, upid, upids, type, code, locatePoint, notLocatePoint, 
      relatedUsers);
  }
  
  public PageList search(String username, String matchType, String registTime, Double minMoney, Double maxMoney, Double minLotteryMoney, Double maxLotteryMoney, Integer minCode, Integer maxCode, String sortColoum, String sortType, Integer aStatus, Integer bStatus, Integer onlineStatus, Integer type, String nickname, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    boolean isSearch = true;
    boolean iszz = false;
    if (StringUtil.isNotNull(username))
    {
      User targetUser = this.uDao.getByUsername(username);
      if (targetUser != null)
      {
        if (StringUtil.isNotNull(matchType))
        {
          if ("LOWER".equals(matchType)) {
            criterions.add(Restrictions.eq("upid", Integer.valueOf(targetUser.getId())));
          }
          if ("UPPER".equals(matchType)) {
            if (targetUser.getUpid() != 0) {
              criterions.add(Restrictions.eq("id", Integer.valueOf(targetUser.getUpid())));
            } else {
              isSearch = false;
            }
          }
          if ("TEAM".equals(matchType)) {
            criterions.add(Restrictions.like("upids", "[" + targetUser.getId() + "]", MatchMode.ANYWHERE));
          }
          if ("USER".equals(matchType)) {
            criterions.add(Restrictions.eq("username", username));
          }
          if (targetUser.getId() == 72) {
            iszz = true;
          }
        }
      }
      else
      {
        isSearch = true;
        criterions.add(Restrictions.like("username", "%" + username + "%"));
      }
    }
    if (StringUtil.isNotNull(registTime))
    {
      criterions.add(Restrictions.ge("registTime", registTime + " 00:00:00"));
      criterions.add(Restrictions.le("registTime", registTime + " 23:59:59"));
    }
    if ((type == null) && (!iszz)) {
      criterions.add(Restrictions.not(Restrictions.eq("upid", Integer.valueOf(0))));
    }
    if (minMoney != null) {
      criterions.add(Restrictions.ge("totalMoney", Double.valueOf(minMoney.doubleValue())));
    }
    if (maxMoney != null) {
      criterions.add(Restrictions.le("totalMoney", Double.valueOf(maxMoney.doubleValue())));
    }
    if (minLotteryMoney != null) {
      criterions.add(Restrictions.ge("lotteryMoney", Double.valueOf(minLotteryMoney.doubleValue())));
    }
    if (maxLotteryMoney != null) {
      criterions.add(Restrictions.le("lotteryMoney", Double.valueOf(maxLotteryMoney.doubleValue())));
    }
    if (minCode != null) {
      criterions.add(Restrictions.ge("code", Integer.valueOf(minCode.intValue())));
    }
    if (maxCode != null) {
      criterions.add(Restrictions.le("code", Integer.valueOf(maxCode.intValue())));
    }
    if (aStatus != null) {
      criterions.add(Restrictions.eq("AStatus", Integer.valueOf(aStatus.intValue())));
    }
    if (bStatus != null) {
      criterions.add(Restrictions.eq("BStatus", Integer.valueOf(bStatus.intValue())));
    }
    if (onlineStatus != null) {
      criterions.add(Restrictions.eq("onlineStatus", Integer.valueOf(onlineStatus.intValue())));
    }
    if (type != null) {
      criterions.add(Restrictions.eq("type", Integer.valueOf(type.intValue())));
    }
    if (StringUtil.isNotNull(sortColoum)) {
      if ("DESC".equals(sortType)) {
        orders.add(Order.desc(sortColoum));
      } else {
        orders.add(Order.asc(sortColoum));
      }
    }
    orders.add(Order.desc("registTime"));
    if (isSearch)
    {
      List<UserBaseVO> list = new ArrayList();
      PageList plist = this.uDao.search(criterions, orders, start, limit);
      for (Object tmpBean : plist.getList()) {
        list.add(new UserBaseVO((User)tmpBean));
      }
      plist.setList(list);
      return plist;
    }
    return null;
  }
  
  public PageList listOnline(String sortColoum, String sortType, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    criterions.add(Restrictions.eq("onlineStatus", Integer.valueOf(1)));
    criterions.add(Restrictions.not(Restrictions.eq("upid", Integer.valueOf(0))));
    if (StringUtil.isNotNull(sortColoum)) {
      if ("DESC".equals(sortType)) {
        orders.add(Order.desc(sortColoum));
      } else {
        orders.add(Order.asc(sortColoum));
      }
    }
    orders.add(Order.desc("registTime"));
    List<UserOnlineVO> list = new ArrayList();
    PageList plist = this.uDao.search(criterions, orders, start, limit);
    for (Object tmpBean : plist.getList()) {
      list.add(new UserOnlineVO((User)tmpBean, this.lotteryDataFactory));
    }
    plist.setList(list);
    return plist;
  }
  
  public boolean kickOutUser(int userId, String sessionId)
  {
    if (StringUtils.isNotEmpty(sessionId))
    {
      String sessionKey = "spring:session:sessions:" + sessionId;
      this.jedisTemplate.del(new String[] { sessionKey });
    }
    this.uDao.updateOffline(userId);
    
    return true;
  }
  
  public boolean modifyRelatedUpper(WebJSONObject json, String username, String relatedUpUser, double relatedPoint)
  {
    if ((relatedPoint < 0.0D) || (relatedPoint > 1.0D))
    {
      json.set(Integer.valueOf(2), "2-2209");
      return false;
    }
    if (StringUtils.equalsIgnoreCase(username, relatedUpUser))
    {
      json.set(Integer.valueOf(2), "2-2210");
      return false;
    }
    User user = this.uDao.getByUsername(username);
    if (user == null)
    {
      json.set(Integer.valueOf(2), "2-2201");
      return false;
    }
    if (user.getId() == 72)
    {
      json.set(Integer.valueOf(2), "2-33");
      return false;
    }
    User relatedUp = this.uDao.getByUsername(relatedUpUser);
    if (relatedUp == null)
    {
      json.set(Integer.valueOf(2), "2-2202");
      return false;
    }
    if (relatedUp.getId() == 72)
    {
      json.set(Integer.valueOf(2), "2-33");
      return false;
    }
    if ((StringUtils.isEmpty(user.getUpids())) || (StringUtils.isEmpty(relatedUp.getUpids())))
    {
      json.set(Integer.valueOf(2), "2-38");
      return false;
    }
    if ((user.getUpids().indexOf("[" + relatedUp.getId() + "]") > -1) || 
      (relatedUp.getUpids().indexOf("[" + user.getId() + "]") > -1))
    {
      json.set(Integer.valueOf(2), "2-2205");
      return false;
    }
    if ((user.getRelatedUpid() == relatedUp.getId()) && (user.getRelatedPoint() == relatedPoint))
    {
      json.set(Integer.valueOf(2), "2-29");
      return false;
    }
    if (relatedUp.getRelatedUpid() == user.getId())
    {
      json.set(Integer.valueOf(2), "2-2211");
      return false;
    }
    boolean updated = this.uDao.updateRelatedUpper(user.getId(), relatedUp.getId(), relatedPoint);
    
    String relatedLowers = ArrayUtils.addId(relatedUp.getRelatedLowers(), user.getId());
    this.uDao.updateRelatedLowers(relatedUp.getId(), relatedLowers);
    
    return updated;
  }
  
  public boolean reliveRelatedUpper(WebJSONObject json, String username)
  {
    User user = this.uDao.getByUsername(username);
    if (user == null)
    {
      json.set(Integer.valueOf(2), "2-2201");
      return false;
    }
    this.uDao.updateRelatedUpper(user.getId(), 0, 0.0D);
    
    int relatedUpid = user.getRelatedUpid();
    User upUser = this.uDao.getById(relatedUpid);
    if (upUser != null)
    {
      String relatedLowers = upUser.getRelatedLowers();
      relatedLowers = ArrayUtils.deleteInsertIds(relatedLowers, user.getId(), false);
      this.uDao.updateRelatedLowers(upUser.getId(), relatedLowers);
    }
    return true;
  }
  
  public boolean modifyRelatedUsers(WebJSONObject json, String username, String relatedUsers)
  {
    User user = this.uDao.getByUsername(username);
    if (user == null)
    {
      json.set(Integer.valueOf(2), "2-2201");
      return false;
    }
    if (user.getType() != 3)
    {
      json.set(Integer.valueOf(2), "2-2206");
      return false;
    }
    String relatedUserIds = convertRelatedUsers(json, relatedUsers);
    if (relatedUserIds == null) {
      return false;
    }
    if (StringUtils.equalsIgnoreCase(relatedUserIds, user.getRelatedUsers()))
    {
      json.set(Integer.valueOf(2), "2-29");
      return false;
    }
    boolean updated = this.uDao.updateRelatedUsers(user.getId(), relatedUserIds);
    
    return updated;
  }
  
  private String convertRelatedUsers(WebJSONObject json, String relatedUsers)
  {
    String[] relatedUserNames = relatedUsers.split(",");
    if ((relatedUserNames == null) || (relatedUserNames.length <= 0))
    {
      json.set(Integer.valueOf(2), "2-2204");
      return null;
    }
    if (relatedUserNames.length > 10)
    {
      json.setWithParams(Integer.valueOf(2), "2-2207", new Object[] { Integer.valueOf(10) });
      return null;
    }
    if (ArrayUtils.hasRepeat(relatedUserNames))
    {
      json.set(Integer.valueOf(2), "2-28");
      return null;
    }
    StringBuilder relatedUserIds = new StringBuilder();
    List<User> users = new ArrayList();
    String relatedUserName;
    for (int i = 0; i < relatedUserNames.length; i++)
    {
      relatedUserName = relatedUserNames[i].trim();
      User user = this.uDao.getByUsername(relatedUserName);
      if (user == null)
      {
        json.setWithParams(Integer.valueOf(2), "2-2212", new Object[] { relatedUserName });
        return null;
      }
      if ((user.getId() == 72) || (user.getUpid() == 0))
      {
        json.setWithParams(Integer.valueOf(2), "2-2203", new Object[] { relatedUserName });
        return null;
      }
      if (!this.uCodePointUtil.isLevel1Proxy(user))
      {
        json.setWithParams(Integer.valueOf(2), "2-2208", new Object[] { relatedUserName });
        return null;
      }
      relatedUserIds.append("[").append(user.getId()).append("]");
      if (i < relatedUserNames.length - 1) {
        relatedUserIds.append(",");
      }
      users.add(user);
    }
    for (User subUser : users) {
      if (!StringUtils.isEmpty(subUser.getUpids())) {
        for (User upUser : users) {
          if (subUser.getUpids().indexOf("[" + upUser.getId() + "]") > -1)
          {
            json.setWithParams(Integer.valueOf(2), "2-2213", new Object[] { upUser.getUsername(), subUser.getUsername() });
            return null;
          }
        }
      }
    }
    return relatedUserIds.toString();
  }
  
  public boolean lockTeam(WebJSONObject json, String username, String remark)
  {
    User uBean = this.uDao.getByUsername(username);
    if ((uBean == null) || (uBean.getId() == 72)) {
      return false;
    }
    boolean updated = this.uDao.lockTeam(uBean.getId(), -1, remark);
    if (StringUtils.isNotEmpty(uBean.getSessionId())) {
      kickOutUser(uBean.getId(), uBean.getSessionId());
    }
    return updated;
  }
  
  public boolean unLockTeam(WebJSONObject json, String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if ((uBean == null) || (uBean.getId() == 72)) {
      return false;
    }
    boolean updated = this.uDao.unLockTeam(uBean.getId(), 0);
    return updated;
  }
  
  public boolean prohibitTeamWithdraw(WebJSONObject json, String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if ((uBean == null) || (uBean.getId() == 72)) {
      return false;
    }
    boolean updated = this.uDao.prohibitTeamWithdraw(uBean.getId(), -1);
    return updated;
  }
  
  public boolean allowTeamWithdraw(WebJSONObject json, String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if ((uBean == null) || (uBean.getId() == 72)) {
      return false;
    }
    boolean updated = this.uDao.allowTeamWithdraw(uBean.getId(), 1);
    return updated;
  }
  
  public boolean allowTeamTransfers(WebJSONObject json, String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if ((uBean == null) || (uBean.getId() == 72)) {
      return false;
    }
    boolean updated = this.uDao.allowTeamTransfers(uBean.getId(), 1);
    return updated;
  }
  
  public boolean prohibitTeamTransfers(WebJSONObject json, String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if ((uBean == null) || (uBean.getId() == 72)) {
      return false;
    }
    boolean updated = this.uDao.prohibitTeamTransfers(uBean.getId(), -1);
    return updated;
  }
  
  public boolean allowTeamPlatformTransfers(WebJSONObject json, String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if ((uBean == null) || (uBean.getId() == 72)) {
      return false;
    }
    boolean updated = this.uDao.allowTeamPlatformTransfers(uBean.getId(), 1);
    return updated;
  }
  
  public boolean prohibitTeamPlatformTransfers(WebJSONObject json, String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if ((uBean == null) || (uBean.getId() == 72)) {
      return false;
    }
    boolean updated = this.uDao.prohibitTeamPlatformTransfers(uBean.getId(), -1);
    return updated;
  }
  
  public boolean transfer(WebJSONObject json, User aUser, User bUser, double money, String remarks)
  {
    double balance = 0.0D;
    if (aUser.getTotalMoney() > 0.0D) {
      balance = MathUtil.add(balance, aUser.getTotalMoney());
    }
    if (aUser.getLotteryMoney() > 0.0D) {
      balance = MathUtil.add(balance, aUser.getLotteryMoney());
    }
    if (balance <= 0.0D)
    {
      json.setWithParams(Integer.valueOf(2), "2-2028", new Object[] { "0" });
      return false;
    }
    if (money > balance)
    {
      String balanceStr = MathUtil.doubleToStringDown(balance, 1);
      json.setWithParams(Integer.valueOf(2), "2-2028", new Object[] { balanceStr });
      return false;
    }
    double remain = money;
    String billDesc = "管理员转账：" + aUser.getUsername() + "转入" + bUser.getUsername() + " 备注：" + remarks;
    if ((remain > 0.0D) && (aUser.getLotteryMoney() > 0.0D))
    {
      double lotteryMoney = aUser.getLotteryMoney() > remain ? remain : aUser.getLotteryMoney();
      boolean uFlag = this.uDao.updateLotteryMoney(aUser.getId(), -lotteryMoney);
      if (uFlag)
      {
        this.uBillService.addAdminMinusBill(aUser, 2, lotteryMoney, billDesc);
        remain = lotteryMoney >= remain ? 0.0D : MathUtil.subtract(remain, lotteryMoney);
      }
    }
    if ((remain > 0.0D) && (aUser.getTotalMoney() > 0.0D))
    {
      double totalMoney = aUser.getTotalMoney() > remain ? remain : aUser.getTotalMoney();
      boolean uFlag = this.uDao.updateTotalMoney(aUser.getId(), -totalMoney);
      if (uFlag)
      {
        this.uBillService.addAdminMinusBill(aUser, 1, totalMoney, billDesc);
        remain = totalMoney >= remain ? 0.0D : MathUtil.subtract(remain, totalMoney);
      }
    }
    if (remain <= 0.0D)
    {
      boolean uFlag = this.uDao.updateLotteryMoney(bUser.getId(), money);
      if (uFlag)
      {
        this.uBillService.addAdminAddBill(bUser, 2, money, billDesc);
        return true;
      }
    }
    json.set(Integer.valueOf(1), "1-5");
    return false;
  }
  
  public boolean changeZhaoShang(WebJSONObject json, String username, int isCJZhaoShang)
  {
    User user = this.uDao.getByUsername(username);
    if (!this.uCodePointUtil.isLevel2Proxy(user))
    {
      json.set(Integer.valueOf(2), "2-3013");
      return false;
    }
    if (isCJZhaoShang != user.getIsCjZhaoShang())
    {
      json.set(Integer.valueOf(2), "2-29");
      return false;
    }
    if (isCJZhaoShang == 1)
    {
      this.uDailySettleService.changeZhaoShang(user, false);
      
      this.uDividendService.changeZhaoShang(user, false);
      
      return this.uDao.changeZhaoShang(user.getId(), 0);
    }
    this.uDailySettleService.changeZhaoShang(user, true);
    
    this.uDividendService.changeZhaoShang(user, true);
    
    return this.uDao.changeZhaoShang(user.getId(), 1);
  }
  
  public List<String> getUserLevels(User user)
  {
    List<String> userLevels = new ArrayList();
    userLevels.add(user.getUsername());
    if (StringUtil.isNotNull(user.getUpids()))
    {
      String[] ids = user.getUpids().replaceAll("\\[|\\]", "").split(",");
      String[] arrayOfString1;
      int j = (arrayOfString1 = ids).length;
      for (int i = 0; i < j; i++)
      {
        String upId = arrayOfString1[i];
        UserVO thisUser = this.dataFactory.getUser(Integer.parseInt(upId));
        if (thisUser != null) {
          userLevels.add(thisUser.getUsername());
        }
      }
    }
    return userLevels;
  }
  
  public List<User> findNeibuZhaoShang()
  {
    List<Criterion> criterions = new ArrayList();
    
    criterions.add(Restrictions.eq("upid", Integer.valueOf(72)));
    criterions.add(Restrictions.not(Restrictions.in("AStatus", Arrays.asList(new Integer[] { Integer.valueOf(-2), Integer.valueOf(-3) }))));
    criterions.add(Restrictions.not(Restrictions.eq("upid", Integer.valueOf(0))));
    
    List<User> users = this.uDao.list(criterions, null);
    
    return users;
  }
  
  public List<User> findZhaoShang(List<User> neibuZhaoShangs)
  {
    List<Integer> neibuZhaoShangIds = new ArrayList();
    for (User neibuZhaoShang : neibuZhaoShangs) {
      neibuZhaoShangIds.add(Integer.valueOf(neibuZhaoShang.getId()));
    }
    List<Criterion> criterions = new ArrayList();
    
    criterions.add(Restrictions.in("upid", neibuZhaoShangIds));
    criterions.add(Restrictions.not(Restrictions.eq("upid", Integer.valueOf(0))));
    criterions.add(Restrictions.not(Restrictions.in("AStatus", Arrays.asList(new Integer[] { Integer.valueOf(-2), Integer.valueOf(-3) }))));
    
    Object users = this.uDao.list(criterions, null);
    
    return (List<User>)users;
  }
  
  public List<User> getUserDirectLower(int userId)
  {
    List<User> result = new ArrayList();
    User user1 = this.uDao.getById(userId);
    if (userId == 72)
    {
      List<User> temp = this.uDao.getUserDirectLower(userId);
      List<User> temp2 = new ArrayList();
      for (int i = 0; i < temp.size(); i++) {
        temp2.addAll(this.uDao.getUserDirectLower(((User)temp.get(i)).getId()));
      }
      for (int i = 0; i < temp2.size(); i++) {
        result.addAll(this.uDao.getUserDirectLower(((User)temp2.get(i)).getId()));
      }
    }
    else if (this.uCodePointUtil.isLevel1Proxy(user1))
    {
      List<User> temp = this.uDao.getUserDirectLower(userId);
      for (int i = 0; i < temp.size(); i++) {
        result.addAll(this.uDao.getUserDirectLower(((User)temp.get(i)).getId()));
      }
    }
    else
    {
      result.addAll(this.uDao.getUserDirectLower(userId));
    }
    return result;
  }
}
