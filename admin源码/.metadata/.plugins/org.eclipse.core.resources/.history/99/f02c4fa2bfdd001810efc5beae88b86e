package lottery.domains.content.jobs;

import lottery.domains.content.entity.UserLotteryReport;
import java.math.RoundingMode;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import java.math.BigDecimal;
import java.util.Collections;
import javautils.math.MathUtil;
import lottery.domains.content.entity.UserDailySettleBill;
import lottery.domains.content.entity.UserDailySettle;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import lottery.domains.content.vo.user.UserDailySettleBillAdapter;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import javautils.date.Moment;
import org.slf4j.LoggerFactory;
import lottery.domains.pool.LotteryDataFactory;
import admin.domains.jobs.MailJob;
import lottery.web.content.utils.UserCodePointUtil;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.dao.UserLotteryReportDao;
import lottery.domains.content.biz.UserLotteryReportService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserDailySettleBillService;
import lottery.domains.content.biz.UserDailySettleService;
import lottery.domains.content.biz.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DailySettleJob
{
    private static final Logger log;
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
    
    static {
        log = LoggerFactory.getLogger((Class)DailySettleJob.class);
    }
    
    @Scheduled(cron = "0 5 2 0/1 * *")
    public void schedule() {
        try {
            if (!this.dataFactory.getDailySettleConfig().isEnable()) {
                DailySettleJob.log.info("日结没有开启，不发放");
                return;
            }
            final String yesterday = new Moment().subtract(1, "days").toSimpleDate();
            final String today = new Moment().toSimpleDate();
            DailySettleJob.log.info("发放日结开始：{}~{}", (Object)yesterday, (Object)today);
            this.settleUp(yesterday, today);
            DailySettleJob.log.info("发放日结完成：{}~{}", (Object)yesterday, (Object)today);
        }
        catch (Exception e) {
            DailySettleJob.log.error("发放日结出错", (Throwable)e);
        }
    }
    
    public List<UserDailySettleBillAdapter> settleUp(final String sTime, final String eTime) {
        final List<User> neibuZhaoShangs = this.uService.findNeibuZhaoShang();
        if (CollectionUtils.isEmpty((Collection)neibuZhaoShangs)) {
            DailySettleJob.log.error("没有找到任何内部招商账号，本次未产生任何日结数据");
            return null;
        }
        final List<User> zhaoShangs = this.uService.findZhaoShang(neibuZhaoShangs);
        if (CollectionUtils.isEmpty((Collection)zhaoShangs)) {
            DailySettleJob.log.error("没有找到任何招商账号，本次未产生任何日结数据");
            return null;
        }
        final List<User> zhishus = this.uService.findZhaoShang(zhaoShangs);
        if (CollectionUtils.isEmpty((Collection)zhishus)) {
            DailySettleJob.log.error("没有找到任何直属账号，本次未产生任何日结数据");
            return null;
        }
        List<UserDailySettleBillAdapter> neibuZhaoShangBills = null;
        try {
            DailySettleJob.log.info("发放直属日结开始：{}~{}", (Object)sTime, (Object)eTime);
            neibuZhaoShangBills = this.settleUpNeibuZhaoShangs(zhishus, sTime, eTime);
            DailySettleJob.log.info("发放直属日结完成：{}~{}", (Object)sTime, (Object)eTime);
        }
        catch (Exception e) {
            DailySettleJob.log.error("发放直属日结出错", (Throwable)e);
        }
        final List<UserDailySettleBillAdapter> allBills = new ArrayList<UserDailySettleBillAdapter>();
        if (CollectionUtils.isNotEmpty((Collection)neibuZhaoShangBills)) {
            allBills.addAll(neibuZhaoShangBills);
        }
        this.sendMail(allBills, sTime, eTime);
        return allBills;
    }
    
    private List<UserDailySettleBillAdapter> settleUpNeibuZhaoShangs(final List<User> neibuZhaoShangs, final String sTime, final String eTime) {
        final List<UserDailySettleBillAdapter> bills = new ArrayList<UserDailySettleBillAdapter>();
        for (final User neibuZhaoShang : neibuZhaoShangs) {
            final UserDailySettleBillAdapter billAdapter = this.settleUpWithUser(neibuZhaoShang, sTime, eTime, true, 1);
            if (billAdapter != null) {
                bills.add(billAdapter);
            }
        }
        if (CollectionUtils.isNotEmpty((Collection)bills)) {
            for (final UserDailySettleBillAdapter bill : bills) {
                this.processLineBill(bill);
            }
        }
        return bills;
    }
    
    private List<UserDailySettleBillAdapter> settleUpZhaoShangs(final List<User> zhaoShangs, final String sTime, final String eTime) {
        final List<UserDailySettleBillAdapter> bills = new ArrayList<UserDailySettleBillAdapter>();
        for (final User zhaoShang : zhaoShangs) {
            final UserDailySettleBillAdapter billAdapter = this.settleUpWithUser(zhaoShang, sTime, eTime, true, 1);
            if (billAdapter != null) {
                bills.add(billAdapter);
            }
        }
        if (CollectionUtils.isNotEmpty((Collection)bills)) {
            for (final UserDailySettleBillAdapter bill : bills) {
                this.processLineBill(bill);
            }
        }
        return bills;
    }
    
    private UserDailySettleBill createBill(final int userId, final String eTime, final UserDailySettle dailySettle) {
        final int minValidUserCfg = this.dataFactory.getDailySettleConfig().getMinValidUserl();
        final UserDailySettleBill dailySettleBill = new UserDailySettleBill();
        dailySettleBill.setUserId(userId);
        dailySettleBill.setIndicateDate(eTime);
        if (dailySettle.getMinValidUser() >= minValidUserCfg) {
            dailySettleBill.setMinValidUser(dailySettle.getMinValidUser());
        }
        else {
            dailySettleBill.setMinValidUser(minValidUserCfg);
        }
        dailySettleBill.setValidUser(0);
        dailySettleBill.setScale(0.0);
        dailySettleBill.setBillingOrder(0.0);
        dailySettleBill.setUserAmount(0.0);
        return dailySettleBill;
    }
    
    private boolean check(final User user, final UserDailySettle dailySettle) {
        if (user.getId() == 72) {
            final String error = String.format("契约日结错误提醒;用户%s为总账号，但查找到其拥有日结配置，本次不对其进行结算，不影响整体结算；", user.getUsername());
            DailySettleJob.log.error(error);
            this.mailJob.addWarning(error);
            return false;
        }
        final boolean isZhaoShang = this.uCodePointUtil.isLevel3Proxy(user);
        if (isZhaoShang) {
            return true;
        }
        final UserDailySettle upUserDailySettle = this.uDailySettleService.getByUserId(user.getUpid());
        if (upUserDailySettle == null) {
            final String error2 = String.format("契约日结错误提醒;用户%s没有找到上级的日结配置，本次不对其团队进行结算；", user.getUsername());
            DailySettleJob.log.error(error2);
            this.mailJob.addWarning(error2);
            return false;
        }
        if (!this.uDailySettleService.checkValidLevel(dailySettle.getScaleLevel(), dailySettle.getSalesLevel(), dailySettle.getLossLevel(), upUserDailySettle, dailySettle.getUserLevel())) {
            final String error2 = String.format("日结分红错误提醒;用户%s，所签订分红条款为无效条款，条款内容：分红比例[%s]，销量[%s]，亏损[%s]，本次不对其团队进行结算；", user.getUsername(), dailySettle.getScaleLevel(), dailySettle.getSalesLevel(), dailySettle.getLossLevel());
            DailySettleJob.log.error(error2);
            this.mailJob.addWarning(error2);
            return false;
        }
        return true;
    }
    
    public UserDailySettleBillAdapter settleUpWithUser(final User user, final String sTime, final String eTime, final boolean settleLowers, final int issueType) {
        final UserDailySettle dailySettle = this.uDailySettleService.getByUserId(user.getId());
        if (dailySettle == null || dailySettle.getStatus() != 1) {
            return null;
        }
        final boolean checked = this.check(user, dailySettle);
        if (!checked) {
            return null;
        }
        final List<UserLotteryReportVO> reports = this.uLotteryReportService.report(user.getId(), sTime, eTime);
        if (CollectionUtils.isEmpty((Collection)reports)) {
            return null;
        }
        final UserDailySettleBill upperBill = this.createBill(user.getId(), eTime, dailySettle);
        upperBill.setIssueType(issueType);
        final UserDailySettleBill summaryBill = this.summaryUpReports(reports, user.getId(), sTime, eTime);
        upperBill.setBillingOrder(summaryBill.getBillingOrder());
        upperBill.setThisLoss(summaryBill.getThisLoss());
        upperBill.setValidUser(summaryBill.getValidUser());
        if (summaryBill.getBillingOrder() <= 0.0) {
            return null;
        }
        final boolean isCheckLossCfg = this.dataFactory.getDailySettleConfig().isCheckLoss();
        final double billingOrder = MathUtil.divide(upperBill.getBillingOrder(), 10000.0, 2);
        final double thisLoss = MathUtil.divide(Math.abs(upperBill.getThisLoss()), 10000.0, 2);
        final int thisUser = upperBill.getValidUser();
        final String[] scaleLevels = dailySettle.getScaleLevel().split(",");
        final String[] salesLevels = dailySettle.getSalesLevel().split(",");
        final String[] lossLevels = dailySettle.getLossLevel().split(",");
        final String[] userLevels = dailySettle.getUserLevel().split(",");
        final List<Integer> levels = new ArrayList<Integer>();
        for (int i = 0; i < salesLevels.length; ++i) {
            if (billingOrder >= Double.parseDouble(salesLevels[i]) && thisUser >= Integer.valueOf(userLevels[i])) {
                levels.add(i);
            }
        }
        if (levels.size() > 0) {
            Collections.sort(levels);
            upperBill.setScale(Double.valueOf(scaleLevels[levels.get(levels.size() - 1)]));
        }
        if (upperBill.getStatus() != 5) {
            double calAmount = 0.0;
            if (levels.size() > 0 && upperBill.getValidUser() >= upperBill.getMinValidUser()) {
                double scale = MathUtil.divide(upperBill.getScale(), 100.0, 6);
                calAmount = MathUtil.decimalFormat(new BigDecimal(MathUtil.multiply(Math.abs(upperBill.getBillingOrder()), scale)), 2);
                if (isCheckLossCfg) {
                    levels.clear();
                    calAmount = 0.0;
                    for (int j = 0; j < salesLevels.length; ++j) {
                        if (billingOrder >= Double.parseDouble(salesLevels[j]) && thisLoss >= Double.parseDouble(lossLevels[j]) && thisUser >= Integer.valueOf(userLevels[j])) {
                            levels.add(j);
                        }
                    }
                    if (levels.size() > 0) {
                        Collections.sort(levels);
                        upperBill.setScale(Double.valueOf(scaleLevels[levels.get(levels.size() - 1)]));
                        scale = MathUtil.divide(upperBill.getScale(), 100.0, 4);
                        calAmount = MathUtil.decimalFormat(new BigDecimal(MathUtil.multiply(Math.abs(upperBill.getBillingOrder()), scale)), 2);
                    }
                }
            }
            upperBill.setCalAmount(calAmount);
            if (calAmount == 0.0) {
                upperBill.setCalAmount(0.0);
                upperBill.setScale(0.0);
                upperBill.setStatus(5);
                upperBill.setRemarks("日结分红条款未达标");
                return new UserDailySettleBillAdapter(upperBill, null);
            }
        }
        double lowerTotalAmount = 0.0;
        final List<UserDailySettleBillAdapter> lowerBills = new ArrayList<UserDailySettleBillAdapter>();
        if (settleLowers) {
            for (final UserLotteryReportVO report : reports) {
                if (!"总计".equals(report.getName()) && !report.getName().equalsIgnoreCase(user.getUsername())) {
                    final User subUser = this.uService.getByUsername(report.getName());
                    final UserDailySettleBillAdapter lowerBillAdapter = this.settleUpWithUser(subUser, sTime, eTime, true, 2);
                    if (lowerBillAdapter == null) {
                        continue;
                    }
                    final UserDailySettleBill lowerUpperBill = lowerBillAdapter.getUpperBill();
                    lowerTotalAmount = MathUtil.add(lowerTotalAmount, lowerUpperBill.getCalAmount());
                    lowerBills.add(lowerBillAdapter);
                }
            }
        }
        upperBill.setLowerTotalAmount(lowerTotalAmount);
        return new UserDailySettleBillAdapter(upperBill, lowerBills);
    }
    
    private void processLineBill(final UserDailySettleBillAdapter uDailySettleBillAdapter) {
        final UserDailySettleBill upperBill = uDailySettleBillAdapter.getUpperBill();
        final List<UserDailySettleBillAdapter> lowerBills = uDailySettleBillAdapter.getLowerBills();
        if (upperBill.getIssueType() == 1 && CollectionUtils.isEmpty((Collection)lowerBills)) {
            if (upperBill.getStatus() == 4) {
                final double amount = 0.0;
                upperBill.setUserAmount(amount);
                upperBill.setTotalReceived(amount);
                this.saveBill(upperBill, 4, amount);
            }
            else {
                final double amount = upperBill.getCalAmount();
                upperBill.setUserAmount(amount);
                upperBill.setTotalReceived(amount);
                this.saveBill(upperBill, 1, amount);
            }
            return;
        }
        if (upperBill.getIssueType() == 2 && CollectionUtils.isEmpty((Collection)lowerBills)) {
            if (upperBill.getStatus() == 4) {
                final double amount = 0.0;
                upperBill.setUserAmount(amount);
                upperBill.setTotalReceived(amount);
                this.saveBill(upperBill, 4, amount);
            }
            else {
                final int status = (upperBill.getTotalReceived() >= upperBill.getCalAmount()) ? 1 : 2;
                upperBill.setUserAmount(upperBill.getCalAmount());
                this.saveBill(upperBill, status, upperBill.getTotalReceived());
            }
            return;
        }
        double upperBillMoney;
        if (upperBill.getIssueType() == 1) {
            upperBillMoney = upperBill.getCalAmount();
        }
        else {
            upperBillMoney = upperBill.getTotalReceived();
        }
        double upperThisTimePaid = 0.0;
        for (final UserDailySettleBillAdapter lowerBill : lowerBills) {
            final UserDailySettleBill lowerUpperBill = lowerBill.getUpperBill();
            final double lowerUpperAmount = lowerUpperBill.getCalAmount();
            if (lowerUpperAmount > 0.0) {
                double lowerRemainReceived = lowerUpperAmount;
                double billGive = 0.0;
                if (upperBillMoney > 0.0 && lowerRemainReceived > 0.0) {
                    billGive = ((lowerUpperAmount >= upperBillMoney) ? upperBillMoney : lowerUpperAmount);
                    upperBillMoney = MathUtil.subtract(upperBillMoney, billGive);
                    lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, billGive);
                }
                double totalMoneyGive = 0.0;
                final User upperUser = this.uDao.getById(upperBill.getUserId());
                if (lowerRemainReceived > 0.0 && upperUser.getTotalMoney() > 0.0) {
                    totalMoneyGive = ((lowerRemainReceived >= upperUser.getTotalMoney()) ? upperUser.getTotalMoney() : lowerRemainReceived);
                    lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, totalMoneyGive);
                }
                double lotteryMoneyGive = 0.0;
                if (lowerRemainReceived > 0.0 && upperUser.getLotteryMoney() > 0.0) {
                    lotteryMoneyGive = ((lowerRemainReceived >= upperUser.getLotteryMoney()) ? upperUser.getLotteryMoney() : lowerRemainReceived);
                    lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, lotteryMoneyGive);
                }
                final double totalGive = MathUtil.add(MathUtil.add(billGive, totalMoneyGive), lotteryMoneyGive);
                if (totalGive > 0.0) {
                    if (totalMoneyGive > 0.0) {
                        final UserVO subUser = this.dataFactory.getUser(lowerUpperBill.getUserId());
                        this.uDao.updateTotalMoney(upperUser.getId(), -totalMoneyGive);
                        this.uBillService.addDailySettleBill(upperUser, 1, -totalMoneyGive, "系统自动扣发" + totalMoneyGive + "日结金额到" + subUser.getUsername(), false);
                    }
                    if (lotteryMoneyGive > 0.0) {
                        final UserVO subUser = this.dataFactory.getUser(lowerUpperBill.getUserId());
                        this.uDao.updateLotteryMoney(upperUser.getId(), -lotteryMoneyGive);
                        this.uBillService.addDailySettleBill(upperUser, 2, -lotteryMoneyGive, "系统自动扣发" + lotteryMoneyGive + "日结金额到" + subUser.getUsername(), false);
                    }
                    upperThisTimePaid = MathUtil.add(upperThisTimePaid, totalGive);
                    lowerUpperBill.setTotalReceived(totalGive);
                }
            }
            this.processLineBill(lowerBill);
        }
        upperBill.setLowerPaidAmount(upperThisTimePaid);
        if (upperBillMoney > 0.0) {
            upperBill.setUserAmount(upperBillMoney);
            upperBill.setTotalReceived(upperBillMoney);
            this.saveBill(upperBill, 1, upperBillMoney);
        }
        else {
            final double notYetPay = MathUtil.subtract(upperBill.getLowerTotalAmount(), upperBill.getLowerPaidAmount());
            if (notYetPay > 0.0) {
                final double amount2 = 0.0;
                upperBill.setUserAmount(amount2);
                upperBill.setRemarks("余额不足,请充值！");
                this.saveBill(upperBill, 3, 0.0);
            }
            else {
                final double amount2 = 0.0;
                upperBill.setUserAmount(amount2);
                this.saveBill(upperBill, 1, 0.0);
            }
        }
    }
    
    private void saveBill(final UserDailySettleBill upperBill, final int status, final double userAmount) {
        upperBill.setSettleTime(new Moment().toSimpleTime());
        upperBill.setStatus(status);
        if (userAmount > 0.0) {
            final User user = this.uDao.getById(upperBill.getUserId());
            if (user != null) {
                final String remarks = String.format("日结,销量：%s", new BigDecimal(upperBill.getBillingOrder()).setScale(4, RoundingMode.FLOOR).toString());
                final boolean addedBill = this.uBillService.addDailySettleBill(user, 2, userAmount, remarks, true);
                if (addedBill) {
                    this.uDao.updateLotteryMoney(user.getId(), userAmount);
                    this.uSysMessageService.addDailySettleBill(user.getId(), upperBill.getIndicateDate());
                }
            }
        }
        this.uDailySettleBillService.add(upperBill);
    }
    
    private UserDailySettleBill summaryUpReports(final List<UserLotteryReportVO> reports, final int userId, final String sTime, final String eTime) {
        double billingOrder = 0.0;
        for (final UserLotteryReportVO report : reports) {
            if ("总计".equals(report.getName())) {
                billingOrder = report.getBillingOrder();
                break;
            }
        }
        final double minBillingOrder = this.dataFactory.getDailySettleConfig().getMinBillingOrder();
        final List<User> userLowers = this.uDao.getUserLower(userId);
        int validUser = 0;
        for (final User lowerUser : userLowers) {
            final double lowerUserBillingOrder = this.summaryUpLowerUserReports(lowerUser.getId(), sTime, eTime);
            if (lowerUserBillingOrder >= minBillingOrder) {
                ++validUser;
            }
        }
        final double selfBilling = this.summaryUpLowerUserReports(userId, sTime, eTime);
        if (selfBilling >= minBillingOrder) {
            ++validUser;
        }
        final double thisLoss = this.calculateLotteryLossByLotteryReport(reports);
        final UserDailySettleBill bill = new UserDailySettleBill();
        bill.setBillingOrder(billingOrder);
        bill.setValidUser(validUser);
        bill.setThisLoss(thisLoss);
        return bill;
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
    
    private double summaryUpLowerUserReports(final int userId, final String sTime, final String eTime) {
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
    
    private void sendMail(final List<UserDailySettleBillAdapter> bills, final String sTime, final String eTime) {
        try {
            double totalBillingOrder = 0.0;
            double totalAmount = 0.0;
            if (CollectionUtils.isNotEmpty((Collection)bills)) {
                final List<UserDailySettleBill> allBills = new ArrayList<UserDailySettleBill>();
                this.getAllBills(bills, allBills);
                for (final UserDailySettleBill bill : allBills) {
                    if (bill.getIssueType() == 1) {
                        totalAmount = MathUtil.add(totalAmount, bill.getCalAmount());
                    }
                }
            }
            final List<UserLotteryReportVO> reports = this.uLotteryReportService.report(sTime, eTime);
            if (CollectionUtils.isNotEmpty((Collection)reports)) {
                for (final UserLotteryReportVO report : reports) {
                    if ("总计".equals(report.getName())) {
                        totalBillingOrder = report.getBillingOrder();
                        break;
                    }
                }
            }
            this.mailJob.sendDailySettle(sTime, totalBillingOrder, totalAmount);
        }
        catch (Exception e) {
            DailySettleJob.log.error("发送契约日结邮件出错", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    private List<UserDailySettleBill> getAllBills(final List<UserDailySettleBillAdapter> bills, final List<UserDailySettleBill> container) {
        if (CollectionUtils.isEmpty((Collection)bills)) {
            return container;
        }
        for (final UserDailySettleBillAdapter bill : bills) {
            container.add(bill.getUpperBill());
            this.getAllBills(bill.getLowerBills(), container);
        }
        return container;
    }
}
