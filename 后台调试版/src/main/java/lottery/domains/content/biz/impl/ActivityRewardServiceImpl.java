package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javautils.StringUtil;
import javautils.date.Moment;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.ActivityRewardService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.dao.ActivityRebateDao;
import lottery.domains.content.dao.ActivityRewardBillDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserLotteryReportDao;
import lottery.domains.content.entity.ActivityRebate;
import lottery.domains.content.entity.ActivityRewardBill;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserLotteryReport;
import lottery.domains.content.entity.activity.RebateRulesReward;
import lottery.domains.content.vo.activity.ActivityRewardBillVO;
import lottery.domains.pool.LotteryDataFactory;
import net.sf.json.JSONArray;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityRewardServiceImpl
  implements ActivityRewardService
{
  @Autowired
  private UserDao uDao;
  @Autowired
  private ActivityRebateDao aRebateDao;
  @Autowired
  private ActivityRewardBillDao aRewardBillDao;
  @Autowired
  private UserLotteryReportDao uLotteryReportDao;
  @Autowired
  private UserBillService uBillService;
  @Autowired
  private UserSysMessageService uSysMessageService;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public PageList search(String username, String date, Integer type, Integer status, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    boolean isSearch = true;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        criterions.add(Restrictions.eq("toUser", Integer.valueOf(user.getId())));
      } else {
        isSearch = false;
      }
    }
    if (StringUtil.isNotNull(date)) {
      criterions.add(Restrictions.eq("date", date));
    }
    if (type != null) {
      criterions.add(Restrictions.eq("type", Integer.valueOf(type.intValue())));
    }
    if (status != null) {
      criterions.add(Restrictions.eq("status", Integer.valueOf(status.intValue())));
    }
    orders.add(Order.desc("id"));
    if (isSearch)
    {
      List<ActivityRewardBillVO> list = new ArrayList();
      PageList pList = this.aRewardBillDao.find(criterions, orders, start, limit);
      for (Object tmpBean : pList.getList()) {
        list.add(new ActivityRewardBillVO((ActivityRewardBill)tmpBean, this.lotteryDataFactory));
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  public boolean add(int toUser, int fromUser, int type, double totalMoney, double money, String date)
  {
    String time = new Moment().toSimpleTime();
    int status = 0;
    ActivityRewardBill entity = new ActivityRewardBill(toUser, fromUser, type, totalMoney, money, date, time, status);
    return this.aRewardBillDao.add(entity);
  }
  
  public List<ActivityRewardBillVO> getLatest(int toUser, int status, int count)
  {
    List<ActivityRewardBillVO> formatList = new ArrayList();
    List<ActivityRewardBill> list = this.aRewardBillDao.getLatest(toUser, status, count);
    for (ActivityRewardBill tmpBean : list) {
      formatList.add(new ActivityRewardBillVO(tmpBean, this.lotteryDataFactory));
    }
    return formatList;
  }
  
  public boolean calculate(int type, String date)
  {
    ActivityRebate activity = this.aRebateDao.getByType(type);
    if (activity.getStatus() == 0)
    {
      List<RebateRulesReward> rewards = (List)JSONArray.toCollection(JSONArray.fromObject(activity.getRules()), RebateRulesReward.class);
      String sTime = new Moment().fromDate(date).toSimpleDate();
      String eTime = new Moment().fromDate(date).add(1, "days").toSimpleDate();
      List<UserLotteryReport> list = doReport(sTime, eTime);
      for (UserLotteryReport tmpBean : list) {
        if (tmpBean.getUserId() != 72)
        {
          if (type == 1)
          {
            double totalMoney = tmpBean.getBillingOrder();
            if (totalMoney > 0.0D)
            {
              RebateRulesReward rBean = doMatch(rewards, totalMoney);
              int bType = 1;
              addHandelBill(tmpBean.getUserId(), rBean, bType, totalMoney, date);
            }
          }
          if (type == 2)
          {
            double totalMoney = tmpBean.getBillingOrder() - tmpBean.getPrize() - tmpBean.getSpendReturn();
            if (totalMoney > 0.0D)
            {
              RebateRulesReward rBean = doMatch(rewards, totalMoney);
              int bType = 2;
              addHandelBill(tmpBean.getUserId(), rBean, bType, totalMoney, date);
            }
          }
        }
      }
      return true;
    }
    return false;
  }
  
  List<UserLotteryReport> doReport(String sTime, String eTime)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    if (StringUtil.isNotNull(sTime)) {
      criterions.add(Restrictions.ge("time", sTime));
    }
    if (StringUtil.isNotNull(eTime)) {
      criterions.add(Restrictions.lt("time", eTime));
    }
    return this.uLotteryReportDao.find(criterions, orders);
  }
  
  RebateRulesReward doMatch(List<RebateRulesReward> rewards, double totalMoney)
  {
    RebateRulesReward reward = new RebateRulesReward();
    for (RebateRulesReward tmpBean : rewards)
    {
      double money = tmpBean.getMoney();
      if ((totalMoney >= money) && (money > reward.getMoney())) {
        reward = tmpBean;
      }
    }
    return reward;
  }
  
  boolean addHandelBill(int fromUser, RebateRulesReward rBean, int type, double totalMoney, String date)
  {
    if (rBean.getMoney() > 0.0D)
    {
      User thisUser = this.uDao.getById(fromUser);
      if (thisUser == null) {
        return false;
      }
      if (rBean.getRewardUp1() == 0.0D) {
        return false;
      }
      if ((thisUser.getUpid() == 0) || (thisUser.getUpid() == 72)) {
        return false;
      }
      User up1 = this.uDao.getById(thisUser.getUpid());
      if (up1 == null) {
        return false;
      }
      try
      {
        int toUesr = up1.getId();
        double money = rBean.getRewardUp1();
        boolean hasRecord = this.aRewardBillDao.hasRecord(toUesr, fromUser, type, date);
        if (!hasRecord) {
          add(toUesr, fromUser, type, totalMoney, money, date);
        }
      }
      catch (Exception localException) {}
      if (rBean.getRewardUp2() == 0.0D) {
        return false;
      }
      if (up1.getUpid() == 0) {
        return false;
      }
      User up2 = this.uDao.getById(up1.getUpid());
      if (up2 == null) {
        return false;
      }
      try
      {
        int toUesr = up2.getId();
        double money = rBean.getRewardUp2();
        boolean hasRecord = this.aRewardBillDao.hasRecord(toUesr, fromUser, type, date);
        if (!hasRecord) {
          add(toUesr, fromUser, type, totalMoney, money, date);
        }
      }
      catch (Exception localException1) {}
      return true;
    }
    return false;
  }
  
  public boolean agreeAll(String date)
  {
    List<ActivityRewardBill> list = this.aRewardBillDao.getUntreated(date);
    Set<Integer> uSet = new HashSet();
    for (ActivityRewardBill tmpBean : list) {
      try
      {
        if (tmpBean.getStatus() == 0)
        {
          String thisTime = new Moment().toSimpleTime();
          tmpBean.setStatus(1);
          tmpBean.setTime(thisTime);
          boolean aFlag = this.aRewardBillDao.update(tmpBean);
          if (aFlag)
          {
            User uBean = this.uDao.getById(tmpBean.getToUser());
            boolean uFlag = this.uDao.updateLotteryMoney(uBean.getId(), tmpBean.getMoney());
            if (uFlag)
            {
              uSet.add(Integer.valueOf(uBean.getId()));
              String remarks = "彩票账户，";
              if (tmpBean.getType() == 1) {
                remarks = remarks + "发放消费佣金。";
              }
              if (tmpBean.getType() == 2) {
                remarks = remarks + "发放盈亏佣金。";
              }
              int refType = 1;
              this.uBillService.addActivityBill(uBean, 2, tmpBean.getMoney(), refType, remarks);
            }
          }
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    String formatDate = new Moment().fromDate(date).format("yyyy年MM月dd日");
    for (Integer id : uSet) {
      this.uSysMessageService.addRewardMessage(id.intValue(), formatDate);
    }
    return true;
  }
}
