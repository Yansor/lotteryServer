package lottery.domains.content.biz.impl;

import admin.web.WebJSONObject;
import javautils.date.Moment;
import lottery.domains.content.entity.User;
import java.util.Iterator;
import lottery.domains.content.entity.UserDailySettle;
import lottery.domains.content.vo.user.UserDailySettleVO;
import org.hibernate.criterion.Order;
import javautils.StringUtil;
import org.hibernate.criterion.Restrictions;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import java.util.List;
import org.slf4j.LoggerFactory;
import lottery.web.content.utils.UserCodePointUtil;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.biz.UserService;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDailySettleDao;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserDailySettleService;

@Service
public class UserDailySettleServiceImpl implements UserDailySettleService
{
    private static final Logger log;
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
    
    static {
        log = LoggerFactory.getLogger((Class)UserDailySettleServiceImpl.class);
    }
    
    @Override
    public PageList search(final List<Integer> userIds, final String sTime, final String eTime, final Double minScale, final Double maxScale, final Integer minValidUser, final Integer maxValidUser, final Integer status, int start, int limit) {
        start = ((start < 0) ? 0 : start);
        limit = ((limit < 0) ? 0 : limit);
        limit = ((limit > 20) ? 20 : limit);
        final List<Criterion> criterions = new ArrayList<Criterion>();
        if (CollectionUtils.isNotEmpty((Collection)userIds)) {
            criterions.add(Restrictions.in("userId", (Collection)userIds));
        }
        if (StringUtil.isNotNull(sTime)) {
            criterions.add((Criterion)Restrictions.ge("agreeTime", (Object)sTime));
        }
        if (StringUtil.isNotNull(eTime)) {
            criterions.add((Criterion)Restrictions.le("agreeTime", (Object)eTime));
        }
        if (minScale != null) {
            criterions.add((Criterion)Restrictions.ge("scale", (Object)minScale));
        }
        if (maxScale != null) {
            criterions.add((Criterion)Restrictions.le("scale", (Object)maxScale));
        }
        if (minValidUser != null) {
            criterions.add((Criterion)Restrictions.ge("minValidUser", (Object)minValidUser));
        }
        if (maxValidUser != null) {
            criterions.add((Criterion)Restrictions.le("minValidUser", (Object)maxValidUser));
        }
        if (status != null) {
            criterions.add((Criterion)Restrictions.eq("status", (Object)status));
        }
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("id"));
        final PageList pList = this.uDailySettleDao.search(criterions, orders, start, limit);
        final List<UserDailySettleVO> convertList = new ArrayList<UserDailySettleVO>();
        if (pList != null && pList.getList() != null) {
            for (final Object tmpBean : pList.getList()) {
                convertList.add(new UserDailySettleVO((UserDailySettle)tmpBean, this.dataFactory));
            }
        }
        pList.setList(convertList);
        return pList;
    }
    
    @Override
    public UserDailySettle getByUserId(final int userId) {
        return this.uDailySettleDao.getByUserId(userId);
    }
    
    @Override
    public UserDailySettle getById(final int id) {
        return this.uDailySettleDao.getById(id);
    }
    
    private boolean add(final User user, final String scaleLevel, final String salasLevel, final String lossLevel, final int minValidUser, final int status, final int fixed, final double minScale, final double maxScale, final String usersLevel) {
        final UserDailySettle bean = this.uDailySettleDao.getByUserId(user.getId());
        if (bean == null) {
            final int userId = user.getId();
            final Moment moment = new Moment();
            final String createTime = moment.toSimpleTime();
            final String createDate = moment.toSimpleDate();
            final String endDate = moment.add(99, "years").toSimpleDate();
            final double totalAmount = 0.0;
            final UserDailySettle entity = new UserDailySettle(userId, scaleLevel, lossLevel, salasLevel, minValidUser, createTime, createTime, createDate, endDate, totalAmount, status, fixed, minScale, maxScale, usersLevel);
            this.uDailySettleDao.add(entity);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean deleteByTeam(final String username) {
        final User uBean = this.uService.getByUsername(username);
        if (uBean != null) {
            this.uDailySettleDao.deleteByTeam(uBean.getId());
            return true;
        }
        return false;
    }
    
    @Override
    public boolean update(final WebJSONObject json, final int id, final String scaleLevel, final String salesLevel, final String lossLevel, final int minValidUser, final String usersLevel) {
        final UserDailySettle dailySettle = this.uDailySettleDao.getById(id);
        if (dailySettle == null) {
            json.set(1, "1-7");
            return false;
        }
        if (dailySettle.getScaleLevel().equals(scaleLevel) && dailySettle.getSalesLevel().equals(salesLevel) && dailySettle.getLossLevel().equals(lossLevel) && dailySettle.getUserLevel() == usersLevel) {
            json.set(1, "2-29");
            return false;
        }
        final User user = this.uDao.getById(dailySettle.getUserId());
        if (!this.checkCanEdit(json, user)) {
            return false;
        }
        final UserDailySettle upDailySettle = this.uDailySettleDao.getByUserId(user.getUpid());
        if (!this.uCodePointUtil.isLevel3Proxy(user) && upDailySettle == null) {
            json.setWithParams(2, "2-3008", new Object[0]);
            return false;
        }
        if (!this.checkValidLevel(scaleLevel, salesLevel, lossLevel, upDailySettle, usersLevel)) {
            json.setWithParams(2, "2-3009", new Object[0]);
            return false;
        }
        final double[] minMaxScale = this.getMinMaxScale(user);
        final double minScale = minMaxScale[0];
        final double maxScale = minMaxScale[1];
        final String[] scaleLevels = scaleLevel.split(",");
        if (Double.valueOf(scaleLevels[0]) < minScale || Double.valueOf(scaleLevels[scaleLevels.length - 1]) > maxScale) {
            json.setWithParams(2, "2-3006", new Object[0]);
            return false;
        }
        final double[] minMaxSales = this.getMinMaxSales(user);
        final double minSales = minMaxSales[0];
        final double maxSales = minMaxSales[1];
        final String[] salesLevels = salesLevel.split(",");
        if (Double.valueOf(salesLevels[0]) < minSales || Double.valueOf(salesLevels[salesLevels.length - 1]) > maxSales) {
            json.setWithParams(2, "2-3006", new Object[0]);
            return false;
        }
        final double[] minMaxLoss = this.getMinMaxLoss(user);
        final double minLoss = minMaxLoss[0];
        final double maxLoss = minMaxLoss[1];
        final String[] lossLevels = lossLevel.split(",");
        if (Double.valueOf(lossLevels[0]) < minLoss || Double.valueOf(lossLevels[lossLevels.length - 1]) > maxLoss) {
            json.setWithParams(2, "2-3006", new Object[0]);
            return false;
        }
        final int[] minMaxUser = this.getMinMaxUsers(user);
        final int minUser = minMaxUser[0];
        final int maxUser = minMaxUser[1];
        final String[] userLevels = usersLevel.split(",");
        if (Integer.valueOf(userLevels[0]) < minUser || Integer.valueOf(userLevels[userLevels.length - 1]) > maxUser) {
            json.setWithParams(2, "2-3006", new Object[0]);
            return false;
        }
        return this.uDailySettleDao.updateSomeFields(id, scaleLevel, lossLevel, salesLevel, minValidUser, usersLevel);
    }
    
    @Override
    public boolean add(final WebJSONObject json, final String username, final String scaleLevel, final String salesLevel, final String lossLevel, final int minValidUser, final int status, final String usersLevel) {
        final User user = this.uDao.getByUsername(username);
        if (!this.checkCanEdit(json, user)) {
            return false;
        }
        final UserDailySettle dailySettle = this.uDailySettleDao.getByUserId(user.getId());
        if (dailySettle != null) {
            json.set(2, "2-3007");
            return false;
        }
        final UserDailySettle upDailySettle = this.uDailySettleDao.getByUserId(user.getUpid());
        if (!this.uCodePointUtil.isLevel3Proxy(user) && upDailySettle == null) {
            json.set(2, "2-3008");
            return false;
        }
        if (!this.checkValidLevel(scaleLevel, salesLevel, lossLevel, upDailySettle, usersLevel)) {
            json.setWithParams(2, "2-3009", new Object[0]);
            return false;
        }
        final double[] minMaxScale = this.getMinMaxScale(user);
        double minScale = minMaxScale[0];
        double maxScale = minMaxScale[1];
        final String[] scaleLevels = scaleLevel.split(",");
        if (Double.valueOf(scaleLevels[0]) < minScale || Double.valueOf(scaleLevels[scaleLevels.length - 1]) > maxScale) {
            json.setWithParams(2, "2-3006", new Object[0]);
            return false;
        }
        final double[] minMaxSales = this.getMinMaxSales(user);
        final double minSales = minMaxSales[0];
        final double maxSales = minMaxSales[1];
        final String[] salesLevels = salesLevel.split(",");
        if (Double.valueOf(salesLevels[0]) < minSales || Double.valueOf(salesLevels[salesLevels.length - 1]) > maxSales) {
            json.setWithParams(2, "2-3006", new Object[0]);
            return false;
        }
        final double[] minMaxLoss = this.getMinMaxLoss(user);
        final double minLoss = minMaxLoss[0];
        final double maxLoss = minMaxLoss[1];
        final String[] lossLevels = lossLevel.split(",");
        if (Double.valueOf(lossLevels[0]) < minLoss || Double.valueOf(lossLevels[lossLevels.length - 1]) > maxLoss) {
            json.setWithParams(2, "2-3006", new Object[0]);
            return false;
        }
        final int[] minMaxUser = this.getMinMaxUsers(user);
        final int minUser = minMaxUser[0];
        final int maxUser = minMaxUser[1];
        final String[] userLevels = usersLevel.split(",");
        if (Integer.valueOf(userLevels[0]) < minUser || Integer.valueOf(userLevels[userLevels.length - 1]) > maxUser) {
            json.setWithParams(2, "2-3006", new Object[0]);
            return false;
        }
        final String[] scaleArrs = scaleLevel.split(",");
        minScale = Double.valueOf(scaleArrs[0]);
        maxScale = Double.valueOf(scaleArrs[scaleArrs.length - 1]);
        return this.add(user, scaleLevel, salesLevel, lossLevel, minValidUser, status, 1, minScale, maxScale, usersLevel);
    }
    
    @Override
    public double[] getMinMaxScale(final User acceptUser) {
        double minScale = this.dataFactory.getDailySettleConfig().getLevelsScale()[0];
        double maxScale = this.dataFactory.getDailySettleConfig().getLevelsScale()[1];
        if (this.uCodePointUtil.isLevel3Proxy(acceptUser)) {
            return new double[] { minScale, maxScale };
        }
        final User requestUser = this.uService.getById(acceptUser.getUpid());
        final UserDailySettle upDailySettle = this.uDailySettleDao.getByUserId(requestUser.getId());
        if (upDailySettle == null) {
            return new double[] { 0.0, 0.0 };
        }
        final String[] scaleLevel = upDailySettle.getScaleLevel().split(",");
        if (Double.valueOf(scaleLevel[scaleLevel.length - 1]) <= minScale) {
            return new double[] { 0.0, 0.0 };
        }
        maxScale = Double.valueOf(scaleLevel[scaleLevel.length - 1]);
        if (maxScale > this.dataFactory.getDailySettleConfig().getLevelsScale()[1]) {
            maxScale = this.dataFactory.getDailySettleConfig().getLevelsScale()[1];
        }
        if (minScale < 0.0) {
            minScale = 0.0;
        }
        if (maxScale < 0.0) {
            maxScale = 0.0;
        }
        return new double[] { minScale, maxScale };
    }
    
    @Override
    public boolean checkCanEdit(final WebJSONObject json, final User user) {
        if (this.uCodePointUtil.isLevel1Proxy(user)) {
            json.set(2, "2-36");
            return false;
        }
        if (this.uCodePointUtil.isLevel2Proxy(user)) {
            json.set(2, "2-39");
            return false;
        }
        final boolean checked = this.checkForRequest(user);
        if (!checked) {
            json.set(2, "2-3012");
            return false;
        }
        return true;
    }
    
    @Override
    public boolean checkCanDel(final WebJSONObject json, final User user) {
        if (user.getId() == 72) {
            json.set(2, "2-33");
            return false;
        }
        if (this.uCodePointUtil.isLevel1Proxy(user)) {
            json.set(2, "2-36");
            return false;
        }
        if (this.uCodePointUtil.isLevel2Proxy(user)) {
            json.set(2, "2-39");
            return false;
        }
        return true;
    }
    
    private boolean checkForRequest(final User acceptUser) {
        final User requestUser = this.uService.getById(acceptUser.getUpid());
        return this.allowRequestByUser(requestUser) && this.allowAccept(requestUser, acceptUser);
    }
    
    public boolean allowRequestByUser(final User user) {
        return this.dataFactory.getDailySettleConfig().isEnable() && user.getId() != 72 && !this.uCodePointUtil.isLevel1Proxy(user) && user.getCode() >= 1800 && user.getCode() >= 1800;
    }
    
    public boolean allowAccept(final User requestUser, final User acceptUser) {
        return acceptUser.getUpid() == requestUser.getId() && requestUser.getId() != 72 && acceptUser.getId() != 72 && !this.uCodePointUtil.isLevel1Proxy(requestUser) && !this.uCodePointUtil.isLevel1Proxy(acceptUser) && requestUser.getCode() >= 1800 && acceptUser.getCode() >= 1800 && (!this.uCodePointUtil.isLevel2Proxy(requestUser) || this.uCodePointUtil.isLevel3Proxy(acceptUser));
    }
    
    @Override
    public boolean changeZhaoShang(final User user, final boolean changeToCJZhaoShang) {
        return true;
    }
    
    @Override
    public void checkDailySettle(final String username) {
        final User user = this.uDao.getByUsername(username);
        if (user.getId() == 72) {
            return;
        }
        this.uDailySettleDao.deleteByTeam(user.getId());
    }
    
    @Override
    public boolean checkValidLevel(final String scaleLevel, final String salesLevel, final String lossLevel, final UserDailySettle upDailySettle, final String usersLevel) {
        if (!StringUtil.isNotNull(scaleLevel) || !StringUtil.isNotNull(salesLevel) || !StringUtil.isNotNull(lossLevel) || !StringUtil.isNotNull(usersLevel)) {
            return false;
        }
        if (upDailySettle == null) {
            return this.checkStartDailyLevel(scaleLevel, salesLevel, lossLevel, usersLevel);
        }
        final String[] scaleArrs = scaleLevel.split(",");
        final String[] upScaleArrs = upDailySettle.getScaleLevel().split(",");
        final String[] salesArrs = salesLevel.split(",");
        final String[] upSalesArrs = upDailySettle.getSalesLevel().split(",");
        final String[] lossArrs = lossLevel.split(",");
        final String[] upLossArrs = upDailySettle.getLossLevel().split(",");
        final String[] userArrs = usersLevel.split(",");
        final String[] upUserArrs = upDailySettle.getUserLevel().split(",");
        final int maxLength = this.dataFactory.getDailySettleConfig().getMaxSignLevel();
        if (scaleArrs.length > maxLength || salesArrs.length > maxLength || lossArrs.length > maxLength || userArrs.length > maxLength) {
            return false;
        }
        if (scaleArrs.length != salesArrs.length || scaleArrs.length != lossArrs.length || salesArrs.length != lossArrs.length || userArrs.length != scaleArrs.length) {
            return false;
        }
        final double[] scaleConfig = this.dataFactory.getDailySettleConfig().getLevelsScale();
        for (int i = 0; i < scaleArrs.length; ++i) {
            final double val = Double.valueOf(scaleArrs[i]);
            if (upDailySettle != null) {
                if (val < scaleConfig[0] || val > Double.valueOf(upScaleArrs[upScaleArrs.length - 1])) {
                    return false;
                }
            }
            else if (val < scaleConfig[0] || val > scaleConfig[1]) {
                return false;
            }
            if (i > 0 && Double.valueOf(scaleArrs[i - 1]) >= val) {
                return false;
            }
            final double tmSales = Double.valueOf(salesArrs[i]);
            final double tmLoss = Double.valueOf(lossArrs[i]);
            int upIndex = -1;
            for (int j = 0; j < upScaleArrs.length; ++j) {
                final double tmUpSales = Double.valueOf(upSalesArrs[j]);
                final double tmUpLoss = Double.valueOf(upLossArrs[j]);
                if (tmSales >= tmUpSales && tmLoss >= tmUpLoss) {
                    upIndex = j;
                }
            }
            if (upIndex == -1) {
                return false;
            }
            double tmUpScale = Double.valueOf(upScaleArrs[upIndex]);
            if (val > tmUpScale) {
                if (++upIndex >= upScaleArrs.length) {
                    return false;
                }
                final double tmUpSales2 = Double.valueOf(upSalesArrs[upIndex]);
                final double tmUpLoss2 = Double.valueOf(upLossArrs[upIndex]);
                if (tmSales < tmUpSales2 || tmLoss < tmUpLoss2) {
                    return false;
                }
                tmUpScale = Double.valueOf(upScaleArrs[upIndex]);
                if (val > tmUpScale) {
                    return false;
                }
            }
        }
        final double[] salesConfig = this.dataFactory.getDailySettleConfig().getLevelsSales();
        for (int k = 0; k < salesArrs.length; ++k) {
            final double val2 = Double.valueOf(salesArrs[k]);
            if (upDailySettle != null) {
                double minSales = Double.valueOf(upSalesArrs[0]);
                String[] array;
                for (int length = (array = upLossArrs).length, n = 0; n < length; ++n) {
                    final String l = array[n];
                    final double ll = Double.valueOf(l);
                    if (ll < minSales) {
                        minSales = ll;
                    }
                }
                if (val2 < minSales || val2 > salesConfig[1]) {
                    return false;
                }
            }
            else if (val2 < salesConfig[0] || val2 > salesConfig[1]) {
                return false;
            }
        }
        final double[] lossConfig = this.dataFactory.getDailySettleConfig().getLevelsLoss();
        for (int m = 0; m < lossArrs.length; ++m) {
            final double val3 = Double.valueOf(lossArrs[m]);
            if (upDailySettle != null) {
                double minLoss = Double.valueOf(upLossArrs[0]);
                String[] array2;
                for (int length2 = (array2 = upLossArrs).length, n2 = 0; n2 < length2; ++n2) {
                    final String l2 = array2[n2];
                    final double ll2 = Double.valueOf(l2);
                    if (ll2 < minLoss) {
                        minLoss = ll2;
                    }
                }
                if (val3 < minLoss || val3 > lossConfig[1]) {
                    return false;
                }
            }
            else if (val3 < lossConfig[0] || val3 > lossConfig[1]) {
                return false;
            }
            if (Double.valueOf(lossArrs[m]) > Double.valueOf(salesArrs[m])) {
                return false;
            }
        }
        final int[] userConfig = { this.dataFactory.getDailySettleConfig().getMinValidUserl(), 1000 };
        for (int i2 = 0; i2 < userArrs.length; ++i2) {
            final int val4 = Integer.valueOf(userArrs[i2]);
            if (upDailySettle != null) {
                int minUser = Integer.valueOf(upUserArrs[0]);
                String[] array3;
                for (int length3 = (array3 = upUserArrs).length, n3 = 0; n3 < length3; ++n3) {
                    final String l = array3[n3];
                    final int ll3 = Integer.valueOf(l);
                    if (ll3 < minUser) {
                        minUser = ll3;
                    }
                }
                if (val4 < minUser || val4 > userConfig[1]) {
                    return false;
                }
            }
            else if (val4 < userConfig[0] || val4 > userConfig[1]) {
                return false;
            }
            if (i2 > 0 && Integer.valueOf(userArrs[i2 - 1]) > val4) {
                return false;
            }
        }
        return true;
    }
    
    private boolean checkStartDailyLevel(final String scaleLevel, final String salesLevel, final String lossLevel, final String userLevel) {
        final String[] scaleArrs = scaleLevel.split(",");
        final String[] salesArrs = salesLevel.split(",");
        final String[] lossArrs = lossLevel.split(",");
        final String[] userArrs = userLevel.split(",");
        final int maxLength = this.dataFactory.getDailySettleConfig().getMaxSignLevel();
        if (scaleArrs.length > maxLength || salesArrs.length > maxLength || lossArrs.length > maxLength || userArrs.length > maxLength) {
            return false;
        }
        if (scaleArrs.length != salesArrs.length || scaleArrs.length != lossArrs.length || salesArrs.length != lossArrs.length || scaleArrs.length != userArrs.length) {
            return false;
        }
        final double[] scaleConfig = this.dataFactory.getDailySettleConfig().getLevelsScale();
        for (int i = 0; i < scaleArrs.length; ++i) {
            final double val = Double.valueOf(scaleArrs[i]);
            if (val < scaleConfig[0] || val > scaleConfig[1]) {
                return false;
            }
            if (i > 0 && Double.valueOf(scaleArrs[i - 1]) >= val) {
                return false;
            }
        }
        final int[] userConfig = { this.dataFactory.getDailySettleConfig().getMinValidUserl(), 1000 };
        for (int j = 0; j < userArrs.length; ++j) {
            final int val2 = Integer.valueOf(userArrs[j]);
            if (val2 < userConfig[0] || val2 > userConfig[1]) {
                return false;
            }
            if (j > 0 && Integer.valueOf(userArrs[j - 1]) > val2) {
                return false;
            }
        }
        final double[] salesConfig = this.dataFactory.getDailySettleConfig().getLevelsSales();
        for (int k = 0; k < salesArrs.length; ++k) {
            final double val3 = Double.valueOf(salesArrs[k]);
            if (val3 < salesConfig[0] || val3 > salesConfig[1]) {
                return false;
            }
        }
        final double[] lossConfig = this.dataFactory.getDailySettleConfig().getLevelsLoss();
        for (int l = 0; l < lossArrs.length; ++l) {
            final double val4 = Double.valueOf(lossArrs[l]);
            if (val4 < lossConfig[0] || val4 > lossConfig[1]) {
                return false;
            }
            if (Double.valueOf(lossArrs[l]) > Double.valueOf(salesArrs[l])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public double[] getMinMaxSales(final User acceptUser) {
        double minSales = this.dataFactory.getDailySettleConfig().getLevelsSales()[0];
        double maxSales = this.dataFactory.getDailySettleConfig().getLevelsSales()[1];
        final User requestUser = this.uService.getById(acceptUser.getUpid());
        if (this.uCodePointUtil.isLevel3Proxy(acceptUser)) {
            return new double[] { minSales, maxSales };
        }
        final UserDailySettle upDividend = this.uDailySettleDao.getByUserId(requestUser.getId());
        if (upDividend == null) {
            return new double[] { 0.0, 0.0 };
        }
        maxSales = this.dataFactory.getDailySettleConfig().getLevelsSales()[1];
        final String[] salesLevel = upDividend.getSalesLevel().split(",");
        minSales = Double.valueOf(salesLevel[0]);
        String[] array;
        for (int length = (array = salesLevel).length, i = 0; i < length; ++i) {
            final String l = array[i];
            final double ll = Double.valueOf(l);
            if (ll < minSales) {
                minSales = ll;
            }
        }
        if (minSales < 0.0) {
            minSales = 0.0;
        }
        if (maxSales < 0.0) {
            maxSales = 0.0;
        }
        return new double[] { minSales, maxSales };
    }
    
    @Override
    public double[] getMinMaxLoss(final User acceptUser) {
        double minLoss = this.dataFactory.getDailySettleConfig().getLevelsLoss()[0];
        double maxLoss = this.dataFactory.getDailySettleConfig().getLevelsLoss()[1];
        final User requestUser = this.uService.getById(acceptUser.getUpid());
        if (this.uCodePointUtil.isLevel3Proxy(acceptUser)) {
            return new double[] { minLoss, maxLoss };
        }
        final UserDailySettle upDividend = this.uDailySettleDao.getByUserId(requestUser.getId());
        if (upDividend == null) {
            return new double[] { 0.0, 0.0 };
        }
        maxLoss = this.dataFactory.getDailySettleConfig().getLevelsLoss()[1];
        final String[] lossArr = upDividend.getLossLevel().split(",");
        minLoss = Double.valueOf(lossArr[0]);
        String[] array;
        for (int length = (array = lossArr).length, i = 0; i < length; ++i) {
            final String l = array[i];
            final double ll = Double.valueOf(l);
            if (ll < minLoss) {
                minLoss = ll;
            }
        }
        if (minLoss < 0.0) {
            minLoss = 0.0;
        }
        if (maxLoss < 0.0) {
            maxLoss = 0.0;
        }
        return new double[] { minLoss, maxLoss };
    }
    
    @Override
    public int[] getMinMaxUsers(final User acceptUser) {
        int minUser = this.dataFactory.getDailySettleConfig().getMinValidUserl();
        final int maxUser = 1000;
        final User requestUser = this.uService.getById(acceptUser.getUpid());
        if (this.uCodePointUtil.isLevel3Proxy(acceptUser)) {
            return new int[] { minUser, maxUser };
        }
        final UserDailySettle upDividend = this.uDailySettleDao.getByUserId(requestUser.getId());
        if (upDividend == null) {
            return new int[2];
        }
        final String[] lossArr = upDividend.getUserLevel().split(",");
        minUser = Integer.valueOf(lossArr[0]);
        String[] array;
        for (int length = (array = lossArr).length, i = 0; i < length; ++i) {
            final String l = array[i];
            final int ll = Integer.valueOf(l);
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
