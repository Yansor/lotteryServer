package lottery.domains.content.biz.impl;

import admin.web.WebJSONObject;
import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.date.Moment;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.UserDividendService;
import lottery.domains.content.biz.UserService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserDividendDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserDividend;
import lottery.domains.content.vo.config.CodeConfig;
import lottery.domains.content.vo.config.DividendConfig;
import lottery.domains.content.vo.user.UserDividendVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.UserCodePointUtil;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDividendServiceImpl
  implements UserDividendService
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
  
  public PageList search(List<Integer> userIds, String sTime, String eTime, Double minScale, Double maxScale, Integer minValidUser, Integer maxValidUser, Integer status, Integer fixed, int start, int limit)
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
    if (fixed != null) {
      criterions.add(Restrictions.eq("fixed", fixed));
    }
    List<Order> orders = new ArrayList();
    orders.add(Order.desc("id"));
    PageList pList = this.uDividendDao.search(criterions, orders, start, limit);
    List<UserDividendVO> convertList = new ArrayList();
    if ((pList != null) && (pList.getList() != null)) {
      for (Object tmpBean : pList.getList()) {
        convertList.add(new UserDividendVO((UserDividend)tmpBean, this.dataFactory));
      }
    }
    pList.setList(convertList);
    return pList;
  }
  
  public UserDividend getByUserId(int userId)
  {
    return this.uDividendDao.getByUserId(userId);
  }
  
  public UserDividend getById(int id)
  {
    return this.uDividendDao.getById(id);
  }
  
  private boolean add(User user, String scaleLevel, String salesLevel, String lossLevel, int minValidUser, int status, int fixed, double minScale, double maxScale, String remarks, String userLevel)
  {
    UserDividend bean = this.uDividendDao.getByUserId(user.getId());
    if (bean == null)
    {
      int userId = user.getId();
      Moment moment = new Moment();
      String createTime = moment.toSimpleTime();
      String createDate = moment.toSimpleDate();
      String endDate = moment.add(99, "years").toSimpleDate();
      String agreeTime = status == 2 ? "" : createTime;
      UserDividend entity = new UserDividend(userId, scaleLevel, lossLevel, salesLevel, minValidUser, createTime, 
        agreeTime, createDate, endDate, 0.0D, 0.0D, status, fixed, minScale, maxScale, remarks, userLevel);
      this.uDividendDao.add(entity);
      return true;
    }
    return false;
  }
  
  public boolean deleteByTeam(String username)
  {
    User uBean = this.uService.getByUsername(username);
    if (uBean != null)
    {
      this.uDividendDao.deleteByTeam(uBean.getId());
      return true;
    }
    return false;
  }
  
  public boolean changeZhaoShang(User user, boolean changeToCJZhaoShang)
  {
    return false;
  }
  
  public void checkDividend(String username)
  {
    User user = this.uDao.getByUsername(username);
    if (user.getId() == 72) {
      return;
    }
    this.uDividendDao.deleteByTeam(user.getId());
  }
  
  private void adjustDividend1990(User user)
  {
    UserDividend uDividend = this.uDividendDao.getByUserId(user.getId());
    
    int minValidUser = this.dataFactory.getDividendConfig().getZhaoShangMinValidUser();
    int fixed = this.dataFactory.getDividendConfig().getFixedType();
    int status = 1;
    String scaleLevel = this.dataFactory.getDividendConfig().getZhaoShangScaleLevels();
    String[] scaleLevelArr = scaleLevel.split(",");
    String lossLevel = this.dataFactory.getDividendConfig().getZhaoShangLossLevels();
    String salesLevel = this.dataFactory.getDividendConfig().getZhaoShangSalesLevels();
    double minScale = Double.valueOf(scaleLevelArr[0]).doubleValue();
    double maxScale = Double.valueOf(scaleLevelArr[(scaleLevelArr.length - 1)]).doubleValue();
    String remarks = "自动分红配置";
    if (uDividend != null)
    {
      int id = uDividend.getId();
      this.uDividendDao.updateSomeFields(id, scaleLevel, lossLevel, salesLevel, minValidUser, fixed, minScale, 
        maxScale, status);
    }
    else
    {
      add(user, scaleLevel, salesLevel, lossLevel, minValidUser, status, fixed, minScale, maxScale, remarks, "");
    }
    this.uDividendDao.deleteLowers(user.getId());
  }
  
  public boolean update(WebJSONObject json, int id, String scaleLevel, String lossLevel, String salesLevel, int minValidUser, String userLevel)
  {
    UserDividend dividend = this.uDividendDao.getById(id);
    if (dividend == null)
    {
      json.set(Integer.valueOf(1), "1-7");
      return false;
    }
    if (dividend.getStatus() == 1) {
      return false;
    }
    if ((dividend.getScaleLevel().equals(scaleLevel)) && (dividend.getSalesLevel().equals(salesLevel)) && 
      (dividend.getLossLevel().equals(lossLevel)) && (dividend.getUserLevel().equals(userLevel)))
    {
      json.set(Integer.valueOf(1), "2-29");
      return false;
    }
    User user = this.uDao.getById(dividend.getUserId());
    UserDividend upDividend = this.uDividendDao.getByUserId(user.getUpid());
    if ((!this.uCodePointUtil.isLevel2Proxy(user)) && ((upDividend == null) || (upDividend.getStatus() != 1)))
    {
      json.set(Integer.valueOf(2), "2-3011");
      return false;
    }
    if (!checkValidLevel(scaleLevel, salesLevel, lossLevel, upDividend, userLevel))
    {
      json.setWithParams(Integer.valueOf(2), "2-3009", new Object[0]);
      return false;
    }
    if (!checkCanEdit(json, user)) {
      return false;
    }
    double[] minMaxScale = getMinMaxScale(user);
    double minScale = minMaxScale[0];
    double maxScale = minMaxScale[1];
    String[] scaleLevels = scaleLevel.split(",");
    if ((Double.valueOf(scaleLevels[0]).doubleValue() < minScale) || 
      (Double.valueOf(scaleLevels[(scaleLevels.length - 1)]).doubleValue() > maxScale))
    {
      json.setWithParams(Integer.valueOf(2), "2-3009", new Object[0]);
      return false;
    }
    double[] minMaxSales = getMinMaxSales(user);
    double minSales = minMaxSales[0];
    double maxSales = minMaxSales[1];
    String[] salesLevels = salesLevel.split(",");
    if ((Double.valueOf(salesLevels[0]).doubleValue() < minSales) || 
      (Double.valueOf(salesLevels[(salesLevels.length - 1)]).doubleValue() > maxSales))
    {
      json.setWithParams(Integer.valueOf(2), "2-3009", new Object[0]);
      return false;
    }
    double[] minMaxLoss = getMinMaxLoss(user);
    double minLoss = minMaxLoss[0];
    double maxLoss = minMaxLoss[1];
    String[] lossLevels = lossLevel.split(",");
    if ((Double.valueOf(lossLevels[0]).doubleValue() < minLoss) || (Double.valueOf(lossLevels[(lossLevels.length - 1)]).doubleValue() > maxLoss))
    {
      json.setWithParams(Integer.valueOf(2), "2-3009", new Object[0]);
      return false;
    }
    int[] minMaxUser = getMinMaxUser(user);
    int minUser = minMaxUser[0];
    int maxUser = minMaxUser[1];
    String[] userLevels = userLevel.split(",");
    if ((Integer.valueOf(userLevels[0]).intValue() < minUser) || (Integer.valueOf(userLevels[(userLevels.length - 1)]).intValue() > maxUser))
    {
      json.setWithParams(Integer.valueOf(2), "2-3006", new Object[0]);
      return false;
    }
    return this.uDividendDao.updateSomeFields(id, scaleLevel, lossLevel, salesLevel, minValidUser, 
      Double.valueOf(scaleLevels[0]).doubleValue(), Double.valueOf(scaleLevels[(scaleLevels.length - 1)]).doubleValue(), userLevel);
  }
  
  public double[] getMinMaxScale(User acceptUser)
  {
    double minScale = this.dataFactory.getDividendConfig().getLevelsScale()[0];
    
    double maxScale = this.dataFactory.getDividendConfig().getLevelsScale()[1];
    
    User requestUser = this.uService.getById(acceptUser.getUpid());
    if (this.uCodePointUtil.isLevel2Proxy(acceptUser)) {
      return new double[] { minScale, maxScale };
    }
    UserDividend upDividend = this.uDividendDao.getByUserId(requestUser.getId());
    if (upDividend == null) {
      return new double[] { 0.0D, 0.0D };
    }
    minScale = this.dataFactory.getDividendConfig().getLevelsScale()[0];
    
    String[] scaleArr = upDividend.getScaleLevel().split(",");
    maxScale = Double.valueOf(scaleArr[(scaleArr.length - 1)]).doubleValue();
    if (maxScale > this.dataFactory.getDividendConfig().getLevelsScale()[1]) {
      maxScale = this.dataFactory.getDividendConfig().getLevelsScale()[1];
    }
    if (minScale < 0.0D) {
      minScale = 0.0D;
    }
    if (maxScale < 0.0D) {
      maxScale = 0.0D;
    }
    return new double[] { minScale, maxScale };
  }
  
  public double[] getMinMaxSales(User acceptUser)
  {
    double minSales = this.dataFactory.getDividendConfig().getLevelsSales()[0];
    double maxSales = this.dataFactory.getDividendConfig().getLevelsSales()[1];
    
    User requestUser = this.uService.getById(acceptUser.getUpid());
    if (this.uCodePointUtil.isLevel2Proxy(acceptUser)) {
      return new double[] { minSales, maxSales };
    }
    UserDividend upDividend = this.uDividendDao.getByUserId(requestUser.getId());
    if (upDividend == null) {
      return new double[] { 0.0D, 0.0D };
    }
    maxSales = this.dataFactory.getDividendConfig().getLevelsSales()[1];
    
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
    double minLoss = this.dataFactory.getDividendConfig().getLevelsLoss()[0];
    double maxLoss = this.dataFactory.getDividendConfig().getLevelsLoss()[1];
    
    User requestUser = this.uService.getById(acceptUser.getUpid());
    if (this.uCodePointUtil.isLevel2Proxy(acceptUser)) {
      return new double[] { minLoss, maxLoss };
    }
    UserDividend upDividend = this.uDividendDao.getByUserId(requestUser.getId());
    if (upDividend == null) {
      return new double[] { 0.0D, 0.0D };
    }
    maxLoss = this.dataFactory.getDividendConfig().getLevelsLoss()[1];
    
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
  
  public boolean add(WebJSONObject json, String username, String scaleLevel, String lossLevel, String salesLevel, int minValidUser, int status, String userLevel)
  {
    User user = this.uDao.getByUsername(username);
    if (!checkCanEdit(json, user)) {
      return false;
    }
    UserDividend uDividend = this.uDividendDao.getByUserId(user.getId());
    if (uDividend != null)
    {
      json.set(Integer.valueOf(2), "2-3010");
      return false;
    }
    UserDividend upDividend = this.uDividendDao.getByUserId(user.getUpid());
    if ((!this.uCodePointUtil.isLevel2Proxy(user)) && ((upDividend == null) || (upDividend.getStatus() != 1)))
    {
      json.set(Integer.valueOf(2), "2-3011");
      return false;
    }
    if (!checkValidLevel(scaleLevel, salesLevel, lossLevel, upDividend, userLevel))
    {
      json.setWithParams(Integer.valueOf(2), "2-3009", new Object[0]);
      return false;
    }
    double[] minMaxScale = getMinMaxScale(user);
    double minScale = minMaxScale[0];
    double maxScale = minMaxScale[1];
    String[] scaleLevels = scaleLevel.split(",");
    if ((Double.valueOf(scaleLevels[0]).doubleValue() < minScale) || 
      (Double.valueOf(scaleLevels[(scaleLevels.length - 1)]).doubleValue() > maxScale))
    {
      json.setWithParams(Integer.valueOf(2), "2-3009", new Object[0]);
      return false;
    }
    double[] minMaxSales = getMinMaxSales(user);
    double minSales = minMaxSales[0];
    double maxSales = minMaxSales[1];
    String[] salesLevels = salesLevel.split(",");
    if ((Double.valueOf(salesLevels[0]).doubleValue() < minSales) || 
      (Double.valueOf(salesLevels[(salesLevels.length - 1)]).doubleValue() > maxSales))
    {
      json.setWithParams(Integer.valueOf(2), "2-3009", new Object[0]);
      return false;
    }
    double[] minMaxLoss = getMinMaxLoss(user);
    double minLoss = minMaxLoss[0];
    double maxLoss = minMaxLoss[1];
    String[] lossLevels = lossLevel.split(",");
    if ((Double.valueOf(lossLevels[0]).doubleValue() < minLoss) || (Double.valueOf(lossLevels[(lossLevels.length - 1)]).doubleValue() > maxLoss))
    {
      json.setWithParams(Integer.valueOf(2), "2-3009", new Object[0]);
      return false;
    }
    int[] minMaxUser = getMinMaxUser(user);
    int minUser = minMaxUser[0];
    int maxUser = minMaxUser[1];
    String[] userLevels = userLevel.split(",");
    if ((Integer.valueOf(userLevels[0]).intValue() < minUser) || (Integer.valueOf(userLevels[(userLevels.length - 1)]).intValue() > maxUser))
    {
      json.setWithParams(Integer.valueOf(2), "2-3006", new Object[0]);
      return false;
    }
    return add(user, scaleLevel, salesLevel, lossLevel, minValidUser, status, 
      this.dataFactory.getDividendConfig().getFixedType(), Double.valueOf(scaleLevels[0]).doubleValue(), 
      Double.valueOf(scaleLevels[(scaleLevels.length - 1)]).doubleValue(), "后台分红签署", userLevel);
  }
  
  public boolean checkCanEdit(WebJSONObject json, User user)
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
    if (!this.dataFactory.getDividendConfig().isEnable()) {
      return false;
    }
    if (user.getId() == 72) {
      return false;
    }
    if ((this.uCodePointUtil.isLevel1Proxy(user)) || (user.getCode() < 1800)) {
      return false;
    }
    return true;
  }
  
  public boolean allowAccept(User requestUser, User acceptUser)
  {
    if (!this.dataFactory.getDividendConfig().isEnable()) {
      return false;
    }
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
    if (requestUser.getCode() == this.dataFactory.getCodeConfig().getSysCode())
    {
      if ((this.uCodePointUtil.isLevel1Proxy(requestUser)) || (this.uCodePointUtil.isLevel1Proxy(acceptUser))) {
        return false;
      }
      if (this.uCodePointUtil.isLevel2Proxy(acceptUser)) {
        return false;
      }
      if (this.uCodePointUtil.isLevel2Proxy(requestUser))
      {
        if (this.uCodePointUtil.isLevel3Proxy(acceptUser)) {
          return true;
        }
        return false;
      }
    }
    return true;
  }
  
  public boolean checkValidLevel(String scaleLevel, String salesLevel, String lossLevel, UserDividend upDividend, String userLevel)
  {
    if ((!StringUtil.isNotNull(scaleLevel)) || (!StringUtil.isNotNull(salesLevel)) || (!StringUtil.isNotNull(lossLevel)) || (!StringUtil.isNotNull(userLevel))) {
      return false;
    }
    if (upDividend == null) {
      return checkStartDividendLevel(scaleLevel, salesLevel, lossLevel, userLevel);
    }
    String[] scaleArrs = scaleLevel.split(",");
    String[] upScaleArrs = upDividend.getScaleLevel().split(",");
    
    String[] salesArrs = salesLevel.split(",");
    String[] upSalesArrs = upDividend.getSalesLevel().split(",");
    
    String[] lossArrs = lossLevel.split(",");
    String[] upLossArrs = upDividend.getLossLevel().split(",");
    
    String[] userArrs = userLevel.split(",");
    String[] upUserArrs = upDividend.getUserLevel().split(",");
    
    int maxLength = this.dataFactory.getDividendConfig().getMaxSignLevel();
    if ((scaleArrs.length > maxLength) || (salesArrs.length > maxLength) || (lossArrs.length > maxLength) || (userArrs.length > maxLength)) {
      return false;
    }
    if ((scaleArrs.length != salesArrs.length) || (scaleArrs.length != lossArrs.length) || 
      (salesArrs.length != lossArrs.length) || (userArrs.length != scaleArrs.length)) {
      return false;
    }
    double[] scaleConfig = this.dataFactory.getDividendConfig().getLevelsScale();
    int upIndex;
    double tmUpSales;
    double tmUpScale;
    for (int i = 0; i < scaleArrs.length; i++)
    {
      double val = Double.valueOf(scaleArrs[i]).doubleValue();
      if (upDividend != null)
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
    double[] salesConfig = this.dataFactory.getDividendConfig().getLevelsSales();
    double ll;
    for (int i = 0; i < salesArrs.length; i++)
    {
      double val = Double.valueOf(salesArrs[i]).doubleValue();
      if (upDividend != null)
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
    double[] lossConfig = this.dataFactory.getDividendConfig().getLevelsLoss();
    String l;
    for (int i = 0; i < lossArrs.length; i++)
    {
      double val = Double.valueOf(lossArrs[i]).doubleValue();
      if (upDividend != null)
      {
        double minLoss = Double.valueOf(upLossArrs[0]).doubleValue();
        tmUpSales = upLossArrs.length;
        for (tmUpScale = 0; tmUpScale < tmUpSales; tmUpScale++)
        {
          l = upLossArrs[(int)tmUpScale];
           ll = Double.valueOf(l).doubleValue();
          if (ll < minLoss) {
            minLoss = ll;
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
    int[] userConfig = { this.dataFactory.getDividendConfig().getMinValidUserl(), 1000 };
    for (int i = 0; i < userArrs.length; i++)
    {
      int val = Integer.valueOf(userArrs[i]).intValue();
      if (upDividend != null)
      {
        int minUser = Integer.valueOf(upUserArrs[0]).intValue();
        tmUpScale = upUserArrs.length;
        for (int li = 0; li < tmUpScale; li++)
        {
          String lValue = upUserArrs[li];
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
  
  private boolean checkStartDividendLevel(String scaleLevel, String salesLevel, String lossLevel, String userLevel)
  {
    String[] scaleArrs = scaleLevel.split(",");
    
    String[] salesArrs = salesLevel.split(",");
    
    String[] lossArrs = lossLevel.split(",");
    
    String[] userArrs = userLevel.split(",");
    
    int maxLength = this.dataFactory.getDividendConfig().getMaxSignLevel();
    if ((scaleArrs.length > maxLength) || (salesArrs.length > maxLength) || (lossArrs.length > maxLength) || (userArrs.length > maxLength)) {
      return false;
    }
    if ((scaleArrs.length != salesArrs.length) || (scaleArrs.length != lossArrs.length) || (salesArrs.length != lossArrs.length) || (scaleArrs.length != userArrs.length)) {
      return false;
    }
    int[] userConfig = { this.dataFactory.getDividendConfig().getMinValidUserl(), 1000 };
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
    double[] scaleConfig = this.dataFactory.getDividendConfig().getLevelsScale();
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
    double[] salesConfig = this.dataFactory.getDividendConfig().getLevelsSales();
    for (int i = 0; i < salesArrs.length; i++)
    {
      double val = Double.valueOf(salesArrs[i]).doubleValue();
      if ((val < salesConfig[0]) || (val > salesConfig[1])) {
        return false;
      }
    }
    double[] lossConfig = this.dataFactory.getDividendConfig().getLevelsLoss();
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
  
  public int[] getMinMaxUser(User acceptUser)
  {
    int minUser = this.dataFactory.getDividendConfig().getMinValidUserl();
    int maxUser = 1000;
    
    User requestUser = this.uService.getById(acceptUser.getUpid());
    if (this.uCodePointUtil.isLevel2Proxy(acceptUser)) {
      return new int[] { minUser, maxUser };
    }
    UserDividend upDividend = this.uDividendDao.getByUserId(requestUser.getId());
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
