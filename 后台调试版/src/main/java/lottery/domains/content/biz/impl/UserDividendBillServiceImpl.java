package lottery.domains.content.biz.impl;

import admin.web.WebJSONObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javautils.StringUtil;
import javautils.array.ArrayUtils;
import javautils.jdbc.PageList;
import javautils.math.MathUtil;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserDividendBillService;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserDividendBillDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserDividendBill;
import lottery.domains.content.vo.user.UserDividendBillVO;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.UserCodePointUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDividendBillServiceImpl
  implements UserDividendBillService
{
  @Autowired
  private UserDividendBillDao uDividendBillDao;
  @Autowired
  private LotteryDataFactory dataFactory;
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserBillService uBillService;
  @Autowired
  private UserCodePointUtil uCodePointUtil;
  @Autowired
  private UserSysMessageService uSysMessageService;
  
  public PageList search(List<Integer> userIds, String sTime, String eTime, Double minUserAmount, Double maxUserAmount, Integer status, Integer issueType, int start, int limit)
  {
    start = start < 0 ? 0 : start;
    limit = limit < 0 ? 0 : limit;
    limit = limit > 20 ? 20 : limit;
    
    List<Criterion> criterions = new ArrayList();
    if (CollectionUtils.isNotEmpty(userIds)) {
      criterions.add(Restrictions.in("userId", userIds));
    }
    if (StringUtil.isNotNull(sTime)) {
      criterions.add(Restrictions.ge("indicateStartDate", sTime));
    }
    if (StringUtil.isNotNull(eTime)) {
      criterions.add(Restrictions.le("indicateEndDate", eTime));
    }
    if (minUserAmount != null) {
      criterions.add(Restrictions.ge("userAmount", minUserAmount));
    }
    if (maxUserAmount != null) {
      criterions.add(Restrictions.le("userAmount", maxUserAmount));
    }
    if (status != null) {
      criterions.add(Restrictions.eq("status", status));
    }
    if (issueType != null) {
      criterions.add(Restrictions.eq("issueType", issueType));
    }
    List<Order> orders = new ArrayList();
    orders.add(Order.desc("id"));
    PageList pList = this.uDividendBillDao.search(criterions, orders, start, limit);
    List<UserDividendBillVO> voList = new ArrayList();
    if ((pList != null) && (pList.getList() != null)) {
      for (Object tmpBean : pList.getList()) {
        voList.add(new UserDividendBillVO((UserDividendBill)tmpBean, this.dataFactory));
      }
    }
    pList.setList(voList);
    return pList;
  }
  
  public PageList searchPlatformLoss(List<Integer> userIds, String sTime, String eTime, Double minUserAmount, Double maxUserAmount, int start, int limit)
  {
    start = start < 0 ? 0 : start;
    limit = limit < 0 ? 0 : limit;
    limit = limit > 20 ? 20 : limit;
    
    List<Criterion> criterions = new ArrayList();
    if (CollectionUtils.isNotEmpty(userIds)) {
      criterions.add(Restrictions.in("userId", userIds));
    }
    if (StringUtil.isNotNull(sTime)) {
      criterions.add(Restrictions.ge("indicateStartDate", sTime));
    }
    if (StringUtil.isNotNull(eTime)) {
      criterions.add(Restrictions.le("indicateEndDate", eTime));
    }
    if (minUserAmount != null) {
      criterions.add(Restrictions.ge("userAmount", minUserAmount));
    }
    if (maxUserAmount != null) {
      criterions.add(Restrictions.le("userAmount", maxUserAmount));
    }
    criterions.add(Restrictions.eq("issueType", Integer.valueOf(1)));
    
    criterions.add(Restrictions.lt("totalLoss", Double.valueOf(0.0D)));
    
    List<Integer> status = new ArrayList();
    status.add(Integer.valueOf(1));
    status.add(Integer.valueOf(2));
    status.add(Integer.valueOf(3));
    status.add(Integer.valueOf(6));
    criterions.add(Restrictions.in("status", status));
    
    List<Order> orders = new ArrayList();
    orders.add(Order.desc("id"));
    PageList pList = this.uDividendBillDao.search(criterions, orders, start, limit);
    List<UserDividendBillVO> voList = new ArrayList();
    if ((pList != null) && (pList.getList() != null)) {
      for (Object tmpBean : pList.getList()) {
        voList.add(new UserDividendBillVO((UserDividendBill)tmpBean, this.dataFactory));
      }
    }
    voList = convertUserLevels(voList);
    
    pList.setList(voList);
    return pList;
  }
  
  private List<UserDividendBillVO> convertUserLevels(List<UserDividendBillVO> voList)
  {
    Set<Integer> userIds = new HashSet();
    for (UserDividendBillVO userDividendBillVO : voList) {
      userIds.add(Integer.valueOf(userDividendBillVO.getBean().getUserId()));
    }
    List<Criterion> criterions = new ArrayList();
    criterions.add(Restrictions.in("id", userIds));
    
    Object users = this.uDao.list(criterions, null);
    Iterator localIterator3;
    for (Iterator localIterator2 = voList.iterator(); localIterator2.hasNext(); localIterator3.hasNext())
    {
      UserDividendBillVO userDividendBillVO = (UserDividendBillVO)localIterator2.next();
      localIterator3 = ((List)users).iterator();
      User user = (User)localIterator3.next();
      if (userDividendBillVO.getBean().getUserId() == user.getId())
      {
        int[] upIds = ArrayUtils.transGetIds(user.getUpids());
        int[] arrayOfInt1;
        int j = (arrayOfInt1 = upIds).length;
        for (int i = 0; i < j; i++)
        {
          int upId = arrayOfInt1[i];
          UserVO upUser = this.dataFactory.getUser(upId);
          if (upUser != null) {
            userDividendBillVO.getUserLevels().add(upUser.getUsername());
          }
        }
      }
    }
    return voList;
  }
  
  public double[] sumUserAmount(List<Integer> userIds, String sTime, String eTime, Double minUserAmount, Double maxUserAmount)
  {
    return this.uDividendBillDao.sumUserAmount(userIds, sTime, eTime, minUserAmount, maxUserAmount);
  }
  
  public List<UserDividendBill> findByCriteria(List<Criterion> criterions, List<Order> orders)
  {
    return this.uDividendBillDao.findByCriteria(criterions, orders);
  }
  
  public boolean updateAllExpire()
  {
    return this.uDividendBillDao.updateAllExpire();
  }
  
  public List<UserDividendBill> getLowerBills(int userId, String sTime, String eTime)
  {
    List<User> userLowers = this.uDao.getUserLower(userId);
    if (CollectionUtils.isEmpty(userLowers)) {
      return new ArrayList();
    }
    List<Integer> userIds = new ArrayList();
    
    List<Criterion> criterions = new ArrayList();
    if (CollectionUtils.isNotEmpty(userLowers))
    {
      for (User userLower : userLowers) {
        userIds.add(Integer.valueOf(userLower.getId()));
      }
      criterions.add(Restrictions.in("userId", userIds));
    }
    if (CollectionUtils.isEmpty(userIds)) {
      return new ArrayList();
    }
    if (StringUtil.isNotNull(sTime)) {
      criterions.add(Restrictions.ge("indicateStartDate", sTime));
    }
    if (StringUtil.isNotNull(eTime)) {
      criterions.add(Restrictions.le("indicateEndDate", eTime));
    }
    return this.uDividendBillDao.findByCriteria(criterions, null);
  }
  
  public List<UserDividendBill> getDirectLowerBills(int userId, String sTime, String eTime)
  {
    List<User> userLowers = this.uDao.getUserDirectLower(userId);
    if (CollectionUtils.isEmpty(userLowers)) {
      return new ArrayList();
    }
    List<Integer> userIds = new ArrayList();
    
    List<Criterion> criterions = new ArrayList();
    if (CollectionUtils.isNotEmpty(userLowers))
    {
      for (User userLower : userLowers) {
        userIds.add(Integer.valueOf(userLower.getId()));
      }
      criterions.add(Restrictions.in("userId", userIds));
    }
    if (CollectionUtils.isEmpty(userIds)) {
      return new ArrayList();
    }
    if (StringUtil.isNotNull(sTime)) {
      criterions.add(Restrictions.ge("indicateStartDate", sTime));
    }
    if (StringUtil.isNotNull(eTime)) {
      criterions.add(Restrictions.le("indicateEndDate", eTime));
    }
    return this.uDividendBillDao.findByCriteria(criterions, null);
  }
  
  public UserDividendBill getById(int id)
  {
    return this.uDividendBillDao.getById(id);
  }
  
  public UserDividendBill getBill(int userId, String sTime, String eTime)
  {
    List<Criterion> criterions = new ArrayList();
    criterions.add(Restrictions.eq("userId", Integer.valueOf(userId)));
    if (StringUtil.isNotNull(sTime)) {
      criterions.add(Restrictions.ge("indicateStartDate", sTime));
    }
    if (StringUtil.isNotNull(eTime)) {
      criterions.add(Restrictions.le("indicateEndDate", eTime));
    }
    List<Order> orders = new ArrayList();
    orders.add(Order.desc("id"));
    
    PageList pList = this.uDividendBillDao.search(criterions, orders, 0, 1);
    if ((pList != null) && (CollectionUtils.isNotEmpty(pList.getList()))) {
      return (UserDividendBill)pList.getList().get(0);
    }
    return null;
  }
  
  public boolean add(UserDividendBill dividendBill)
  {
    return this.uDividendBillDao.add(dividendBill);
  }
  
  public boolean addAvailableMoney(int id, double money)
  {
    return this.uDividendBillDao.addAvailableMoney(id, money);
  }
  
  public synchronized void issueInsufficient(int id)
  {
    UserDividendBill dividendBill = getById(id);
    if (dividendBill.getStatus() != 6) {
      return;
    }
    if (dividendBill.getLowerTotalAmount() <= 0.0D) {
      return;
    }
    double upperBillMoney = 0.0D;
    double upperLowerTotalAmount = dividendBill.getLowerTotalAmount();
    double upperLowerPaidAmount = dividendBill.getLowerPaidAmount();
    if (dividendBill.getIssueType() != 1) {
      upperBillMoney = dividendBill.getAvailableAmount();
    }
    double upperStillNotPay = MathUtil.subtract(upperLowerTotalAmount, upperLowerPaidAmount);
    if (upperStillNotPay <= 0.0D)
    {
      if (upperBillMoney > 0.0D) {
        this.uDividendBillDao.updateStatus(dividendBill.getId(), 3);
      } else {
        this.uDividendBillDao.updateStatus(dividendBill.getId(), 1);
      }
      return;
    }
    double upperThisTimePaid = 0.0D;
    List<UserDividendBill> directLowerBills = getDirectLowerBills(dividendBill.getUserId(), dividendBill.getIndicateStartDate(), dividendBill.getIndicateEndDate());
    for (UserDividendBill directLowerBill : directLowerBills) {
      if ((directLowerBill.getStatus() == 3) || 
        (directLowerBill.getStatus() == 6) || 
        (directLowerBill.getStatus() == 7))
      {
        double lowerCalAmount = directLowerBill.getUserAmount();
        if (directLowerBill.getStatus() == 6) {
          lowerCalAmount = MathUtil.add(lowerCalAmount, directLowerBill.getCalAmount());
        }
        if (lowerCalAmount > 0.0D)
        {
          double lowerReceived = MathUtil.add(directLowerBill.getAvailableAmount(), directLowerBill.getTotalReceived());
          
          double lowerRemainReceived = MathUtil.subtract(lowerCalAmount, lowerReceived);
          if (lowerRemainReceived > 0.1D)
          {
            double billGive = 0.0D;
            if ((upperBillMoney > 0.0D) && (lowerRemainReceived > 0.0D))
            {
              billGive = lowerRemainReceived >= upperBillMoney ? upperBillMoney : lowerRemainReceived;
              upperBillMoney = MathUtil.subtract(upperBillMoney, billGive);
              lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, billGive);
              upperStillNotPay = MathUtil.subtract(upperStillNotPay, billGive);
            }
            double totalMoneyGive = 0.0D;
            User upUser = this.uDao.getById(dividendBill.getUserId());
            if ((lowerRemainReceived > 0.0D) && (upperStillNotPay > 0.0D) && (upUser.getTotalMoney() > 0.0D))
            {
              totalMoneyGive = lowerRemainReceived >= upUser.getTotalMoney() ? upUser.getTotalMoney() : lowerRemainReceived;
              lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, totalMoneyGive);
              upperStillNotPay = MathUtil.subtract(upperStillNotPay, totalMoneyGive);
            }
            double lotteryMoneyGive = 0.0D;
            if ((lowerRemainReceived > 0.0D) && (upperStillNotPay > 0.0D) && (upUser.getLotteryMoney() > 0.0D))
            {
              lotteryMoneyGive = lowerRemainReceived >= upUser.getLotteryMoney() ? upUser.getLotteryMoney() : lowerRemainReceived;
              lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, lotteryMoneyGive);
              upperStillNotPay = MathUtil.subtract(upperStillNotPay, lotteryMoneyGive);
            }
            double totalGive = MathUtil.add(MathUtil.add(billGive, totalMoneyGive), lotteryMoneyGive);
            if (totalGive <= 0.0D) {
              break;
            }
            if (totalMoneyGive > 0.0D)
            {
              UserVO subUser = this.dataFactory.getUser(directLowerBill.getUserId());
              this.uDao.updateTotalMoney(upUser.getId(), -totalMoneyGive);
              this.uBillService.addDividendBill(upUser, 1, -totalMoneyGive, "系统自动扣发" + totalMoneyGive + "分红金额到" + subUser.getUsername(), false);
            }
            if (lotteryMoneyGive > 0.0D)
            {
              UserVO subUser = this.dataFactory.getUser(directLowerBill.getUserId());
              this.uDao.updateLotteryMoney(upUser.getId(), -lotteryMoneyGive);
              this.uBillService.addDividendBill(upUser, 2, -lotteryMoneyGive, "系统自动扣发" + lotteryMoneyGive + "分红金额到" + subUser.getUsername(), false);
            }
            upperThisTimePaid = MathUtil.add(upperThisTimePaid, totalGive);
            
            this.uDividendBillDao.addAvailableMoney(directLowerBill.getId(), totalGive);
            if (directLowerBill.getStatus() == 6) {
              this.uDividendBillDao.addTotalReceived(directLowerBill.getId(), totalGive);
            }
          }
        }
      }
    }
    if (upperThisTimePaid > 0.0D) {
      this.uDividendBillDao.addLowerPaidAmount(dividendBill.getId(), upperThisTimePaid);
    }
    if ((dividendBill.getIssueType() == 1) && 
      (upperStillNotPay <= 0.1D))
    {
      this.uDividendBillDao.update(dividendBill.getId(), 1, "");
    }
    else if (dividendBill.getIssueType() == 2)
    {
      double upperRemains = MathUtil.subtract(dividendBill.getAvailableAmount(), upperThisTimePaid);
      if (upperRemains > 0.0D) {
        this.uDividendBillDao.update(dividendBill.getId(), 3, upperRemains, "");
      } else {
        this.uDividendBillDao.setAvailableMoney(dividendBill.getId(), 0.0D);
      }
    }
  }
  
  public boolean agree(WebJSONObject json, int id, String remarks)
  {
    UserDividendBill dividendBill = getById(id);
    if (dividendBill.getStatus() != 2)
    {
      json.set(Integer.valueOf(2), "2-3004");
      return false;
    }
    if (dividendBill.getIssueType() == 2)
    {
      User user = this.uDao.getById(dividendBill.getUserId());
      
      UserDividendBill bill = getBill(user.getUpid(), dividendBill.getIndicateStartDate(), dividendBill.getIndicateEndDate());
      if ((bill != null) && (bill.getStatus() == 2))
      {
        UserVO upper = this.dataFactory.getUser(user.getUpid());
        json.setWithParams(Integer.valueOf(2), "2-3003", new Object[] { upper == null ? "" : upper.getUsername() });
        return false;
      }
    }
    boolean updated = false;
    if (dividendBill.getLowerTotalAmount() > 0.0D)
    {
      double[] result = agreeProcess(id);
      double stillNotPay = result[0];
      double availableAmount = result[1];
      double upperNotYetPay = result[2];
      stillNotPay -= 0.1D;
      if (stillNotPay > 0.0D)
      {
        String _remarks = remarks;
        if (StringUtils.isEmpty(_remarks)) {
          _remarks = "余额不足，请充值";
        }
        updated = this.uDividendBillDao.update(id, 6, _remarks);
      }
      else if (availableAmount <= 0.0D)
      {
        if (upperNotYetPay > 0.0D) {
          updated = this.uDividendBillDao.update(id, 7, remarks);
        } else {
          updated = this.uDividendBillDao.update(id, 1, remarks);
        }
      }
      else if (availableAmount > 0.0D)
      {
        updated = this.uDividendBillDao.update(id, 3, remarks);
      }
    }
    else
    {
      if (dividendBill.getIssueType() == 1) {
        this.uDividendBillDao.addAvailableMoney(id, dividendBill.getCalAmount());
      }
      updated = this.uDividendBillDao.update(id, 3, remarks);
    }
    if (updated) {
      this.uSysMessageService.addDividendBill(dividendBill.getUserId(), dividendBill.getIndicateStartDate(), dividendBill.getIndicateEndDate());
    }
    return updated;
  }
  
  private double[] agreeProcess(int id)
  {
    UserDividendBill dividendBill = getById(id);
    if ((dividendBill.getStatus() != 2) && 
      (dividendBill.getStatus() != 3) && 
      (dividendBill.getStatus() != 6) && 
      (dividendBill.getStatus() != 7)) {
      return new double[] { 0.0D, 0.0D };
    }
    if (dividendBill.getLowerTotalAmount() <= 0.0D)
    {
      if (dividendBill.getIssueType() == 1) {
        this.uDividendBillDao.addAvailableMoney(id, dividendBill.getCalAmount());
      }
      return new double[] { 0.0D, dividendBill.getCalAmount() };
    }
    double upperLowerTotalAmount = dividendBill.getLowerTotalAmount();
    double upperLowerPaidAmount = dividendBill.getLowerPaidAmount();
    double upperBillMoney;
    if (dividendBill.getIssueType() == 1) {
      upperBillMoney = dividendBill.getCalAmount();
    } else {
      upperBillMoney = dividendBill.getAvailableAmount();
    }
    double upperStillNotPay = MathUtil.subtract(upperLowerTotalAmount, upperLowerPaidAmount);
    if (upperStillNotPay <= 0.0D)
    {
      if (dividendBill.getIssueType() == 1) {
        this.uDividendBillDao.addAvailableMoney(id, upperBillMoney);
      }
      return new double[] { 0.0D, upperBillMoney };
    }
    double upperThisTimePaid = 0.0D;
    List<UserDividendBill> directLowerBills = getDirectLowerBills(dividendBill.getUserId(), dividendBill.getIndicateStartDate(), dividendBill.getIndicateEndDate());
    double accountPayout = 0.0D;
    for (UserDividendBill directLowerBill : directLowerBills) {
      if ((directLowerBill.getStatus() == 2) || 
        (directLowerBill.getStatus() == 3) || 
        (directLowerBill.getStatus() == 6) || 
        (directLowerBill.getStatus() == 7))
      {
        double lowerCalAmount = directLowerBill.getCalAmount();
        if (lowerCalAmount > 0.0D)
        {
          double lowerReceived = MathUtil.add(directLowerBill.getAvailableAmount(), directLowerBill.getTotalReceived());
          
          double lowerRemainReceived = MathUtil.subtract(lowerCalAmount, lowerReceived);
          if (lowerRemainReceived > 0.1D)
          {
            double billGive = 0.0D;
            if ((upperBillMoney > 0.0D) && (lowerRemainReceived > 0.0D))
            {
              billGive = lowerRemainReceived >= upperBillMoney ? upperBillMoney : lowerRemainReceived;
              upperBillMoney = MathUtil.subtract(upperBillMoney, billGive);
              lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, billGive);
              upperStillNotPay = MathUtil.subtract(upperStillNotPay, billGive);
            }
            double totalMoneyGive = 0.0D;
            User upUser = this.uDao.getById(dividendBill.getUserId());
            if ((lowerRemainReceived > 0.0D) && (upperStillNotPay > 0.0D) && (upUser.getTotalMoney() > 0.0D))
            {
              totalMoneyGive = lowerRemainReceived >= upUser.getTotalMoney() ? upUser.getTotalMoney() : lowerRemainReceived;
              lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, totalMoneyGive);
              upperStillNotPay = MathUtil.subtract(upperStillNotPay, totalMoneyGive);
            }
            double lotteryMoneyGive = 0.0D;
            if ((lowerRemainReceived > 0.0D) && (upperStillNotPay > 0.0D) && (upUser.getLotteryMoney() > 0.0D))
            {
              lotteryMoneyGive = lowerRemainReceived >= upUser.getLotteryMoney() ? upUser.getLotteryMoney() : lowerRemainReceived;
              lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, lotteryMoneyGive);
              upperStillNotPay = MathUtil.subtract(upperStillNotPay, lotteryMoneyGive);
            }
            double totalGive = MathUtil.add(MathUtil.add(billGive, totalMoneyGive), lotteryMoneyGive);
            if (totalGive <= 0.0D) {
              break;
            }
            if (totalMoneyGive > 0.0D)
            {
              UserVO subUser = this.dataFactory.getUser(directLowerBill.getUserId());
              this.uDao.updateTotalMoney(upUser.getId(), -totalMoneyGive);
              this.uBillService.addDividendBill(upUser, 1, -totalMoneyGive, "系统自动扣发" + totalMoneyGive + "分红金额到" + subUser.getUsername(), false);
            }
            if (lotteryMoneyGive > 0.0D)
            {
              UserVO subUser = this.dataFactory.getUser(directLowerBill.getUserId());
              this.uDao.updateLotteryMoney(upUser.getId(), -lotteryMoneyGive);
              this.uBillService.addDividendBill(upUser, 2, -lotteryMoneyGive, "系统自动扣发" + lotteryMoneyGive + "分红金额到" + subUser.getUsername(), false);
            }
            upperThisTimePaid = MathUtil.add(upperThisTimePaid, totalGive);
            
            this.uDividendBillDao.addAvailableMoney(directLowerBill.getId(), totalGive);
            
            accountPayout = MathUtil.add(totalMoneyGive, lotteryMoneyGive);
          }
        }
      }
    }
    User user = this.uDao.getById(dividendBill.getUserId());
    UserDividendBill upperBill = this.uDividendBillDao.getByUserId(user.getUpid(), dividendBill.getIndicateStartDate(), dividendBill.getIndicateEndDate());
    
    double upperNotYetPay = 0.0D;
    if ((upperBill != null) && (upperBill.getStatus() == 6) && (accountPayout > 0.0D))
    {
      double meReceived = MathUtil.add(dividendBill.getAvailableAmount(), dividendBill.getTotalReceived());
      
      double meRemainReceived = MathUtil.subtract(dividendBill.getCalAmount(), meReceived);
      if (meRemainReceived > 0.0D)
      {
        double addUserAmount = accountPayout > meRemainReceived ? meRemainReceived : accountPayout;
        this.uDividendBillDao.addUserAmount(dividendBill.getId(), addUserAmount);
        upperNotYetPay = addUserAmount;
      }
    }
    if (upperThisTimePaid > 0.0D) {
      this.uDividendBillDao.addLowerPaidAmount(dividendBill.getId(), upperThisTimePaid);
    }
    if ((dividendBill.getIssueType() == 1) && 
      (dividendBill.getStatus() == 2) && 
      (upperBillMoney > 0.0D) && 
      (dividendBill.getCalAmount() > 0.0D) && 
      (upperStillNotPay <= 0.1D) && 
      (dividendBill.getAvailableAmount() <= 0.0D))
    {
      this.uDividendBillDao.setAvailableMoney(dividendBill.getId(), upperBillMoney);
    }
    else if (dividendBill.getIssueType() == 2)
    {
      double upperRemains = MathUtil.subtract(dividendBill.getAvailableAmount(), upperThisTimePaid);
      if (upperRemains > 0.0D) {
        this.uDividendBillDao.addAvailableMoney(dividendBill.getId(), -upperThisTimePaid);
      } else {
        this.uDividendBillDao.setAvailableMoney(dividendBill.getId(), 0.0D);
      }
    }
    return new double[] { upperStillNotPay, upperBillMoney, upperNotYetPay };
  }
  
  public boolean deny(WebJSONObject json, int id, String remarks)
  {
    UserDividendBill dividendBill = getById(id);
    if (dividendBill.getStatus() != 2)
    {
      json.set(Integer.valueOf(2), "2-3004");
      return false;
    }
    User user = this.uDao.getById(dividendBill.getUserId());
    if (user == null)
    {
      json.set(Integer.valueOf(2), "2-32");
      return false;
    }
    boolean isZhuGuan = this.uCodePointUtil.isLevel1Proxy(user);
    if (isZhuGuan) {
      return this.uDividendBillDao.update(id, 4, remarks);
    }
    json.set(Integer.valueOf(2), "2-3005");
    return false;
  }
  
  public boolean del(WebJSONObject json, int id)
  {
    return this.uDividendBillDao.del(id);
  }
  
  public boolean reset(WebJSONObject json, int id, String remarks)
  {
    UserDividendBill userDividendBill = getById(id);
    if (userDividendBill == null)
    {
      json.set(Integer.valueOf(2), "2-3001");
      return false;
    }
    if (userDividendBill.getStatus() != 6)
    {
      json.set(Integer.valueOf(2), "2-3002");
      return false;
    }
    double stillNotPay = MathUtil.subtract(userDividendBill.getLowerTotalAmount(), userDividendBill.getLowerPaidAmount());
    if (stillNotPay <= 0.0D)
    {
      json.set(Integer.valueOf(2), "2-3002");
      return false;
    }
    this.uDividendBillDao.updateStatus(id, 1, remarks);
    
    List<UserDividendBill> lowerBills = getLowerBills(userDividendBill.getUserId(), userDividendBill.getIndicateStartDate(), userDividendBill.getIndicateEndDate());
    if (CollectionUtils.isNotEmpty(lowerBills)) {
      for (UserDividendBill lowerBill : lowerBills) {
        if ((lowerBill.getStatus() == 3) || 
          (lowerBill.getStatus() == 7)) {
          this.uDividendBillDao.updateStatus(lowerBill.getId(), 1, remarks);
        }
      }
    }
    return true;
  }
  
  public double queryPeriodCollect(int userId, String sTime, String eTime)
  {
    List<Criterion> criterions = new ArrayList();
    criterions.add(Restrictions.ge("collectTime", sTime));
    criterions.add(Restrictions.lt("collectTime", eTime));
    criterions.add(Restrictions.eq("userId", Integer.valueOf(userId)));
    List<UserDividendBill> lists = this.uDividendBillDao.findByCriteria(criterions, null);
    double result = 0.0D;
    if ((lists == null) || (lists.isEmpty())) {
      return result;
    }
    for (UserDividendBill bill : lists) {
      result += bill.getUserAmount();
    }
    return result;
  }
}
