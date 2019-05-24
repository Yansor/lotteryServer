package admin.domains.jobs;

import admin.domains.content.dao.AdminUserCriticalLogDao;
import admin.domains.content.entity.AdminUser;
import admin.domains.content.entity.AdminUserAction;
import admin.domains.content.entity.AdminUserCriticalLog;
import admin.domains.pool.AdminDataFactory;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import javautils.date.Moment;
import javautils.http.HttpUtil;
import javautils.ip.IpUtil;
import javautils.math.MathUtil;
import javax.servlet.http.HttpServletRequest;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.PaymentBank;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserDailySettle;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
  private BlockingQueue<AdminUserCriticalLog> logQueue = new LinkedBlockingDeque();
  
  @Scheduled(cron="0/5 * * * * *")
  public void run()
  {
    if ((this.logQueue != null) && (this.logQueue.size() > 0)) {
      try
      {
        List<AdminUserCriticalLog> list = new LinkedList();
        this.logQueue.drainTo(list, 1000);
        this.adminUserCriticalLogDao.save(list);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  String ip2Address(String ip)
  {
    String address = "[未知地址]";
    try
    {
      String[] infos = IpUtil.find(ip);
      address = Arrays.toString(infos);
    }
    catch (Exception localException) {}
    return address;
  }
  
  boolean add(AdminUser uEntity, HttpServletRequest request, String action, String actionKey, String username)
  {
    AdminUserAction adminUserAction = this.adminDataFactory.getAdminUserAction(actionKey);
    User user = new User();
    if (StringUtils.isNotEmpty(username)) {
      user = this.UserDao.getByUsername(username);
    }
    int adminUserId = uEntity.getId();
    String ip = HttpUtil.getClientIpAddr(request);
    String address = ip2Address(ip);
    String time = new Moment().toSimpleTime();
    AdminUserCriticalLog entity = new AdminUserCriticalLog(adminUserId, user.getId(), adminUserAction.getId(), ip, address, action, time);
    String userAgent = request.getHeader("user-agent");
    entity.setUserAgent(userAgent);
    return this.logQueue.offer(entity);
  }
  
  public boolean logAddUser(AdminUser uEntity, HttpServletRequest request, String username, String relatedUsers, int type, double point, String actionKey)
  {
    String formatType = "未知";
    String _relatedInfo = "";
    if (type == 1) {
      formatType = "代理";
    }
    if (type == 2) {
      formatType = "玩家";
    }
    if (type == 3)
    {
      formatType = "关联账号";
      _relatedInfo = "关联会员：" + relatedUsers + "；";
    }
    String action = String.format("添加会员账号；用户名：%s；用户类型：%s；返点：%s；%s", new Object[] { username, formatType, Double.valueOf(point), _relatedInfo });
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean logLockUser(AdminUser uEntity, HttpServletRequest request, String username, int status, String message, String actionKey)
  {
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
    String action = String.format("冻结用户；用户名：%s；状态：%s；说明：%s", new Object[] { username, formatStatus, message });
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean logUnLockUser(AdminUser uEntity, HttpServletRequest request, String username, String actionKey)
  {
    String action = String.format("解冻用户；用户名：%s；", new Object[] { username });
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean logRecharge(AdminUser uEntity, HttpServletRequest request, String username, int type, int account, double amount, int limit, String remarks, String actionKey)
  {
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
    String formatLimit = limit == 1 ? "是" : "否";
    String action = String.format("充值；用户名：%s；充值类型：%s；账户类型：%s；金额：%s；是否需要消费：%s；备注：%s", new Object[] { username, formatType, formatAccount, Double.valueOf(amount), formatLimit, remarks });
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean logUserTransfer(AdminUser uEntity, HttpServletRequest request, String aUser, String bUser, double money, String remarks, String actionKey)
  {
    String moneyStr = MathUtil.doubleToStringDown(money, 1);
    String action = String.format("管理操作用户转账；待转会员：%s；目标会员：%s；金额：%s；备注：%s", new Object[] { aUser, bUser, moneyStr, remarks });
    return add(uEntity, request, action, actionKey, aUser);
  }
  
  public boolean logModLoginPwd(AdminUser uEntity, HttpServletRequest request, String username, String actionKey)
  {
    String action = String.format("修改用户登录密码；用户名：%s", new Object[] { username });
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean logModWithdrawPwd(AdminUser uEntity, HttpServletRequest request, String username, String actionKey)
  {
    String action = String.format("修改用户资金密码；用户名：%s", new Object[] { username });
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean logResetSecurity(AdminUser uEntity, HttpServletRequest request, String username, String actionKey)
  {
    String action = String.format("重置密保；用户名：%s", new Object[] { username });
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean logModPoint(AdminUser uEntity, HttpServletRequest request, String username, double point, String actionKey)
  {
    String action = String.format("修改返点；用户名：%s；返点：%s", new Object[] { username, Double.valueOf(point) });
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean logModExtraPoint(AdminUser uEntity, HttpServletRequest request, String username, double point, String actionKey)
  {
    String action = String.format("修改用户私返点数；用户名：%s；返点：%s", new Object[] { username, Double.valueOf(point) });
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean logModWithdrawName(AdminUser uEntity, HttpServletRequest request, String username, String withdrawName, String actionKey)
  {
    String action = String.format("修改用户绑定取款人；用户名：%s；绑定取款人：%s", new Object[] { username, withdrawName });
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean unbindGoogle(AdminUser uEntity, HttpServletRequest request, String username, String actionKey)
  {
    String action = String.format("解绑谷歌身份验证器；用户名：%s", new Object[] { username });
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean logAddUserCard(AdminUser uEntity, HttpServletRequest request, String username, int bankId, String bankBranch, String cardId, String actionKey)
  {
    PaymentBank paymentBank = this.lotteryDataFactory.getPaymentBank(bankId);
    String bankName = paymentBank.getName();
    String action = String.format("添加用户银行卡；用户名：%s；开户行：%s；支行：%s；卡号：%s", new Object[] { username, bankName, bankBranch, cardId });
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean logModUserCard(AdminUser uEntity, HttpServletRequest request, String username, int bankId, String bankBranch, String cardId, String actionKey)
  {
    PaymentBank paymentBank = this.lotteryDataFactory.getPaymentBank(bankId);
    String bankName = paymentBank.getName();
    String action = String.format("修改用户银行卡资料；用户名：%s；开户行：%s；支行：%s；卡号：%s", new Object[] { username, bankName, bankBranch, cardId });
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean logPatchRecharge(AdminUser uEntity, HttpServletRequest request, String billno, String payBillno, String remarks, String actionKey)
  {
    String action = String.format("充值漏单补单；订单号：%s；支付单号：%s；说明：%s", new Object[] { billno, payBillno, remarks });
    return add(uEntity, request, action, actionKey, null);
  }
  
  public boolean logDelDailySettle(AdminUser uEntity, HttpServletRequest request, String username, String actionKey)
  {
    String action = "删除团队日结；用户名：%s；";
    
    Object[] values = { username };
    action = String.format(action, values);
    
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean logEditDailySettleScale(AdminUser uEntity, HttpServletRequest request, UserDailySettle uds, double beforeScale, double afterScale, String actionKey)
  {
    String action = "编辑用户日结比例；ID：%s；编辑前比例：%s；编辑后比例：%s；";
    
    Object[] values = { Integer.valueOf(uds.getId()), Double.valueOf(beforeScale), Double.valueOf(afterScale) };
    action = String.format(action, values);
    
    return add(uEntity, request, action, actionKey, null);
  }
  
  public boolean logDelDividend(AdminUser uEntity, HttpServletRequest request, String username, String actionKey)
  {
    String action = "删除契约分红；用户名：%s；";
    
    Object[] values = { username };
    action = String.format(action, values);
    
    return add(uEntity, request, action, actionKey, username);
  }
  
  public boolean logChangeLine(AdminUser uEntity, HttpServletRequest request, String aUser, String bUser, String actionKey)
  {
    String action = String.format("线路转移；转移线路：%s；目标线路：%s", new Object[] { aUser, bUser });
    return add(uEntity, request, action, actionKey, aUser);
  }
  
  public boolean logChangeZhaoShang(AdminUser uEntity, HttpServletRequest request, String username, int isCJZhaoShang, String actionKey)
  {
    String action;
//    String action;
    if (isCJZhaoShang == 1) {
      action = String.format("超级招商转为招商；用户名：%s", new Object[] { username });
    } else {
      action = String.format("招商转为超级招商；用户名：%s", new Object[] { username });
    }
    return add(uEntity, request, action, actionKey, username);
  }
}
