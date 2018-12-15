package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.date.Moment;
import javautils.jdbc.PageList;
import javautils.math.MathUtil;
import lottery.domains.content.biz.UserBaccaratReportService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserGameReportService;
import lottery.domains.content.biz.UserLotteryDetailsReportService;
import lottery.domains.content.biz.UserLotteryReportService;
import lottery.domains.content.biz.UserMainReportService;
import lottery.domains.content.dao.UserBetsDao;
import lottery.domains.content.dao.UserBillDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserRechargeDao;
import lottery.domains.content.dao.UserWithdrawDao;
import lottery.domains.content.entity.HistoryUserBets;
import lottery.domains.content.entity.HistoryUserBill;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserBets;
import lottery.domains.content.entity.UserBill;
import lottery.domains.content.entity.UserRecharge;
import lottery.domains.content.entity.UserTransfers;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.vo.bill.HistoryUserBillVO;
import lottery.domains.content.vo.bill.UserBillVO;
import lottery.domains.content.vo.config.RechargeConfig;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserBillServiceImpl
  implements UserBillService
{
  private static final Logger logger = LoggerFactory.getLogger(UserBillServiceImpl.class);
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserBillDao uBillDao;
  @Autowired
  private UserBetsDao uBetsDao;
  @Autowired
  private UserRechargeDao uRechargeDao;
  @Autowired
  private UserWithdrawDao uWithdrawDao;
  @Autowired
  private UserMainReportService uMainReportService;
  @Autowired
  private UserGameReportService uGameReportService;
  @Autowired
  private UserLotteryReportService uLotteryReportService;
  @Autowired
  private UserLotteryDetailsReportService uLotteryDetailsReportService;
  @Autowired
  private UserBaccaratReportService uBaccaratReportService;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  private String billno()
  {
    return ObjectId.get().toString();
  }
  
  public boolean addRechargeBill(UserRecharge cBean, User uBean, String remarks)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = uBean.getId();
      
      int account = 2;
      int type = 1;
      double money = cBean.getRecMoney();
      
      double beforeMoney = uBean.getLotteryMoney();
      double afterMoney = beforeMoney + money;
      Integer refType = Integer.valueOf(type);
      String refId = cBean.getBillno();
      Moment thisTime = new Moment();
      UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
      flag = this.uBillDao.add(tmpBill);
      if (flag)
      {
        this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
        if (this.lotteryDataFactory.getRechargeConfig().getFeePercent() > 0.0D)
        {
          double feeAmount = MathUtil.multiply(money, this.lotteryDataFactory.getRechargeConfig().getFeePercent());
          if (feeAmount > 0.0D) {
            this.uLotteryReportService.updateRechargeFee(uBean.getId(), feeAmount, thisTime.toSimpleDate());
          }
        }
      }
    }
    catch (Exception e)
    {
      logger.error("写入存款账单失败！", e);
    }
    return flag;
  }
  
  public boolean addWithdrawReport(UserWithdraw wBean)
  {
    boolean flag = false;
    try
    {
      int userId = wBean.getUserId();
      int type = 2;
      double money = wBean.getMoney();
      Moment thisTime = new Moment();
      this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
    }
    catch (Exception e)
    {
      logger.error("写入取款账单失败！", e);
    }
    return flag;
  }
  
  public boolean addDrawBackBill(UserWithdraw wBean, User uBean, String remarks)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = wBean.getUserId();
      int account = 2;
      int type = 16;
      double money = wBean.getMoney();
      double beforeMoney = uBean.getLotteryMoney();
      double afterMoney = beforeMoney + money;
      Integer refType = null;
      String refId = wBean.getBillno();
      Moment thisTime = new Moment();
      UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
      return this.uBillDao.add(tmpBill);
    }
    catch (Exception e)
    {
      logger.error("写入取款账单失败！", e);
    }
    return flag;
  }
  
  public boolean addTransInBill(UserTransfers tBean, User uBean, int account, String remarks)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = uBean.getId();
      int type = 3;
      double money = tBean.getMoney();
      double beforeMoney = 0.0D;
      if (account == 1) {
        beforeMoney = uBean.getTotalMoney();
      }
      if (account == 2) {
        beforeMoney = uBean.getLotteryMoney();
      }
      if (account == 3) {
        beforeMoney = uBean.getBaccaratMoney();
      }
      double afterMoney = beforeMoney + money;
      Integer refType = null;
      String refId = tBean.getBillno();
      Moment thisTime = new Moment();
      UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
      flag = this.uBillDao.add(tmpBill);
      if (flag)
      {
        if (account == 1) {
          this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        if (account == 2) {
          this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        if (account == 3) {
          this.uBaccaratReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
      }
    }
    catch (Exception e)
    {
      logger.error("写入转入账单失败！", e);
    }
    return flag;
  }
  
  public boolean addTransOutBill(UserTransfers tBean, User uBean, int account, String remarks)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = uBean.getId();
      int type = 4;
      double money = tBean.getMoney();
      double beforeMoney = 0.0D;
      if (account == 1) {
        beforeMoney = uBean.getTotalMoney();
      }
      if (account == 2) {
        beforeMoney = uBean.getLotteryMoney();
      }
      if (account == 3) {
        beforeMoney = uBean.getBaccaratMoney();
      }
      double afterMoney = beforeMoney - money;
      Integer refType = null;
      String refId = tBean.getBillno();
      Moment thisTime = new Moment();
      UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
      flag = this.uBillDao.add(tmpBill);
      if (flag)
      {
        if (account == 1) {
          this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        if (account == 2) {
          this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        if (account == 3) {
          this.uBaccaratReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
      }
    }
    catch (Exception e)
    {
      logger.error("写入转出账单失败！", e);
    }
    return flag;
  }
  
  public boolean addActivityBill(User uBean, int account, double amount, int refType, String remarks)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = uBean.getId();
      int type = 5;
      double money = amount;
      double beforeMoney = 0.0D;
      if (account == 1) {
        beforeMoney = uBean.getTotalMoney();
      }
      if (account == 2) {
        beforeMoney = uBean.getLotteryMoney();
      }
      if (account == 3) {
        beforeMoney = uBean.getBaccaratMoney();
      }
      double afterMoney = beforeMoney + money;
      String refId = null;
      Moment thisTime = new Moment();
      UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, Integer.valueOf(refType), refId, thisTime.toSimpleTime(), remarks);
      flag = this.uBillDao.add(tmpBill);
      if (flag)
      {
        if (account == 1) {
          this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        if (account == 2) {
          this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        if (account == 3) {
          this.uBaccaratReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
      }
    }
    catch (Exception e)
    {
      logger.error("写入活动账单失败！", e);
    }
    return flag;
  }
  
  public boolean addAdminAddBill(User uBean, int account, double amount, String remarks)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = uBean.getId();
      int type = 13;
      double money = amount;
      double beforeMoney = 0.0D;
      if (account == 1) {
        beforeMoney = uBean.getTotalMoney();
      }
      if (account == 2) {
        beforeMoney = uBean.getLotteryMoney();
      }
      if (account == 3) {
        beforeMoney = uBean.getBaccaratMoney();
      }
      double afterMoney = beforeMoney + money;
      Integer refType = null;
      String refId = null;
      Moment thisTime = new Moment();
      UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
      return this.uBillDao.add(tmpBill);
    }
    catch (Exception e)
    {
      logger.error("写入管理员增加资金失败！", e);
    }
    return flag;
  }
  
  public boolean addAdminMinusBill(User uBean, int account, double amount, String remarks)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = uBean.getId();
      int type = 14;
      double money = amount;
      double beforeMoney = 0.0D;
      if (account == 1) {
        beforeMoney = uBean.getTotalMoney();
      }
      if (account == 2) {
        beforeMoney = uBean.getLotteryMoney();
      }
      if (account == 3) {
        beforeMoney = uBean.getBaccaratMoney();
      }
      double afterMoney = beforeMoney - money;
      Integer refType = null;
      String refId = null;
      Moment thisTime = new Moment();
      UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
      return this.uBillDao.add(tmpBill);
    }
    catch (Exception e)
    {
      logger.error("写入管理员减少资金失败！", e);
    }
    return flag;
  }
  
  public boolean addSpendBill(UserBets bBean, User uBean)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = uBean.getId();
      int account = 2;
      int type = 6;
      double money = bBean.getMoney();
      double beforeMoney = uBean.getLotteryMoney();
      double afterMoney = beforeMoney - money;
      Integer refType = null;
      String refId = String.valueOf(bBean.getId());
      Moment thisTime = new Moment();
      String remarks = "用户投注：" + bBean.getExpect();
      UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
      flag = this.uBillDao.add(tmpBill);
      if (flag)
      {
        this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
        this.uLotteryDetailsReportService.update(userId, bBean.getLotteryId(), bBean.getRuleId(), type, money, thisTime.toSimpleDate());
      }
    }
    catch (Exception e)
    {
      logger.error("写入彩票消费失败！", e);
    }
    return flag;
  }
  
  public boolean addCancelOrderBill(UserBets bBean, User uBean)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = uBean.getId();
      int account = 2;
      int type = 10;
      double money = bBean.getMoney();
      double beforeMoney = uBean.getLotteryMoney();
      double afterMoney = beforeMoney + money;
      Integer refType = null;
      String refId = String.valueOf(bBean.getId());
      Moment thisTime = new Moment();
      String remarks = "用户撤单：" + bBean.getExpect();
      UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
      flag = this.uBillDao.add(tmpBill);
      if (flag)
      {
        this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
        this.uLotteryDetailsReportService.update(userId, bBean.getLotteryId(), bBean.getRuleId(), type, money, thisTime.toSimpleDate());
      }
    }
    catch (Exception e)
    {
      logger.error("写入彩票撤单失败！", e);
    }
    return flag;
  }
  
  public boolean addDividendBill(User uBean, int account, double amount, String remarks, boolean activity)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = uBean.getId();
      int type = 12;
      double money = amount;
      double beforeMoney = 0.0D;
      if (account == 1) {
        beforeMoney = uBean.getTotalMoney();
      }
      if (account == 2) {
        beforeMoney = uBean.getLotteryMoney();
      }
      if (account == 3) {
        beforeMoney = uBean.getBaccaratMoney();
      }
      double afterMoney = beforeMoney + money;
      Integer refType = null;
      String refId = null;
      Moment thisTime = new Moment();
      UserBill tmpBill = new UserBill(billno, userId, account, type, Math.abs(money), beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
      flag = this.uBillDao.add(tmpBill);
      if ((flag) && (activity))
      {
        if (account == 1) {
          this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        if (account == 2) {
          this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        if (account == 3) {
          this.uBaccaratReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
      }
    }
    catch (Exception e)
    {
      logger.error("写入分红账单失败！", e);
    }
    return flag;
  }
  
  public boolean addRewardPayBill(User uBean, int account, double amount, String remarks)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = uBean.getId();
      int type = 18;
      double money = amount;
      double beforeMoney = 0.0D;
      if (account == 1) {
        beforeMoney = uBean.getTotalMoney();
      }
      if (account == 2) {
        beforeMoney = uBean.getLotteryMoney();
      }
      if (account == 3) {
        beforeMoney = uBean.getBaccaratMoney();
      }
      double afterMoney = beforeMoney - money;
      Integer refType = null;
      String refId = null;
      Moment thisTime = new Moment();
      UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
      flag = this.uBillDao.add(tmpBill);
      if (flag)
      {
        if (account == 1) {
          this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        if (account == 2) {
          this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        if (account == 3) {
          this.uBaccaratReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
      }
    }
    catch (Exception e)
    {
      logger.error("写入支付佣金账单失败！", e);
    }
    return flag;
  }
  
  public boolean addRewardIncomeBill(User uBean, int account, double amount, String remarks)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = uBean.getId();
      int type = 19;
      double money = amount;
      double beforeMoney = 0.0D;
      if (account == 1) {
        beforeMoney = uBean.getTotalMoney();
      }
      if (account == 2) {
        beforeMoney = uBean.getLotteryMoney();
      }
      if (account == 3) {
        beforeMoney = uBean.getBaccaratMoney();
      }
      double afterMoney = beforeMoney + money;
      Integer refType = null;
      String refId = null;
      Moment thisTime = new Moment();
      UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
      flag = this.uBillDao.add(tmpBill);
      if (flag)
      {
        if (account == 1) {
          this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        if (account == 2) {
          this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        if (account == 3) {
          this.uBaccaratReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
      }
    }
    catch (Exception e)
    {
      logger.error("写入收取佣金账单失败！", e);
    }
    return flag;
  }
  
  public boolean addRewardReturnBill(User uBean, int account, double amount, String remarks)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = uBean.getId();
      int type = 20;
      double money = amount;
      double beforeMoney = 0.0D;
      if (account == 1) {
        beforeMoney = uBean.getTotalMoney();
      }
      if (account == 2) {
        beforeMoney = uBean.getLotteryMoney();
      }
      if (account == 3) {
        beforeMoney = uBean.getBaccaratMoney();
      }
      double afterMoney = beforeMoney + money;
      Integer refType = null;
      String refId = null;
      Moment thisTime = new Moment();
      UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
      flag = this.uBillDao.add(tmpBill);
      if (flag)
      {
        if (account == 1) {
          this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        if (account == 2) {
          this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        if (account == 3) {
          this.uBaccaratReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
      }
    }
    catch (Exception e)
    {
      logger.error("写入退还佣金账单失败！", e);
    }
    return flag;
  }
  
  public boolean addDailySettleBill(User uBean, int account, double amount, String remarks, boolean activity)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = uBean.getId();
      int type = 22;
      double money = amount;
      double beforeMoney = 0.0D;
      if (account == 1) {
        beforeMoney = uBean.getTotalMoney();
      }
      if (account == 2) {
        beforeMoney = uBean.getLotteryMoney();
      }
      if (account == 3) {
        beforeMoney = uBean.getBaccaratMoney();
      }
      double afterMoney = beforeMoney + money;
      Integer refType = null;
      String refId = null;
      Moment thisTime = new Moment();
      UserBill tmpBill = new UserBill(billno, userId, account, type, Math.abs(money), beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
      flag = this.uBillDao.add(tmpBill);
      if ((flag) && (activity))
      {
        if (account == 1) {
          this.uMainReportService.update(userId, 5, Math.abs(money), thisTime.toSimpleDate());
        }
        if (account == 2) {
          this.uLotteryReportService.update(userId, 5, Math.abs(money), thisTime.toSimpleDate());
        }
        if (account == 3) {
          this.uBaccaratReportService.update(userId, 5, Math.abs(money), thisTime.toSimpleDate());
        }
      }
    }
    catch (Exception e)
    {
      logger.error("写入退还佣金账单失败！", e);
    }
    return flag;
  }
  
  public boolean addGameWaterBill(User uBean, int account, int type, double amount, String remarks)
  {
    boolean flag = false;
    try
    {
      String billno = billno();
      int userId = uBean.getId();
      double money = amount;
      double beforeMoney = 0.0D;
      
      int _type = type == 1 ? 11 : 9;
      if (account == 1) {
        beforeMoney = uBean.getTotalMoney();
      }
      if (account == 2) {
        beforeMoney = uBean.getLotteryMoney();
      }
      if (account == 3) {
        beforeMoney = uBean.getBaccaratMoney();
      }
      double afterMoney = beforeMoney + money;
      Integer refType = null;
      String refId = null;
      Moment thisTime = new Moment();
      UserBill tmpBill = new UserBill(billno, userId, account, _type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
      flag = this.uBillDao.add(tmpBill);
      if (flag) {
        if (type == 1) {
          this.uGameReportService.update(userId, 4, 0.0D, 0.0D, amount, 0.0D, thisTime.toSimpleDate());
        } else {
          this.uGameReportService.update(userId, 4, 0.0D, 0.0D, 0.0D, amount, thisTime.toSimpleDate());
        }
      }
    }
    catch (Exception e)
    {
      logger.error("写入退还佣金账单失败！", e);
    }
    return flag;
  }
  
  @Transactional(readOnly=true)
  public PageList search(String keyword, String username, Integer utype, Integer type, String minTime, String maxTime, Double minMoney, Double maxMoney, Integer status, int start, int limit)
  {
    boolean isSearch = true;
    User targetUser = null;
    StringBuilder sqlStr = new StringBuilder();
    if (StringUtil.isNotNull(username))
    {
      targetUser = this.uDao.getByUsername(username);
      if (targetUser != null) {
        sqlStr.append("\tand b.user_id = ").append(targetUser.getId());
      } else {
        isSearch = false;
      }
    }
    if (StringUtil.isNotNull(keyword))
    {
      List<UserBets> tmpBets = this.uBetsDao.getByBillno(keyword, false);
      UserBets uBets = CollectionUtils.isNotEmpty(tmpBets) ? (UserBets)tmpBets.get(0) : null;
      if (uBets != null)
      {
        sqlStr.append("\tand b.ref_id = ").append("'" + uBets.getId() + "'");
        sqlStr.append("\tand b.account = ").append(2);
        sqlStr.append("\tand b.type  in ").append("(6,7,8,9,10)");
      }
    }
    if (type != null) {
      sqlStr.append(" and b.type  =").append(type.intValue());
    }
    if (utype != null) {
      sqlStr.append(" and u.type  =").append(utype.intValue());
    } else {
      sqlStr.append("  and u.upid != ").append(0);
    }
    if (StringUtil.isNotNull(minTime)) {
      sqlStr.append(" and b.time  >=").append("'" + minTime + "'");
    }
    if (StringUtil.isNotNull(maxTime)) {
      sqlStr.append(" and b.time  <=").append("'" + maxTime + "'");
    }
    if (minMoney != null) {
      sqlStr.append("  and ABS(b.money) >= ").append(minMoney.doubleValue());
    }
    if (maxMoney != null) {
      sqlStr.append("  and ABS(b.money) <= ").append(maxMoney.doubleValue());
    }
    if (status != null) {
      sqlStr.append("  and b.status = ").append(status.intValue());
    }
    sqlStr.append("   ORDER BY  b.time,  b.id desc ");
    if (isSearch)
    {
      List<UserBillVO> list = new ArrayList();
      PageList pList = this.uBillDao.findNoDemoUserBill(sqlStr.toString(), start, limit);
      for (Object tmpBean : pList.getList()) {
        list.add(new UserBillVO((UserBill)tmpBean, this.lotteryDataFactory));
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  @Transactional(readOnly=true)
  public PageList searchHistory(String keyword, String username, Integer type, String minTime, String maxTime, Double minMoney, Double maxMoney, Integer status, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    boolean isSearch = true;
    User targetUser = null;
    if (StringUtil.isNotNull(username))
    {
      targetUser = this.uDao.getByUsername(username);
      if (targetUser != null) {
        criterions.add(Restrictions.eq("userId", Integer.valueOf(targetUser.getId())));
      } else {
        isSearch = false;
      }
    }
    List<HistoryUserBets> tmpBets;
    if (StringUtil.isNotNull(keyword))
    {
      boolean isOrder = false;
      
      Conjunction conjunctionBill = Restrictions.conjunction();
      conjunctionBill.add(Restrictions.like("billno", keyword, MatchMode.ANYWHERE));
      
      Conjunction conjunctionOrder = Restrictions.conjunction();
      
      tmpBets = this.uBetsDao.getHistoryByBillno(keyword, false);
      if (tmpBets.size() > 0)
      {
        isOrder = true;
        conjunctionOrder.add(Restrictions.eq("account", Integer.valueOf(2)));
        conjunctionOrder.add(Restrictions.in("type", new Integer[] { Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(9), Integer.valueOf(9), Integer.valueOf(10) }));
        List<String> targetIds = new ArrayList();
        for (HistoryUserBets tmpBean : tmpBets) {
          targetIds.add(String.valueOf(tmpBean.getId()));
        }
        conjunctionOrder.add(Restrictions.in("refId", targetIds));
      }
      if (isOrder) {
        criterions.add(conjunctionOrder);
      } else {
        criterions.add(conjunctionBill);
      }
    }
    if (type != null) {
      criterions.add(Restrictions.eq("type", Integer.valueOf(type.intValue())));
    }
    if (StringUtil.isNotNull(minTime)) {
      criterions.add(Restrictions.ge("time", minTime));
    }
    if (StringUtil.isNotNull(maxTime)) {
      criterions.add(Restrictions.lt("time", maxTime));
    }
    if (minMoney != null) {
      criterions.add(Restrictions.sqlRestriction("ABS(money) >= " + minMoney.doubleValue()));
    }
    if (maxMoney != null) {
      criterions.add(Restrictions.sqlRestriction("ABS(money) <= " + maxMoney.doubleValue()));
    }
    if (status != null) {
      criterions.add(Restrictions.eq("status", Integer.valueOf(status.intValue())));
    }
    orders.add(Order.desc("time"));
    orders.add(Order.desc("id"));
    if (isSearch)
    {
      List<HistoryUserBillVO> list = new ArrayList();
      PageList pList = this.uBillDao.findHistory(criterions, orders, start, limit);
      for (Object tmpBean : pList.getList())
      {
        HistoryUserBillVO tmpVO = new HistoryUserBillVO((HistoryUserBill)tmpBean, this.lotteryDataFactory);
        list.add(tmpVO);
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  @Transactional(readOnly=true)
  public List<UserBillVO> getLatest(int userId, int type, int count)
  {
    List<UserBillVO> formatList = new ArrayList();
    List<UserBill> list = this.uBillDao.getLatest(userId, type, count);
    for (UserBill tmpBean : list) {
      formatList.add(new UserBillVO(tmpBean, this.lotteryDataFactory));
    }
    return formatList;
  }
}
