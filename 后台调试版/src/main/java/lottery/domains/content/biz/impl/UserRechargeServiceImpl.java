package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.date.Moment;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.ActivityFirstRechargeBillService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserLotteryReportService;
import lottery.domains.content.biz.UserRechargeService;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.biz.UserWithdrawLimitService;
import lottery.domains.content.dao.PaymentChannelDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserRechargeDao;
import lottery.domains.content.entity.HistoryUserRecharge;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserRecharge;
import lottery.domains.content.vo.config.WithdrawConfig;
import lottery.domains.content.vo.payment.PaymentChannelVO;
import lottery.domains.content.vo.user.HistoryUserRechargeVO;
import lottery.domains.content.vo.user.UserRechargeVO;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRechargeServiceImpl
  implements UserRechargeService
{
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserRechargeDao uRechargeDao;
  @Autowired
  private UserBillService uBillService;
  @Autowired
  private UserSysMessageService uSysMessageService;
  @Autowired
  private UserWithdrawLimitService uWithdrawLimitService;
  @Autowired
  private UserLotteryReportService uLotteryReportService;
  @Autowired
  private ActivityFirstRechargeBillService activityFirstRechargeBillService;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  @Autowired
  private PaymentChannelDao paymentChannelDao;
  @Autowired
  private UserDao userDao;
  
  private String billno()
  {
    return new Moment().format("yyMMddHHmmss") + RandomStringUtils.random(8, true, true);
  }
  
  public UserRechargeVO getById(int id)
  {
    UserRecharge bean = this.uRechargeDao.getById(id);
    if (bean != null) {
      return new UserRechargeVO(bean, this.lotteryDataFactory);
    }
    return null;
  }
  
  public HistoryUserRechargeVO getHistoryById(int id)
  {
    HistoryUserRecharge bean = this.uRechargeDao.getHistoryById(id);
    if (bean != null) {
      return new HistoryUserRechargeVO(bean, this.lotteryDataFactory);
    }
    return null;
  }
  
  public List<UserRechargeVO> getLatest(int userId, int status, int count)
  {
    List<UserRechargeVO> formatList = new ArrayList();
    List<UserRecharge> list = this.uRechargeDao.getLatest(userId, status, count);
    for (UserRecharge tmpBean : list) {
      formatList.add(new UserRechargeVO(tmpBean, this.lotteryDataFactory));
    }
    return formatList;
  }
  
  public List<UserRecharge> listByPayTimeAndStatus(String sDate, String eDate, int status)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    orders.add(Order.desc("time"));
    orders.add(Order.desc("id"));
    
    criterions.add(Restrictions.eq("status", Integer.valueOf(status)));
    criterions.add(Restrictions.ge("payTime", sDate));
    criterions.add(Restrictions.lt("payTime", eDate));
    
    List<UserRecharge> rechargeList = this.uRechargeDao.list(criterions, orders);
    return rechargeList;
  }
  
  public PageList search(Integer type, String billno, String username, String minTime, String maxTime, String minPayTime, String maxPayTime, Double minMoney, Double maxMoney, Integer status, Integer channelId, int start, int limit)
  {
    StringBuilder queryStr = new StringBuilder();
    boolean isSearch = true;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        queryStr.append(" and b.user_id  = ").append(user.getId());
      } else {
        isSearch = false;
      }
    }
    if (StringUtil.isNotNull(billno)) {
      queryStr.append(" and b.billno  = ").append("'" + billno + "'");
    }
    if (StringUtil.isNotNull(minTime)) {
      queryStr.append(" and b.time  > ").append("'" + minTime + "'");
    }
    if (StringUtil.isNotNull(maxTime)) {
      queryStr.append(" and b.time  < ").append("'" + maxTime + "'");
    }
    if (StringUtil.isNotNull(minPayTime)) {
      queryStr.append(" and b.pay_time  > ").append("'" + minPayTime + "'");
    }
    if (StringUtil.isNotNull(maxPayTime)) {
      queryStr.append(" and b.pay_time  < ").append("'" + maxPayTime + "'");
    }
    if (minMoney != null) {
      queryStr.append(" and b.money  >= ").append(minMoney.doubleValue());
    }
    if (maxMoney != null) {
      queryStr.append(" and b.money  <= ").append(maxMoney.doubleValue());
    }
    if (status != null) {
      queryStr.append(" and b.status  = ").append(status.intValue());
    }
    if (channelId != null) {
      queryStr.append(" and b.channel_id  = ").append(channelId);
    }
    if (type != null) {
      queryStr.append(" and  u.type  = ").append(type);
    } else {
      queryStr.append(" and u.upid  != ").append(0);
    }
    queryStr.append("  ORDER BY b.time,b.id DESC ");
    if (isSearch)
    {
      List<UserRechargeVO> list = new ArrayList();
      PageList pList = this.uRechargeDao.find(queryStr.toString(), start, limit);
      for (Object tmpBean : pList.getList()) {
        list.add(new UserRechargeVO((UserRecharge)tmpBean, this.lotteryDataFactory));
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  public PageList searchHistory(String billno, String username, String minTime, String maxTime, String minPayTime, String maxPayTime, Double minMoney, Double maxMoney, Integer status, Integer channelId, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    orders.add(Order.desc("time"));
    orders.add(Order.desc("id"));
    boolean isSearch = true;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        criterions.add(Restrictions.eq("userId", Integer.valueOf(user.getId())));
      } else {
        isSearch = false;
      }
    }
    if (StringUtil.isNotNull(billno)) {
      criterions.add(Restrictions.eq("billno", billno));
    }
    if (StringUtil.isNotNull(minTime)) {
      criterions.add(Restrictions.gt("time", minTime));
    }
    if (StringUtil.isNotNull(maxTime)) {
      criterions.add(Restrictions.lt("time", maxTime));
    }
    if (StringUtil.isNotNull(minPayTime)) {
      criterions.add(Restrictions.gt("payTime", minPayTime));
    }
    if (StringUtil.isNotNull(maxPayTime)) {
      criterions.add(Restrictions.lt("payTime", maxPayTime));
    }
    if (minMoney != null) {
      criterions.add(Restrictions.ge("money", Double.valueOf(minMoney.doubleValue())));
    }
    if (maxMoney != null) {
      criterions.add(Restrictions.le("money", Double.valueOf(maxMoney.doubleValue())));
    }
    if (status != null) {
      criterions.add(Restrictions.eq("status", Integer.valueOf(status.intValue())));
    }
    if (channelId != null) {
      criterions.add(Restrictions.eq("channelId", channelId));
    }
    if (isSearch)
    {
      List<HistoryUserRechargeVO> list = new ArrayList();
      PageList pList = this.uRechargeDao.findHistory(criterions, orders, start, limit);
      for (Object tmpBean : pList.getList()) {
        list.add(new HistoryUserRechargeVO((HistoryUserRecharge)tmpBean, this.lotteryDataFactory));
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  public boolean addSystemRecharge(String username, int subtype, int account, double amount, int isLimit, String remarks)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null)
    {
      if ((subtype == 1) && 
        (amount > 0.0D))
      {
        boolean uFlag = this.uDao.updateLotteryMoney(uBean.getId(), amount);
        if (uFlag)
        {
          String billno = billno();
          double money = amount;
          
          double beforeMoney = uBean.getLotteryMoney();
          double afterMoney = beforeMoney + money;
          double recMoney = money;
          double feeMoney = 0.0D;
          Moment moment = new Moment();
          String time = moment.toSimpleTime();
          int status = 1;
          int type = 3;
          String infos = "系统补充值未到账。";
          if (StringUtil.isNotNull(remarks)) {
            infos = infos + "备注：" + remarks;
          }
          UserRecharge cBean = new UserRecharge(billno, uBean.getId(), money, beforeMoney, afterMoney, recMoney, feeMoney, time, status, type, subtype);
          cBean.setChannelId(Integer.valueOf(-1));
          cBean.setPayTime(time);
          cBean.setInfos(infos);
          cBean.setRemarks(remarks);
          boolean cFlag = this.uRechargeDao.add(cBean);
          if (cFlag)
          {
            this.uBillService.addRechargeBill(cBean, uBean, infos);
            
            double percent = this.lotteryDataFactory.getWithdrawConfig().getSystemConsumptionPercent();
            this.uWithdrawLimitService.add(uBean.getId(), amount, time, type, subtype, percent);
            this.uSysMessageService.addSysRecharge(uBean.getId(), amount, remarks);
          }
          return cFlag;
        }
      }
      if ((subtype == 2) && 
        (amount > 0.0D))
      {
        boolean uFlag = false;
        if (account == 2) {
          uFlag = this.uDao.updateLotteryMoney(uBean.getId(), amount);
        }
        if (uFlag)
        {
          String infos = "系统活动补贴。";
          if (StringUtil.isNotNull(remarks)) {
            infos = infos + "备注：" + remarks;
          }
          int refType = 0;
          this.uBillService.addActivityBill(uBean, account, amount, refType, infos);
          if (isLimit == 1)
          {
            String time = new Moment().toSimpleTime();
            
            int type = 3;
            double percent = this.lotteryDataFactory.getWithdrawConfig().getSystemConsumptionPercent();
            this.uWithdrawLimitService.add(uBean.getId(), amount, time, type, subtype, percent);
          }
        }
        return uFlag;
      }
      if ((subtype == 3) && 
        (amount > 0.0D))
      {
        boolean uFlag = false;
        if (account == 1) {
          uFlag = this.uDao.updateTotalMoney(uBean.getId(), amount);
        }
        if (account == 2) {
          uFlag = this.uDao.updateLotteryMoney(uBean.getId(), amount);
        }
        if (account == 3) {
          uFlag = this.uDao.updateBaccaratMoney(uBean.getId(), amount);
        }
        if (uFlag)
        {
          String infos = "管理员增加资金。";
          if (StringUtil.isNotNull(remarks)) {
            infos = infos + "备注：" + remarks;
          }
          this.uBillService.addAdminAddBill(uBean, account, amount, infos);
        }
        return uFlag;
      }
      if ((subtype == 4) && 
        (amount > 0.0D))
      {
        boolean uFlag = false;
        if (account == 1) {
          uFlag = this.uDao.updateTotalMoney(uBean.getId(), -amount);
        }
        if (account == 2) {
          uFlag = this.uDao.updateLotteryMoney(uBean.getId(), -amount);
        }
        if (account == 3) {
          uFlag = this.uDao.updateBaccaratMoney(uBean.getId(), -amount);
        }
        if (uFlag)
        {
          String infos = "管理员减少资金。";
          if (StringUtil.isNotNull(remarks)) {
            infos = infos + "备注：" + remarks;
          }
          this.uBillService.addAdminMinusBill(uBean, account, amount, infos);
        }
        return uFlag;
      }
    }
    return false;
  }
  
  public boolean patchOrder(String billno, String payBillno, String remarks)
  {
    UserRecharge cBean = this.uRechargeDao.getByBillno(billno);
    if ((cBean != null) && (cBean.getStatus() == 0))
    {
      User uBean = this.uDao.getById(cBean.getUserId());
      if (uBean != null)
      {
        Moment moment = new Moment();
        String payTime = moment.toSimpleTime();
        
        double money = cBean.getRecMoney();
        String infos = "充值漏单补单，" + money;
        
        PaymentChannelVO channel = this.lotteryDataFactory.getPaymentChannelVO(cBean.getChannelId().intValue());
        if (channel == null) {
          return false;
        }
        if (channel.getAddMoneyType() == 2) {
          infos = "在线充值";
        }
        cBean.setBeforeMoney(uBean.getLotteryMoney());
        cBean.setAfterMoney(uBean.getLotteryMoney() + money);
        cBean.setStatus(1);
        cBean.setPayBillno(payBillno);
        cBean.setPayTime(payTime);
        cBean.setInfos(infos);
        cBean.setRemarks(remarks);
        boolean result = this.uRechargeDao.update(cBean);
        if (result)
        {
          boolean flag = this.uDao.updateLotteryMoney(uBean.getId(), money);
          if (flag)
          {
            String _remarks = "在线充值";
            this.uBillService.addRechargeBill(cBean, uBean, _remarks);
            this.uWithdrawLimitService.add(uBean.getId(), money, payTime, channel.getType(), channel.getSubType(), channel.getConsumptionPercent());
            this.uSysMessageService.addOnlineRecharge(uBean.getId(), money);
            if (uBean.getUpid() != 0) {
              this.activityFirstRechargeBillService.tryCollect(cBean.getUserId(), money, cBean.getApplyIp());
            }
            if (channel.getAddMoneyType() == 2) {
              this.paymentChannelDao.addUsedCredits(channel.getId(), money);
            }
          }
        }
        return result;
      }
    }
    return false;
  }
  
  public boolean cancelOrder(String billno)
  {
    UserRecharge bean = this.uRechargeDao.getByBillno(billno);
    if ((bean != null) && (bean.getStatus() == 0))
    {
      bean.setStatus(-1);
      return this.uRechargeDao.update(bean);
    }
    return false;
  }
  
  public double getTotalRecharge(Integer type, String billno, String username, String minTime, String maxTime, String minPayTime, String maxPayTime, Double minMoney, Double maxMoney, Integer status, Integer channelId)
  {
    Integer userId = null;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        userId = Integer.valueOf(user.getId());
      }
    }
    return this.uRechargeDao.getTotalRecharge(billno, userId, minTime, maxTime, minPayTime, maxPayTime, minMoney, maxMoney, status, channelId);
  }
  
  public double getHistoryTotalRecharge(String billno, String username, String minTime, String maxTime, String minPayTime, String maxPayTime, Double minMoney, Double maxMoney, Integer status, Integer channelId)
  {
    Integer userId = null;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        userId = Integer.valueOf(user.getId());
      }
    }
    return this.uRechargeDao.getHistoryTotalRecharge(billno, userId, minTime, maxTime, minPayTime, maxPayTime, minMoney, maxMoney, status, channelId);
  }
}
