package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.date.Moment;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.ActivityFirstRechargeBillService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.dao.ActivityFirstRechargeBillDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.ActivityFirstRechargeBill;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.activity.ActivityFirstRechargeConfigRule;
import lottery.domains.content.entity.activity.ActivityFirstRechargeConfigVO;
import lottery.domains.content.vo.activity.ActivityFirstRechargeBillVO;
import lottery.domains.pool.LotteryDataFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityFirstRechargeBillServiceImpl
  implements ActivityFirstRechargeBillService
{
  @Autowired
  private ActivityFirstRechargeBillDao billDao;
  @Autowired
  private UserBillService uBillService;
  @Autowired
  private UserDao uDao;
  @Autowired
  private LotteryDataFactory dataFactory;
  @Autowired
  private UserSysMessageService uSysMessageService;
  
  private double chooseAmount(double rechargeAmount)
  {
    ActivityFirstRechargeConfigVO config = this.dataFactory.getActivityFirstRechargeConfig();
    if ((config == null) || (config.getStatus() != 1)) {
      return 0.0D;
    }
    List<ActivityFirstRechargeConfigRule> rules = config.getRuleVOs();
    for (ActivityFirstRechargeConfigRule rule : rules) {
      if ((rechargeAmount >= rule.getMinRecharge()) && ((rule.getMaxRecharge() <= -1.0D) || (rechargeAmount <= rule.getMaxRecharge()))) {
        return rule.getAmount();
      }
    }
    return 0.0D;
  }
  
  public PageList find(String username, String sDate, String eDate, String ip, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        criterions.add(Restrictions.eq("userId", Integer.valueOf(user.getId())));
      } else {
        return new PageList();
      }
    }
    if (StringUtil.isNotNull(sDate)) {
      criterions.add(Restrictions.ge("date", sDate));
    }
    if (StringUtil.isNotNull(eDate)) {
      criterions.add(Restrictions.lt("date", eDate));
    }
    if (StringUtil.isNotNull(ip)) {
      criterions.add(Restrictions.eq("ip", ip));
    }
    orders.add(Order.desc("time"));
    orders.add(Order.desc("id"));
    List<ActivityFirstRechargeBillVO> list = new ArrayList();
    PageList pList = this.billDao.find(criterions, orders, start, limit);
    for (Object tmpBean : pList.getList())
    {
      ActivityFirstRechargeBillVO tmpVO = new ActivityFirstRechargeBillVO((ActivityFirstRechargeBill)tmpBean, this.dataFactory);
      list.add(tmpVO);
    }
    pList.setList(list);
    return pList;
  }
  
  public double sumAmount(String username, String sDate, String eDate, String ip)
  {
    Integer userId = null;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        userId = Integer.valueOf(user.getId());
      }
    }
    return this.billDao.sumAmount(userId, sDate, eDate, ip);
  }
  
  public double tryCollect(int userId, double rechargeAmount, String ip)
  {
    ActivityFirstRechargeConfigVO config = this.dataFactory.getActivityFirstRechargeConfig();
    if ((config == null) || (config.getStatus() != 1)) {
      return 0.0D;
    }
    String date = new Moment().toSimpleDate();
    
    ActivityFirstRechargeBill ipBill = this.billDao.getByDateAndIp(date, ip);
    if (ipBill != null) {
      return 0.0D;
    }
    ActivityFirstRechargeBill userBill = this.billDao.getByUserIdAndDate(userId, date);
    if (userBill != null) {
      return 0.0D;
    }
    double amount = chooseAmount(rechargeAmount);
    if (amount <= 0.0D) {
      return 0.0D;
    }
    ActivityFirstRechargeBill firstRechargeBill = new ActivityFirstRechargeBill();
    firstRechargeBill.setUserId(userId);
    firstRechargeBill.setDate(date);
    firstRechargeBill.setTime(new Moment().toSimpleTime());
    firstRechargeBill.setRecharge(rechargeAmount);
    firstRechargeBill.setAmount(amount);
    firstRechargeBill.setIp(ip);
    
    boolean added = this.billDao.add(firstRechargeBill);
    if (added)
    {
      User user = this.uDao.getById(userId);
      boolean addedBill = this.uBillService.addActivityBill(user, 2, amount, firstRechargeBill.getId(), "首充活动");
      if (addedBill)
      {
        this.uDao.updateLotteryMoney(userId, amount);
        
        this.uSysMessageService.addFirstRecharge(userId, rechargeAmount, amount);
      }
      return amount;
    }
    return 0.0D;
  }
}
