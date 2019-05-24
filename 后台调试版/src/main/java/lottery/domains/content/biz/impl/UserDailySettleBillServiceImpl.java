package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import javautils.math.MathUtil;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserDailySettleBillService;
import lottery.domains.content.dao.UserDailySettleBillDao;
import lottery.domains.content.dao.UserDailySettleDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserDailySettleBill;
import lottery.domains.content.vo.user.UserDailySettleBillVO;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDailySettleBillServiceImpl
  implements UserDailySettleBillService
{
  @Autowired
  private UserBillService uBillService;
  @Autowired
  private UserDailySettleBillDao dailySettleBillDao;
  @Autowired
  private UserDailySettleDao uDailySettleDao;
  @Autowired
  private UserDao uDao;
  @Autowired
  private LotteryDataFactory dataFactory;
  
  public PageList search(List<Integer> userIds, String sTime, String eTime, Double minUserAmount, Double maxUserAmount, Integer status, int start, int limit)
  {
    start = start < 0 ? 0 : start;
    limit = limit < 0 ? 0 : limit;
    limit = limit > 20 ? 20 : limit;
    
    List<Criterion> criterions = new ArrayList();
    if (CollectionUtils.isNotEmpty(userIds)) {
      criterions.add(Restrictions.in("userId", userIds));
    }
    if (StringUtil.isNotNull(sTime)) {
      criterions.add(Restrictions.ge("indicateDate", sTime));
    }
    if (StringUtil.isNotNull(eTime)) {
      criterions.add(Restrictions.lt("indicateDate", eTime));
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
    List<Order> orders = new ArrayList();
    orders.add(Order.desc("id"));
    PageList pList = this.dailySettleBillDao.search(criterions, orders, start, limit);
    List<UserDailySettleBillVO> convertList = new ArrayList();
    if ((pList != null) && (pList.getList() != null)) {
      for (Object tmpBean : pList.getList()) {
        convertList.add(new UserDailySettleBillVO((UserDailySettleBill)tmpBean, this.dataFactory));
      }
    }
    pList.setList(convertList);
    return pList;
  }
  
  public List<UserDailySettleBill> findByCriteria(List<Criterion> criterions, List<Order> orders)
  {
    return this.dailySettleBillDao.findByCriteria(criterions, orders);
  }
  
  public List<UserDailySettleBill> getDirectLowerBills(int userId, String indicateDate, Integer[] status, Integer issueType)
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
    if ((status != null) && (status.length > 0)) {
      criterions.add(Restrictions.in("status", Arrays.asList(status)));
    }
    if (issueType != null) {
      criterions.add(Restrictions.eq("issueType", issueType));
    }
    if (StringUtil.isNotNull(indicateDate)) {
      criterions.add(Restrictions.eq("indicateDate", indicateDate));
    }
    return this.dailySettleBillDao.findByCriteria(criterions, null);
  }
  
  public boolean add(UserDailySettleBill settleBill)
  {
    boolean added = this.dailySettleBillDao.add(settleBill);
    if (!added) {
      return added;
    }
    this.uDailySettleDao.updateTotalAmount(settleBill.getUserId(), settleBill.getUserAmount());
    
    return added;
  }
  
  public boolean update(UserDailySettleBill settleBill)
  {
    return this.dailySettleBillDao.update(settleBill);
  }
  
  public synchronized UserDailySettleBill issue(int id)
  {
    UserDailySettleBill upperBill = this.dailySettleBillDao.getById(id);
    if ((upperBill == null) || (upperBill.getStatus() != 3)) {
      return upperBill;
    }
    Integer[] status = { Integer.valueOf(2), Integer.valueOf(3) };
    List<UserDailySettleBill> lowerBills = getDirectLowerBills(upperBill.getUserId(), upperBill.getIndicateDate(), status, Integer.valueOf(2));
    if (CollectionUtils.isEmpty(lowerBills))
    {
      upperBill.setRemarks("已发放");
      upperBill.setStatus(1);
      update(upperBill);
      return upperBill;
    }
    double upperBillMoney = 0.0D;
    if (upperBill.getIssueType() == 2)
    {
      upperBillMoney = upperBill.getAvailableAmount();
      upperBill.setAvailableAmount(0.0D);
    }
    double upperThisTimePaid = 0.0D;
    for (UserDailySettleBill lowerBill : lowerBills)
    {
      double lowerAmount = lowerBill.getCalAmount();
      
      double lowerRemainReceived = MathUtil.subtract(lowerAmount, lowerBill.getTotalReceived());
      
      double billGive = 0.0D;
      if ((upperBillMoney > 0.0D) && (lowerRemainReceived > 0.0D))
      {
        billGive = lowerAmount >= upperBillMoney ? upperBillMoney : lowerAmount;
        upperBillMoney = MathUtil.subtract(upperBillMoney, billGive);
        lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, billGive);
      }
      double totalMoneyGive = 0.0D;
      User upperUser = this.uDao.getById(upperBill.getUserId());
      if ((lowerRemainReceived > 0.0D) && (upperUser.getTotalMoney() > 0.0D))
      {
        totalMoneyGive = lowerRemainReceived >= upperUser.getTotalMoney() ? upperUser.getTotalMoney() : lowerRemainReceived;
        lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, totalMoneyGive);
      }
      double lotteryMoneyGive = 0.0D;
      if ((lowerRemainReceived > 0.0D) && (upperUser.getLotteryMoney() > 0.0D))
      {
        lotteryMoneyGive = lowerRemainReceived >= upperUser.getLotteryMoney() ? upperUser.getLotteryMoney() : lowerRemainReceived;
        lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, lotteryMoneyGive);
      }
      double totalGive = MathUtil.add(MathUtil.add(billGive, totalMoneyGive), lotteryMoneyGive);
      if (totalGive > 0.0D)
      {
        if (totalMoneyGive > 0.0D)
        {
          UserVO subUser = this.dataFactory.getUser(lowerBill.getUserId());
          this.uDao.updateTotalMoney(upperUser.getId(), -totalMoneyGive);
          this.uBillService.addDailySettleBill(upperUser, 1, -totalMoneyGive, "系统自动扣发" + totalMoneyGive + "日结金额到" + subUser.getUsername(), false);
        }
        if (lotteryMoneyGive > 0.0D)
        {
          UserVO subUser = this.dataFactory.getUser(lowerBill.getUserId());
          this.uDao.updateLotteryMoney(upperUser.getId(), -lotteryMoneyGive);
          this.uBillService.addDailySettleBill(upperUser, 2, -lotteryMoneyGive, "系统自动扣发" + lotteryMoneyGive + "日结金额到" + subUser.getUsername(), false);
        }
        upperThisTimePaid = MathUtil.add(upperThisTimePaid, totalGive);
        
        lowerBill.setTotalReceived(MathUtil.add(lowerBill.getTotalReceived(), totalGive));
        if (lowerBill.getStatus() == 2)
        {
          User lowerUser = this.uDao.getById(lowerBill.getUserId());
          boolean addedBill = this.uBillService.addDailySettleBill(lowerUser, 2, totalGive, "系统自动从上级账户中扣发日结金额", true);
          if (addedBill)
          {
            if (lowerRemainReceived <= 0.0D)
            {
              lowerBill.setRemarks("已发放");
              lowerBill.setStatus(1);
            }
            this.uDao.updateLotteryMoney(lowerBill.getUserId(), totalGive);
          }
        }
        else
        {
          lowerBill.setAvailableAmount(totalGive);
        }
        this.dailySettleBillDao.update(lowerBill);
      }
    }
    upperBill.setLowerPaidAmount(MathUtil.add(upperBill.getLowerPaidAmount(), upperThisTimePaid));
    if (upperBillMoney > 0.0D)
    {
      User upperUser = this.uDao.getById(upperBill.getUserId());
      if (upperUser != null)
      {
        boolean addedBill = this.uBillService.addDailySettleBill(upperUser, 2, upperBillMoney, "系统自动从上级账户中扣发日结金额", true);
        if (addedBill)
        {
          upperBill.setRemarks("已发放");
          upperBill.setStatus(1);
          this.dailySettleBillDao.update(upperBill);
          
          this.uDao.updateLotteryMoney(upperBill.getUserId(), upperBillMoney);
        }
      }
    }
    else
    {
      double notYetPay = MathUtil.subtract(upperBill.getLowerTotalAmount(), upperBill.getLowerPaidAmount());
      if (notYetPay > 0.0D)
      {
        this.dailySettleBillDao.update(upperBill);
      }
      else
      {
        upperBill.setRemarks("已发放");
        upperBill.setStatus(1);
        this.dailySettleBillDao.update(upperBill);
      }
    }
    return upperBill;
  }
}
