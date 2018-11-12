package admin.domains.jobs;

import lottery.domains.content.entity.UserDailySettle;
import lottery.domains.content.entity.PaymentBank;
import javautils.math.MathUtil;
import admin.domains.content.entity.AdminUserAction;
import javautils.date.Moment;
import javautils.http.HttpUtil;
import org.apache.commons.lang.StringUtils;
import lottery.domains.content.entity.User;
import javax.servlet.http.HttpServletRequest;
import admin.domains.content.entity.AdminUser;
import java.util.Arrays;
import javautils.ip.IpUtil;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.List;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;
import admin.domains.content.entity.AdminUserCriticalLog;
import java.util.concurrent.BlockingQueue;
import admin.domains.pool.AdminDataFactory;
import lottery.domains.content.dao.UserDao;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.dao.AdminUserCriticalLogDao;
import org.springframework.stereotype.Component;

@Component
public class AdminUserCriticalLogJob
{
    @Autowired
    private AdminUserCriticalLogDao adminUserCriticalLogDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    @Autowired
    private UserDao UserDao;
    @Autowired
    private AdminDataFactory adminDataFactory;
    private BlockingQueue<AdminUserCriticalLog> logQueue;
    
    public AdminUserCriticalLogJob() {
        this.logQueue = new LinkedBlockingDeque<AdminUserCriticalLog>();
    }
    
    @Scheduled(cron = "0/5 * * * * *")
    public void run() {
        if (this.logQueue != null && this.logQueue.size() > 0) {
            try {
                final List<AdminUserCriticalLog> list = new LinkedList<AdminUserCriticalLog>();
                this.logQueue.drainTo(list, 1000);
                this.adminUserCriticalLogDao.save(list);
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
    
    boolean add(final AdminUser uEntity, final HttpServletRequest request, final String action, final String actionKey, final String username) {
        final AdminUserAction adminUserAction = this.adminDataFactory.getAdminUserAction(actionKey);
        User user = new User();
        if (StringUtils.isNotEmpty(username)) {
            user = this.UserDao.getByUsername(username);
        }
        final int adminUserId = uEntity.getId();
        final String ip = HttpUtil.getClientIpAddr(request);
        final String address = this.ip2Address(ip);
        final String time = new Moment().toSimpleTime();
        final AdminUserCriticalLog entity = new AdminUserCriticalLog(adminUserId, user.getId(), adminUserAction.getId(), ip, address, action, time);
        final String userAgent = request.getHeader("user-agent");
        entity.setUserAgent(userAgent);
        return this.logQueue.offer(entity);
    }
    
    public boolean logAddUser(final AdminUser uEntity, final HttpServletRequest request, final String username, final String relatedUsers, final int type, final double point, final String actionKey) {
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
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean logLockUser(final AdminUser uEntity, final HttpServletRequest request, final String username, final int status, final String message, final String actionKey) {
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
        final String action = String.format("冻结用户；用户名：%s；状态：%s；说明：%s", username, formatStatus, message);
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean logUnLockUser(final AdminUser uEntity, final HttpServletRequest request, final String username, final String actionKey) {
        final String action = String.format("解冻用户；用户名：%s；", username);
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean logRecharge(final AdminUser uEntity, final HttpServletRequest request, final String username, final int type, final int account, final double amount, final int limit, final String remarks, final String actionKey) {
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
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean logUserTransfer(final AdminUser uEntity, final HttpServletRequest request, final String aUser, final String bUser, final double money, final String remarks, final String actionKey) {
        final String moneyStr = MathUtil.doubleToStringDown(money, 1);
        final String action = String.format("管理操作用户转账；待转会员：%s；目标会员：%s；金额：%s；备注：%s", aUser, bUser, moneyStr, remarks);
        return this.add(uEntity, request, action, actionKey, aUser);
    }
    
    public boolean logModLoginPwd(final AdminUser uEntity, final HttpServletRequest request, final String username, final String actionKey) {
        final String action = String.format("修改用户登录密码；用户名：%s", username);
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean logModWithdrawPwd(final AdminUser uEntity, final HttpServletRequest request, final String username, final String actionKey) {
        final String action = String.format("修改用户资金密码；用户名：%s", username);
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean logResetSecurity(final AdminUser uEntity, final HttpServletRequest request, final String username, final String actionKey) {
        final String action = String.format("重置密保；用户名：%s", username);
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean logModPoint(final AdminUser uEntity, final HttpServletRequest request, final String username, final double point, final String actionKey) {
        final String action = String.format("修改返点；用户名：%s；返点：%s", username, point);
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean logModExtraPoint(final AdminUser uEntity, final HttpServletRequest request, final String username, final double point, final String actionKey) {
        final String action = String.format("修改用户私返点数；用户名：%s；返点：%s", username, point);
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean logModWithdrawName(final AdminUser uEntity, final HttpServletRequest request, final String username, final String withdrawName, final String actionKey) {
        final String action = String.format("修改用户绑定取款人；用户名：%s；绑定取款人：%s", username, withdrawName);
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean unbindGoogle(final AdminUser uEntity, final HttpServletRequest request, final String username, final String actionKey) {
        final String action = String.format("解绑谷歌身份验证器；用户名：%s", username);
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean logAddUserCard(final AdminUser uEntity, final HttpServletRequest request, final String username, final int bankId, final String bankBranch, final String cardId, final String actionKey) {
        final PaymentBank paymentBank = this.lotteryDataFactory.getPaymentBank(bankId);
        final String bankName = paymentBank.getName();
        final String action = String.format("添加用户银行卡；用户名：%s；开户行：%s；支行：%s；卡号：%s", username, bankName, bankBranch, cardId);
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean logModUserCard(final AdminUser uEntity, final HttpServletRequest request, final String username, final int bankId, final String bankBranch, final String cardId, final String actionKey) {
        final PaymentBank paymentBank = this.lotteryDataFactory.getPaymentBank(bankId);
        final String bankName = paymentBank.getName();
        final String action = String.format("修改用户银行卡资料；用户名：%s；开户行：%s；支行：%s；卡号：%s", username, bankName, bankBranch, cardId);
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean logPatchRecharge(final AdminUser uEntity, final HttpServletRequest request, final String billno, final String payBillno, final String remarks, final String actionKey) {
        final String action = String.format("充值漏单补单；订单号：%s；支付单号：%s；说明：%s", billno, payBillno, remarks);
        return this.add(uEntity, request, action, actionKey, null);
    }
    
    public boolean logDelDailySettle(final AdminUser uEntity, final HttpServletRequest request, final String username, final String actionKey) {
        String action = "删除团队日结；用户名：%s；";
        final Object[] values = { username };
        action = String.format(action, values);
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean logEditDailySettleScale(final AdminUser uEntity, final HttpServletRequest request, final UserDailySettle uds, final double beforeScale, final double afterScale, final String actionKey) {
        String action = "编辑用户日结比例；ID：%s；编辑前比例：%s；编辑后比例：%s；";
        final Object[] values = { uds.getId(), beforeScale, afterScale };
        action = String.format(action, values);
        return this.add(uEntity, request, action, actionKey, null);
    }
    
    public boolean logDelDividend(final AdminUser uEntity, final HttpServletRequest request, final String username, final String actionKey) {
        String action = "删除契约分红；用户名：%s；";
        final Object[] values = { username };
        action = String.format(action, values);
        return this.add(uEntity, request, action, actionKey, username);
    }
    
    public boolean logChangeLine(final AdminUser uEntity, final HttpServletRequest request, final String aUser, final String bUser, final String actionKey) {
        final String action = String.format("线路转移；转移线路：%s；目标线路：%s", aUser, bUser);
        return this.add(uEntity, request, action, actionKey, aUser);
    }
    
    public boolean logChangeZhaoShang(final AdminUser uEntity, final HttpServletRequest request, final String username, final int isCJZhaoShang, final String actionKey) {
        String action;
        if (isCJZhaoShang == 1) {
            action = String.format("超级招商转为招商；用户名：%s", username);
        }
        else {
            action = String.format("招商转为超级招商；用户名：%s", username);
        }
        return this.add(uEntity, request, action, actionKey, username);
    }
}
