package admin.domains.jobs;

import lottery.domains.content.entity.UserDividend;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.content.entity.UserDailySettle;
import lottery.domains.content.entity.UserGameDividendBill;
import lottery.domains.content.entity.UserDividendBill;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import lottery.domains.content.entity.LotteryPlayRules;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.PaymentBank;
import java.math.BigDecimal;
import lottery.domains.content.entity.User;
import javautils.math.MathUtil;
import javautils.date.Moment;
import javautils.http.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import admin.domains.content.entity.AdminUser;
import java.util.Arrays;
import javautils.ip.IpUtil;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.List;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;
import admin.domains.content.entity.AdminUserLog;
import java.util.concurrent.BlockingQueue;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.dao.AdminUserLogDao;
import org.springframework.stereotype.Component;

@Component
public class AdminUserLogJob
{
    @Autowired
    private AdminUserLogDao adminUserLogDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    private BlockingQueue<AdminUserLog> logQueue;
    
    public AdminUserLogJob() {
        this.logQueue = new LinkedBlockingDeque<AdminUserLog>();
    }
    
    @Scheduled(cron = "0/5 * * * * *")
    public void run() {
        if (this.logQueue != null && this.logQueue.size() > 0) {
            try {
                final List<AdminUserLog> list = new LinkedList<AdminUserLog>();
                this.logQueue.drainTo(list, 1000);
                this.adminUserLogDao.save(list);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    String ip2Address(final String ip) {
        String address = "[未知地址]";
        try {
            final String[] infos = IpUtil.find(ip);
            address = Arrays.toString(infos);
        }
        catch (Exception ex) {}
        return address;
    }
    
    boolean add(final AdminUser uEntity, final HttpServletRequest request, final String action) {
        final int userId = uEntity.getId();
        final String ip = HttpUtil.getClientIpAddr(request);
        final String address = this.ip2Address(ip);
        final String time = new Moment().toSimpleTime();
        final AdminUserLog entity = new AdminUserLog(userId, ip, address, action, time);
        final String userAgent = request.getHeader("user-agent");
        entity.setUserAgent(userAgent);
        return this.logQueue.offer(entity);
    }
    
    public boolean logAddUser(final AdminUser uEntity, final HttpServletRequest request, final String username, final String relatedUsers, final int type, final double point) {
        String formatType = "未知";
        String _relatedInfo = "";
        if (type == 1) {
            formatType = "代理";
        }
        if (type == 2) {
            formatType = "玩家";
        }
        if (type == 3) {
            formatType = "关联账号";
            _relatedInfo = "关联会员：" + relatedUsers + "；";
        }
        final String action = String.format("添加会员账号；用户名：%s；用户类型：%s；返点：%s；%s", username, formatType, point, _relatedInfo);
        return this.add(uEntity, request, action);
    }
    
    public boolean logChangeLine(final AdminUser uEntity, final HttpServletRequest request, final String aUser, final String bUser) {
        final String action = String.format("线路转移；转移线路：%s；目标线路：%s", aUser, bUser);
        return this.add(uEntity, request, action);
    }
    
    public boolean logRecharge(final AdminUser uEntity, final HttpServletRequest request, final String username, final int type, final int account, final double amount, final int limit, final String remarks) {
        String formatType = "未知";
        if (type == 1) {
            formatType = "充值未到账";
        }
        if (type == 2) {
            formatType = "活动补贴";
        }
        if (type == 3) {
            formatType = "修正资金（增加）";
        }
        if (type == 4) {
            formatType = "修正资金（减少）";
        }
        String formatAccount = "未知";
        if (account == 1) {
            formatAccount = "主账户";
        }
        if (account == 2) {
            formatAccount = "彩票账户";
        }
        if (account == 3) {
            formatAccount = "百家乐账户";
        }
        final String formatLimit = (limit == 1) ? "是" : "否";
        final String action = String.format("充值；用户名：%s；充值类型：%s；账户类型：%s；金额：%s；是否需要消费：%s；备注：%s", username, formatType, formatAccount, amount, formatLimit, remarks);
        return this.add(uEntity, request, action);
    }
    
    public boolean logLockUser(final AdminUser uEntity, final HttpServletRequest request, final String username, final int status, final String message) {
        String formatStatus = "未知";
        if (status == -1) {
            formatStatus = "冻结";
        }
        if (status == -2) {
            formatStatus = "永久冻结";
        }
        if (status == -3) {
            formatStatus = "禁用";
        }
        final String action = String.format("冻结用户；用户名：%s；冻结状态：%s；冻结说明：%s", username, formatStatus, message);
        return this.add(uEntity, request, action);
    }
    
    public boolean logLockTeam(final AdminUser uEntity, final HttpServletRequest request, final String username, final String message) {
        final String action = String.format("冻结团队；用户名：%s；冻结说明：%s", username, message);
        return this.add(uEntity, request, action);
    }
    
    public boolean logUnLockTeam(final AdminUser uEntity, final HttpServletRequest request, final String username, final String message) {
        final String action = String.format("解冻团队；用户名：%s；解冻说明：%s", username, message);
        return this.add(uEntity, request, action);
    }
    
    public boolean prohibitTeamWithdraw(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("禁止团队取款；用户名：%s；", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean allowTeamWithdraw(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("开启团队取款；用户名：%s；", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean allowTeamTransfers(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("开启团队上下级转账；用户名：%s；", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean allowTeamPlatformTransfers(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("开启团队转账；用户名：%s；", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean prohibitTeamTransfers(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("禁止团队上下级转账；用户名：%s；", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean prohibitTeamPlatformTransfers(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("禁止团队转账；用户名：%s；", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean logUserTransfer(final AdminUser uEntity, final HttpServletRequest request, final String aUser, final String bUser, final double money, final String remarks) {
        final String moneyStr = MathUtil.doubleToStringDown(money, 1);
        final String action = String.format("管理操作用户转账；待转会员：%s；目标会员：%s；金额：%s；备注：%s", aUser, bUser, moneyStr, remarks);
        return this.add(uEntity, request, action);
    }
    
    public boolean logUnlockUser(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("解冻用户；用户名：%s", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModLoginPwd(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("修改用户登录密码；用户名：%s", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModWithdrawPwd(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("修改用户资金密码；用户名：%s", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean logResetEmail(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("重置用户绑定邮箱；用户名：%s", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean logResetSecurity(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("重置密保；用户名：%s", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModPoint(final AdminUser uEntity, final HttpServletRequest request, final String username, final double point) {
        final String action = String.format("修改返点；用户名：%s；返点：%s", username, point);
        return this.add(uEntity, request, action);
    }
    
    public boolean logDownPoint(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("统一降点；用户名：%s", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModExtraPoint(final AdminUser uEntity, final HttpServletRequest request, final String username, final double point) {
        final String action = String.format("修改用户私返点数；用户名：%s；返点：%s", username, point);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModBStatus(final AdminUser uEntity, final HttpServletRequest request, final String username, final int status, final String message) {
        String formatStatus = "未知";
        if (status == 0) {
            formatStatus = "正常";
        }
        if (status == -1) {
            formatStatus = "禁止投注";
        }
        if (status == -2) {
            formatStatus = "自动掉线";
        }
        if (status == -3) {
            formatStatus = "投注超时";
        }
        final String action = String.format("修改用户投注权限；用户名：%s；投注权限：%s；说明：%s", username, formatStatus, message);
        return this.add(uEntity, request, action);
    }
    
    public boolean logRecoverUser(final AdminUser uEntity, final HttpServletRequest request, final User user) {
        String action = "回收账号；用户名：%s；主账户：%s；彩票账户：%s；百家乐账户：%s；";
        final String username = user.getUsername();
        final String totalMoney = new BigDecimal(user.getTotalMoney()).setScale(4, 5).toString();
        final String lotteryMoney = new BigDecimal(user.getLotteryMoney()).setScale(4, 5).toString();
        final String baccaratMoney = new BigDecimal(user.getBaccaratMoney()).setScale(4, 5).toString();
        final Object[] values = { username, totalMoney, lotteryMoney, baccaratMoney };
        action = String.format(action, values);
        return this.add(uEntity, request, action);
    }
    
    public boolean logRecoverUser(final AdminUser uEntity, final HttpServletRequest request, final String username, final int amount1, final int amount2, final int amount3) {
        final String amount4 = String.valueOf(amount1) + "," + amount2 + "," + amount3;
        final String action = String.format("修改用户配额；用户名：%s；配额数量：[%s]", username, amount4);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModEqualCode(final AdminUser uEntity, final HttpServletRequest request, final String username, final int status) {
        final String formatStatus = (status == 1) ? "允许" : "不允许";
        final String action = String.format("修改用户同级开号权限；用户名：%s；状态：%s", username, formatStatus);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModTransfers(final AdminUser uEntity, final HttpServletRequest request, final String username, final int status) {
        final String formatStatus = (status == 1) ? "允许" : "不允许";
        final String action = String.format("修改用户上下级转账权限；用户名：%s；状态：%s", username, formatStatus);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModWithdraw(final AdminUser uEntity, final HttpServletRequest request, final String username, final int status) {
        final String formatStatus = (status == 1) ? "允许" : "不允许";
        final String action = String.format("修改用户取款权限；用户名：%s；状态：%s", username, formatStatus);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModPlatformTransfers(final AdminUser uEntity, final HttpServletRequest request, final String username, final int status) {
        final String formatStatus = (status == 1) ? "允许" : "不允许";
        final String action = String.format("修改用户转账权限；用户名：%s；状态：%s", username, formatStatus);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModWithdrawName(final AdminUser uEntity, final HttpServletRequest request, final String username, final String withdrawName) {
        final String action = String.format("修改用户绑定取款人；用户名：%s；绑定取款人：%s", username, withdrawName);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModEmail(final AdminUser uEntity, final HttpServletRequest request, final String username, final String email) {
        final String action = String.format("修改用户绑定邮箱；用户名：%s；绑定邮箱：%s", username, email);
        return this.add(uEntity, request, action);
    }
    
    public boolean logResetLimit(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("清空用户提款消费量；用户名：%s", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean logChangeProxy(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("玩家转代理；用户名：%s", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean logChangeZhaoShang(final AdminUser uEntity, final HttpServletRequest request, final String username, final int isCJZhaoShang) {
        String action;
        if (isCJZhaoShang == 1) {
            action = String.format("超级招商转为招商；用户名：%s", username);
        }
        else {
            action = String.format("招商转为超级招商；用户名：%s", username);
        }
        return this.add(uEntity, request, action);
    }
    
    public boolean unbindGoogle(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("解绑谷歌身份验证器；用户名：%s", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean resetLockTime(final AdminUser uEntity, final HttpServletRequest request, final String username) {
        final String action = String.format("清空账户时间锁；用户名：%s", username);
        return this.add(uEntity, request, action);
    }
    
    public boolean logAddUserCard(final AdminUser uEntity, final HttpServletRequest request, final String username, final int bankId, final String bankBranch, final String cardId) {
        final PaymentBank paymentBank = this.lotteryDataFactory.getPaymentBank(bankId);
        final String bankName = paymentBank.getName();
        final String action = String.format("添加用户银行卡；用户名：%s；开户行：%s；支行：%s；卡号：%s", username, bankName, bankBranch, cardId);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModUserCard(final AdminUser uEntity, final HttpServletRequest request, final String username, final int bankId, final String bankBranch, final String cardId) {
        final PaymentBank paymentBank = this.lotteryDataFactory.getPaymentBank(bankId);
        final String bankName = paymentBank.getName();
        final String action = String.format("修改用户银行卡资料；用户名：%s；开户行：%s；支行：%s；卡号：%s", username, bankName, bankBranch, cardId);
        return this.add(uEntity, request, action);
    }
    
    public boolean logPatchRecharge(final AdminUser uEntity, final HttpServletRequest request, final String billno, final String payBillno, final String remarks) {
        final String action = String.format("充值漏单补单；订单号：%s；支付单号：%s；说明：%s", billno, payBillno, remarks);
        return this.add(uEntity, request, action);
    }
    
    public boolean logCancelRecharge(final AdminUser uEntity, final HttpServletRequest request, final String billno) {
        final String action = String.format("充值订单撤销；订单号：%s", billno);
        return this.add(uEntity, request, action);
    }
    
    public boolean logCheckWithdraw(final AdminUser uEntity, final HttpServletRequest request, final int id, final int status) {
        String formatStatus = "未知";
        if (status == 1) {
            formatStatus = "已通过";
        }
        if (status == -1) {
            formatStatus = "未通过";
        }
        final String action = String.format("审核用户提现；订单ID：%s；审核结果：%s", id, formatStatus);
        return this.add(uEntity, request, action);
    }
    
    public boolean logManualPay(final AdminUser uEntity, final HttpServletRequest request, final int id, final String payBillno, final String remarks) {
        final String action = String.format("使用手动出款；订单ID：%s；支付单号：%s；备注说明：%s", id, payBillno, remarks);
        return this.add(uEntity, request, action);
    }
    
    public boolean logAPIPay(final AdminUser uEntity, final HttpServletRequest request, final int id, final PaymentChannel paymentChannel) {
        final String action = String.format("使用API代付；订单ID：%s；第三方：%s", id, paymentChannel.getName());
        return this.add(uEntity, request, action);
    }
    
    public boolean logRefuseWithdraw(final AdminUser uEntity, final HttpServletRequest request, final int id, final String reason, final String remarks) {
        final String action = String.format("拒绝支付用户提现；订单ID：%s；拒绝原因：%s；备注说明：%s", id, reason, remarks);
        return this.add(uEntity, request, action);
    }
    
    public boolean reviewedFail(final AdminUser uEntity, final HttpServletRequest request, final int id, final String remarks) {
        final String action = String.format("用户提现审核失败；订单ID：%s；备注说明：%s", id, remarks);
        return this.add(uEntity, request, action);
    }
    
    public boolean logWithdrawFailure(final AdminUser uEntity, final HttpServletRequest request, final int id, final String remarks) {
        final String action = String.format("确认用户提现失败；订单ID：%s；备注说明：%s", id, remarks);
        return this.add(uEntity, request, action);
    }
    
    public boolean logCompleteRemitWithdraw(final AdminUser uEntity, final HttpServletRequest request, final int id) {
        final String action = String.format("确认用户提现到账；订单ID：%s；", id);
        return this.add(uEntity, request, action);
    }
    
    public boolean logLockWithdraw(final AdminUser uEntity, final HttpServletRequest request, final int id) {
        final String action = String.format("锁定用户提现；订单ID：%s；", id);
        return this.add(uEntity, request, action);
    }
    
    public boolean logUnLockWithdraw(final AdminUser uEntity, final HttpServletRequest request, final int id) {
        final String action = String.format("解锁用户提现；订单ID：%s；", id);
        return this.add(uEntity, request, action);
    }
    
    public boolean logCancelOrder(final AdminUser uEntity, final HttpServletRequest request, final int id) {
        final String action = String.format("撤销用户投注订单；订单ID：%s", id);
        return this.add(uEntity, request, action);
    }
    
    public boolean logBatchCancelOrder(final AdminUser uEntity, final HttpServletRequest request, final int lotteryId, final Integer ruleId, final String expect, final String match) {
        final Lottery lottery = this.lotteryDataFactory.getLottery(lotteryId);
        if (lottery != null) {
            String formatMethod = "全部玩法";
            if (ruleId != null) {
                final LotteryPlayRules rules = this.lotteryDataFactory.getLotteryPlayRules(ruleId);
                if (rules != null) {
                    final LotteryPlayRulesGroup group = this.lotteryDataFactory.getLotteryPlayRulesGroup(rules.getGroupId());
                    if (group != null) {
                        formatMethod = "[" + group.getName() + "_" + rules.getName() + "]";
                    }
                }
            }
            String formatExpect = expect;
            if ("eq".equals(match)) {
                formatExpect = "等于" + expect;
            }
            if ("le".equals(match)) {
                formatExpect = "小于等于" + expect;
            }
            if ("ge".equals(match)) {
                formatExpect = "大于等于" + expect;
            }
            final String action = String.format("批量撤销用户订单；彩票类型：%s；玩法规则：%s；投注期号：%s", lottery.getShowName(), formatMethod, formatExpect);
            return this.add(uEntity, request, action);
        }
        return false;
    }
    
    public boolean logAgreeDividend(final AdminUser uEntity, final HttpServletRequest request, final String username, final UserDividendBill userDividendBill, final String remarks) {
        String action = "同意发放彩票分红；用户名：%s；周期：%s~%s；备注：%s；";
        final String startDate = userDividendBill.getIndicateStartDate();
        final String endDate = userDividendBill.getIndicateEndDate();
        final Object[] values = { username, startDate, endDate, (remarks == null) ? "" : remarks };
        action = String.format(action, values);
        return this.add(uEntity, request, action);
    }
    
    public boolean logDenyDividend(final AdminUser uEntity, final HttpServletRequest request, final String username, final UserDividendBill userDividendBill, final String remarks) {
        String action = "拒绝发放彩票分红；用户名：%s；周期：%s~%s；拒绝前用户金额：%s；拒绝后用户金额：%s；备注：%s；";
        final String startDate = userDividendBill.getIndicateStartDate();
        final String endDate = userDividendBill.getIndicateEndDate();
        final Object[] values = { username, startDate, endDate, (remarks == null) ? "" : remarks };
        action = String.format(action, values);
        return this.add(uEntity, request, action);
    }
    
    public boolean logDelDividend(final AdminUser uEntity, final HttpServletRequest request, final String username, final UserDividendBill userDividendBill, final String remarks) {
        String action = "删除彩票分红数据；用户名：%s；周期：%s~%s；用户金额：%s；备注：%s；";
        final String startDate = userDividendBill.getIndicateStartDate();
        final String endDate = userDividendBill.getIndicateEndDate();
        final String userAmount = new BigDecimal(userDividendBill.getUserAmount()).setScale(4, 5).toString();
        final Object[] values = { username, startDate, endDate, userAmount, (remarks == null) ? "" : remarks };
        action = String.format(action, values);
        return this.add(uEntity, request, action);
    }
    
    public boolean logAgreeGameDividend(final AdminUser uEntity, final HttpServletRequest request, final String username, final UserGameDividendBill userDividendBill, final double userAmount, final String remarks) {
        String action = "同意发放老虎机真人体育分红；用户名：%s；周期：%s~%s；同意前用户金额：%s；同意后用户金额：%s；备注：%s；";
        final String startDate = userDividendBill.getIndicateStartDate();
        final String endDate = userDividendBill.getIndicateEndDate();
        final String beforeUserAmount = new BigDecimal(userDividendBill.getUserAmount()).setScale(4, 5).toString();
        final String afterUserAmount = new BigDecimal(userAmount).setScale(4, 5).toString();
        final Object[] values = { username, startDate, endDate, beforeUserAmount, afterUserAmount, (remarks == null) ? "" : remarks };
        action = String.format(action, values);
        return this.add(uEntity, request, action);
    }
    
    public boolean logDenyGameDividend(final AdminUser uEntity, final HttpServletRequest request, final String username, final UserGameDividendBill userDividendBill, final double userAmount, final String remarks) {
        String action = "拒绝发放老虎机真人体育分红；用户名：%s；周期：%s~%s；拒绝前用户金额：%s；拒绝后用户金额：%s；备注：%s；";
        final String startDate = userDividendBill.getIndicateStartDate();
        final String endDate = userDividendBill.getIndicateEndDate();
        final String beforeUserAmount = new BigDecimal(userDividendBill.getUserAmount()).setScale(4, 5).toString();
        final String afterUserAmount = new BigDecimal(userAmount).setScale(4, 5).toString();
        final Object[] values = { username, startDate, endDate, beforeUserAmount, afterUserAmount, (remarks == null) ? "" : remarks };
        action = String.format(action, values);
        return this.add(uEntity, request, action);
    }
    
    public boolean logDelGameDividend(final AdminUser uEntity, final HttpServletRequest request, final String username, final UserGameDividendBill userDividendBill, final String remarks) {
        String action = "删除老虎机真人体育分红数据；用户名：%s；周期：%s~%s；用户金额：%s；备注：%s；";
        final String startDate = userDividendBill.getIndicateStartDate();
        final String endDate = userDividendBill.getIndicateEndDate();
        final String userAmount = new BigDecimal(userDividendBill.getUserAmount()).setScale(4, 5).toString();
        final Object[] values = { username, startDate, endDate, userAmount, (remarks == null) ? "" : remarks };
        action = String.format(action, values);
        return this.add(uEntity, request, action);
    }
    
    public boolean logResetDividend(final AdminUser uEntity, final HttpServletRequest request, final String username, final UserDividendBill userDividendBill, final String remarks) {
        String action = "清零分红数据；用户名：%s；周期：%s~%s；清空金额：%s；备注：%s；";
        final String startDate = userDividendBill.getIndicateStartDate();
        final String endDate = userDividendBill.getIndicateEndDate();
        final String availableAmount = new BigDecimal(userDividendBill.getAvailableAmount()).setScale(4, 5).toString();
        final Object[] values = { username, startDate, endDate, availableAmount, (remarks == null) ? "" : remarks };
        action = String.format(action, values);
        return this.add(uEntity, request, action);
    }
    
    public boolean logAddGame(final AdminUser uEntity, final HttpServletRequest request, final String gameName) {
        final String action = String.format("添加第三方新游戏；游戏名：%s；", gameName);
        return this.add(uEntity, request, action);
    }
    
    public boolean logUpdateGame(final AdminUser uEntity, final HttpServletRequest request, final String gameName) {
        final String action = String.format("修改游戏；游戏名：%s；", gameName);
        return this.add(uEntity, request, action);
    }
    
    public boolean logDelGame(final AdminUser uEntity, final HttpServletRequest request, final String gameName) {
        final String action = String.format("删除第三方游戏；游戏名：%s；", gameName);
        return this.add(uEntity, request, action);
    }
    
    public boolean logUpdateGameStatus(final AdminUser uEntity, final HttpServletRequest request, final String gameName, final int status) {
        String statusCN = "未知";
        if (status == 0) {
            statusCN = "禁用";
        }
        else if (status == 1) {
            statusCN = "启用";
        }
        final String action = String.format("修改第三方游戏状态；游戏名：%s；状态：%s；", gameName, statusCN);
        return this.add(uEntity, request, action);
    }
    
    public boolean logUpdateGameDisplay(final AdminUser uEntity, final HttpServletRequest request, final String gameName, final int display) {
        String displayCN = "未知";
        if (display == 0) {
            displayCN = "不显示";
        }
        else if (display == 1) {
            displayCN = "显示";
        }
        final String action = String.format("修改第三方游戏显示状态；游戏名：%s；是否显示：%s；", gameName, displayCN);
        return this.add(uEntity, request, action);
    }
    
    public boolean logPlatformModStatus(final AdminUser uEntity, final HttpServletRequest request, final int id, final int status) {
        String statusCN;
        if (status == 1) {
            statusCN = "启用";
        }
        else {
            statusCN = "禁用";
        }
        final String action = String.format("修改第三方游戏平台状态；平台ID：%s；状态：%s；", id, statusCN);
        return this.add(uEntity, request, action);
    }
    
    public boolean logLockHighPrize(final AdminUser uEntity, final HttpServletRequest request, final int id) {
        final String action = String.format("锁定大额中奖记录；记录ID：%s；", id);
        return this.add(uEntity, request, action);
    }
    
    public boolean logUnLockHighPrize(final AdminUser uEntity, final HttpServletRequest request, final int id) {
        final String action = String.format("解锁大额中奖记录；记录ID：%s；", id);
        return this.add(uEntity, request, action);
    }
    
    public boolean logConfirmHighPrize(final AdminUser uEntity, final HttpServletRequest request, final int id) {
        final String action = String.format("确认大额中奖记录；记录ID：%s；", id);
        return this.add(uEntity, request, action);
    }
    
    public boolean logEditRedPacketRainConfig(final AdminUser uEntity, final HttpServletRequest request) {
        final String action = String.format("修改红包雨配置；", new Object[0]);
        return this.add(uEntity, request, action);
    }
    
    public boolean logUpdateStatusRedPacketRain(final AdminUser uEntity, final HttpServletRequest request, final int status) {
        String statusCN;
        if (status == 1) {
            statusCN = "启用";
        }
        else {
            statusCN = "禁用";
        }
        final String action = String.format(String.valueOf(statusCN) + "红包雨活动", new Object[0]);
        return this.add(uEntity, request, action);
    }
    
    public boolean logEditFirstRechargeConfig(final AdminUser uEntity, final HttpServletRequest request, final String rules) {
        final String action = String.format("修改首充活动配置；规则：%s", rules);
        return this.add(uEntity, request, action);
    }
    
    public boolean logUpdateStatusFirstRecharge(final AdminUser uEntity, final HttpServletRequest request, final int status) {
        String statusCN;
        if (status == 1) {
            statusCN = "启用";
        }
        else {
            statusCN = "禁用";
        }
        final String action = String.format(String.valueOf(statusCN) + "首充活动", new Object[0]);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModifyRelatedUpper(final AdminUser uEntity, final HttpServletRequest request, final String username, final String relatedUpUser, final double relatedPoint, final String remarks) {
        final String action = String.format("修改关联上级；用户名：%s；关联上级：%s；关联返点：%s；备注：%s；", username, relatedUpUser, relatedPoint, remarks);
        return this.add(uEntity, request, action);
    }
    
    public boolean logReliveRelatedUpper(final AdminUser uEntity, final HttpServletRequest request, final String username, final String remarks) {
        final String action = String.format("解除关联上级；用户名：%s；备注：%s；", username, remarks);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModifyUpdateRelatedUsers(final AdminUser uEntity, final HttpServletRequest request, final String username, final String relatedUsers, final String remarks) {
        final String action = String.format("修改关联会员；用户名：%s；关联会员：%s；备注：%s；", username, relatedUsers, remarks);
        return this.add(uEntity, request, action);
    }
    
    public boolean logDelUserCardUnbindRecord(final AdminUser uEntity, final HttpServletRequest request, final String cardId, final String remarks) {
        final String action = String.format("删除银行卡解锁记录；银行卡：%s；备注：%s；", cardId, remarks);
        return this.add(uEntity, request, action);
    }
    
    public boolean logModifyRefExpect(final AdminUser uEntity, final HttpServletRequest request, final String lottery, final int times) {
        final String action = String.format("增减彩票期号；彩种：%s；增减期数：%s；", lottery, times);
        return this.add(uEntity, request, action);
    }
    
    public boolean logAddPaymenChannel(final AdminUser uEntity, final HttpServletRequest request, final String name) {
        final String action = String.format("增加充值通道账号；名称：%s；；", name);
        return this.add(uEntity, request, action);
    }
    
    public boolean logEditPaymenChannel(final AdminUser uEntity, final HttpServletRequest request, final String name) {
        final String action = String.format("编辑充值通道账号；名称：%s；", name);
        return this.add(uEntity, request, action);
    }
    
    public boolean logEditPaymenChannelStatus(final AdminUser uEntity, final HttpServletRequest request, final int id, final int status) {
        final String statusStr = (status == 0) ? "启用" : "禁用";
        final String action = String.format("修改充值通道账号状态；ID：%s；修改为状态：%s；", id, statusStr);
        return this.add(uEntity, request, action);
    }
    
    public boolean logDeletePaymenChannel(final AdminUser uEntity, final HttpServletRequest request, final int id) {
        final String action = String.format("删除充值通道账号；ID：%s；", id);
        return this.add(uEntity, request, action);
    }
    
    public boolean logEditDailySettle(final AdminUser uEntity, final HttpServletRequest request, final UserDailySettle uds, final String scale, final String sales, final String loss, final String minValidUser) {
        String action = "编辑用户日结配置；ID：%s；用户名：%s；编辑前比例：%s；编辑后比例：%s；编辑前销量：%s；编辑后销量：%s；编辑前亏损：%s；编辑后亏损：%s；编辑前有效人数：%s；编辑后有效人数：%s；";
        final UserVO user = this.lotteryDataFactory.getUser(uds.getUserId());
        String username;
        if (user != null) {
            username = user.getUsername();
        }
        else {
            username = "未知";
        }
        final Object[] values = { uds.getId(), username, uds.getScaleLevel(), scale, uds.getSalesLevel(), sales, uds.getLossLevel(), loss, uds.getUserLevel(), minValidUser };
        action = String.format(action, values);
        return this.add(uEntity, request, action);
    }
    
    public boolean logAddDailySettle(final AdminUser uEntity, final HttpServletRequest request, final String username, final String scaleLevel, final String salesLevel, final String lossLevel, final String minValidUser, final int status) {
        String action = "新增契约日结；用户名：%s；销量：%s；亏损：%s；比例：%s；有效人数：%s；状态：%s；";
        String statusCN;
        if (status == 1) {
            statusCN = "生效";
        }
        else if (status == 2) {
            statusCN = "待同意";
        }
        else {
            statusCN = "未知";
        }
        final Object[] values = { username, salesLevel, lossLevel, scaleLevel, minValidUser, statusCN };
        action = String.format(action, values);
        return this.add(uEntity, request, action);
    }
    
    public boolean logEditDividend(final AdminUser uEntity, final HttpServletRequest request, final UserDividend ud, final String scaleLevel, final String lossLevel, final String salesLevel, final String userLevel) {
        String action = "编辑用户分红配置；ID：%s；用户名：%s；编辑前比例：%s；编辑后比例：%s；编辑前最低有效人数：%s；编辑后最低有效人数：%s； 编辑前销量：%s; 编辑后销量：%s; 编辑前亏损：%s；编辑后亏损：%s；";
        final UserVO user = this.lotteryDataFactory.getUser(ud.getUserId());
        String username;
        if (user != null) {
            username = user.getUsername();
        }
        else {
            username = "未知";
        }
        final Object[] values = { ud.getId(), username, ud.getScaleLevel(), scaleLevel, ud.getUserLevel(), userLevel, ud.getSalesLevel(), salesLevel, ud.getLossLevel(), lossLevel };
        action = String.format(action, values);
        return this.add(uEntity, request, action);
    }
    
    public boolean logAddDividend(final AdminUser uEntity, final HttpServletRequest request, final String username, final String scaleLevel, final String lossLevel, final String salesLevel, final String userLevel, final int status) {
        String action = "新增契约分红；用户名：%s；阶梯比例：%s；阶梯销量：%s,阶梯亏损：%s，最低有效人数：%s；状态：%s；";
        String statusCN;
        if (status == 1) {
            statusCN = "生效";
        }
        else if (status == 2) {
            statusCN = "待同意";
        }
        else {
            statusCN = "未知";
        }
        final Object[] values = { username, scaleLevel, salesLevel, lossLevel, userLevel, statusCN };
        action = String.format(action, values);
        return this.add(uEntity, request, action);
    }
}
