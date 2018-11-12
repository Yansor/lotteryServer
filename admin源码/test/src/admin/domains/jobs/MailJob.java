package admin.domains.jobs;

import java.util.Iterator;
import lottery.domains.content.vo.config.MailConfig;
import javautils.email.SpringMailUtil;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import javautils.math.MathUtil;
import lottery.domains.content.entity.SysPlatform;
import lottery.domains.content.entity.GameBets;
import lottery.domains.content.entity.User;
import javautils.date.Moment;
import lottery.domains.content.entity.UserRecharge;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import java.util.Collection;
import java.util.LinkedList;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.concurrent.LinkedBlockingDeque;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.pool.LotteryDataFactory;
import java.util.concurrent.BlockingQueue;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MailJob
{
    private static final Logger log;
    private BlockingQueue<String> logQueue;
    private BlockingQueue<String> warningQueue;
    @Autowired
    private LotteryDataFactory dataFactory;
    private static boolean isRunning;
    private static boolean isRunningWarning;
    private static Object warningLock;
    
    static {
        log = LoggerFactory.getLogger((Class)MailJob.class);
        MailJob.isRunning = false;
        MailJob.isRunningWarning = false;
        MailJob.warningLock = new Object();
    }
    
    public MailJob() {
        this.logQueue = new LinkedBlockingDeque<String>();
        this.warningQueue = new LinkedBlockingDeque<String>();
    }
    
    @Scheduled(cron = "0/10 * * * * *")
    public void run() {
        synchronized (MailJob.class) {
            if (MailJob.isRunning) {
                // monitorexit(MailJob.class)
                return;
            }
            MailJob.isRunning = true;
        }
        // monitorexit(MailJob.class)
        try {
            this.send();
        }
        finally {
            MailJob.isRunning = false;
        }
        MailJob.isRunning = false;
    }
    
    @Scheduled(cron = "0/8 * * * * *")
    public void runWarning() {
        synchronized (MailJob.warningLock) {
            if (MailJob.isRunningWarning) {
                // monitorexit(MailJob.warningLock)
                return;
            }
            MailJob.isRunningWarning = true;
        }
        // monitorexit(MailJob.warningLock)
        try {
            this.sendWarning();
        }
        finally {
            MailJob.isRunningWarning = false;
        }
        MailJob.isRunningWarning = false;
    }
    
    private void send() {
        if (this.logQueue != null && this.logQueue.size() > 0) {
            try {
                final List<String> msgs = new LinkedList<String>();
                this.logQueue.drainTo(msgs, 5);
                if (CollectionUtils.isNotEmpty((Collection)msgs)) {
                    this.send(msgs, null);
                }
            }
            catch (Exception e) {
                MailJob.log.error("发送邮件错误", (Throwable)e);
            }
        }
    }
    
    private void sendWarning() {
        if (this.warningQueue != null && this.warningQueue.size() > 0) {
            try {
                final List<String> msgs = new LinkedList<String>();
                this.warningQueue.drainTo(msgs, 5);
                if (CollectionUtils.isNotEmpty((Collection)msgs)) {
                    this.send(msgs, "999wudi@gmail.com");
                }
            }
            catch (Exception e) {
                MailJob.log.error("发送邮件错误", (Throwable)e);
            }
        }
    }
    
    private void add(final String message) {
        this.logQueue.offer(message);
    }
    
    public void sendRecharge(final String username, final String paymentThird, final UserRecharge userRecharge) {
        try {
            final int amount = (int)userRecharge.getMoney();
            final String payTime = userRecharge.getPayTime();
            final String billNo = userRecharge.getBillno();
            final Object[] values = { username, amount, payTime, paymentThird, billNo };
            final String message = String.format("用户充值提醒；用户名：%s；金额：%s；时间：%s；渠道：%s；充值单号：%s；", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendRecharge", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendSystemRecharge(final String username, final String operator, final int type, final int account, final double amount, final String remarks) {
        try {
            final int amountInt = (int)amount;
            final String typeStr = this.getSystemRechargeType(type);
            final String accountStr = this.getAccount(account);
            final String time = new Moment().toSimpleTime();
            final Object[] values = { username, amountInt, typeStr, operator, accountStr, time, remarks };
            final String message = String.format("管理员加减钱提醒；用户名：%s；金额：%s；类型：%s；操作人：%s；账户：%s；操作时间：%s；备注：%s；", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendSystemRecharge", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendLockUser(final User user, final String operator, final int status, final String remarks) {
        try {
            final String username = user.getUsername();
            final int totalMoney = (int)user.getTotalMoney();
            final int lotteryMoney = (int)user.getLotteryMoney();
            final String statusStr = this.getLockUserStatus(status);
            final String time = new Moment().toSimpleTime();
            final Object[] values = { username, statusStr, totalMoney, lotteryMoney, operator, time, remarks };
            final String message = String.format("管理员冻结用户提醒；用户名：%s；状态：%s；主账户：%s；彩票账户：%s；操作人：%s；操作时间：%s；备注：%s；", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendLockUser", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendUnLockUser(final User user, final String operator) {
        try {
            final String username = user.getUsername();
            final int totalMoney = (int)user.getTotalMoney();
            final int lotteryMoney = (int)user.getLotteryMoney();
            final String time = new Moment().toSimpleTime();
            final Object[] values = { username, totalMoney, lotteryMoney, operator, time };
            final String message = String.format("管理员解冻用户提醒；用户名：%s；主账户：%s；彩票账户：%s；操作人：%s；操作时间：%s；", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendUnLockUser", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendLockTeam(final String username, final String operator, final String remarks) {
        try {
            final String time = new Moment().toSimpleTime();
            final Object[] values = { username, operator, time, remarks };
            final String message = String.format("管理员冻结团队提醒；用户名：%s；操作人：%s；操作时间：%s；备注：%s；", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendLockUser", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendUnLockTeam(final String username, final String operator, final String remarks) {
        try {
            final String time = new Moment().toSimpleTime();
            final Object[] values = { username, operator, time, remarks };
            final String message = String.format("管理员解冻团队提醒；用户名：%s；操作人：%s；操作时间：%s；备注：%s；", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendLockUser", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendProhibitTeamWithdraw(final String username, final String operator) {
        try {
            final String time = new Moment().toSimpleTime();
            final Object[] values = { username, operator, time };
            final String message = String.format("管理员禁止团队取款提醒；用户名：%s；操作人：%s；操作时间：%s", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendLockUser", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendAllowTeamWithdraw(final String username, final String operator) {
        try {
            final String time = new Moment().toSimpleTime();
            final Object[] values = { username, operator, time };
            final String message = String.format("管理员开启团队取款提醒；用户名：%s；操作人：%s；操作时间：%s", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendLockUser", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendAllowTeamTransfers(final String username, final String operator) {
        try {
            final String time = new Moment().toSimpleTime();
            final Object[] values = { username, operator, time };
            final String message = String.format("管理员开启团队上下级转账提醒；用户名：%s；操作人：%s；操作时间：%s", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendLockUser", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendProhibitTeamTransfers(final String username, final String operator) {
        try {
            final String time = new Moment().toSimpleTime();
            final Object[] values = { username, operator, time };
            final String message = String.format("管理员禁止团队上下级转账提醒；用户名：%s；操作人：%s；操作时间：%s", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendLockUser", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendAllowTeamPlatformTransfers(final String username, final String operator) {
        try {
            final String time = new Moment().toSimpleTime();
            final Object[] values = { username, operator, time };
            final String message = String.format("管理员开启团队平台转账提醒；用户名：%s；操作人：%s；操作时间：%s", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendLockUser", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendProhibitTeamPlatformTransfers(final String username, final String operator) {
        try {
            final String time = new Moment().toSimpleTime();
            final Object[] values = { username, operator, time };
            final String message = String.format("管理员禁止团队平台转账提醒；用户名：%s；操作人：%s；操作时间：%s", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendLockUser", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendRecoverUser(final User user, final String operator) {
        try {
            final String username = user.getUsername();
            final int totalMoney = (int)user.getTotalMoney();
            final int lotteryMoney = (int)user.getLotteryMoney();
            final String time = new Moment().toSimpleTime();
            final Object[] values = { username, totalMoney, lotteryMoney, operator, time };
            final String message = String.format("管理员回收用户提醒；用户名：%s；主账户：%s；彩票账户：%s；操作人：%s；操作时间：%s；", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendRecoverUser", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendDailySettle(final String eTime, final double billingOrder, final double totalAmount) {
        try {
            final int billingOrderInt = (int)billingOrder;
            final int totalAmountInt = (int)totalAmount;
            final String time = new Moment().toSimpleTime();
            final Object[] values = { eTime, totalAmountInt, billingOrderInt, time };
            final String message = String.format("契约日结已派发；日期：%s；总金额：%s；总销量：%s；系统派发时间：%s；", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendDailySettle", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendDividend(final String sTime, final String eTime, final double billingOrder, final double totalLoss, final double platformTotalLoss, final double platformTotalAmount) {
        try {
            final int billingOrderInt = (int)billingOrder;
            final int totalLossInt = (int)totalLoss;
            final int platformTotalLossInt = (int)platformTotalLoss;
            final int platformTotalAmountInt =(int) platformTotalAmount;
            final String time = new Moment().toSimpleTime();
            final Object[] values = { sTime, eTime, billingOrderInt, totalLossInt, platformTotalLossInt, platformTotalAmountInt, time };
            final String message = String.format("契约分红已计算；周期：%s~%s；报表销量：%s；报表亏损：%s；平台发放层级总亏损：%s；平台总发放：%s；系统计算时间：%s；", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendDividend", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendGameDividend(final String sTime, final String eTime, final double billingOrder, final double totalLoss, final double totalAmount) {
        try {
            final int billingOrderInt = (int)billingOrder;
            final int totalLossInt = (int)totalLoss;
            final int totalAmountInt = (int)totalAmount;
            final String time = new Moment().toSimpleTime();
            final Object[] values = { sTime, eTime, billingOrderInt, totalLossInt, totalAmountInt, time };
            final String message = String.format("老虎机真人体育分红已计算；周期：%s~%s；总销量：%s；总亏损：%s；总发放：%s；系统计算时间：%s；", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误sendGameDividend", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendOpen(final String username, final GameBets gameBets) {
        try {
            final SysPlatform sysPlatform = this.dataFactory.getSysPlatform(gameBets.getPlatformId());
            final String platformName = sysPlatform.getName();
            final int amount = (int)gameBets.getMoney();
            final int prizeAmount = (int)gameBets.getPrizeMoney();
            final String type = gameBets.getGameType();
            final String name = gameBets.getGameName();
            final String time = gameBets.getTime();
            final String prizeTime = gameBets.getPrizeTime();
            final int id = gameBets.getId();
            final Object[] values = { platformName, username, amount, prizeAmount, type, name, time, prizeTime, id };
            final String message = String.format("用户%s中奖提醒；用户名：%s；投注金额：%s；中奖金额：%s；游戏类型：%s；游戏名称：%s；下单时间：%s；派奖时间：%s；注单ID：%s；", values);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    public void sendUserTransfer(final String aUser, final String bUser, final double money, final String remarks) {
        try {
            final String moneyStr = MathUtil.doubleToStringDown(money, 1);
            final String message = String.format("管理员操作用户转账提醒；待转会员：%s；目标会员：%s；金额：%s；备注：%s；", aUser, bUser, moneyStr, remarks);
            this.add(message);
        }
        catch (Exception e) {
            MailJob.log.error("发送邮件发生错误", (Object)((e == null) ? "" : e.getMessage()));
        }
    }
    
    private String getSystemRechargeType(final int subType) {
        String typeStr = null;
        switch (subType) {
            case 1: {
                typeStr = "充值未到账";
                break;
            }
            case 2: {
                typeStr = "活动补贴";
                break;
            }
            case 3: {
                typeStr = "修改资金（增）";
                break;
            }
            case 4: {
                typeStr = "修改资金（减）";
                break;
            }
            default: {
                typeStr = "未知";
                break;
            }
        }
        return typeStr;
    }
    
    private String getAccount(final int account) {
        String accountStr = null;
        switch (account) {
            case 1: {
                accountStr = "主账户";
                break;
            }
            case 2: {
                accountStr = "彩票账户";
                break;
            }
            case 3: {
                accountStr = "百家乐账户";
                break;
            }
            default: {
                accountStr = "未知";
                break;
            }
        }
        return accountStr;
    }
    
    private String getLockUserStatus(final int status) {
        String statusStr = null;
        switch (status) {
            case 0: {
                statusStr = "正常";
                break;
            }
            case -1: {
                statusStr = "冻结";
                break;
            }
            case -2: {
                statusStr = "永久冻结";
                break;
            }
            case -3: {
                statusStr = "禁用";
                break;
            }
            default: {
                statusStr = "未知";
                break;
            }
        }
        return statusStr;
    }
    
    private void send(final List<String> msgs, final String email) {
        List<String> receiveMails;
        if (StringUtils.isEmpty(email)) {
            receiveMails = this.dataFactory.getMailConfig().getReceiveMails();
            if (CollectionUtils.isEmpty((Collection)receiveMails)) {
                return;
            }
        }
        else {
            receiveMails = new ArrayList<String>();
            receiveMails.add(email);
        }
        try {
            final MailConfig mailConfig = this.dataFactory.getMailConfig();
            final SpringMailUtil mailUtil = new SpringMailUtil(mailConfig.getUsername(), mailConfig.getPersonal(), mailConfig.getPassword(), mailConfig.getHost());
            for (final String msg : msgs) {
                for (final String receiveMail : receiveMails) {
                    mailUtil.send(receiveMail, "提醒", msg);
                    Thread.sleep(1000L);
                }
            }
        }
        catch (InterruptedException e) {
            MailJob.log.error("发送邮件错误");
        }
    }
    
    public void addWarning(final String message) {
        this.warningQueue.offer(message);
    }
}
