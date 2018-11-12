package lottery.domains.content.jobs;

import javautils.StringUtil;
import lottery.domains.content.vo.bill.UserGameReportVO;
import lottery.domains.content.entity.UserGameReport;
import java.util.Map;
import javautils.date.DateUtil;
import java.util.HashMap;
import lottery.domains.content.entity.UserLotteryReport;
import javautils.date.Moment;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import java.math.BigDecimal;
import java.util.Collections;
import javautils.math.MathUtil;
import lottery.domains.content.entity.UserDividendBill;
import lottery.domains.content.entity.UserDividend;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.vo.user.UserDividendBillAdapter;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.LoggerFactory;
import lottery.domains.pool.LotteryDataFactory;
import admin.domains.jobs.MailJob;
import lottery.web.content.utils.UserCodePointUtil;
import lottery.domains.content.dao.UserGameReportDao;
import lottery.domains.content.biz.UserGameReportService;
import lottery.domains.content.dao.UserLotteryReportDao;
import lottery.domains.content.biz.UserLotteryReportService;
import lottery.domains.content.biz.UserDividendBillService;
import lottery.domains.content.biz.UserDividendService;
import lottery.domains.content.biz.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DividendRankJob
{
    private static final Logger log;
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
    
    static {
        log = LoggerFactory.getLogger((Class)DividendRankJob.class);
    }
    
    @Scheduled(cron = "0 10 2 1,16 * *")
    public void schedule() {
        try {
            if (!this.dataFactory.getDividendConfig().isEnable()) {
                DividendRankJob.log.info("分红没有开启，不发放");
                return;
            }
            final String startDate = this.getStartDate();
            final int startDay = Integer.valueOf(startDate.substring(8));
            if (startDay != 1 && startDay != 16) {
                return;
            }
            final String endDate = this.getEndDate();
            DividendRankJob.log.info("发放分红开始：{}~{}", (Object)startDate, (Object)endDate);
            this.settleUp(startDate, endDate);
            DividendRankJob.log.info("发放分红完成：{}-{}", (Object)startDate, (Object)endDate);
        }
        catch (Exception e) {
            DividendRankJob.log.error("分红发放出错", (Throwable)e);
        }
    }
    
    private void updateAllExpire() {
        this.uDividendBillService.updateAllExpire();
    }
    
    public void settleUp(final String sTime, final String eTime) {
        final List<User> neibuZhaoShangs = this.uService.findNeibuZhaoShang();
        if (CollectionUtils.isEmpty((Collection)neibuZhaoShangs)) {
            DividendRankJob.log.error("没有找到任何内部招商账号，本次未产生任何分红数据");
            return;
        }
        final List<User> zhaoShangs = this.uService.findZhaoShang(neibuZhaoShangs);
        if (CollectionUtils.isEmpty((Collection)zhaoShangs)) {
            DividendRankJob.log.error("没有找到任何招商账号，本次未产生任何分红数据");
            return;
        }
        final List<UserDividendBillAdapter> bills = new ArrayList<UserDividendBillAdapter>();
        try {
            DividendRankJob.log.info("发放招商及以下分红开始：{}~{}", (Object)sTime, (Object)eTime);
            final List<UserDividendBillAdapter> zhaoShangBills = this.settleUpZhaoShangs(zhaoShangs, sTime, eTime);
            if (CollectionUtils.isNotEmpty((Collection)zhaoShangBills)) {
                bills.addAll(zhaoShangBills);
            }
            DividendRankJob.log.info("发放招商及以下分红完成：{}~{}", (Object)sTime, (Object)eTime);
        }
        catch (Exception e) {
            DividendRankJob.log.error("发放招商及以下分红出错", (Throwable)e);
        }
        this.sendMail(bills, sTime, eTime);
    }
    
    private List<UserDividendBillAdapter> settleUpZhaoShangs(final List<User> zhaoShangs, final String sTime, final String eTime) {
        final List<UserDividendBillAdapter> bills = new ArrayList<UserDividendBillAdapter>();
        for (final User zhaoShang : zhaoShangs) {
            if (zhaoShang.getUpid() != 0) {
                final UserDividendBillAdapter billAdapter = this.settleUpWithUser(zhaoShang, sTime, eTime, true, 1);
                if (billAdapter == null) {
                    continue;
                }
                bills.add(billAdapter);
            }
        }
        if (CollectionUtils.isNotEmpty((Collection)bills)) {
            for (final UserDividendBillAdapter bill : bills) {
                this.processLineBill(bill);
            }
        }
        return bills;
    }
    
    private UserDividendBill createBill(final int userId, final String sTime, final String eTime, final UserDividend userDividend, final int issueType) {
        final int minValidUserCfg = this.dataFactory.getDividendConfig().getMinValidUserl();
        final UserDividendBill dividendBill = new UserDividendBill();
        dividendBill.setUserId(userId);
        dividendBill.setIndicateStartDate(sTime);
        dividendBill.setIndicateEndDate(eTime);
        if (userDividend.getMinValidUser() >= minValidUserCfg) {
            dividendBill.setMinValidUser(userDividend.getMinValidUser());
        }
        else {
            dividendBill.setMinValidUser(minValidUserCfg);
        }
        dividendBill.setValidUser(0);
        dividendBill.setScale(0.0);
        dividendBill.setBillingOrder(0.0);
        dividendBill.setUserAmount(0.0);
        dividendBill.setCalAmount(0.0);
        dividendBill.setIssueType(issueType);
        return dividendBill;
    }
    
    private boolean check(final User user, final UserDividend uDividend) {
        if (user.getId() == 72) {
            final String error = String.format("契约分红错误提醒;用户%s为总账号，但查找到其拥有分红配置，本次不对其进行结算，不影响整体结算；", user.getUsername());
            DividendRankJob.log.error(error);
            this.mailJob.addWarning(error);
            return false;
        }
        final boolean isNeibuZhaoShang = this.uCodePointUtil.isLevel2Proxy(user);
        final UserDividend upUserDividend = this.uDividendService.getByUserId(user.getUpid());
        if (!isNeibuZhaoShang && upUserDividend == null) {
            final String error2 = String.format("契约分红错误提醒;用户%s没有找到上级的分红配置，本次不对其团队进行结算；", user.getUsername());
            DividendRankJob.log.error(error2);
            this.mailJob.addWarning(error2);
            return false;
        }
        final String[] scaleLevels = uDividend.getScaleLevel().split(",");
        final double minScale = this.dataFactory.getDividendConfig().getLevelsScale()[0];
        double maxScale = this.dataFactory.getDividendConfig().getLevelsScale()[1];
        if (!isNeibuZhaoShang) {
            final String[] upScaleLevels = upUserDividend.getScaleLevel().split(",");
            maxScale = Double.valueOf(upScaleLevels[upScaleLevels.length - 1]);
        }
        if (Double.valueOf(scaleLevels[scaleLevels.length - 1]) > maxScale || Double.valueOf(scaleLevels[0]) < minScale) {
            final String error3 = String.format("契约分红错误提醒;用户%s为直属号，但分红比例%s不是有效比例%s~%s，本次不对其团队进行结算；", user.getUsername(), uDividend.getScaleLevel(), minScale, maxScale);
            DividendRankJob.log.error(error3);
            this.mailJob.addWarning(error3);
            return false;
        }
        if (!this.uDividendService.checkValidLevel(uDividend.getScaleLevel(), uDividend.getSalesLevel(), uDividend.getLossLevel(), upUserDividend, uDividend.getUserLevel())) {
            final String error3 = String.format("契约分红错误提醒;用户%s，所签订分红条款为无效条款，条款内容：分红比例[%s]，销量[%s]，亏损[%s]，本次不对其团队进行结算；", user.getUsername(), uDividend.getScaleLevel(), uDividend.getSalesLevel(), uDividend.getLossLevel());
            DividendRankJob.log.error(error3);
            this.mailJob.addWarning(error3);
            return false;
        }
        return true;
    }
    
    private UserDividendBillAdapter settleUpWithUser(final User user, final String sTime, final String eTime, final boolean settleLowers, final int issueType) {
        final UserDividend userDividend = this.uDividendService.getByUserId(user.getId());
        if (userDividend == null || userDividend.getStatus() != 1) {
            return null;
        }
        final boolean checked = this.check(user, userDividend);
        if (!checked) {
            return null;
        }
        final UserDividendBill upperBill = this.createBill(user.getId(), sTime, eTime, userDividend, issueType);
        final List<UserLotteryReportVO> reports = this.uLotteryReportService.report(user.getId(), sTime, eTime);
        if (CollectionUtils.isNotEmpty((Collection)reports)) {
            this.summaryUpReports(reports, user.getId(), sTime, eTime, upperBill);
        }
        final boolean isCheckLossCfg = this.dataFactory.getDividendConfig().isCheckLoss();
        final double billingOrder = MathUtil.divide(upperBill.getBillingOrder(), 10000.0, 2);
        final double thisLoss = MathUtil.divide(Math.abs(upperBill.getThisLoss()), 10000.0, 2);
        final int vaildUser = upperBill.getValidUser();
        final String[] scaleLevels = userDividend.getScaleLevel().split(",");
        final String[] salesLevels = userDividend.getSalesLevel().split(",");
        final String[] lossLevels = userDividend.getLossLevel().split(",");
        final String[] userLevels = userDividend.getUserLevel().split(",");
        final List<Integer> levels = new ArrayList<Integer>();
        for (int i = 0; i < salesLevels.length; ++i) {
            if (billingOrder >= Double.parseDouble(salesLevels[i]) && vaildUser >= Integer.valueOf(userLevels[i])) {
                levels.add(i);
            }
        }
        if (levels.size() > 0) {
            Collections.sort(levels);
            upperBill.setScale(Double.valueOf(scaleLevels[levels.get(levels.size() - 1)]));
        }
        if (upperBill.getStatus() != 5) {
            double calAmount = 0.0;
            if (levels.size() > 0 && upperBill.getThisLoss() < 0.0 && upperBill.getValidUser() >= upperBill.getMinValidUser()) {
                double scale = MathUtil.divide(upperBill.getScale(), 100.0, 6);
                calAmount = MathUtil.decimalFormat(new BigDecimal(MathUtil.multiply(Math.abs(upperBill.getThisLoss()), scale)), 2);
                if (isCheckLossCfg) {
                    if (userDividend.getFixed() == 0) {
                        calAmount = 0.0;
                        for (final Integer l : levels) {
                            scale = MathUtil.divide(Double.valueOf(scaleLevels[l]), 100.0, 4);
                            double tempCal = MathUtil.decimalFormat(new BigDecimal(MathUtil.multiply(Math.abs(upperBill.getThisLoss()), scale)), 2);
                            if (Double.parseDouble(lossLevels[l]) > 0.0) {
                                final double lossRate = MathUtil.divide(thisLoss, Double.parseDouble(lossLevels[l]), 4);
                                if (lossRate < 1.0) {
                                    tempCal = MathUtil.decimalFormat(new BigDecimal(String.valueOf(MathUtil.multiply(tempCal, lossRate))), 2);
                                }
                            }
                            if (tempCal > calAmount) {
                                calAmount = tempCal;
                                upperBill.setScale(Double.valueOf(scaleLevels[l]));
                            }
                        }
                    }
                    else if (userDividend.getFixed() == 1) {
                        levels.clear();
                        calAmount = 0.0;
                        for (int j = 0; j < salesLevels.length; ++j) {
                            if (billingOrder >= Double.parseDouble(salesLevels[j]) && thisLoss >= Double.parseDouble(lossLevels[j]) && vaildUser >= Integer.valueOf(userLevels[j])) {
                                levels.add(j);
                            }
                        }
                        if (levels.size() > 0) {
                            Collections.sort(levels);
                            upperBill.setScale(Double.valueOf(scaleLevels[levels.get(levels.size() - 1)]));
                            scale = MathUtil.divide(upperBill.getScale(), 100.0, 4);
                            calAmount = MathUtil.decimalFormat(new BigDecimal(MathUtil.multiply(Math.abs(upperBill.getThisLoss()), scale)), 2);
                        }
                    }
                }
            }
            upperBill.setCalAmount(calAmount);
            if (calAmount == 0.0) {
                upperBill.setCalAmount(0.0);
                upperBill.setScale(0.0);
                upperBill.setStatus(5);
                upperBill.setRemarks("契约分红条款未达标");
            }
        }
        int billType = 1;
        if (this.uCodePointUtil.isLevel2Proxy(user)) {
            upperBill.setCalAmount(0.0);
            upperBill.setScale(0.0);
            upperBill.setStatus(1);
            upperBill.setRemarks("内部招商分红，不结算");
        }
        else {
            billType = 2;
        }
        double lowerTotalAmount = 0.0;
        final List<UserDividendBillAdapter> lowerBills = new ArrayList<UserDividendBillAdapter>();
        if (settleLowers) {
            for (final UserLotteryReportVO report : reports) {
                if (!"总计".equals(report.getName()) && !report.getName().equalsIgnoreCase(user.getUsername())) {
                    final User subUser = this.userDao.getByUsername(report.getName());
                    final UserDividendBillAdapter lowerBillAdapter = this.settleUpWithUser(subUser, sTime, eTime, true, billType);
                    if (lowerBillAdapter == null) {
                        continue;
                    }
                    if (lowerBillAdapter.getUpperBill().getStatus() != 5) {
                        lowerTotalAmount = MathUtil.add(lowerTotalAmount, lowerBillAdapter.getUpperBill().getCalAmount());
                    }
                    lowerBills.add(lowerBillAdapter);
                }
            }
        }
        upperBill.setLowerTotalAmount(lowerTotalAmount);
        if (this.uCodePointUtil.isLevel2Proxy(user)) {
            upperBill.setLowerTotalAmount(0.0);
        }
        return new UserDividendBillAdapter(upperBill, lowerBills);
    }
    
    private void processLineBill(final UserDividendBillAdapter uDividendBillAdapter) {
        final UserDividendBill upperBill = uDividendBillAdapter.getUpperBill();
        final List<UserDividendBillAdapter> lowerBills = uDividendBillAdapter.getLowerBills();
        if (CollectionUtils.isEmpty((Collection)lowerBills)) {
            if (upperBill.getStatus() == 5) {
                final double userAmount = 0.0;
                upperBill.setUserAmount(userAmount);
                upperBill.setRemarks("销量或人数未达标");
                this.saveBill(upperBill, 5);
            }
            else {
                final double userAmount = upperBill.getCalAmount();
                upperBill.setUserAmount(userAmount);
                if (userAmount == 0.0) {
                    this.saveBill(upperBill, 5);
                }
                else {
                    this.saveBill(upperBill, 2);
                }
            }
            return;
        }
        for (final UserDividendBillAdapter lowerBill : lowerBills) {
            this.processLineBill(lowerBill);
        }
        if (upperBill.getCalAmount() == 0.0 && upperBill.getLowerTotalAmount() == 0.0) {
            upperBill.setUserAmount(0.0);
            upperBill.setRemarks("销量或人数未达标");
            this.saveBill(upperBill, 5);
        }
        else {
            final double calAmount = upperBill.getCalAmount();
            final double lowerTotalAmount = upperBill.getLowerTotalAmount();
            double userAmount2 = MathUtil.subtract(calAmount, lowerTotalAmount);
            userAmount2 = ((userAmount2 < 0.0) ? 0.0 : userAmount2);
            upperBill.setUserAmount(userAmount2);
            this.saveBill(upperBill, 2);
        }
    }
    
    private void saveBill(final UserDividendBill upperBill, final int status) {
        upperBill.setSettleTime(new Moment().toSimpleTime());
        upperBill.setStatus(status);
        this.uDividendBillService.add(upperBill);
    }
    
    private void summaryUpReports(final List<UserLotteryReportVO> reports, final int userId, final String sTime, final String eTime, final UserDividendBill dividendBill) {
        double billingOrder = 0.0;
        for (final UserLotteryReportVO report : reports) {
            if ("总计".equals(report.getName())) {
                billingOrder = report.getBillingOrder();
                break;
            }
        }
        double dailyBillingOrder = 0.0;
        if (billingOrder > 0.0) {
            dailyBillingOrder = MathUtil.divide(billingOrder, 15.0, 4);
        }
        final double minBillingOrder = this.dataFactory.getDividendConfig().getMinBillingOrder();
        final int validUser = this.sumUserLottery(userId, sTime, eTime, minBillingOrder);
        final double thisLoss = this.calculateLotteryLossByLotteryReport(reports);
        final double lastLoss = this.calculateLotteryLastLoss(userId, sTime, eTime);
        final double totalLoss = (lastLoss > 0.0) ? MathUtil.add(thisLoss, lastLoss) : thisLoss;
        dividendBill.setDailyBillingOrder(dailyBillingOrder);
        dividendBill.setBillingOrder(billingOrder);
        dividendBill.setThisLoss(thisLoss);
        dividendBill.setLastLoss(lastLoss);
        dividendBill.setTotalLoss(totalLoss);
        dividendBill.setValidUser(validUser);
    }
    
    private int sumUserLottery(final int userId, final String sTime, final String eTime, final double minBillingOrder) {
        final List<User> userLowers = this.userDao.getUserLower(userId);
        int validUser = 0;
        for (final User lowerUser : userLowers) {
            final double lowerUserBillingOrder = this.summaryUpLotteryLowerUserBillingOrder(lowerUser.getId(), sTime, eTime);
            if (lowerUserBillingOrder >= minBillingOrder) {
                ++validUser;
            }
        }
        final double selfLotteryBilling = this.summaryUpLotteryLowerUserBillingOrder(userId, sTime, eTime);
        if (selfLotteryBilling >= minBillingOrder) {
            ++validUser;
        }
        return validUser;
    }
    
    private double summaryUpLotteryLowerUserBillingOrder(final int userId, final String sTime, final String eTime) {
        final List<UserLotteryReport> lowerUserReports = this.uLotteryReportDao.list(userId, sTime, eTime);
        if (CollectionUtils.isEmpty((Collection)lowerUserReports)) {
            return 0.0;
        }
        double billingOrder = 0.0;
        for (final UserLotteryReport lowerUserReport : lowerUserReports) {
            billingOrder = MathUtil.add(billingOrder, lowerUserReport.getBillingOrder());
        }
        return billingOrder;
    }
    
    private int dayMeanLotteryUser(final int userId, final String sTime, final String eTime, final double minBillingOrder) {
        int sum = 0;
        final List<User> userLowers = this.userDao.getUserLower(userId);
        final Map<String, Integer> daySum = new HashMap<String, Integer>();
        final String[] dates = DateUtil.getDateArray(sTime, eTime);
        for (int i = 0; i < dates.length - 1; ++i) {
            daySum.put(dates[i], 0);
        }
        for (final User lowerUser : userLowers) {
            final List<UserLotteryReport> lowerUserReports = this.uLotteryReportDao.list(lowerUser.getId(), sTime, eTime);
            for (final UserLotteryReport lowerUserReport : lowerUserReports) {
                if (lowerUserReport.getBillingOrder() >= minBillingOrder) {
                    final int num = daySum.get(lowerUserReport.getTime()) + 1;
                    daySum.put(lowerUserReport.getTime(), num);
                }
            }
        }
        final List<UserLotteryReport> lowerUserReports2 = this.uLotteryReportDao.list(userId, sTime, eTime);
        for (final UserLotteryReport lowerUserReport2 : lowerUserReports2) {
            if (lowerUserReport2.getBillingOrder() >= minBillingOrder) {
                final int num2 = daySum.get(lowerUserReport2.getTime()) + 1;
                daySum.put(lowerUserReport2.getTime(), num2);
            }
        }
        for (final String key : daySum.keySet()) {
            sum += daySum.get(key);
        }
        final int result = sum / (dates.length - 1);
        return result;
    }
    
    private double summaryUpGameLowerUserBillingOrder(final int userId, final String sTime, final String eTime) {
        final List<UserGameReport> lowerUserReports = this.uGameReportDao.list(userId, sTime, eTime);
        if (CollectionUtils.isEmpty((Collection)lowerUserReports)) {
            return 0.0;
        }
        double billingOrder = 0.0;
        for (final UserGameReport lowerUserReport : lowerUserReports) {
            billingOrder = MathUtil.add(billingOrder, lowerUserReport.getBillingOrder());
        }
        return billingOrder;
    }
    
    private double calculateLotteryLossByLotteryReport(final List<UserLotteryReportVO> reports) {
        double lotteryLoss = 0.0;
        for (final UserLotteryReportVO report : reports) {
            if ("总计".equals(report.getName())) {
                lotteryLoss = report.getPrize() + report.getSpendReturn() + report.getProxyReturn() + report.getActivity() + report.getRechargeFee() - report.getBillingOrder();
                break;
            }
        }
        return lotteryLoss;
    }
    
    private double calculateGameLossByGameReport(final List<UserGameReportVO> reports) {
        double gameLoss = 0.0;
        for (final UserGameReportVO report : reports) {
            if ("总计".equals(report.getName())) {
                gameLoss = report.getPrize() + report.getWaterReturn() + report.getProxyReturn() + report.getActivity() - report.getBillingOrder();
                break;
            }
        }
        return gameLoss;
    }
    
    private double calculateLotteryLastLoss(final int userId, final String sTime, final String eTime) {
        final String currDate = sTime;
        if (StringUtil.isNotNull(currDate)) {
            final int currDay = Integer.valueOf(currDate.substring(8));
            if (currDay == 16) {
                final String lastStartDate = String.valueOf(currDate.substring(0, 8)) + "01";
                final String lastEndDate = currDate;
                final List<UserLotteryReportVO> lastReports = this.uLotteryReportService.report(userId, lastStartDate, lastEndDate);
                final double lastLoss = this.calculateLotteryLossByLotteryReport(lastReports);
                return lastLoss;
            }
        }
        return 0.0;
    }
    
    private double calculateGameLastLoss(final int userId, final String sTime, final String eTime) {
        final String currDate = sTime;
        if (StringUtil.isNotNull(currDate)) {
            final int currDay = Integer.valueOf(currDate.substring(8));
            if (currDay == 16) {
                final String lastStartDate = String.valueOf(currDate.substring(0, 8)) + "01";
                final String lastEndDate = currDate;
                final List<UserGameReportVO> lastReports = this.uGameReportService.report(userId, lastStartDate, lastEndDate);
                final double lastLoss = this.calculateGameLossByGameReport(lastReports);
                return lastLoss;
            }
        }
        return 0.0;
    }
    
    private String getStartDate() {
        Moment moment = new Moment().add(-1, "days");
        final int day = moment.day();
        if (day <= 15) {
            moment = moment.day(1);
        }
        else {
            moment = moment.day(16);
        }
        return moment.toSimpleDate();
    }
    
    private String getEndDate() {
        Moment moment = new Moment().add(-1, "days");
        final int day = moment.day();
        if (day <= 15) {
            moment = moment.day(16);
        }
        else {
            moment = moment.add(1, "months");
            moment = moment.day(1);
        }
        return moment.toSimpleDate();
    }
    
    private void sendMail(final List<UserDividendBillAdapter> bills, final String sTime, final String eTime) {
        double platformTotalLoss = 0.0;
        double platformTotalAmount = 0.0;
        if (CollectionUtils.isNotEmpty((Collection)bills)) {
            final List<UserDividendBill> allBills = new ArrayList<UserDividendBill>();
            this.getAllBills(bills, allBills);
            for (final UserDividendBill bill : allBills) {
                if (bill.getIssueType() != 1) {
                    continue;
                }
                if (bill.getStatus() != 1 && bill.getStatus() != 2 && bill.getStatus() != 3 && bill.getStatus() != 6) {
                    continue;
                }
                platformTotalAmount = MathUtil.add(platformTotalAmount, bill.getCalAmount());
                if (bill.getThisLoss() >= 0.0) {
                    continue;
                }
                platformTotalLoss = MathUtil.add(platformTotalLoss, bill.getThisLoss());
            }
        }
        double totalBillingOrder = 0.0;
        double totalLoss = 0.0;
        final List<UserLotteryReportVO> lotteryReportVOs = this.uLotteryReportService.report(sTime, eTime);
        if (CollectionUtils.isNotEmpty((Collection)lotteryReportVOs)) {
            for (final UserLotteryReportVO report : lotteryReportVOs) {
                if ("总计".equals(report.getName())) {
                    totalBillingOrder += report.getBillingOrder();
                    totalLoss += this.calculateLotteryLossByLotteryReport(lotteryReportVOs);
                    break;
                }
            }
        }
        this.mailJob.sendDividend(sTime, eTime, totalBillingOrder, totalLoss, platformTotalLoss, platformTotalAmount);
    }
    
    private List<UserDividendBill> getAllBills(final List<UserDividendBillAdapter> bills, final List<UserDividendBill> container) {
        if (CollectionUtils.isEmpty((Collection)bills)) {
            return container;
        }
        for (final UserDividendBillAdapter bill : bills) {
            container.add(bill.getUpperBill());
            this.getAllBills(bill.getLowerBills(), container);
        }
        return container;
    }
}
