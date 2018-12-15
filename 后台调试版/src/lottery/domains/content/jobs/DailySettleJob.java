package lottery.domains.content.jobs;

import admin.domains.jobs.MailJob;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javautils.date.Moment;
import javautils.math.MathUtil;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserDailySettleBillService;
import lottery.domains.content.biz.UserDailySettleService;
import lottery.domains.content.biz.UserLotteryReportService;
import lottery.domains.content.biz.UserService;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserLotteryReportDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserDailySettle;
import lottery.domains.content.entity.UserDailySettleBill;
import lottery.domains.content.entity.UserLotteryReport;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import lottery.domains.content.vo.config.DailySettleConfig;
import lottery.domains.content.vo.user.UserDailySettleBillAdapter;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.UserCodePointUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailySettleJob
{
  private static final Logger log = LoggerFactory.getLogger(DailySettleJob.class);
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserService uService;
  @Autowired
  private UserDailySettleService uDailySettleService;
  @Autowired
  private UserDailySettleBillService uDailySettleBillService;
  @Autowired
  private UserBillService uBillService;
  @Autowired
  private UserLotteryReportService uLotteryReportService;
  @Autowired
  private UserLotteryReportDao uLotteryReportDao;
  @Autowired
  private UserSysMessageService uSysMessageService;
  @Autowired
  private UserCodePointUtil uCodePointUtil;
  @Autowired
  private MailJob mailJob;
  @Autowired
  private LotteryDataFactory dataFactory;
  
  @Scheduled(cron="0 5 2 0/1 * *")
  public void schedule()
  {
    try
    {
      if (!this.dataFactory.getDailySettleConfig().isEnable())
      {
        log.info("日结没有开启，不发放");
        return;
      }
      String yesterday = new Moment().subtract(1, "days").toSimpleDate();
      String today = new Moment().toSimpleDate();
      
      log.info("发放日结开始：{}~{}", yesterday, today);
      
      settleUp(yesterday, today);
      
      log.info("发放日结完成：{}~{}", yesterday, today);
    }
    catch (Exception e)
    {
      log.error("发放日结出错", e);
    }
  }
  
  public List<UserDailySettleBillAdapter> settleUp(String sTime, String eTime)
  {
    List<User> neibuZhaoShangs = this.uService.findNeibuZhaoShang();
    if (CollectionUtils.isEmpty(neibuZhaoShangs))
    {
      log.error("没有找到任何内部招商账号，本次未产生任何日结数据");
      return null;
    }
    List<User> zhaoShangs = this.uService.findZhaoShang(neibuZhaoShangs);
    if (CollectionUtils.isEmpty(zhaoShangs))
    {
      log.error("没有找到任何招商账号，本次未产生任何日结数据");
      return null;
    }
    List<User> zhishus = this.uService.findZhaoShang(zhaoShangs);
    if (CollectionUtils.isEmpty(zhishus))
    {
      log.error("没有找到任何直属账号，本次未产生任何日结数据");
      return null;
    }
    List<UserDailySettleBillAdapter> neibuZhaoShangBills = null;
    try
    {
      log.info("发放直属日结开始：{}~{}", sTime, eTime);
      neibuZhaoShangBills = settleUpNeibuZhaoShangs(zhishus, sTime, eTime);
      log.info("发放直属日结完成：{}~{}", sTime, eTime);
    }
    catch (Exception e)
    {
      log.error("发放直属日结出错", e);
    }
    List<UserDailySettleBillAdapter> allBills = new ArrayList();
    if (CollectionUtils.isNotEmpty(neibuZhaoShangBills)) {
      allBills.addAll(neibuZhaoShangBills);
    }
    sendMail(allBills, sTime, eTime);
    
    return allBills;
  }
  
  private List<UserDailySettleBillAdapter> settleUpNeibuZhaoShangs(List<User> neibuZhaoShangs, String sTime, String eTime)
  {
    List<UserDailySettleBillAdapter> bills = new ArrayList();
    for (User neibuZhaoShang : neibuZhaoShangs)
    {
      UserDailySettleBillAdapter billAdapter = settleUpWithUser(neibuZhaoShang, sTime, eTime, true, 1);
      if (billAdapter != null) {
        bills.add(billAdapter);
      }
    }
    if (CollectionUtils.isNotEmpty(bills)) {
      for (UserDailySettleBillAdapter bill : bills) {
        processLineBill(bill);
      }
    }
    return bills;
  }
  
  private List<UserDailySettleBillAdapter> settleUpZhaoShangs(List<User> zhaoShangs, String sTime, String eTime)
  {
    List<UserDailySettleBillAdapter> bills = new ArrayList();
    for (User zhaoShang : zhaoShangs)
    {
      UserDailySettleBillAdapter billAdapter = settleUpWithUser(zhaoShang, sTime, eTime, true, 1);
      if (billAdapter != null) {
        bills.add(billAdapter);
      }
    }
    if (CollectionUtils.isNotEmpty(bills)) {
      for (UserDailySettleBillAdapter bill : bills) {
        processLineBill(bill);
      }
    }
    return bills;
  }
  
  private UserDailySettleBill createBill(int userId, String eTime, UserDailySettle dailySettle)
  {
    int minValidUserCfg = this.dataFactory.getDailySettleConfig().getMinValidUserl();
    UserDailySettleBill dailySettleBill = new UserDailySettleBill();
    dailySettleBill.setUserId(userId);
    dailySettleBill.setIndicateDate(eTime);
    if (dailySettle.getMinValidUser() >= minValidUserCfg) {
      dailySettleBill.setMinValidUser(dailySettle.getMinValidUser());
    } else {
      dailySettleBill.setMinValidUser(minValidUserCfg);
    }
    dailySettleBill.setValidUser(0);
    dailySettleBill.setScale(0.0D);
    dailySettleBill.setBillingOrder(0.0D);
    dailySettleBill.setUserAmount(0.0D);
    return dailySettleBill;
  }
  
  private boolean check(User user, UserDailySettle dailySettle)
  {
    if (user.getId() == 72)
    {
      String error = String.format("契约日结错误提醒;用户%s为总账号，但查找到其拥有日结配置，本次不对其进行结算，不影响整体结算；", new Object[] { user.getUsername() });
      log.error(error);
      this.mailJob.addWarning(error);
      return false;
    }
    boolean isZhaoShang = this.uCodePointUtil.isLevel3Proxy(user);
    if (isZhaoShang) {
      return true;
    }
    UserDailySettle upUserDailySettle = this.uDailySettleService.getByUserId(user.getUpid());
    if (upUserDailySettle == null)
    {
      String error = String.format("契约日结错误提醒;用户%s没有找到上级的日结配置，本次不对其团队进行结算；", new Object[] { user.getUsername() });
      log.error(error);
      this.mailJob.addWarning(error);
      return false;
    }
    if (!this.uDailySettleService.checkValidLevel(dailySettle.getScaleLevel(), dailySettle.getSalesLevel(), dailySettle.getLossLevel(), upUserDailySettle, dailySettle.getUserLevel()))
    {
      String error = String.format("日结分红错误提醒;用户%s，所签订分红条款为无效条款，条款内容：分红比例[%s]，销量[%s]，亏损[%s]，本次不对其团队进行结算；", new Object[] {
        user.getUsername(), dailySettle.getScaleLevel(), dailySettle.getSalesLevel(), dailySettle.getLossLevel() });
      log.error(error);
      this.mailJob.addWarning(error);
      return false;
    }
    return true;
  }
  
  public UserDailySettleBillAdapter settleUpWithUser(User user, String sTime, String eTime, boolean settleLowers, int issueType)
  {
    UserDailySettle dailySettle = this.uDailySettleService.getByUserId(user.getId());
    if ((dailySettle == null) || (dailySettle.getStatus() != 1)) {
      return null;
    }
    boolean checked = check(user, dailySettle);
    if (!checked) {
      return null;
    }
    List<UserLotteryReportVO> reports = this.uLotteryReportService.report(user.getId(), sTime, eTime);
    if (CollectionUtils.isEmpty(reports)) {
      return null;
    }
    UserDailySettleBill upperBill = createBill(user.getId(), eTime, dailySettle);
    upperBill.setIssueType(issueType);
    
    UserDailySettleBill summaryBill = summaryUpReports(reports, user.getId(), sTime, eTime);
    upperBill.setBillingOrder(summaryBill.getBillingOrder());
    upperBill.setThisLoss(summaryBill.getThisLoss());
    upperBill.setValidUser(summaryBill.getValidUser());
    if (summaryBill.getBillingOrder() <= 0.0D) {
      return null;
    }
    boolean isCheckLossCfg = this.dataFactory.getDailySettleConfig().isCheckLoss();
    double billingOrder = MathUtil.divide(upperBill.getBillingOrder(), 10000.0D, 2);
    double thisLoss = MathUtil.divide(Math.abs(upperBill.getThisLoss()), 10000.0D, 2);
    int thisUser = upperBill.getValidUser();
    
    String[] scaleLevels = dailySettle.getScaleLevel().split(",");
    String[] salesLevels = dailySettle.getSalesLevel().split(",");
    String[] lossLevels = dailySettle.getLossLevel().split(",");
    String[] userLevels = dailySettle.getUserLevel().split(",");
    
    List<Integer> levels = new ArrayList();
    for (int i = 0; i < salesLevels.length; i++) {
      if ((billingOrder >= Double.parseDouble(salesLevels[i])) && (thisUser >= Integer.valueOf(userLevels[i]).intValue())) {
        levels.add(Integer.valueOf(i));
      }
    }
    if (levels.size() > 0)
    {
      Collections.sort(levels);
      upperBill.setScale(Double.valueOf(scaleLevels[((Integer)levels.get(levels.size() - 1)).intValue()]).doubleValue());
    }
    int i;
    if (upperBill.getStatus() != 5)
    {
      double calAmount = 0.0D;
      if ((levels.size() > 0) && (upperBill.getValidUser() >= upperBill.getMinValidUser()))
      {
        double scale = MathUtil.divide(upperBill.getScale(), 100.0D, 6);
        
        calAmount = MathUtil.decimalFormat(new BigDecimal(MathUtil.multiply(Math.abs(upperBill.getBillingOrder()), scale)), 2);
        if (isCheckLossCfg)
        {
          levels.clear();
          calAmount = 0.0D;
          for (i = 0; i < salesLevels.length; i++) {
            if ((billingOrder >= Double.parseDouble(salesLevels[i])) && (thisLoss >= Double.parseDouble(lossLevels[i])) && (thisUser >= Integer.valueOf(userLevels[i]).intValue())) {
              levels.add(Integer.valueOf(i));
            }
          }
          if (levels.size() > 0)
          {
            Collections.sort(levels);
            upperBill.setScale(Double.valueOf(scaleLevels[((Integer)levels.get(levels.size() - 1)).intValue()]).doubleValue());
            
            scale = MathUtil.divide(upperBill.getScale(), 100.0D, 4);
            calAmount = MathUtil.decimalFormat(new BigDecimal(MathUtil.multiply(Math.abs(upperBill.getBillingOrder()), scale)), 2);
          }
        }
      }
      upperBill.setCalAmount(calAmount);
      if (calAmount == 0.0D)
      {
        upperBill.setCalAmount(0.0D);
        upperBill.setScale(0.0D);
        upperBill.setStatus(5);
        upperBill.setRemarks("日结分红条款未达标");
        return new UserDailySettleBillAdapter(upperBill, null);
      }
    }
    double lowerTotalAmount = 0.0D;
    List<UserDailySettleBillAdapter> lowerBills = new ArrayList();
    if (settleLowers) {
      for (UserLotteryReportVO report : reports) {
        if ((!"总计".equals(report.getName())) && 
          (!report.getName().equalsIgnoreCase(user.getUsername())))
        {
          User subUser = this.uService.getByUsername(report.getName());
          
          UserDailySettleBillAdapter lowerBillAdapter = settleUpWithUser(subUser, sTime, eTime, true, 2);
          if (lowerBillAdapter != null)
          {
            UserDailySettleBill lowerUpperBill = lowerBillAdapter.getUpperBill();
            lowerTotalAmount = MathUtil.add(lowerTotalAmount, lowerUpperBill.getCalAmount());
            lowerBills.add(lowerBillAdapter);
          }
        }
      }
    }
    upperBill.setLowerTotalAmount(lowerTotalAmount);
    return new UserDailySettleBillAdapter(upperBill, lowerBills);
  }
  
  private void processLineBill(UserDailySettleBillAdapter uDailySettleBillAdapter)
  {
    UserDailySettleBill upperBill = uDailySettleBillAdapter.getUpperBill();
    List<UserDailySettleBillAdapter> lowerBills = uDailySettleBillAdapter.getLowerBills();
    if ((upperBill.getIssueType() == 1) && (CollectionUtils.isEmpty(lowerBills)))
    {
      if (upperBill.getStatus() == 4)
      {
        double amount = 0.0D;
        upperBill.setUserAmount(amount);
        upperBill.setTotalReceived(amount);
        saveBill(upperBill, 4, amount);
      }
      else
      {
        double amount = upperBill.getCalAmount();
        upperBill.setUserAmount(amount);
        upperBill.setTotalReceived(amount);
        saveBill(upperBill, 1, amount);
      }
      return;
    }
    if ((upperBill.getIssueType() == 2) && (CollectionUtils.isEmpty(lowerBills)))
    {
      if (upperBill.getStatus() == 4)
      {
        double amount = 0.0D;
        upperBill.setUserAmount(amount);
        upperBill.setTotalReceived(amount);
        saveBill(upperBill, 4, amount);
      }
      else
      {
        int status = upperBill.getTotalReceived() >= upperBill.getCalAmount() ? 1 : 2;
        upperBill.setUserAmount(upperBill.getCalAmount());
        saveBill(upperBill, status, upperBill.getTotalReceived());
      }
      return;
    }
    double upperBillMoney;
    if (upperBill.getIssueType() == 1) {
      upperBillMoney = upperBill.getCalAmount();
    } else {
      upperBillMoney = upperBill.getTotalReceived();
    }
    double upperThisTimePaid = 0.0D;
    for (UserDailySettleBillAdapter lowerBill : lowerBills)
    {
      UserDailySettleBill lowerUpperBill = lowerBill.getUpperBill();
      
      double lowerUpperAmount = lowerUpperBill.getCalAmount();
      if (lowerUpperAmount > 0.0D)
      {
        double lowerRemainReceived = lowerUpperAmount;
        
        double billGive = 0.0D;
        if ((upperBillMoney > 0.0D) && (lowerRemainReceived > 0.0D))
        {
          billGive = lowerUpperAmount >= upperBillMoney ? upperBillMoney : lowerUpperAmount;
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
            UserVO subUser = this.dataFactory.getUser(lowerUpperBill.getUserId());
            this.uDao.updateTotalMoney(upperUser.getId(), -totalMoneyGive);
            
            this.uBillService.addDailySettleBill(upperUser, 1, -totalMoneyGive, "系统自动扣发" + totalMoneyGive + "日结金额到" + subUser.getUsername(), false);
          }
          if (lotteryMoneyGive > 0.0D)
          {
            UserVO subUser = this.dataFactory.getUser(lowerUpperBill.getUserId());
            this.uDao.updateLotteryMoney(upperUser.getId(), -lotteryMoneyGive);
            
            this.uBillService.addDailySettleBill(upperUser, 2, -lotteryMoneyGive, "系统自动扣发" + lotteryMoneyGive + "日结金额到" + subUser.getUsername(), false);
          }
          upperThisTimePaid = MathUtil.add(upperThisTimePaid, totalGive);
          
          lowerUpperBill.setTotalReceived(totalGive);
        }
      }
      processLineBill(lowerBill);
    }
    upperBill.setLowerPaidAmount(upperThisTimePaid);
    if (upperBillMoney > 0.0D)
    {
      upperBill.setUserAmount(upperBillMoney);
      upperBill.setTotalReceived(upperBillMoney);
      saveBill(upperBill, 1, upperBillMoney);
    }
    else
    {
      double notYetPay = MathUtil.subtract(upperBill.getLowerTotalAmount(), upperBill.getLowerPaidAmount());
      if (notYetPay > 0.0D)
      {
        double amount = 0.0D;
        upperBill.setUserAmount(amount);
        upperBill.setRemarks("余额不足,请充值！");
        saveBill(upperBill, 3, 0.0D);
      }
      else
      {
        double amount = 0.0D;
        upperBill.setUserAmount(amount);
        saveBill(upperBill, 1, 0.0D);
      }
    }
  }
  
  private void saveBill(UserDailySettleBill upperBill, int status, double userAmount)
  {
    upperBill.setSettleTime(new Moment().toSimpleTime());
    upperBill.setStatus(status);
    if (userAmount > 0.0D)
    {
      User user = this.uDao.getById(upperBill.getUserId());
      if (user != null)
      {
        String remarks = String.format("日结,销量：%s", new Object[] { new BigDecimal(upperBill.getBillingOrder()).setScale(4, RoundingMode.FLOOR).toString() });
        boolean addedBill = this.uBillService.addDailySettleBill(user, 2, userAmount, remarks, true);
        if (addedBill)
        {
          this.uDao.updateLotteryMoney(user.getId(), userAmount);
          this.uSysMessageService.addDailySettleBill(user.getId(), upperBill.getIndicateDate());
        }
      }
    }
    this.uDailySettleBillService.add(upperBill);
  }
  
  private UserDailySettleBill summaryUpReports(List<UserLotteryReportVO> reports, int userId, String sTime, String eTime)
  {
    double billingOrder = 0.0D;
    for (UserLotteryReportVO report : reports) {
      if ("总计".equals(report.getName()))
      {
        billingOrder = report.getBillingOrder();
        break;
      }
    }
    double minBillingOrder = this.dataFactory.getDailySettleConfig().getMinBillingOrder();
    
    List<User> userLowers = this.uDao.getUserLower(userId);
    int validUser = 0;
    for (User lowerUser : userLowers)
    {
      double lowerUserBillingOrder = summaryUpLowerUserReports(lowerUser.getId(), sTime, eTime);
      if (lowerUserBillingOrder >= minBillingOrder) {
        validUser++;
      }
    }
    double selfBilling = summaryUpLowerUserReports(userId, sTime, eTime);
    if (selfBilling >= minBillingOrder) {
      validUser++;
    }
    double thisLoss = calculateLotteryLossByLotteryReport(reports);
    
    UserDailySettleBill bill = new UserDailySettleBill();
    bill.setBillingOrder(billingOrder);
    bill.setValidUser(validUser);
    bill.setThisLoss(thisLoss);
    return bill;
  }
  
  private double calculateLotteryLossByLotteryReport(List<UserLotteryReportVO> reports)
  {
    double lotteryLoss = 0.0D;
    for (UserLotteryReportVO report : reports) {
      if ("总计".equals(report.getName()))
      {
        lotteryLoss = report.getPrize() + report.getSpendReturn() + report.getProxyReturn() + 
          report.getActivity() + report.getRechargeFee() - report.getBillingOrder();
        break;
      }
    }
    return lotteryLoss;
  }
  
  private double summaryUpLowerUserReports(int userId, String sTime, String eTime)
  {
    List<UserLotteryReport> lowerUserReports = this.uLotteryReportDao.list(userId, sTime, eTime);
    if (CollectionUtils.isEmpty(lowerUserReports)) {
      return 0.0D;
    }
    double billingOrder = 0.0D;
    for (UserLotteryReport lowerUserReport : lowerUserReports) {
      billingOrder = MathUtil.add(billingOrder, lowerUserReport.getBillingOrder());
    }
    return billingOrder;
  }
  
  private void sendMail(List<UserDailySettleBillAdapter> bills, String sTime, String eTime)
  {
    try
    {
      double totalBillingOrder = 0.0D;
      double totalAmount = 0.0D;
      if (CollectionUtils.isNotEmpty(bills))
      {
        List<UserDailySettleBill> allBills = new ArrayList();
        getAllBills(bills, allBills);
        for (UserDailySettleBill bill : allBills) {
          if (bill.getIssueType() == 1) {
            totalAmount = MathUtil.add(totalAmount, bill.getCalAmount());
          }
        }
      }
      List<UserLotteryReportVO> reports = this.uLotteryReportService.report(sTime, eTime);
      if (CollectionUtils.isNotEmpty(reports)) {
        for (UserLotteryReportVO report : reports) {
          if ("总计".equals(report.getName()))
          {
            totalBillingOrder = report.getBillingOrder();
            break;
          }
        }
      }
      this.mailJob.sendDailySettle(sTime, totalBillingOrder, totalAmount);
    }
    catch (Exception e)
    {
      log.error("发送契约日结邮件出错", e == null ? "" : e.getMessage());
    }
  }
  
  private List<UserDailySettleBill> getAllBills(List<UserDailySettleBillAdapter> bills, List<UserDailySettleBill> container)
  {
    if (CollectionUtils.isEmpty(bills)) {
      return container;
    }
    for (UserDailySettleBillAdapter bill : bills)
    {
      container.add(bill.getUpperBill());
      
      getAllBills(bill.getLowerBills(), container);
    }
    return container;
  }
}
