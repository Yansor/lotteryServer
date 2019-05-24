package lottery.domains.content.jobs;

import admin.domains.jobs.MailJob;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javautils.StringUtil;
import javautils.date.DateUtil;
import javautils.date.Moment;
import javautils.math.MathUtil;
import lottery.domains.content.biz.UserDividendBillService;
import lottery.domains.content.biz.UserDividendService;
import lottery.domains.content.biz.UserGameReportService;
import lottery.domains.content.biz.UserLotteryReportService;
import lottery.domains.content.biz.UserService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserGameReportDao;
import lottery.domains.content.dao.UserLotteryReportDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserDividend;
import lottery.domains.content.entity.UserDividendBill;
import lottery.domains.content.entity.UserGameReport;
import lottery.domains.content.entity.UserLotteryReport;
import lottery.domains.content.vo.bill.UserGameReportVO;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import lottery.domains.content.vo.config.DividendConfig;
import lottery.domains.content.vo.user.UserDividendBillAdapter;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.UserCodePointUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DividendRankJob
{
  private static final Logger log = LoggerFactory.getLogger(DividendRankJob.class);
  @Autowired
  private UserDao userDao;
  @Autowired
  private UserService uService;
  @Autowired
  private UserDividendService uDividendService;
  @Autowired
  private UserDividendBillService uDividendBillService;
  @Autowired
  private UserLotteryReportService uLotteryReportService;
  @Autowired
  private UserLotteryReportDao uLotteryReportDao;
  @Autowired
  private UserGameReportService uGameReportService;
  @Autowired
  private UserGameReportDao uGameReportDao;
  @Autowired
  private UserCodePointUtil uCodePointUtil;
  @Autowired
  private MailJob mailJob;
  private final boolean ISTOP = true;
  @Autowired
  private LotteryDataFactory dataFactory;
  
  @Scheduled(cron="0 10 2 1,16 * *")
  public void schedule()
  {
    try
    {
      if (!this.dataFactory.getDividendConfig().isEnable())
      {
        log.info("分红没有开启，不发放");
        return;
      }
      String startDate = getStartDate();
      int startDay = Integer.valueOf(startDate.substring(8)).intValue();
      if ((startDay != 1) && (startDay != 16)) {
        return;
      }
      String endDate = getEndDate();
      
      log.info("发放分红开始：{}~{}", startDate, endDate);
      
      settleUp(startDate, endDate);
      
      log.info("发放分红完成：{}-{}", startDate, endDate);
    }
    catch (Exception e)
    {
      log.error("分红发放出错", e);
    }
  }
  
  private void updateAllExpire()
  {
    this.uDividendBillService.updateAllExpire();
  }
  
  public void settleUp(String sTime, String eTime)
  {
    List<User> neibuZhaoShangs = this.uService.findNeibuZhaoShang();
    if (CollectionUtils.isEmpty(neibuZhaoShangs))
    {
      log.error("没有找到任何内部招商账号，本次未产生任何分红数据");
      return;
    }
    List<User> zhaoShangs = this.uService.findZhaoShang(neibuZhaoShangs);
    if (CollectionUtils.isEmpty(zhaoShangs))
    {
      log.error("没有找到任何招商账号，本次未产生任何分红数据");
      return;
    }
    List<UserDividendBillAdapter> bills = new ArrayList();
    try
    {
      log.info("发放招商及以下分红开始：{}~{}", sTime, eTime);
      List<UserDividendBillAdapter> zhaoShangBills = settleUpZhaoShangs(zhaoShangs, sTime, eTime);
      if (CollectionUtils.isNotEmpty(zhaoShangBills)) {
        bills.addAll(zhaoShangBills);
      }
      log.info("发放招商及以下分红完成：{}~{}", sTime, eTime);
    }
    catch (Exception e)
    {
      log.error("发放招商及以下分红出错", e);
    }
    sendMail(bills, sTime, eTime);
  }
  
  private List<UserDividendBillAdapter> settleUpZhaoShangs(List<User> zhaoShangs, String sTime, String eTime)
  {
    List<UserDividendBillAdapter> bills = new ArrayList();
    for (User zhaoShang : zhaoShangs) {
      if (zhaoShang.getUpid() != 0)
      {
        UserDividendBillAdapter billAdapter = settleUpWithUser(zhaoShang, sTime, eTime, true, 
          1);
        if (billAdapter != null) {
          bills.add(billAdapter);
        }
      }
    }
    if (CollectionUtils.isNotEmpty(bills)) {
      for (UserDividendBillAdapter bill : bills) {
        processLineBill(bill);
      }
    }
    return bills;
  }
  
  private UserDividendBill createBill(int userId, String sTime, String eTime, UserDividend userDividend, int issueType)
  {
    int minValidUserCfg = this.dataFactory.getDividendConfig().getMinValidUserl();
    UserDividendBill dividendBill = new UserDividendBill();
    dividendBill.setUserId(userId);
    dividendBill.setIndicateStartDate(sTime);
    dividendBill.setIndicateEndDate(eTime);
    if (userDividend.getMinValidUser() >= minValidUserCfg) {
      dividendBill.setMinValidUser(userDividend.getMinValidUser());
    } else {
      dividendBill.setMinValidUser(minValidUserCfg);
    }
    dividendBill.setValidUser(0);
    dividendBill.setScale(0.0D);
    dividendBill.setBillingOrder(0.0D);
    dividendBill.setUserAmount(0.0D);
    dividendBill.setCalAmount(0.0D);
    dividendBill.setIssueType(issueType);
    return dividendBill;
  }
  
  private boolean check(User user, UserDividend uDividend)
  {
    if (user.getId() == 72)
    {
      String error = String.format("契约分红错误提醒;用户%s为总账号，但查找到其拥有分红配置，本次不对其进行结算，不影响整体结算；", new Object[] { user.getUsername() });
      log.error(error);
      this.mailJob.addWarning(error);
      return false;
    }
    boolean isNeibuZhaoShang = this.uCodePointUtil.isLevel2Proxy(user);
    
    UserDividend upUserDividend = this.uDividendService.getByUserId(user.getUpid());
    if ((!isNeibuZhaoShang) && (upUserDividend == null))
    {
      String error = String.format("契约分红错误提醒;用户%s没有找到上级的分红配置，本次不对其团队进行结算；", new Object[] { user.getUsername() });
      log.error(error);
      this.mailJob.addWarning(error);
      return false;
    }
    String[] scaleLevels = uDividend.getScaleLevel().split(",");
    double minScale = this.dataFactory.getDividendConfig().getLevelsScale()[0];
    double maxScale = this.dataFactory.getDividendConfig().getLevelsScale()[1];
    if (!isNeibuZhaoShang)
    {
      String[] upScaleLevels = upUserDividend.getScaleLevel().split(",");
      maxScale = Double.valueOf(upScaleLevels[(upScaleLevels.length - 1)]).doubleValue();
    }
    if ((Double.valueOf(scaleLevels[(scaleLevels.length - 1)]).doubleValue() > maxScale) || 
      (Double.valueOf(scaleLevels[0]).doubleValue() < minScale))
    {
      String error = String.format("契约分红错误提醒;用户%s为直属号，但分红比例%s不是有效比例%s~%s，本次不对其团队进行结算；", new Object[] { user.getUsername(), 
        uDividend.getScaleLevel(), Double.valueOf(minScale), Double.valueOf(maxScale) });
      log.error(error);
      this.mailJob.addWarning(error);
      return false;
    }
    if (!this.uDividendService.checkValidLevel(uDividend.getScaleLevel(), uDividend.getSalesLevel(), uDividend.getLossLevel(), upUserDividend, uDividend.getUserLevel()))
    {
      String error = String.format("契约分红错误提醒;用户%s，所签订分红条款为无效条款，条款内容：分红比例[%s]，销量[%s]，亏损[%s]，本次不对其团队进行结算；", new Object[] {
        user.getUsername(), uDividend.getScaleLevel(), uDividend.getSalesLevel(), uDividend.getLossLevel() });
      log.error(error);
      this.mailJob.addWarning(error);
      return false;
    }
    return true;
  }
  
  private UserDividendBillAdapter settleUpWithUser(User user, String sTime, String eTime, boolean settleLowers, int issueType)
  {
    UserDividend userDividend = this.uDividendService.getByUserId(user.getId());
    if ((userDividend == null) || (userDividend.getStatus() != 1)) {
      return null;
    }
    boolean checked = check(user, userDividend);
    if (!checked) {
      return null;
    }
    UserDividendBill upperBill = createBill(user.getId(), sTime, eTime, userDividend, issueType);
    
    List<UserLotteryReportVO> reports = this.uLotteryReportService.report(user.getId(), sTime, eTime);
    if (CollectionUtils.isNotEmpty(reports)) {
      summaryUpReports(reports, user.getId(), sTime, eTime, upperBill);
    }
    boolean isCheckLossCfg = this.dataFactory.getDividendConfig().isCheckLoss();
    double billingOrder = MathUtil.divide(upperBill.getBillingOrder(), 10000.0D, 2);
    double thisLoss = MathUtil.divide(Math.abs(upperBill.getThisLoss()), 10000.0D, 2);
    int vaildUser = upperBill.getValidUser();
    
    String[] scaleLevels = userDividend.getScaleLevel().split(",");
    String[] salesLevels = userDividend.getSalesLevel().split(",");
    String[] lossLevels = userDividend.getLossLevel().split(",");
    String[] userLevels = userDividend.getUserLevel().split(",");
    
    List<Integer> levels = new ArrayList();
    for (int i = 0; i < salesLevels.length; i++) {
      if ((billingOrder >= Double.parseDouble(salesLevels[i])) && (vaildUser >= Integer.valueOf(userLevels[i]).intValue())) {
        levels.add(Integer.valueOf(i));
      }
    }
    if (levels.size() > 0)
    {
      Collections.sort(levels);
      upperBill.setScale(Double.valueOf(scaleLevels[((Integer)levels.get(levels.size() - 1)).intValue()]).doubleValue());
    }
    if (upperBill.getStatus() != 5)
    {
      double calAmount = 0.0D;
      if ((levels.size() > 0) && (upperBill.getThisLoss() < 0.0D) && (upperBill.getValidUser() >= upperBill.getMinValidUser()))
      {
        double scale = MathUtil.divide(upperBill.getScale(), 100.0D, 6);
        
        calAmount = MathUtil.decimalFormat(new BigDecimal(MathUtil.multiply(Math.abs(upperBill.getThisLoss()), scale)), 2);
        if (isCheckLossCfg) {
          if (userDividend.getFixed() == 0)
          {
            calAmount = 0.0D;
            for (Integer l : levels)
            {
              scale = MathUtil.divide(Double.valueOf(scaleLevels[l.intValue()]).doubleValue(), 100.0D, 4);
              
              double tempCal = MathUtil.decimalFormat(new BigDecimal(MathUtil.multiply(Math.abs(upperBill.getThisLoss()), scale)), 2);
              if (Double.parseDouble(lossLevels[l.intValue()]) > 0.0D)
              {
                double lossRate = MathUtil.divide(thisLoss, Double.parseDouble(lossLevels[l.intValue()]), 4);
                if (lossRate < 1.0D) {
                  tempCal = MathUtil.decimalFormat(new BigDecimal(String.valueOf(MathUtil.multiply(tempCal, lossRate))), 2);
                }
              }
              if (tempCal > calAmount)
              {
                calAmount = tempCal;
                upperBill.setScale(Double.valueOf(scaleLevels[l.intValue()]).doubleValue());
              }
            }
          }
          else if (userDividend.getFixed() == 1)
          {
            levels.clear();
            calAmount = 0.0D;
            for (int i = 0; i < salesLevels.length; i++) {
              if ((billingOrder >= Double.parseDouble(salesLevels[i])) && (thisLoss >= Double.parseDouble(lossLevels[i])) && (vaildUser >= Integer.valueOf(userLevels[i]).intValue())) {
                levels.add(Integer.valueOf(i));
              }
            }
            if (levels.size() > 0)
            {
              Collections.sort(levels);
              upperBill.setScale(Double.valueOf(scaleLevels[((Integer)levels.get(levels.size() - 1)).intValue()]).doubleValue());
              
              scale = MathUtil.divide(upperBill.getScale(), 100.0D, 4);
              calAmount = MathUtil.decimalFormat(new BigDecimal(MathUtil.multiply(Math.abs(upperBill.getThisLoss()), scale)), 2);
            }
          }
        }
      }
      upperBill.setCalAmount(calAmount);
      if (calAmount == 0.0D)
      {
        upperBill.setCalAmount(0.0D);
        upperBill.setScale(0.0D);
        upperBill.setStatus(5);
        upperBill.setRemarks("契约分红条款未达标");
      }
    }
    int billType = 1;
    if (this.uCodePointUtil.isLevel2Proxy(user))
    {
      upperBill.setCalAmount(0.0D);
      upperBill.setScale(0.0D);
      upperBill.setStatus(1);
      upperBill.setRemarks("内部招商分红，不结算");
    }
    else
    {
      billType = 2;
    }
    double lowerTotalAmount = 0.0D;
    List<UserDividendBillAdapter> lowerBills = new ArrayList();
    if (settleLowers) {
      for (UserLotteryReportVO report : reports) {
        if ((!"总计".equals(report.getName())) && (!report.getName().equalsIgnoreCase(user.getUsername())))
        {
          User subUser = this.userDao.getByUsername(report.getName());
          
          UserDividendBillAdapter lowerBillAdapter = settleUpWithUser(subUser, sTime, eTime, true, billType);
          if (lowerBillAdapter != null)
          {
            if (lowerBillAdapter.getUpperBill().getStatus() != 5) {
              lowerTotalAmount = MathUtil.add(lowerTotalAmount, 
                lowerBillAdapter.getUpperBill().getCalAmount());
            }
            lowerBills.add(lowerBillAdapter);
          }
        }
      }
    }
    upperBill.setLowerTotalAmount(lowerTotalAmount);
    if (this.uCodePointUtil.isLevel2Proxy(user)) {
      upperBill.setLowerTotalAmount(0.0D);
    }
    return new UserDividendBillAdapter(upperBill, lowerBills);
  }
  
  private void processLineBill(UserDividendBillAdapter uDividendBillAdapter)
  {
    UserDividendBill upperBill = uDividendBillAdapter.getUpperBill();
    List<UserDividendBillAdapter> lowerBills = uDividendBillAdapter.getLowerBills();
    if (CollectionUtils.isEmpty(lowerBills))
    {
      if (upperBill.getStatus() == 5)
      {
        double userAmount = 0.0D;
        upperBill.setUserAmount(userAmount);
        upperBill.setRemarks("销量或人数未达标");
        saveBill(upperBill, 5);
      }
      else
      {
        double userAmount = upperBill.getCalAmount();
        upperBill.setUserAmount(userAmount);
        if (userAmount == 0.0D) {
          saveBill(upperBill, 5);
        } else {
          saveBill(upperBill, 2);
        }
      }
      return;
    }
    for (UserDividendBillAdapter lowerBill : lowerBills) {
      processLineBill(lowerBill);
    }
    if ((upperBill.getCalAmount() == 0.0D) && (upperBill.getLowerTotalAmount() == 0.0D))
    {
      upperBill.setUserAmount(0.0D);
      upperBill.setRemarks("销量或人数未达标");
      saveBill(upperBill, 5);
    }
    else
    {
      double calAmount = upperBill.getCalAmount();
      double lowerTotalAmount = upperBill.getLowerTotalAmount();
      
      double userAmount = MathUtil.subtract(calAmount, lowerTotalAmount);
      userAmount = userAmount < 0.0D ? 0.0D : userAmount;
      upperBill.setUserAmount(userAmount);
      saveBill(upperBill, 2);
    }
  }
  
  private void saveBill(UserDividendBill upperBill, int status)
  {
    upperBill.setSettleTime(new Moment().toSimpleTime());
    upperBill.setStatus(status);
    this.uDividendBillService.add(upperBill);
  }
  
  private void summaryUpReports(List<UserLotteryReportVO> reports, int userId, String sTime, String eTime, UserDividendBill dividendBill)
  {
    double billingOrder = 0.0D;
    for (UserLotteryReportVO report : reports) {
      if ("总计".equals(report.getName()))
      {
        billingOrder = report.getBillingOrder();
        break;
      }
    }
    double dailyBillingOrder = 0.0D;
    if (billingOrder > 0.0D) {
      dailyBillingOrder = MathUtil.divide(billingOrder, 15.0D, 4);
    }
    double minBillingOrder = this.dataFactory.getDividendConfig().getMinBillingOrder();
    
    int validUser = sumUserLottery(userId, sTime, eTime, minBillingOrder);
    
    double thisLoss = calculateLotteryLossByLotteryReport(reports);
    double lastLoss = calculateLotteryLastLoss(userId, sTime, eTime);
    
    double totalLoss = lastLoss > 0.0D ? MathUtil.add(thisLoss, lastLoss) : thisLoss;
    
    dividendBill.setDailyBillingOrder(dailyBillingOrder);
    dividendBill.setBillingOrder(billingOrder);
    dividendBill.setThisLoss(thisLoss);
    dividendBill.setLastLoss(lastLoss);
    dividendBill.setTotalLoss(totalLoss);
    dividendBill.setValidUser(validUser);
  }
  
  private int sumUserLottery(int userId, String sTime, String eTime, double minBillingOrder)
  {
    List<User> userLowers = this.userDao.getUserLower(userId);
    int validUser = 0;
    for (User lowerUser : userLowers)
    {
      double lowerUserBillingOrder = summaryUpLotteryLowerUserBillingOrder(lowerUser.getId(), sTime, eTime);
      if (lowerUserBillingOrder >= minBillingOrder) {
        validUser++;
      }
    }
    double selfLotteryBilling = summaryUpLotteryLowerUserBillingOrder(userId, sTime, eTime);
    if (selfLotteryBilling >= minBillingOrder) {
      validUser++;
    }
    return validUser;
  }
  
  private double summaryUpLotteryLowerUserBillingOrder(int userId, String sTime, String eTime)
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
  
  private int dayMeanLotteryUser(int userId, String sTime, String eTime, double minBillingOrder)
  {
    int sum = 0;
    List<User> userLowers = this.userDao.getUserLower(userId);
    
    Map<String, Integer> daySum = new HashMap();
    String[] dates = DateUtil.getDateArray(sTime, eTime);
    for (int i = 0; i < dates.length - 1; i++) {
      daySum.put(dates[i], Integer.valueOf(0));
    }
    List<UserLotteryReport> lowerUserReports;
    Iterator localIterator2;
    for (Iterator localIterator1 = userLowers.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      User lowerUser = (User)localIterator1.next();
      lowerUserReports = this.uLotteryReportDao.list(lowerUser.getId(), sTime, eTime);
      localIterator2 = lowerUserReports.iterator();
      UserLotteryReport lowerUserReport = (UserLotteryReport)localIterator2.next();
      if (lowerUserReport.getBillingOrder() >= minBillingOrder)
      {
        int num = ((Integer)daySum.get(lowerUserReport.getTime())).intValue() + 1;
        daySum.put(lowerUserReport.getTime(), Integer.valueOf(num));
      }
    }
    lowerUserReports = (List<UserLotteryReport>)this.uLotteryReportDao.list(userId, sTime, eTime);
    for (UserLotteryReport lowerUserReport : lowerUserReports) {
      if (lowerUserReport.getBillingOrder() >= minBillingOrder)
      {
        int num = ((Integer)daySum.get(lowerUserReport.getTime())).intValue() + 1;
        daySum.put(lowerUserReport.getTime(), Integer.valueOf(num));
      }
    }
    for (String key : daySum.keySet()) {
      sum += ((Integer)daySum.get(key)).intValue();
    }
    int result = sum / (dates.length - 1);
    return result;
  }
  
  private double summaryUpGameLowerUserBillingOrder(int userId, String sTime, String eTime)
  {
    List<UserGameReport> lowerUserReports = this.uGameReportDao.list(userId, sTime, eTime);
    if (CollectionUtils.isEmpty(lowerUserReports)) {
      return 0.0D;
    }
    double billingOrder = 0.0D;
    for (UserGameReport lowerUserReport : lowerUserReports) {
      billingOrder = MathUtil.add(billingOrder, lowerUserReport.getBillingOrder());
    }
    return billingOrder;
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
  
  private double calculateGameLossByGameReport(List<UserGameReportVO> reports)
  {
    double gameLoss = 0.0D;
    for (UserGameReportVO report : reports) {
      if ("总计".equals(report.getName()))
      {
        gameLoss = report.getPrize() + report.getWaterReturn() + report.getProxyReturn() + report.getActivity() - 
          report.getBillingOrder();
        break;
      }
    }
    return gameLoss;
  }
  
  private double calculateLotteryLastLoss(int userId, String sTime, String eTime)
  {
    String currDate = sTime;
    if (StringUtil.isNotNull(currDate))
    {
      int currDay = Integer.valueOf(currDate.substring(8)).intValue();
      if (currDay == 16)
      {
        String lastStartDate = currDate.substring(0, 8) + "01";
        String lastEndDate = currDate;
        
        List<UserLotteryReportVO> lastReports = this.uLotteryReportService.report(userId, lastStartDate, 
          lastEndDate);
        double lastLoss = calculateLotteryLossByLotteryReport(lastReports);
        return lastLoss;
      }
    }
    return 0.0D;
  }
  
  private double calculateGameLastLoss(int userId, String sTime, String eTime)
  {
    String currDate = sTime;
    if (StringUtil.isNotNull(currDate))
    {
      int currDay = Integer.valueOf(currDate.substring(8)).intValue();
      if (currDay == 16)
      {
        String lastStartDate = currDate.substring(0, 8) + "01";
        String lastEndDate = currDate;
        
        List<UserGameReportVO> lastReports = this.uGameReportService.report(userId, lastStartDate, lastEndDate);
        double lastLoss = calculateGameLossByGameReport(lastReports);
        return lastLoss;
      }
    }
    return 0.0D;
  }
  
  private String getStartDate()
  {
    Moment moment = new Moment().add(-1, "days");
    int day = moment.day();
    if (day <= 15) {
      moment = moment.day(1);
    } else {
      moment = moment.day(16);
    }
    return moment.toSimpleDate();
  }
  
  private String getEndDate()
  {
    Moment moment = new Moment().add(-1, "days");
    int day = moment.day();
    if (day <= 15)
    {
      moment = moment.day(16);
    }
    else
    {
      moment = moment.add(1, "months");
      moment = moment.day(1);
    }
    return moment.toSimpleDate();
  }
  
  private void sendMail(List<UserDividendBillAdapter> bills, String sTime, String eTime)
  {
    double platformTotalLoss = 0.0D;
    double platformTotalAmount = 0.0D;
    if (CollectionUtils.isNotEmpty(bills))
    {
      List<UserDividendBill> allBills = new ArrayList();
      getAllBills(bills, allBills);
      for (UserDividendBill bill : allBills) {
        if (bill.getIssueType() == 1) {
          if ((bill.getStatus() == 1) || 
            (bill.getStatus() == 2) || 
            (bill.getStatus() == 3) || 
            (bill.getStatus() == 6))
          {
            platformTotalAmount = MathUtil.add(platformTotalAmount, bill.getCalAmount());
            if (bill.getThisLoss() < 0.0D) {
              platformTotalLoss = MathUtil.add(platformTotalLoss, bill.getThisLoss());
            }
          }
        }
      }
    }
    double totalBillingOrder = 0.0D;
    double totalLoss = 0.0D;
    List<UserLotteryReportVO> lotteryReportVOs = this.uLotteryReportService.report(sTime, eTime);
    if (CollectionUtils.isNotEmpty(lotteryReportVOs)) {
      for (UserLotteryReportVO report : lotteryReportVOs) {
        if ("总计".equals(report.getName()))
        {
          totalBillingOrder += report.getBillingOrder();
          totalLoss += calculateLotteryLossByLotteryReport(lotteryReportVOs);
          break;
        }
      }
    }
    this.mailJob.sendDividend(sTime, eTime, totalBillingOrder, totalLoss, platformTotalLoss, platformTotalAmount);
  }
  
  private List<UserDividendBill> getAllBills(List<UserDividendBillAdapter> bills, List<UserDividendBill> container)
  {
    if (CollectionUtils.isEmpty(bills)) {
      return container;
    }
    for (UserDividendBillAdapter bill : bills)
    {
      container.add(bill.getUpperBill());
      
      getAllBills(bill.getLowerBills(), container);
    }
    return container;
  }
}
