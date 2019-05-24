package lottery.domains.content.biz.impl;

import admin.web.WebJSONObject;
import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.date.Moment;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.UserDailySettleService;
import lottery.domains.content.biz.UserService;
import lottery.domains.content.dao.UserDailySettleDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserDailySettle;
import lottery.domains.content.vo.config.DailySettleConfig;
import lottery.domains.content.vo.user.UserDailySettleVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.UserCodePointUtil;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDailySettleServiceImpl
  implements UserDailySettleService
{
  private static final Logger log = LoggerFactory.getLogger(UserDailySettleServiceImpl.class);
  @Autowired
  private UserDailySettleDao uDailySettleDao;
  @Autowired
  private LotteryDataFactory dataFactory;
  @Autowired
  private UserService uService;
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserCodePointUtil uCodePointUtil;
  
  public PageList search(List<Integer> userIds, String sTime, String eTime, Double minScale, Double maxScale, Integer minValidUser, Integer maxValidUser, Integer status, int start, int limit)
  {
    start = start < 0 ? 0 : start;
    limit = limit < 0 ? 0 : limit;
    limit = limit > 20 ? 20 : limit;
    
    List<Criterion> criterions = new ArrayList();
    if (CollectionUtils.isNotEmpty(userIds)) {
      criterions.add(Restrictions.in("userId", userIds));
    }
    if (StringUtil.isNotNull(sTime)) {
      criterions.add(Restrictions.ge("agreeTime", sTime));
    }
    if (StringUtil.isNotNull(eTime)) {
      criterions.add(Restrictions.le("agreeTime", eTime));
    }
    if (minScale != null) {
      criterions.add(Restrictions.ge("scale", minScale));
    }
    if (maxScale != null) {
      criterions.add(Restrictions.le("scale", maxScale));
    }
    if (minValidUser != null) {
      criterions.add(Restrictions.ge("minValidUser", minValidUser));
    }
    if (maxValidUser != null) {
      criterions.add(Restrictions.le("minValidUser", maxValidUser));
    }
    if (status != null) {
      criterions.add(Restrictions.eq("status", status));
    }
    List<Order> orders = new ArrayList();
    orders.add(Order.desc("id"));
    PageList pList = this.uDailySettleDao.search(criterions, orders, start, limit);
    List<UserDailySettleVO> convertList = new ArrayList();
    if ((pList != null) && (pList.getList() != null)) {
      for (Object tmpBean : pList.getList()) {
        convertList.add(new UserDailySettleVO((UserDailySettle)tmpBean, this.dataFactory));
      }
    }
    pList.setList(convertList);
    return pList;
  }
  
  public UserDailySettle getByUserId(int userId)
  {
    return this.uDailySettleDao.getByUserId(userId);
  }
  
  public UserDailySettle getById(int id)
  {
    return this.uDailySettleDao.getById(id);
  }
  
  private boolean add(User user, String scaleLevel, String salasLevel, String lossLevel, int minValidUser, int status, int fixed, double minScale, double maxScale, String usersLevel)
  {
    UserDailySettle bean = this.uDailySettleDao.getByUserId(user.getId());
    if (bean == null)
    {
      int userId = user.getId();
      Moment moment = new Moment();
      String createTime = moment.toSimpleTime();
      String createDate = moment.toSimpleDate();
      String endDate = moment.add(99, "years").toSimpleDate();
      double totalAmount = 0.0D;
      UserDailySettle entity = new UserDailySettle(userId, scaleLevel, lossLevel, salasLevel, minValidUser, createTime, createTime, createDate, endDate, totalAmount, status, fixed, minScale, maxScale, usersLevel);
      this.uDailySettleDao.add(entity);
      return true;
    }
    return false;
  }
  
  public boolean deleteByTeam(String username)
  {
    User uBean = this.uService.getByUsername(username);
    if (uBean != null)
    {
      this.uDailySettleDao.deleteByTeam(uBean.getId());
      return true;
    }
    return false;
  }
  
  public boolean update(WebJSONObject json, int id, String scaleLevel, String salesLevel, String lossLevel, int minValidUser, String usersLevel)
  {
    UserDailySettle dailySettle = this.uDailySettleDao.getById(id);
    if (dailySettle == null)
    {
      json.set(Integer.valueOf(1), "1-7");
      return false;
    }
    if ((dailySettle.getScaleLevel().equals(scaleLevel)) && (dailySettle.getSalesLevel().equals(salesLevel)) && 
      (dailySettle.getLossLevel().equals(lossLevel)) && (dailySettle.getUserLevel() == usersLevel))
    {
      json.set(Integer.valueOf(1), "2-29");
      return false;
    }
    User user = this.uDao.getById(dailySettle.getUserId());
    if (!checkCanEdit(json, user)) {
      return false;
    }
    UserDailySettle upDailySettle = this.uDailySettleDao.getByUserId(user.getUpid());
    if ((!this.uCodePointUtil.isLevel3Proxy(user)) && (upDailySettle == null))
    {
      json.setWithParams(Integer.valueOf(2), "2-3008", new Object[0]);
      return false;
    }
    if (!checkValidLevel(scaleLevel, salesLevel, lossLevel, upDailySettle, usersLevel))
    {
      json.setWithParams(Integer.valueOf(2), "2-3009", new Object[0]);
      return false;
    }
    double[] minMaxScale = getMinMaxScale(user);
    double minScale = minMaxScale[0];
    double maxScale = minMaxScale[1];
    
    String[] scaleLevels = scaleLevel.split(",");
    if ((Double.valueOf(scaleLevels[0]).doubleValue() < minScale) || (Double.valueOf(scaleLevels[(scaleLevels.length - 1)]).doubleValue() > maxScale))
    {
      json.setWithParams(Integer.valueOf(2), "2-3006", new Object[0]);
      return false;
    }
    double[] minMaxSales = getMinMaxSales(user);
    double minSales = minMaxSales[0];
    double maxSales = minMaxSales[1];
    String[] salesLevels = salesLevel.split(",");
    if ((Double.valueOf(salesLevels[0]).doubleValue() < minSales) || (Double.valueOf(salesLevels[(salesLevels.length - 1)]).doubleValue() > maxSales))
    {
      json.setWithParams(Integer.valueOf(2), "2-3006", new Object[0]);
      return false;
    }
    double[] minMaxLoss = getMinMaxLoss(user);
    double minLoss = minMaxLoss[0];
    double maxLoss = minMaxLoss[1];
    String[] lossLevels = lossLevel.split(",");
    if ((Double.valueOf(lossLevels[0]).doubleValue() < minLoss) || (Double.valueOf(lossLevels[(lossLevels.length - 1)]).doubleValue() > maxLoss))
    {
      json.setWithParams(Integer.valueOf(2), "2-3006", new Object[0]);
      return false;
    }
    int[] minMaxUser = getMinMaxUsers(user);
    int minUser = minMaxUser[0];
    int maxUser = minMaxUser[1];
    String[] userLevels = usersLevel.split(",");
    if ((Integer.valueOf(userLevels[0]).intValue() < minUser) || (Integer.valueOf(userLevels[(userLevels.length - 1)]).intValue() > maxUser))
    {
      json.setWithParams(Integer.valueOf(2), "2-3006", new Object[0]);
      return false;
    }
    return this.uDailySettleDao.updateSomeFields(id, scaleLevel, lossLevel, salesLevel, minValidUser, usersLevel);
  }
  
  public boolean add(WebJSONObject json, String username, String scaleLevel, String salesLevel, String lossLevel, int minValidUser, int status, String usersLevel)
  {
    User user = this.uDao.getByUsername(username);
    if (!checkCanEdit(json, user)) {
      return false;
    }
    UserDailySettle dailySettle = this.uDailySettleDao.getByUserId(user.getId());
    if (dailySettle != null)
    {
      json.set(Integer.valueOf(2), "2-3007");
      return false;
    }
    UserDailySettle upDailySettle = this.uDailySettleDao.getByUserId(user.getUpid());
    if ((!this.uCodePointUtil.isLevel3Proxy(user)) && (upDailySettle == null))
    {
      json.set(Integer.valueOf(2), "2-3008");
      return false;
    }
    if (!checkValidLevel(scaleLevel, salesLevel, lossLevel, upDailySettle, usersLevel))
    {
      json.setWithParams(Integer.valueOf(2), "2-3009", new Object[0]);
      return false;
    }
    double[] minMaxScale = getMinMaxScale(user);
    double minScale = minMaxScale[0];
    double maxScale = minMaxScale[1];
    
    String[] scaleLevels = scaleLevel.split(",");
    if ((Double.valueOf(scaleLevels[0]).doubleValue() < minScale) || (Double.valueOf(scaleLevels[(scaleLevels.length - 1)]).doubleValue() > maxScale))
    {
      json.setWithParams(Integer.valueOf(2), "2-3006", new Object[0]);
      return false;
    }
    double[] minMaxSales = getMinMaxSales(user);
    double minSales = minMaxSales[0];
    double maxSales = minMaxSales[1];
    String[] salesLevels = salesLevel.split(",");
    if ((Double.valueOf(salesLevels[0]).doubleValue() < minSales) || (Double.valueOf(salesLevels[(salesLevels.length - 1)]).doubleValue() > maxSales))
    {
      json.setWithParams(Integer.valueOf(2), "2-3006", new Object[0]);
      return false;
    }
    double[] minMaxLoss = getMinMaxLoss(user);
    double minLoss = minMaxLoss[0];
    double maxLoss = minMaxLoss[1];
    String[] lossLevels = lossLevel.split(",");
    if ((Double.valueOf(lossLevels[0]).doubleValue() < minLoss) || (Double.valueOf(lossLevels[(lossLevels.length - 1)]).doubleValue() > maxLoss))
    {
      json.setWithParams(Integer.valueOf(2), "2-3006", new Object[0]);
      return false;
    }
    int[] minMaxUser = getMinMaxUsers(user);
    int minUser = minMaxUser[0];
    int maxUser = minMaxUser[1];
    String[] userLevels = usersLevel.split(",");
    if ((Integer.valueOf(userLevels[0]).intValue() < minUser) || (Integer.valueOf(userLevels[(userLevels.length - 1)]).intValue() > maxUser))
    {
      json.setWithParams(Integer.valueOf(2), "2-3006", new Object[0]);
      return false;
    }
    String[] scaleArrs = scaleLevel.split(",");
    minScale = Double.valueOf(scaleArrs[0]).doubleValue();
    maxScale = Double.valueOf(scaleArrs[(scaleArrs.length - 1)]).doubleValue();
    
    return add(user, scaleLevel, salesLevel, lossLevel, minValidUser, status, 1, minScale, maxScale, usersLevel);
  }
  
  public double[] getMinMaxScale(User acceptUser)
  {
    double minScale = this.dataFactory.getDailySettleConfig().getLevelsScale()[0];
    
    double maxScale = this.dataFactory.getDailySettleConfig().getLevelsScale()[1];
    if (this.uCodePointUtil.isLevel3Proxy(acceptUser)) {
      return new double[] { minScale, maxScale };
    }
    User requestUser = this.uService.getById(acceptUser.getUpid());
    UserDailySettle upDailySettle = this.uDailySettleDao.getByUserId(requestUser.getId());
    if (upDailySettle == null) {
      return new double[] { 0.0D, 0.0D };
    }
    String[] scaleLevel = upDailySettle.getScaleLevel().split(",");
    if (Double.valueOf(scaleLevel[(scaleLevel.length - 1)]).doubleValue() <= minScale) {
      return new double[] { 0.0D, 0.0D };
    }
    maxScale = Double.valueOf(scaleLevel[(scaleLevel.length - 1)]).doubleValue();
    if (maxScale > this.dataFactory.getDailySettleConfig().getLevelsScale()[1]) {
      maxScale = this.dataFactory.getDailySettleConfig().getLevelsScale()[1];
    }
    if (minScale < 0.0D) {
      minScale = 0.0D;
    }
    if (maxScale < 0.0D) {
      maxScale = 0.0D;
    }
    return new double[] { minScale, maxScale };
  }
  
  public boolean checkCanEdit(WebJSONObject json, User user)
  {
    if (this.uCodePointUtil.isLevel1Proxy(user))
    {
      json.set(Integer.valueOf(2), "2-36");
      return false;
    }
    if (this.uCodePointUtil.isLevel2Proxy(user))
    {
      json.set(Integer.valueOf(2), "2-39");
      return false;
    }
    boolean checked = checkForRequest(user);
    if (!checked)
    {
      json.set(Integer.valueOf(2), "2-3012");
      return false;
    }
    return true;
  }
  
  public boolean checkCanDel(WebJSONObject json, User user)
  {
    if (user.getId() == 72)
    {
      json.set(Integer.valueOf(2), "2-33");
      return false;
    }
    if (this.uCodePointUtil.isLevel1Proxy(user))
    {
      json.set(Integer.valueOf(2), "2-36");
      return false;
    }
    if (this.uCodePointUtil.isLevel2Proxy(user))
    {
      json.set(Integer.valueOf(2), "2-39");
      return false;
    }
    return true;
  }
  
  private boolean checkForRequest(User acceptUser)
  {
    User requestUser = this.uService.getById(acceptUser.getUpid());
    if (!allowRequestByUser(requestUser)) {
      return false;
    }
    if (!allowAccept(requestUser, acceptUser)) {
      return false;
    }
    return true;
  }
  
  public boolean allowRequestByUser(User user)
  {
    if (!this.dataFactory.getDailySettleConfig().isEnable()) {
      return false;
    }
    if (user.getId() == 72) {
      return false;
    }
    if ((this.uCodePointUtil.isLevel1Proxy(user)) || (user.getCode() < 1800)) {
      return false;
    }
    if (user.getCode() < 1800) {
      return false;
    }
    return true;
  }
  
  public boolean allowAccept(User requestUser, User acceptUser)
  {
    if (acceptUser.getUpid() != requestUser.getId()) {
      return false;
    }
    if ((requestUser.getId() == 72) || (acceptUser.getId() == 72)) {
      return false;
    }
    if ((this.uCodePointUtil.isLevel1Proxy(requestUser)) || (this.uCodePointUtil.isLevel1Proxy(acceptUser))) {
      return false;
    }
    if ((requestUser.getCode() < 1800) || (acceptUser.getCode() < 1800)) {
      return false;
    }
    if (this.uCodePointUtil.isLevel2Proxy(requestUser))
    {
      if (this.uCodePointUtil.isLevel3Proxy(acceptUser)) {
        return true;
      }
      return false;
    }
    return true;
  }
  
  public boolean changeZhaoShang(User user, boolean changeToCJZhaoShang)
  {
    return true;
  }
  
  public void checkDailySettle(String username)
  {
    User user = this.uDao.getByUsername(username);
    if (user.getId() == 72) {
      return;
    }
    this.uDailySettleDao.deleteByTeam(user.getId());
  }
  
  public boolean checkValidLevel(String scaleLevel, String salesLevel, String lossLevel, UserDailySettle upDailySettle, String usersLevel)
  {
    if ((!StringUtil.isNotNull(scaleLevel)) || (!StringUtil.isNotNull(salesLevel)) || (!StringUtil.isNotNull(lossLevel)) || (!StringUtil.isNotNull(usersLevel))) {
      return false;
    }
    if (upDailySettle == null) {
      return checkStartDailyLevel(scaleLevel, salesLevel, lossLevel, usersLevel);
    }
    String[] scaleArrs = scaleLevel.split(",");
    String[] upScaleArrs = upDailySettle.getScaleLevel().split(",");
    
    String[] salesArrs = salesLevel.split(",");
    String[] upSalesArrs = upDailySettle.getSalesLevel().split(",");
    
    String[] lossArrs = lossLevel.split(",");
    String[] upLossArrs = upDailySettle.getLossLevel().split(",");
    
    String[] userArrs = usersLevel.split(",");
    String[] upUserArrs = upDailySettle.getUserLevel().split(",");
    
    int maxLength = this.dataFactory.getDailySettleConfig().getMaxSignLevel();
    if ((scaleArrs.length > maxLength) || (salesArrs.length > maxLength) || (lossArrs.length > maxLength) || (userArrs.length > maxLength)) {
      return false;
    }
    if ((scaleArrs.length != salesArrs.length) || (scaleArrs.length != lossArrs.length) || 
      (salesArrs.length != lossArrs.length) || (userArrs.length != scaleArrs.length)) {
      return false;
    }
    double[] scaleConfig = this.dataFactory.getDailySettleConfig().getLevelsScale();
    int upIndex;
    double tmUpSales;
    double tmUpScale;
    for (int i = 0; i < scaleArrs.length; i++)
    {
      double val = Double.valueOf(scaleArrs[i]).doubleValue();
      if (upDailySettle != null)
      {
        if ((val < scaleConfig[0]) || (val > Double.valueOf(upScaleArrs[(upScaleArrs.length - 1)]).doubleValue())) {
          return false;
        }
      }
      else if ((val < scaleConfig[0]) || (val > scaleConfig[1])) {
        return false;
      }
      if ((i > 0) && (Double.valueOf(scaleArrs[(i - 1)]).doubleValue() >= val)) {
        return false;
      }
      double tmSales = Double.valueOf(salesArrs[i]).doubleValue();
      double tmLoss = Double.valueOf(lossArrs[i]).doubleValue();
      
      upIndex = -1;
      for (int j = 0; j < upScaleArrs.length; j++)
      {
        tmUpSales = Double.valueOf(upSalesArrs[j]).doubleValue();
        double tmUpLoss = Double.valueOf(upLossArrs[j]).doubleValue();
        if ((tmSales >= tmUpSales) && (tmLoss >= tmUpLoss)) {
          upIndex = j;
        }
      }
      if (upIndex == -1) {
        return false;
      }
      tmUpScale = Double.valueOf(upScaleArrs[upIndex]).doubleValue();
      if (val > tmUpScale)
      {
        upIndex++;
        if (upIndex >= upScaleArrs.length) {
          return false;
        }
         tmUpSales = Double.valueOf(upSalesArrs[upIndex]).doubleValue();
        double tmUpLoss = Double.valueOf(upLossArrs[upIndex]).doubleValue();
        if ((tmSales >= tmUpSales) && (tmLoss >= tmUpLoss))
        {
          tmUpScale = Double.valueOf(upScaleArrs[upIndex]).doubleValue();
          if (val > tmUpScale) {
            return false;
          }
        }
        else
        {
          return false;
        }
      }
    }
    double[] salesConfig = this.dataFactory.getDailySettleConfig().getLevelsSales();
    double ll;
    for (int i = 0; i < salesArrs.length; i++)
    {
      double val = Double.valueOf(salesArrs[i]).doubleValue();
      if (upDailySettle != null)
      {
        double minSales = Double.valueOf(upSalesArrs[0]).doubleValue();
        tmUpScale = upLossArrs.length;
        for (upIndex = 0; upIndex < tmUpScale; upIndex++)
        {
          String l = upLossArrs[upIndex];
          ll = Double.valueOf(l).doubleValue();
          if (ll < minSales) {
            minSales = ll;
          }
        }
        if ((val < minSales) || (val > salesConfig[1])) {
          return false;
        }
      }
      else if ((val < salesConfig[0]) || (val > salesConfig[1]))
      {
        return false;
      }
    }
    double[] lossConfig = this.dataFactory.getDailySettleConfig().getLevelsLoss();
    String l;
    for (int i = 0; i < lossArrs.length; i++)
    {
      double val = Double.valueOf(lossArrs[i]).doubleValue();
      if (upDailySettle != null)
      {
        double minLoss = Double.valueOf(upLossArrs[0]).doubleValue();

        tmUpSales = upLossArrs.length;
        for (tmUpScale = 0; tmUpScale < tmUpSales; tmUpScale++)
        {
          l = upLossArrs[(int)tmUpScale];
          double llValue = Double.valueOf(l).doubleValue();
          if (llValue < minLoss) {
            minLoss = llValue;
          }
        }
        if ((val < minLoss) || (val > lossConfig[1])) {
          return false;
        }
      }
      else if ((val < lossConfig[0]) || (val > lossConfig[1]))
      {
        return false;
      }
      if (Double.valueOf(lossArrs[i]).doubleValue() > Double.valueOf(salesArrs[i]).doubleValue()) {
        return false;
      }
    }
    int[] userConfig = { this.dataFactory.getDailySettleConfig().getMinValidUserl(), 1000 };
    for (int i = 0; i < userArrs.length; i++)
    {
      int val = Integer.valueOf(userArrs[i]).intValue();
      if (upDailySettle != null)
      {
        int minUser = Integer.valueOf(upUserArrs[0]).intValue();
        tmUpScale = upUserArrs.length;
        for (int indexxl = 0; indexxl < tmUpScale; indexxl++)
        {
          String lValue = upUserArrs[(int)indexxl];
          int llValue = Integer.valueOf(lValue).intValue();
          if (llValue < minUser) {
            minUser = llValue;
          }
        }
        if ((val < minUser) || (val > userConfig[1])) {
          return false;
        }
      }
      else if ((val < userConfig[0]) || (val > userConfig[1]))
      {
        return false;
      }
      if ((i > 0) && (Integer.valueOf(userArrs[(i - 1)]).intValue() > val)) {
        return false;
      }
    }
    return true;
  }
  
  private boolean checkStartDailyLevel(String scaleLevel, String salesLevel, String lossLevel, String userLevel)
  {
    String[] scaleArrs = scaleLevel.split(",");
    
    String[] salesArrs = salesLevel.split(",");
    
    String[] lossArrs = lossLevel.split(",");
    
    String[] userArrs = userLevel.split(",");
    
    int maxLength = this.dataFactory.getDailySettleConfig().getMaxSignLevel();
    if ((scaleArrs.length > maxLength) || (salesArrs.length > maxLength) || (lossArrs.length > maxLength) || (userArrs.length > maxLength)) {
      return false;
    }
    if ((scaleArrs.length != salesArrs.length) || (scaleArrs.length != lossArrs.length) || (salesArrs.length != lossArrs.length) || (scaleArrs.length != userArrs.length)) {
      return false;
    }
    double[] scaleConfig = this.dataFactory.getDailySettleConfig().getLevelsScale();
    for (int i = 0; i < scaleArrs.length; i++)
    {
      double val = Double.valueOf(scaleArrs[i]).doubleValue();
      if ((val < scaleConfig[0]) || (val > scaleConfig[1])) {
        return false;
      }
      if ((i > 0) && (Double.valueOf(scaleArrs[(i - 1)]).doubleValue() >= val)) {
        return false;
      }
    }
    int[] userConfig = { this.dataFactory.getDailySettleConfig().getMinValidUserl(), 1000 };
    for (int i = 0; i < userArrs.length; i++)
    {
      int val = Integer.valueOf(userArrs[i]).intValue();
      if ((val < userConfig[0]) || (val > userConfig[1])) {
        return false;
      }
      if ((i > 0) && (Integer.valueOf(userArrs[(i - 1)]).intValue() > val)) {
        return false;
      }
    }
    double[] salesConfig = this.dataFactory.getDailySettleConfig().getLevelsSales();
    for (int i = 0; i < salesArrs.length; i++)
    {
      double val = Double.valueOf(salesArrs[i]).doubleValue();
      if ((val < salesConfig[0]) || (val > salesConfig[1])) {
        return false;
      }
    }
    double[] lossConfig = this.dataFactory.getDailySettleConfig().getLevelsLoss();
    for (int i = 0; i < lossArrs.length; i++)
    {
      double val = Double.valueOf(lossArrs[i]).doubleValue();
      if ((val < lossConfig[0]) || (val > lossConfig[1])) {
        return false;
      }
      if (Double.valueOf(lossArrs[i]).doubleValue() > Double.valueOf(salesArrs[i]).doubleValue()) {
        return false;
      }
    }
    return true;
  }
  
  public double[] getMinMaxSales(User acceptUser)
  {
    double minSales = this.dataFactory.getDailySettleConfig().getLevelsSales()[0];
    double maxSales = this.dataFactory.getDailySettleConfig().getLevelsSales()[1];
    
    User requestUser = this.uService.getById(acceptUser.getUpid());
    if (this.uCodePointUtil.isLevel3Proxy(acceptUser)) {
      return new double[] { minSales, maxSales };
    }
    UserDailySettle upDividend = this.uDailySettleDao.getByUserId(requestUser.getId());
    if (upDividend == null) {
      return new double[] { 0.0D, 0.0D };
    }
    maxSales = this.dataFactory.getDailySettleConfig().getLevelsSales()[1];
    
    String[] salesLevel = upDividend.getSalesLevel().split(",");
    minSales = Double.valueOf(salesLevel[0]).doubleValue();
    String[] arrayOfString1;
    int j = (arrayOfString1 = salesLevel).length;
    for (int i = 0; i < j; i++)
    {
      String l = arrayOfString1[i];
      double ll = Double.valueOf(l).doubleValue();
      if (ll < minSales) {
        minSales = ll;
      }
    }
    if (minSales < 0.0D) {
      minSales = 0.0D;
    }
    if (maxSales < 0.0D) {
      maxSales = 0.0D;
    }
    return new double[] { minSales, maxSales };
  }
  
  public double[] getMinMaxLoss(User acceptUser)
  {
    double minLoss = this.dataFactory.getDailySettleConfig().getLevelsLoss()[0];
    double maxLoss = this.dataFactory.getDailySettleConfig().getLevelsLoss()[1];
    
    User requestUser = this.uService.getById(acceptUser.getUpid());
    if (this.uCodePointUtil.isLevel3Proxy(acceptUser)) {
      return new double[] { minLoss, maxLoss };
    }
    UserDailySettle upDividend = this.uDailySettleDao.getByUserId(requestUser.getId());
    if (upDividend == null) {
      return new double[] { 0.0D, 0.0D };
    }
    maxLoss = this.dataFactory.getDailySettleConfig().getLevelsLoss()[1];
    
    String[] lossArr = upDividend.getLossLevel().split(",");
    minLoss = Double.valueOf(lossArr[0]).doubleValue();
    String[] arrayOfString1;
    int j = (arrayOfString1 = lossArr).length;
    for (int i = 0; i < j; i++)
    {
      String l = arrayOfString1[i];
      double ll = Double.valueOf(l).doubleValue();
      if (ll < minLoss) {
        minLoss = ll;
      }
    }
    if (minLoss < 0.0D) {
      minLoss = 0.0D;
    }
    if (maxLoss < 0.0D) {
      maxLoss = 0.0D;
    }
    return new double[] { minLoss, maxLoss };
  }
  
  public int[] getMinMaxUsers(User acceptUser)
  {
    int minUser = this.dataFactory.getDailySettleConfig().getMinValidUserl();
    int maxUser = 1000;
    
    User requestUser = this.uService.getById(acceptUser.getUpid());
    if (this.uCodePointUtil.isLevel3Proxy(acceptUser)) {
      return new int[] { minUser, maxUser };
    }
    UserDailySettle upDividend = this.uDailySettleDao.getByUserId(requestUser.getId());
    if (upDividend == null) {
      return new int[2];
    }
    String[] lossArr = upDividend.getUserLevel().split(",");
    minUser = Integer.valueOf(lossArr[0]).intValue();
    String[] arrayOfString1;
    int j = (arrayOfString1 = lossArr).length;
    for (int i = 0; i < j; i++)
    {
      String l = arrayOfString1[i];
      int ll = Integer.valueOf(l).intValue();
      if (ll < minUser) {
        minUser = ll;
      }
    }
    if (minUser < 0) {
      minUser = 0;
    }
    return new int[] { minUser, maxUser };
  }
}
