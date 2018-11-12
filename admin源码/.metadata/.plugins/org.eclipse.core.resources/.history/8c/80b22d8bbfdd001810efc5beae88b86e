package lottery.domains.content.biz.impl;

import java.util.Collection;
import java.util.Arrays;
import lottery.domains.content.vo.user.UserVO;
import javautils.math.MathUtil;
import lottery.domains.content.vo.user.UserOnlineVO;
import lottery.domains.content.vo.user.UserBaseVO;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import admin.web.WebJSONObject;
import java.util.Iterator;
import javautils.StringUtil;
import javautils.array.ArrayUtils;
import java.util.List;
import lottery.domains.content.vo.user.UserProfileVO;
import javautils.date.Moment;
import javautils.encrypt.PasswordUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import lottery.domains.content.entity.User;
import javautils.redis.JedisTemplate;
import lottery.domains.content.biz.UserBillService;
import lottery.web.content.utils.UserCodePointUtil;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.biz.UserDailySettleService;
import lottery.domains.content.biz.UserDividendService;
import lottery.domains.content.dao.UserRegistLinkDao;
import lottery.domains.content.dao.UserSecurityDao;
import lottery.domains.content.dao.UserCodeQuotaDao;
import lottery.domains.content.dao.UserCardDao;
import lottery.domains.content.dao.UserBetsDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserService;

@Service
public class UserServiceImpl implements UserService
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
    
    @Transactional(readOnly = true)
    @Override
    public User getById(final int id) {
        return this.uDao.getById(id);
    }
    
    @Transactional(readOnly = true)
    @Override
    public User getByUsername(final String username) {
        return this.uDao.getByUsername(username);
    }
    
    @Override
    public boolean aStatus(final String username, final int status, final String message) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean == null || uBean.getAStatus() == -2) {
            return false;
        }
        if (uBean.getId() == 72) {
            return false;
        }
        final boolean updated = this.uDao.updateAStatus(uBean.getId(), status, message);
        if (status != 0 && updated && StringUtils.isNotEmpty(uBean.getSessionId())) {
            this.kickOutUser(uBean.getId(), uBean.getSessionId());
        }
        return updated;
    }
    
    @Override
    public boolean bStatus(final String username, final int status, final String message) {
        final User uBean = this.uDao.getByUsername(username);
        return uBean != null && uBean.getAStatus() >= 0 && this.uDao.updateBStatus(uBean.getId(), status, message);
    }
    
    @Override
    public boolean modifyLoginPwd(final String username, final String password) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean != null) {
            final String md5Pwd = PasswordUtil.generatePasswordByMD5(password);
            final boolean updated = this.uDao.updateLoginPwd(uBean.getId(), md5Pwd);
            if (updated && StringUtils.isNotEmpty(uBean.getSessionId())) {
                this.kickOutUser(uBean.getId(), uBean.getSessionId());
            }
            return updated;
        }
        return false;
    }
    
    @Override
    public boolean modifyWithdrawPwd(final String username, final String password) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean != null) {
            final String md5Pwd = PasswordUtil.generatePasswordByMD5(password);
            final boolean flag = this.uDao.updateWithdrawPassword(uBean.getId(), md5Pwd);
            return flag;
        }
        return false;
    }
    
    @Override
    public boolean modifyWithdrawName(final String username, final String withdrawName) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean != null) {
            final boolean flag = this.uDao.updateWithdrawName(uBean.getId(), withdrawName);
            if (flag) {
                final String lockTime = new Moment().add(3, "days").toSimpleTime();
                this.uDao.updateLockTime(uBean.getId(), lockTime);
                this.uCardDao.updateCardName(uBean.getId(), withdrawName);
            }
            return flag;
        }
        return false;
    }
    
    @Override
    public boolean resetImagePwd(final String username) {
        final User uBean = this.uDao.getByUsername(username);
        return uBean != null && this.uDao.updateImgPwd(uBean.getId(), null);
    }
    
    @Override
    public UserProfileVO getUserProfile(final String username) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean != null) {
            final List<User> lowerUsers = this.uDao.getUserLower(uBean.getId());
            final UserProfileVO pBean = new UserProfileVO(uBean, lowerUsers, this.lotteryDataFactory, this.uCodePointUtil);
            return pBean;
        }
        return null;
    }
    
    @Override
    public boolean changeLine(final int type, final String aUser, final String bUser) {
        final User aBean = this.uDao.getByUsername(aUser);
        final User bBean = this.uDao.getByUsername(bUser);
        if (aBean != null && bBean != null) {
            if (aBean.getId() == 72) {
                return false;
            }
            if (type == 0 && aBean.getCode() <= bBean.getCode()) {
                final List<User> uList = this.uDao.getUserLower(aBean.getId());
                int succ = 0;
                for (final User tmpUser : uList) {
                    int upid = tmpUser.getUpid();
                    if (tmpUser.getUpid() == aBean.getId()) {
                        upid = bBean.getId();
                    }
                    String upids = ArrayUtils.deleteInsertIds(tmpUser.getUpids(), aBean.getId(), true);
                    if (StringUtil.isNotNull(upids)) {
                        upids = String.valueOf(upids) + ",";
                    }
                    else {
                        upids = "";
                    }
                    upids = String.valueOf(upids) + "[" + bBean.getId() + "]";
                    if (bBean.getUpid() != 0) {
                        upids = String.valueOf(upids) + "," + bBean.getUpids();
                    }
                    final boolean flag = this.uDao.updateProxy(tmpUser.getId(), upid, upids);
                    if (flag) {
                        ++succ;
                    }
                }
                final boolean updated = succ == uList.size();
                if (updated) {
                    this.uDividendService.checkDividend(aBean.getUsername());
                    this.uDailySettleService.checkDailySettle(aBean.getUsername());
                }
                return updated;
            }
            if (type == 1 && aBean.getCode() <= bBean.getCode()) {
                final List<User> uList = this.uDao.getUserLower(aBean.getId());
                uList.add(aBean);
                int succ = 0;
                for (final User tmpUser : uList) {
                    int upid = tmpUser.getUpid();
                    if (tmpUser.getId() == aBean.getId()) {
                        upid = bBean.getId();
                    }
                    String upids = ArrayUtils.deleteInsertIds(tmpUser.getUpids(), aBean.getUpid(), true);
                    if (StringUtil.isNotNull(upids)) {
                        upids = String.valueOf(upids) + ",";
                    }
                    else {
                        upids = "";
                    }
                    upids = String.valueOf(upids) + "[" + bBean.getId() + "]";
                    if (bBean.getUpid() != 0) {
                        upids = String.valueOf(upids) + "," + bBean.getUpids();
                    }
                    final boolean flag = this.uDao.updateProxy(tmpUser.getId(), upid, upids);
                    if (flag) {
                        ++succ;
                    }
                }
                final boolean updated = succ == uList.size();
                if (updated) {
                    this.uDividendService.checkDividend(aBean.getUsername());
                    this.uDailySettleService.checkDailySettle(aBean.getUsername());
                }
                return updated;
            }
        }
        return false;
    }
    
    @Override
    public boolean modifyLotteryPoint(final String username, final int code, final double locatePoint, final double notLocatePoint) {
        if (code > this.dataFactory.getCodeConfig().getSysCode() || code < 1800) {
            return false;
        }
        if (code != this.dataFactory.getCodeConfig().getSysCode() && code % 2 != 0) {
            return false;
        }
        if (code < 1800 || code > this.dataFactory.getCodeConfig().getSysCode()) {
            return false;
        }
        final User uBean = this.uDao.getByUsername(username);
        if (uBean == null) {
            return false;
        }
        if (uBean.getId() == 72) {
            return false;
        }
        int BStatus = uBean.getBStatus();
        if (code <= this.dataFactory.getLotteryConfig().getNotBetPointAccount()) {
            BStatus = 0;
        }
        else {
            BStatus = -1;
        }
        final boolean flag = this.uDao.updateLotteryPoint(uBean.getId(), code, locatePoint, BStatus, notLocatePoint);
        return flag;
    }
    
    @Override
    public boolean downLinePoint(final String username) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean == null) {
            return false;
        }
        if (uBean.getId() == 72) {
            return false;
        }
        final List<User> teamList = this.uDao.getUserLower(uBean.getId());
        teamList.add(uBean);
        for (final User tmpBean : teamList) {
            int code = tmpBean.getCode();
            double locatePoint = tmpBean.getLocatePoint();
            double notLocatePoint = tmpBean.getNotLocatePoint();
            if (locatePoint > 0.0) {
                code -= 2;
                locatePoint -= 0.1;
                if (notLocatePoint > 0.0) {
                    notLocatePoint -= 0.1;
                }
                if (code < 1800) {
                    code = 1800;
                    locatePoint = this.uCodePointUtil.getLocatePoint(code);
                    notLocatePoint = this.uCodePointUtil.getNotLocatePoint(code);
                }
            }
            int BStatus = tmpBean.getBStatus();
            if (locatePoint <= this.dataFactory.getLotteryConfig().getNotBetPointAccount()) {
                BStatus = 0;
            }
            else {
                BStatus = -1;
            }
            final boolean flag = this.uDao.updateLotteryPoint(tmpBean.getId(), code, locatePoint, BStatus, notLocatePoint);
            if (flag) {}
        }
        return true;
    }
    
    @Override
    public boolean modifyExtraPoint(final String username, final double point) {
        final User uBean = this.uDao.getByUsername(username);
        return uBean != null && this.uDao.updateExtraPoint(uBean.getId(), point);
    }
    
    @Override
    public boolean modifyEqualCode(final String username, final int status) {
        final User uBean = this.uDao.getByUsername(username);
        return uBean != null && this.uDao.updateAllowEqualCode(uBean.getId(), status);
    }
    
    @Override
    public boolean modifyUserVipLevel(final String username, final int vipLevel) {
        final User uBean = this.uDao.getByUsername(username);
        return uBean != null && this.uDao.updateVipLevel(uBean.getId(), vipLevel);
    }
    
    @Override
    public boolean modifyTransfers(final String username, final int status) {
        final User uBean = this.uDao.getByUsername(username);
        return uBean != null && this.uDao.updateAllowTransfers(uBean.getId(), status);
    }
    
    @Override
    public boolean modifyPlatformTransfers(final String username, final int status) {
        final User uBean = this.uDao.getByUsername(username);
        return uBean != null && this.uDao.updateAllowPlatformTransfers(uBean.getId(), status);
    }
    
    @Override
    public boolean modifyWithdraw(final String username, final int status) {
        final User uBean = this.uDao.getByUsername(username);
        return uBean != null && this.uDao.updateAllowWithdraw(uBean.getId(), status);
    }
    
    @Override
    public boolean changeProxy(final String username) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean != null) {
            if (uBean.getId() == 72) {
                return false;
            }
            if (uBean.getType() == 2) {
                final boolean updated = this.uDao.updateType(uBean.getId(), 1);
                if (updated) {
                    this.uDividendService.checkDividend(uBean.getUsername());
                    this.uDailySettleService.checkDailySettle(uBean.getUsername());
                }
                return updated;
            }
        }
        return false;
    }
    
    @Override
    public boolean unbindGoogle(final String username) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean != null) {
            if (uBean.getId() == 72) {
                return false;
            }
            if (StringUtil.isNotNull(uBean.getSecretKey()) || uBean.getIsBindGoogle() == 1) {
                return this.uDao.unbindGoogle(uBean.getId());
            }
        }
        return false;
    }
    
    @Override
    public boolean resetLockTime(final String username) {
        final User uBean = this.uDao.getByUsername(username);
        return uBean != null && StringUtil.isNotNull(uBean.getLockTime()) && this.uDao.resetLockTime(uBean.getId());
    }
    
    @Override
    public boolean modifyQuota(final String username, final int count1, final int count2, final int count3) {
        return false;
    }
    
    @Override
    public User recover(final String username) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean != null) {
            if (uBean.getId() == 72) {
                return null;
            }
            uBean.setPassword(PasswordUtil.generatePasswordByPlainString("a123456"));
            uBean.setTotalMoney(0.0);
            uBean.setLotteryMoney(0.0);
            uBean.setBaccaratMoney(0.0);
            uBean.setFreezeMoney(0.0);
            uBean.setDividendMoney(0.0);
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
                this.kickOutUser(uBean.getId(), uBean.getSessionId());
            }
            this.uDailySettleService.deleteByTeam(uBean.getUsername());
            this.uDividendService.deleteByTeam(uBean.getUsername());
        }
        return uBean;
    }
    
    @Override
    public boolean addNewUser(final WebJSONObject json, final String username, final String nickname, String password, final int upid, final String upids, final int type, final int code, final double locatePoint, final double notLocatePoint, final String relatedUsers) {
        if (code < 1800 || code > this.dataFactory.getCodeConfig().getSysCode()) {
            json.set(2, "2-2026");
            return false;
        }
        if (code != this.dataFactory.getCodeConfig().getSysCode() && code % 2 != 0) {
            json.set(2, "2-2026");
            return false;
        }
        String relatedUserIds = null;
        if (type == 3) {
            if (StringUtils.isEmpty(relatedUsers)) {
                json.set(2, "2-2204");
                return false;
            }
            relatedUserIds = this.convertRelatedUsers(json, relatedUsers);
            if (StringUtils.isEmpty(relatedUserIds)) {
                return false;
            }
        }
        final double totalMoney = 0.0;
        final double lotteryMoney = 0.0;
        final double baccaratMoney = 0.0;
        final double freezeMoney = 0.0;
        final double dividendMoney = 0.0;
        final int codeType = 0;
        final double extraPoint = 0.0;
        final String registTime = new Moment().toSimpleTime();
        final int AStatus = 0;
        int BStatus = 0;
        if (code > this.dataFactory.getLotteryConfig().getNotBetPointAccount()) {
            BStatus = -1;
        }
        int allowEqualCode = 1;
        if (code >= this.dataFactory.getCodeConfig().getNotCreateAccount()) {
            allowEqualCode = -1;
        }
        final int onlineStatus = 0;
        final int allowTransfers = 1;
        final int loginValidate = 0;
        final int bindStatus = 0;
        final int allowWithdraw = 1;
        final int allowPlatformTransfers = 1;
        password = PasswordUtil.generatePasswordByMD5(password);
        final int vipLevel = 0;
        final double integral = 0.0;
        final User entity = new User(username, password, nickname, totalMoney, lotteryMoney, baccaratMoney, freezeMoney, dividendMoney, type, upid, code, locatePoint, notLocatePoint, codeType, extraPoint, registTime, AStatus, BStatus, onlineStatus, allowEqualCode, allowTransfers, loginValidate, bindStatus, vipLevel, integral, allowWithdraw, allowPlatformTransfers);
        if (StringUtil.isNotNull(relatedUserIds) && type == 3) {
            entity.setRelatedUsers(relatedUserIds);
        }
        if (this.dataFactory.getWithdrawConfig().getRegisterHours() > 0) {
            entity.setLockTime(new Moment().add(this.dataFactory.getWithdrawConfig().getRegisterHours(), "hours").toSimpleTime());
        }
        if (StringUtil.isNotNull(upids)) {
            entity.setUpids(upids);
        }
        final boolean flag = this.uDao.add(entity);
        if (flag) {
            this.uDividendService.checkDividend(entity.getUsername());
            this.uDailySettleService.checkDailySettle(entity.getUsername());
        }
        return flag;
    }
    
    @Override
    public boolean addLowerUser(final WebJSONObject json, final User uBean, final String username, final String nickname, final String password, final int type, final int code, final double locatePoint, final double notLocatePoint, final String relatedUsers) {
        if (code < 1800 || code > this.dataFactory.getCodeConfig().getSysCode()) {
            json.set(2, "2-2026");
            return false;
        }
        if (code != this.dataFactory.getCodeConfig().getSysCode() && code % 2 != 0) {
            return false;
        }
        final int upid = uBean.getId();
        String upids = "[" + upid + "]";
        if (StringUtil.isNotNull(uBean.getUpids())) {
            upids = String.valueOf(upids) + "," + uBean.getUpids();
        }
        return this.addNewUser(json, username, nickname, password, upid, upids, type, code, locatePoint, notLocatePoint, relatedUsers);
    }
    
    @Override
    public PageList search(final String username, final String matchType, final String registTime, final Double minMoney, final Double maxMoney, final Double minLotteryMoney, final Double maxLotteryMoney, final Integer minCode, final Integer maxCode, final String sortColoum, final String sortType, final Integer aStatus, final Integer bStatus, final Integer onlineStatus, final Integer type, final String nickname, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        boolean isSearch = true;
        boolean iszz = false;
        if (StringUtil.isNotNull(username)) {
            final User targetUser = this.uDao.getByUsername(username);
            if (targetUser != null) {
                if (StringUtil.isNotNull(matchType)) {
                    if ("LOWER".equals(matchType)) {
                        criterions.add((Criterion)Restrictions.eq("upid", (Object)targetUser.getId()));
                    }
                    if ("UPPER".equals(matchType)) {
                        if (targetUser.getUpid() != 0) {
                            criterions.add((Criterion)Restrictions.eq("id", (Object)targetUser.getUpid()));
                        }
                        else {
                            isSearch = false;
                        }
                    }
                    if ("TEAM".equals(matchType)) {
                        criterions.add((Criterion)Restrictions.like("upids", "[" + targetUser.getId() + "]", MatchMode.ANYWHERE));
                    }
                    if ("USER".equals(matchType)) {
                        criterions.add((Criterion)Restrictions.eq("username", (Object)username));
                    }
                    if (targetUser.getId() == 72) {
                        iszz = true;
                    }
                }
            }
            else {
                isSearch = true;
                criterions.add((Criterion)Restrictions.like("username", (Object)("%" + username + "%")));
            }
        }
        if (StringUtil.isNotNull(registTime)) {
            criterions.add((Criterion)Restrictions.ge("registTime", (Object)(String.valueOf(registTime) + " 00:00:00")));
            criterions.add((Criterion)Restrictions.le("registTime", (Object)(String.valueOf(registTime) + " 23:59:59")));
        }
        if (type == null && !iszz) {
            criterions.add(Restrictions.not((Criterion)Restrictions.eq("upid", (Object)0)));
        }
        if (minMoney != null) {
            criterions.add((Criterion)Restrictions.ge("totalMoney", (Object)minMoney));
        }
        if (maxMoney != null) {
            criterions.add((Criterion)Restrictions.le("totalMoney", (Object)maxMoney));
        }
        if (minLotteryMoney != null) {
            criterions.add((Criterion)Restrictions.ge("lotteryMoney", (Object)minLotteryMoney));
        }
        if (maxLotteryMoney != null) {
            criterions.add((Criterion)Restrictions.le("lotteryMoney", (Object)maxLotteryMoney));
        }
        if (minCode != null) {
            criterions.add((Criterion)Restrictions.ge("code", (Object)minCode));
        }
        if (maxCode != null) {
            criterions.add((Criterion)Restrictions.le("code", (Object)maxCode));
        }
        if (aStatus != null) {
            criterions.add((Criterion)Restrictions.eq("AStatus", (Object)aStatus));
        }
        if (bStatus != null) {
            criterions.add((Criterion)Restrictions.eq("BStatus", (Object)bStatus));
        }
        if (onlineStatus != null) {
            criterions.add((Criterion)Restrictions.eq("onlineStatus", (Object)onlineStatus));
        }
        if (type != null) {
            criterions.add((Criterion)Restrictions.eq("type", (Object)type));
        }
        if (StringUtil.isNotNull(sortColoum)) {
            if ("DESC".equals(sortType)) {
                orders.add(Order.desc(sortColoum));
            }
            else {
                orders.add(Order.asc(sortColoum));
            }
        }
        orders.add(Order.desc("registTime"));
        if (isSearch) {
            final List<UserBaseVO> list = new ArrayList<UserBaseVO>();
            final PageList plist = this.uDao.search(criterions, orders, start, limit);
            for (final Object tmpBean : plist.getList()) {
                list.add(new UserBaseVO((User)tmpBean));
            }
            plist.setList(list);
            return plist;
        }
        return null;
    }
    
    @Override
    public PageList listOnline(final String sortColoum, final String sortType, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        criterions.add((Criterion)Restrictions.eq("onlineStatus", (Object)1));
        criterions.add(Restrictions.not((Criterion)Restrictions.eq("upid", (Object)0)));
        if (StringUtil.isNotNull(sortColoum)) {
            if ("DESC".equals(sortType)) {
                orders.add(Order.desc(sortColoum));
            }
            else {
                orders.add(Order.asc(sortColoum));
            }
        }
        orders.add(Order.desc("registTime"));
        final List<UserOnlineVO> list = new ArrayList<UserOnlineVO>();
        final PageList plist = this.uDao.search(criterions, orders, start, limit);
        for (final Object tmpBean : plist.getList()) {
            list.add(new UserOnlineVO((User)tmpBean, this.lotteryDataFactory));
        }
        plist.setList(list);
        return plist;
    }
    
    @Override
    public boolean kickOutUser(final int userId, final String sessionId) {
        if (StringUtils.isNotEmpty(sessionId)) {
            final String sessionKey = "spring:session:sessions:" + sessionId;
            this.jedisTemplate.del(sessionKey);
        }
        this.uDao.updateOffline(userId);
        return true;
    }
    
    @Override
    public boolean modifyRelatedUpper(final WebJSONObject json, final String username, final String relatedUpUser, final double relatedPoint) {
        if (relatedPoint < 0.0 || relatedPoint > 1.0) {
            json.set(2, "2-2209");
            return false;
        }
        if (StringUtils.equalsIgnoreCase(username, relatedUpUser)) {
            json.set(2, "2-2210");
            return false;
        }
        final User user = this.uDao.getByUsername(username);
        if (user == null) {
            json.set(2, "2-2201");
            return false;
        }
        if (user.getId() == 72) {
            json.set(2, "2-33");
            return false;
        }
        final User relatedUp = this.uDao.getByUsername(relatedUpUser);
        if (relatedUp == null) {
            json.set(2, "2-2202");
            return false;
        }
        if (relatedUp.getId() == 72) {
            json.set(2, "2-33");
            return false;
        }
        if (StringUtils.isEmpty(user.getUpids()) || StringUtils.isEmpty(relatedUp.getUpids())) {
            json.set(2, "2-38");
            return false;
        }
        if (user.getUpids().indexOf("[" + relatedUp.getId() + "]") > -1 || relatedUp.getUpids().indexOf("[" + user.getId() + "]") > -1) {
            json.set(2, "2-2205");
            return false;
        }
        if (user.getRelatedUpid() == relatedUp.getId() && user.getRelatedPoint() == relatedPoint) {
            json.set(2, "2-29");
            return false;
        }
        if (relatedUp.getRelatedUpid() == user.getId()) {
            json.set(2, "2-2211");
            return false;
        }
        final boolean updated = this.uDao.updateRelatedUpper(user.getId(), relatedUp.getId(), relatedPoint);
        final String relatedLowers = ArrayUtils.addId(relatedUp.getRelatedLowers(), user.getId());
        this.uDao.updateRelatedLowers(relatedUp.getId(), relatedLowers);
        return updated;
    }
    
    @Override
    public boolean reliveRelatedUpper(final WebJSONObject json, final String username) {
        final User user = this.uDao.getByUsername(username);
        if (user == null) {
            json.set(2, "2-2201");
            return false;
        }
        this.uDao.updateRelatedUpper(user.getId(), 0, 0.0);
        final int relatedUpid = user.getRelatedUpid();
        final User upUser = this.uDao.getById(relatedUpid);
        if (upUser != null) {
            String relatedLowers = upUser.getRelatedLowers();
            relatedLowers = ArrayUtils.deleteInsertIds(relatedLowers, user.getId(), false);
            this.uDao.updateRelatedLowers(upUser.getId(), relatedLowers);
        }
        return true;
    }
    
    @Override
    public boolean modifyRelatedUsers(final WebJSONObject json, final String username, final String relatedUsers) {
        final User user = this.uDao.getByUsername(username);
        if (user == null) {
            json.set(2, "2-2201");
            return false;
        }
        if (user.getType() != 3) {
            json.set(2, "2-2206");
            return false;
        }
        final String relatedUserIds = this.convertRelatedUsers(json, relatedUsers);
        if (relatedUserIds == null) {
            return false;
        }
        if (StringUtils.equalsIgnoreCase(relatedUserIds, user.getRelatedUsers())) {
            json.set(2, "2-29");
            return false;
        }
        final boolean updated = this.uDao.updateRelatedUsers(user.getId(), relatedUserIds);
        return updated;
    }
    
    private String convertRelatedUsers(final WebJSONObject json, final String relatedUsers) {
        final String[] relatedUserNames = relatedUsers.split(",");
        if (relatedUserNames == null || relatedUserNames.length <= 0) {
            json.set(2, "2-2204");
            return null;
        }
        if (relatedUserNames.length > 10) {
            json.setWithParams(2, "2-2207", 10);
            return null;
        }
        if (ArrayUtils.hasRepeat(relatedUserNames)) {
            json.set(2, "2-28");
            return null;
        }
        final StringBuilder relatedUserIds = new StringBuilder();
        final List<User> users = new ArrayList<User>();
        for (int i = 0; i < relatedUserNames.length; ++i) {
            final String relatedUserName = relatedUserNames[i].trim();
            final User user = this.uDao.getByUsername(relatedUserName);
            if (user == null) {
                json.setWithParams(2, "2-2212", relatedUserName);
                return null;
            }
            if (user.getId() == 72 || user.getUpid() == 0) {
                json.setWithParams(2, "2-2203", relatedUserName);
                return null;
            }
            if (!this.uCodePointUtil.isLevel1Proxy(user)) {
                json.setWithParams(2, "2-2208", relatedUserName);
                return null;
            }
            relatedUserIds.append("[").append(user.getId()).append("]");
            if (i < relatedUserNames.length - 1) {
                relatedUserIds.append(",");
            }
            users.add(user);
        }
        for (final User subUser : users) {
            if (StringUtils.isEmpty(subUser.getUpids())) {
                continue;
            }
            for (final User upUser : users) {
                if (subUser.getUpids().indexOf("[" + upUser.getId() + "]") > -1) {
                    json.setWithParams(2, "2-2213", upUser.getUsername(), subUser.getUsername());
                    return null;
                }
            }
        }
        return relatedUserIds.toString();
    }
    
    @Override
    public boolean lockTeam(final WebJSONObject json, final String username, final String remark) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean == null || uBean.getId() == 72) {
            return false;
        }
        final boolean updated = this.uDao.lockTeam(uBean.getId(), -1, remark);
        if (StringUtils.isNotEmpty(uBean.getSessionId())) {
            this.kickOutUser(uBean.getId(), uBean.getSessionId());
        }
        return updated;
    }
    
    @Override
    public boolean unLockTeam(final WebJSONObject json, final String username) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean == null || uBean.getId() == 72) {
            return false;
        }
        final boolean updated = this.uDao.unLockTeam(uBean.getId(), 0);
        return updated;
    }
    
    @Override
    public boolean prohibitTeamWithdraw(final WebJSONObject json, final String username) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean == null || uBean.getId() == 72) {
            return false;
        }
        final boolean updated = this.uDao.prohibitTeamWithdraw(uBean.getId(), -1);
        return updated;
    }
    
    @Override
    public boolean allowTeamWithdraw(final WebJSONObject json, final String username) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean == null || uBean.getId() == 72) {
            return false;
        }
        final boolean updated = this.uDao.allowTeamWithdraw(uBean.getId(), 1);
        return updated;
    }
    
    @Override
    public boolean allowTeamTransfers(final WebJSONObject json, final String username) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean == null || uBean.getId() == 72) {
            return false;
        }
        final boolean updated = this.uDao.allowTeamTransfers(uBean.getId(), 1);
        return updated;
    }
    
    @Override
    public boolean prohibitTeamTransfers(final WebJSONObject json, final String username) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean == null || uBean.getId() == 72) {
            return false;
        }
        final boolean updated = this.uDao.prohibitTeamTransfers(uBean.getId(), -1);
        return updated;
    }
    
    @Override
    public boolean allowTeamPlatformTransfers(final WebJSONObject json, final String username) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean == null || uBean.getId() == 72) {
            return false;
        }
        final boolean updated = this.uDao.allowTeamPlatformTransfers(uBean.getId(), 1);
        return updated;
    }
    
    @Override
    public boolean prohibitTeamPlatformTransfers(final WebJSONObject json, final String username) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean == null || uBean.getId() == 72) {
            return false;
        }
        final boolean updated = this.uDao.prohibitTeamPlatformTransfers(uBean.getId(), -1);
        return updated;
    }
    
    @Override
    public boolean transfer(final WebJSONObject json, final User aUser, final User bUser, final double money, final String remarks) {
        double balance = 0.0;
        if (aUser.getTotalMoney() > 0.0) {
            balance = MathUtil.add(balance, aUser.getTotalMoney());
        }
        if (aUser.getLotteryMoney() > 0.0) {
            balance = MathUtil.add(balance, aUser.getLotteryMoney());
        }
        if (balance <= 0.0) {
            json.setWithParams(2, "2-2028", "0");
            return false;
        }
        if (money > balance) {
            final String balanceStr = MathUtil.doubleToStringDown(balance, 1);
            json.setWithParams(2, "2-2028", balanceStr);
            return false;
        }
        double remain = money;
        final String billDesc = "管理员转账：" + aUser.getUsername() + "转入" + bUser.getUsername() + " 备注：" + remarks;
        if (remain > 0.0 && aUser.getLotteryMoney() > 0.0) {
            final double lotteryMoney = (aUser.getLotteryMoney() > remain) ? remain : aUser.getLotteryMoney();
            final boolean uFlag = this.uDao.updateLotteryMoney(aUser.getId(), -lotteryMoney);
            if (uFlag) {
                this.uBillService.addAdminMinusBill(aUser, 2, lotteryMoney, billDesc);
                remain = ((lotteryMoney >= remain) ? 0.0 : MathUtil.subtract(remain, lotteryMoney));
            }
        }
        if (remain > 0.0 && aUser.getTotalMoney() > 0.0) {
            final double totalMoney = (aUser.getTotalMoney() > remain) ? remain : aUser.getTotalMoney();
            final boolean uFlag = this.uDao.updateTotalMoney(aUser.getId(), -totalMoney);
            if (uFlag) {
                this.uBillService.addAdminMinusBill(aUser, 1, totalMoney, billDesc);
                remain = ((totalMoney >= remain) ? 0.0 : MathUtil.subtract(remain, totalMoney));
            }
        }
        if (remain <= 0.0) {
            final boolean uFlag2 = this.uDao.updateLotteryMoney(bUser.getId(), money);
            if (uFlag2) {
                this.uBillService.addAdminAddBill(bUser, 2, money, billDesc);
                return true;
            }
        }
        json.set(1, "1-5");
        return false;
    }
    
    @Override
    public boolean changeZhaoShang(final WebJSONObject json, final String username, final int isCJZhaoShang) {
        final User user = this.uDao.getByUsername(username);
        if (!this.uCodePointUtil.isLevel2Proxy(user)) {
            json.set(2, "2-3013");
            return false;
        }
        if (isCJZhaoShang != user.getIsCjZhaoShang()) {
            json.set(2, "2-29");
            return false;
        }
        if (isCJZhaoShang == 1) {
            this.uDailySettleService.changeZhaoShang(user, false);
            this.uDividendService.changeZhaoShang(user, false);
            return this.uDao.changeZhaoShang(user.getId(), 0);
        }
        this.uDailySettleService.changeZhaoShang(user, true);
        this.uDividendService.changeZhaoShang(user, true);
        return this.uDao.changeZhaoShang(user.getId(), 1);
    }
    
    @Override
    public List<String> getUserLevels(final User user) {
        final List<String> userLevels = new ArrayList<String>();
        userLevels.add(user.getUsername());
        if (StringUtil.isNotNull(user.getUpids())) {
            final String[] ids = user.getUpids().replaceAll("\\[|\\]", "").split(",");
            String[] array;
            for (int length = (array = ids).length, i = 0; i < length; ++i) {
                final String upId = array[i];
                final UserVO thisUser = this.dataFactory.getUser(Integer.parseInt(upId));
                if (thisUser != null) {
                    userLevels.add(thisUser.getUsername());
                }
            }
        }
        return userLevels;
    }
    
    @Override
    public List<User> findNeibuZhaoShang() {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add((Criterion)Restrictions.eq("upid", (Object)72));
        criterions.add(Restrictions.not(Restrictions.in("AStatus", (Collection)Arrays.asList(-2, -3))));
        criterions.add(Restrictions.not((Criterion)Restrictions.eq("upid", (Object)0)));
        final List<User> users = this.uDao.list(criterions, null);
        return users;
    }
    
    @Override
    public List<User> findZhaoShang(final List<User> neibuZhaoShangs) {
        final List<Integer> neibuZhaoShangIds = new ArrayList<Integer>();
        for (final User neibuZhaoShang : neibuZhaoShangs) {
            neibuZhaoShangIds.add(neibuZhaoShang.getId());
        }
        final List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in("upid", (Collection)neibuZhaoShangIds));
        criterions.add(Restrictions.not((Criterion)Restrictions.eq("upid", (Object)0)));
        criterions.add(Restrictions.not(Restrictions.in("AStatus", (Collection)Arrays.asList(-2, -3))));
        final List<User> users = this.uDao.list(criterions, null);
        return users;
    }
    
    @Override
    public List<User> getUserDirectLower(final int userId) {
        final List<User> result = new ArrayList<User>();
        final User user1 = this.uDao.getById(userId);
        if (userId == 72) {
            final List<User> temp = this.uDao.getUserDirectLower(userId);
            final List<User> temp2 = new ArrayList<User>();
            for (int i = 0; i < temp.size(); ++i) {
                temp2.addAll(this.uDao.getUserDirectLower(temp.get(i).getId()));
            }
            for (int i = 0; i < temp2.size(); ++i) {
                result.addAll(this.uDao.getUserDirectLower(temp2.get(i).getId()));
            }
        }
        else if (this.uCodePointUtil.isLevel1Proxy(user1)) {
            final List<User> temp = this.uDao.getUserDirectLower(userId);
            for (int j = 0; j < temp.size(); ++j) {
                result.addAll(this.uDao.getUserDirectLower(temp.get(j).getId()));
            }
        }
        else {
            result.addAll(this.uDao.getUserDirectLower(userId));
        }
        return result;
    }
}
