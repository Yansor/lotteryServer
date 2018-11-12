package lottery.domains.content.biz.impl;

import admin.web.WebJSONObject;
import javautils.date.Moment;
import lottery.domains.content.entity.User;
import java.util.Iterator;
import lottery.domains.content.entity.UserDividend;
import lottery.domains.content.vo.user.UserDividendVO;
import org.hibernate.criterion.Order;
import javautils.StringUtil;
import org.hibernate.criterion.Restrictions;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import java.util.List;
import lottery.web.content.utils.UserCodePointUtil;
import lottery.domains.content.biz.UserService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDividendDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserDividendService;

@Service
public class UserDividendServiceImpl implements UserDividendService
{
    @Autowired
    private UserDividendDao uDividendDao;
    @Autowired
    private LotteryDataFactory dataFactory;
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserService uService;
    @Autowired
    private UserCodePointUtil uCodePointUtil;
    
    @Override
    public PageList search(final List<Integer> userIds, final String sTime, final String eTime, final Double minScale, final Double maxScale, final Integer minValidUser, final Integer maxValidUser, final Integer status, final Integer fixed, int start, int limit) {
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
        if (fixed != null) {
            criterions.add((Criterion)Restrictions.eq("fixed", (Object)fixed));
        }
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("id"));
        final PageList pList = this.uDividendDao.search(criterions, orders, start, limit);
        final List<UserDividendVO> convertList = new ArrayList<UserDividendVO>();
        if (pList != null && pList.getList() != null) {
            for (final Object tmpBean : pList.getList()) {
                convertList.add(new UserDividendVO((UserDividend)tmpBean, this.dataFactory));
            }
        }
        pList.setList(convertList);
        return pList;
    }
    
    @Override
    public UserDividend getByUserId(final int userId) {
        return this.uDividendDao.getByUserId(userId);
    }
    
    @Override
    public UserDividend getById(final int id) {
        return this.uDividendDao.getById(id);
    }
    
    private boolean add(final User user, final String scaleLevel, final String salesLevel, final String lossLevel, final int minValidUser, final int status, final int fixed, final double minScale, final double maxScale, final String remarks, final String userLevel) {
        final UserDividend bean = this.uDividendDao.getByUserId(user.getId());
        if (bean == null) {
            final int userId = user.getId();
            final Moment moment = new Moment();
            final String createTime = moment.toSimpleTime();
            final String createDate = moment.toSimpleDate();
            final String endDate = moment.add(99, "years").toSimpleDate();
            final String agreeTime = (status == 2) ? "" : createTime;
            final UserDividend entity = new UserDividend(userId, scaleLevel, lossLevel, salesLevel, minValidUser, createTime, agreeTime, createDate, endDate, 0.0, 0.0, status, fixed, minScale, maxScale, remarks, userLevel);
            this.uDividendDao.add(entity);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean deleteByTeam(final String username) {
        final User uBean = this.uService.getByUsername(username);
        if (uBean != null) {
            this.uDividendDao.deleteByTeam(uBean.getId());
            return true;
        }
        return false;
    }
    
    @Override
    public boolean changeZhaoShang(final User user, final boolean changeToCJZhaoShang) {
        return false;
    }
    
    @Override
    public void checkDividend(final String username) {
        final User user = this.uDao.getByUsername(username);
        if (user.getId() == 72) {
            return;
        }
        this.uDividendDao.deleteByTeam(user.getId());
    }
    
    private void adjustDividend1990(final User user) {
        final UserDividend uDividend = this.uDividendDao.getByUserId(user.getId());
        final int minValidUser = this.dataFactory.getDividendConfig().getZhaoShangMinValidUser();
        final int fixed = this.dataFactory.getDividendConfig().getFixedType();
        final int status = 1;
        final String scaleLevel = this.dataFactory.getDividendConfig().getZhaoShangScaleLevels();
        final String[] scaleLevelArr = scaleLevel.split(",");
        final String lossLevel = this.dataFactory.getDividendConfig().getZhaoShangLossLevels();
        final String salesLevel = this.dataFactory.getDividendConfig().getZhaoShangSalesLevels();
        final double minScale = Double.valueOf(scaleLevelArr[0]);
        final double maxScale = Double.valueOf(scaleLevelArr[scaleLevelArr.length - 1]);
        final String remarks = "自动分红配置";
        if (uDividend != null) {
            final int id = uDividend.getId();
            this.uDividendDao.updateSomeFields(id, scaleLevel, lossLevel, salesLevel, minValidUser, fixed, minScale, maxScale, status);
        }
        else {
            this.add(user, scaleLevel, salesLevel, lossLevel, minValidUser, status, fixed, minScale, maxScale, remarks, "");
        }
        this.uDividendDao.deleteLowers(user.getId());
    }
    
    @Override
    public boolean update(final WebJSONObject json, final int id, final String scaleLevel, final String lossLevel, final String salesLevel, final int minValidUser, final String userLevel) {
        final UserDividend dividend = this.uDividendDao.getById(id);
        if (dividend == null) {
            json.set(1, "1-7");
            return false;
        }
        if (dividend.getStatus() == 1) {
            return false;
        }
        if (dividend.getScaleLevel().equals(scaleLevel) && dividend.getSalesLevel().equals(salesLevel) && dividend.getLossLevel().equals(lossLevel) && dividend.getUserLevel().equals(userLevel)) {
            json.set(1, "2-29");
            return false;
        }
        final User user = this.uDao.getById(dividend.getUserId());
        final UserDividend upDividend = this.uDividendDao.getByUserId(user.getUpid());
        if (!this.uCodePointUtil.isLevel2Proxy(user) && (upDividend == null || upDividend.getStatus() != 1)) {
            json.set(2, "2-3011");
            return false;
        }
        if (!this.checkValidLevel(scaleLevel, salesLevel, lossLevel, upDividend, userLevel)) {
            json.setWithParams(2, "2-3009", new Object[0]);
            return false;
        }
        if (!this.checkCanEdit(json, user)) {
            return false;
        }
        final double[] minMaxScale = this.getMinMaxScale(user);
        final double minScale = minMaxScale[0];
        final double maxScale = minMaxScale[1];
        final String[] scaleLevels = scaleLevel.split(",");
        if (Double.valueOf(scaleLevels[0]) < minScale || Double.valueOf(scaleLevels[scaleLevels.length - 1]) > maxScale) {
            json.setWithParams(2, "2-3009", new Object[0]);
            return false;
        }
        final double[] minMaxSales = this.getMinMaxSales(user);
        final double minSales = minMaxSales[0];
        final double maxSales = minMaxSales[1];
        final String[] salesLevels = salesLevel.split(",");
        if (Double.valueOf(salesLevels[0]) < minSales || Double.valueOf(salesLevels[salesLevels.length - 1]) > maxSales) {
            json.setWithParams(2, "2-3009", new Object[0]);
            return false;
        }
        final double[] minMaxLoss = this.getMinMaxLoss(user);
        final double minLoss = minMaxLoss[0];
        final double maxLoss = minMaxLoss[1];
        final String[] lossLevels = lossLevel.split(",");
        if (Double.valueOf(lossLevels[0]) < minLoss || Double.valueOf(lossLevels[lossLevels.length - 1]) > maxLoss) {
            json.setWithParams(2, "2-3009", new Object[0]);
            return false;
        }
        final int[] minMaxUser = this.getMinMaxUser(user);
        final int minUser = minMaxUser[0];
        final int maxUser = minMaxUser[1];
        final String[] userLevels = userLevel.split(",");
        if (Integer.valueOf(userLevels[0]) < minUser || Integer.valueOf(userLevels[userLevels.length - 1]) > maxUser) {
            json.setWithParams(2, "2-3006", new Object[0]);
            return false;
        }
        return this.uDividendDao.updateSomeFields(id, scaleLevel, lossLevel, salesLevel, minValidUser, Double.valueOf(scaleLevels[0]), Double.valueOf(scaleLevels[scaleLevels.length - 1]), userLevel);
    }
    
    @Override
    public double[] getMinMaxScale(final User acceptUser) {
        double minScale = this.dataFactory.getDividendConfig().getLevelsScale()[0];
        double maxScale = this.dataFactory.getDividendConfig().getLevelsScale()[1];
        final User requestUser = this.uService.getById(acceptUser.getUpid());
        if (this.uCodePointUtil.isLevel2Proxy(acceptUser)) {
            return new double[] { minScale, maxScale };
        }
        final UserDividend upDividend = this.uDividendDao.getByUserId(requestUser.getId());
        if (upDividend == null) {
            return new double[] { 0.0, 0.0 };
        }
        minScale = this.dataFactory.getDividendConfig().getLevelsScale()[0];
        final String[] scaleArr = upDividend.getScaleLevel().split(",");
        maxScale = Double.valueOf(scaleArr[scaleArr.length - 1]);
        if (maxScale > this.dataFactory.getDividendConfig().getLevelsScale()[1]) {
            maxScale = this.dataFactory.getDividendConfig().getLevelsScale()[1];
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
    public double[] getMinMaxSales(final User acceptUser) {
        double minSales = this.dataFactory.getDividendConfig().getLevelsSales()[0];
        double maxSales = this.dataFactory.getDividendConfig().getLevelsSales()[1];
        final User requestUser = this.uService.getById(acceptUser.getUpid());
        if (this.uCodePointUtil.isLevel2Proxy(acceptUser)) {
            return new double[] { minSales, maxSales };
        }
        final UserDividend upDividend = this.uDividendDao.getByUserId(requestUser.getId());
        if (upDividend == null) {
            return new double[] { 0.0, 0.0 };
        }
        maxSales = this.dataFactory.getDividendConfig().getLevelsSales()[1];
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
        double minLoss = this.dataFactory.getDividendConfig().getLevelsLoss()[0];
        double maxLoss = this.dataFactory.getDividendConfig().getLevelsLoss()[1];
        final User requestUser = this.uService.getById(acceptUser.getUpid());
        if (this.uCodePointUtil.isLevel2Proxy(acceptUser)) {
            return new double[] { minLoss, maxLoss };
        }
        final UserDividend upDividend = this.uDividendDao.getByUserId(requestUser.getId());
        if (upDividend == null) {
            return new double[] { 0.0, 0.0 };
        }
        maxLoss = this.dataFactory.getDividendConfig().getLevelsLoss()[1];
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
    public boolean add(final WebJSONObject json, final String username, final String scaleLevel, final String lossLevel, final String salesLevel, final int minValidUser, final int status, final String userLevel) {
        final User user = this.uDao.getByUsername(username);
        if (!this.checkCanEdit(json, user)) {
            return false;
        }
        final UserDividend uDividend = this.uDividendDao.getByUserId(user.getId());
        if (uDividend != null) {
            json.set(2, "2-3010");
            return false;
        }
        final UserDividend upDividend = this.uDividendDao.getByUserId(user.getUpid());
        if (!this.uCodePointUtil.isLevel2Proxy(user) && (upDividend == null || upDividend.getStatus() != 1)) {
            json.set(2, "2-3011");
            return false;
        }
        if (!this.checkValidLevel(scaleLevel, salesLevel, lossLevel, upDividend, userLevel)) {
            json.setWithParams(2, "2-3009", new Object[0]);
            return false;
        }
        final double[] minMaxScale = this.getMinMaxScale(user);
        final double minScale = minMaxScale[0];
        final double maxScale = minMaxScale[1];
        final String[] scaleLevels = scaleLevel.split(",");
        if (Double.valueOf(scaleLevels[0]) < minScale || Double.valueOf(scaleLevels[scaleLevels.length - 1]) > maxScale) {
            json.setWithParams(2, "2-3009", new Object[0]);
            return false;
        }
        final double[] minMaxSales = this.getMinMaxSales(user);
        final double minSales = minMaxSales[0];
        final double maxSales = minMaxSales[1];
        final String[] salesLevels = salesLevel.split(",");
        if (Double.valueOf(salesLevels[0]) < minSales || Double.valueOf(salesLevels[salesLevels.length - 1]) > maxSales) {
            json.setWithParams(2, "2-3009", new Object[0]);
            return false;
        }
        final double[] minMaxLoss = this.getMinMaxLoss(user);
        final double minLoss = minMaxLoss[0];
        final double maxLoss = minMaxLoss[1];
        final String[] lossLevels = lossLevel.split(",");
        if (Double.valueOf(lossLevels[0]) < minLoss || Double.valueOf(lossLevels[lossLevels.length - 1]) > maxLoss) {
            json.setWithParams(2, "2-3009", new Object[0]);
            return false;
        }
        final int[] minMaxUser = this.getMinMaxUser(user);
        final int minUser = minMaxUser[0];
        final int maxUser = minMaxUser[1];
        final String[] userLevels = userLevel.split(",");
        if (Integer.valueOf(userLevels[0]) < minUser || Integer.valueOf(userLevels[userLevels.length - 1]) > maxUser) {
            json.setWithParams(2, "2-3006", new Object[0]);
            return false;
        }
        return this.add(user, scaleLevel, salesLevel, lossLevel, minValidUser, status, this.dataFactory.getDividendConfig().getFixedType(), Double.valueOf(scaleLevels[0]), Double.valueOf(scaleLevels[scaleLevels.length - 1]), "后台分红签署", userLevel);
    }
    
    @Override
    public boolean checkCanEdit(final WebJSONObject json, final User user) {
        if (user.getId() == 72) {
            json.set(2, "2-33");
            return false;
        }
        if (this.uCodePointUtil.isLevel1Proxy(user)) {
            json.set(2, "2-36");
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
        return true;
    }
    
    private boolean checkForRequest(final User acceptUser) {
        final User requestUser = this.uService.getById(acceptUser.getUpid());
        return this.allowRequestByUser(requestUser) && this.allowAccept(requestUser, acceptUser);
    }
    
    public boolean allowRequestByUser(final User user) {
        return this.dataFactory.getDividendConfig().isEnable() && user.getId() != 72 && !this.uCodePointUtil.isLevel1Proxy(user) && user.getCode() >= 1800;
    }
    
    public boolean allowAccept(final User requestUser, final User acceptUser) {
        if (!this.dataFactory.getDividendConfig().isEnable()) {
            return false;
        }
        if (acceptUser.getUpid() != requestUser.getId()) {
            return false;
        }
        if (requestUser.getId() == 72 || acceptUser.getId() == 72) {
            return false;
        }
        if (this.uCodePointUtil.isLevel1Proxy(requestUser) || this.uCodePointUtil.isLevel1Proxy(acceptUser)) {
            return false;
        }
        if (requestUser.getCode() < 1800 || acceptUser.getCode() < 1800) {
            return false;
        }
        if (requestUser.getCode() == this.dataFactory.getCodeConfig().getSysCode()) {
            if (this.uCodePointUtil.isLevel1Proxy(requestUser) || this.uCodePointUtil.isLevel1Proxy(acceptUser)) {
                return false;
            }
            if (this.uCodePointUtil.isLevel2Proxy(acceptUser)) {
                return false;
            }
            if (this.uCodePointUtil.isLevel2Proxy(requestUser)) {
                return this.uCodePointUtil.isLevel3Proxy(acceptUser);
            }
        }
        return true;
    }
    
    @Override
    public boolean checkValidLevel(final String scaleLevel, final String salesLevel, final String lossLevel, final UserDividend upDividend, final String userLevel) {
        if (!StringUtil.isNotNull(scaleLevel) || !StringUtil.isNotNull(salesLevel) || !StringUtil.isNotNull(lossLevel) || !StringUtil.isNotNull(userLevel)) {
            return false;
        }
        if (upDividend == null) {
            return this.checkStartDividendLevel(scaleLevel, salesLevel, lossLevel, userLevel);
        }
        final String[] scaleArrs = scaleLevel.split(",");
        final String[] upScaleArrs = upDividend.getScaleLevel().split(",");
        final String[] salesArrs = salesLevel.split(",");
        final String[] upSalesArrs = upDividend.getSalesLevel().split(",");
        final String[] lossArrs = lossLevel.split(",");
        final String[] upLossArrs = upDividend.getLossLevel().split(",");
        final String[] userArrs = userLevel.split(",");
        final String[] upUserArrs = upDividend.getUserLevel().split(",");
        final int maxLength = this.dataFactory.getDividendConfig().getMaxSignLevel();
        if (scaleArrs.length > maxLength || salesArrs.length > maxLength || lossArrs.length > maxLength || userArrs.length > maxLength) {
            return false;
        }
        if (scaleArrs.length != salesArrs.length || scaleArrs.length != lossArrs.length || salesArrs.length != lossArrs.length || userArrs.length != scaleArrs.length) {
            return false;
        }
        final double[] scaleConfig = this.dataFactory.getDividendConfig().getLevelsScale();
        for (int i = 0; i < scaleArrs.length; ++i) {
            final double val = Double.valueOf(scaleArrs[i]);
            if (upDividend != null) {
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
        final double[] salesConfig = this.dataFactory.getDividendConfig().getLevelsSales();
        for (int k = 0; k < salesArrs.length; ++k) {
            final double val2 = Double.valueOf(salesArrs[k]);
            if (upDividend != null) {
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
        final double[] lossConfig = this.dataFactory.getDividendConfig().getLevelsLoss();
        for (int m = 0; m < lossArrs.length; ++m) {
            final double val3 = Double.valueOf(lossArrs[m]);
            if (upDividend != null) {
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
        final int[] userConfig = { this.dataFactory.getDividendConfig().getMinValidUserl(), 1000 };
        for (int i2 = 0; i2 < userArrs.length; ++i2) {
            final int val4 = Integer.valueOf(userArrs[i2]);
            if (upDividend != null) {
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
    
    private boolean checkStartDividendLevel(final String scaleLevel, final String salesLevel, final String lossLevel, final String userLevel) {
        final String[] scaleArrs = scaleLevel.split(",");
        final String[] salesArrs = salesLevel.split(",");
        final String[] lossArrs = lossLevel.split(",");
        final String[] userArrs = userLevel.split(",");
        final int maxLength = this.dataFactory.getDividendConfig().getMaxSignLevel();
        if (scaleArrs.length > maxLength || salesArrs.length > maxLength || lossArrs.length > maxLength || userArrs.length > maxLength) {
            return false;
        }
        if (scaleArrs.length != salesArrs.length || scaleArrs.length != lossArrs.length || salesArrs.length != lossArrs.length || scaleArrs.length != userArrs.length) {
            return false;
        }
        final int[] userConfig = { this.dataFactory.getDividendConfig().getMinValidUserl(), 1000 };
        for (int i = 0; i < userArrs.length; ++i) {
            final int val = Integer.valueOf(userArrs[i]);
            if (val < userConfig[0] || val > userConfig[1]) {
                return false;
            }
            if (i > 0 && Integer.valueOf(userArrs[i - 1]) > val) {
                return false;
            }
        }
        final double[] scaleConfig = this.dataFactory.getDividendConfig().getLevelsScale();
        for (int j = 0; j < scaleArrs.length; ++j) {
            final double val2 = Double.valueOf(scaleArrs[j]);
            if (val2 < scaleConfig[0] || val2 > scaleConfig[1]) {
                return false;
            }
            if (j > 0 && Double.valueOf(scaleArrs[j - 1]) >= val2) {
                return false;
            }
        }
        final double[] salesConfig = this.dataFactory.getDividendConfig().getLevelsSales();
        for (int k = 0; k < salesArrs.length; ++k) {
            final double val3 = Double.valueOf(salesArrs[k]);
            if (val3 < salesConfig[0] || val3 > salesConfig[1]) {
                return false;
            }
        }
        final double[] lossConfig = this.dataFactory.getDividendConfig().getLevelsLoss();
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
    public int[] getMinMaxUser(final User acceptUser) {
        int minUser = this.dataFactory.getDividendConfig().getMinValidUserl();
        final int maxUser = 1000;
        final User requestUser = this.uService.getById(acceptUser.getUpid());
        if (this.uCodePointUtil.isLevel2Proxy(acceptUser)) {
            return new int[] { minUser, maxUser };
        }
        final UserDividend upDividend = this.uDividendDao.getByUserId(requestUser.getId());
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
