package admin.domains.jobs;

import admin.domains.content.dao.AdminUserLogDao;
import admin.domains.content.entity.AdminUser;
import admin.domains.content.entity.AdminUserLog;
import java.math.BigDecimal;
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
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryPlayRules;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import lottery.domains.content.entity.PaymentBank;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserDailySettle;
import lottery.domains.content.entity.UserDividend;
import lottery.domains.content.entity.UserDividendBill;
import lottery.domains.content.entity.UserGameDividendBill;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AdminUserLogJob
{
  @Autowired
  private AdminUserLogDao adminUserLogDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  private BlockingQueue<AdminUserLog> logQueue = new LinkedBlockingDeque();
  
  @Scheduled(cron="0/5 * * * * *")
  public void run()
  {
    if ((this.logQueue != null) && (this.logQueue.size() > 0)) {
      try
      {
        List<AdminUserLog> list = new LinkedList();
        this.logQueue.drainTo(list, 1000);
        this.adminUserLogDao.save(list);
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
  
  boolean add(AdminUser uEntity, HttpServletRequest request, String action)
  {
    int userId = uEntity.getId();
    String ip = HttpUtil.getClientIpAddr(request);
    String address = ip2Address(ip);
    String time = new Moment().toSimpleTime();
    AdminUserLog entity = new AdminUserLog(userId, ip, address, action, time);
    String userAgent = request.getHeader("user-agent");
    entity.setUserAgent(userAgent);
    return this.logQueue.offer(entity);
  }
  
  public boolean logAddUser(AdminUser uEntity, HttpServletRequest request, String username, String relatedUsers, int type, double point)
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
    return add(uEntity, request, action);
  }
  
  public boolean logChangeLine(AdminUser uEntity, HttpServletRequest request, String aUser, String bUser)
  {
    String action = String.format("线路转移；转移线路：%s；目标线路：%s", new Object[] { aUser, bUser });
    return add(uEntity, request, action);
  }
  
  public boolean logRecharge(AdminUser uEntity, HttpServletRequest request, String username, int type, int account, double amount, int limit, String remarks)
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
    return add(uEntity, request, action);
  }
  
  public boolean logLockUser(AdminUser uEntity, HttpServletRequest request, String username, int status, String message)
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
    String action = String.format("冻结用户；用户名：%s；冻结状态：%s；冻结说明：%s", new Object[] { username, formatStatus, message });
    return add(uEntity, request, action);
  }
  
  public boolean logLockTeam(AdminUser uEntity, HttpServletRequest request, String username, String message)
  {
    String action = String.format("冻结团队；用户名：%s；冻结说明：%s", new Object[] { username, message });
    return add(uEntity, request, action);
  }
  
  public boolean logUnLockTeam(AdminUser uEntity, HttpServletRequest request, String username, String message)
  {
    String action = String.format("解冻团队；用户名：%s；解冻说明：%s", new Object[] { username, message });
    return add(uEntity, request, action);
  }
  
  public boolean prohibitTeamWithdraw(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("禁止团队取款；用户名：%s；", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean allowTeamWithdraw(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("开启团队取款；用户名：%s；", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean allowTeamTransfers(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("开启团队上下级转账；用户名：%s；", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean allowTeamPlatformTransfers(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("开启团队转账；用户名：%s；", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean prohibitTeamTransfers(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("禁止团队上下级转账；用户名：%s；", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean prohibitTeamPlatformTransfers(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("禁止团队转账；用户名：%s；", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean logUserTransfer(AdminUser uEntity, HttpServletRequest request, String aUser, String bUser, double money, String remarks)
  {
    String moneyStr = MathUtil.doubleToStringDown(money, 1);
    String action = String.format("管理操作用户转账；待转会员：%s；目标会员：%s；金额：%s；备注：%s", new Object[] { aUser, bUser, moneyStr, remarks });
    return add(uEntity, request, action);
  }
  
  public boolean logUnlockUser(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("解冻用户；用户名：%s", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean logModLoginPwd(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("修改用户登录密码；用户名：%s", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean logModWithdrawPwd(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("修改用户资金密码；用户名：%s", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean logResetEmail(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("重置用户绑定邮箱；用户名：%s", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean logResetSecurity(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("重置密保；用户名：%s", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean logModPoint(AdminUser uEntity, HttpServletRequest request, String username, double point)
  {
    String action = String.format("修改返点；用户名：%s；返点：%s", new Object[] { username, Double.valueOf(point) });
    return add(uEntity, request, action);
  }
  
  public boolean logDownPoint(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("统一降点；用户名：%s", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean logModExtraPoint(AdminUser uEntity, HttpServletRequest request, String username, double point)
  {
    String action = String.format("修改用户私返点数；用户名：%s；返点：%s", new Object[] { username, Double.valueOf(point) });
    return add(uEntity, request, action);
  }
  
  public boolean logModBStatus(AdminUser uEntity, HttpServletRequest request, String username, int status, String message)
  {
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
    String action = String.format("修改用户投注权限；用户名：%s；投注权限：%s；说明：%s", new Object[] { username, formatStatus, message });
    return add(uEntity, request, action);
  }
  
  public boolean logRecoverUser(AdminUser uEntity, HttpServletRequest request, User user)
  {
    String action = "回收账号；用户名：%s；主账户：%s；彩票账户：%s；百家乐账户：%s；";
    String username = user.getUsername();
    String totalMoney = new BigDecimal(user.getTotalMoney()).setScale(4, 5).toString();
    String lotteryMoney = new BigDecimal(user.getLotteryMoney()).setScale(4, 5).toString();
    String baccaratMoney = new BigDecimal(user.getBaccaratMoney()).setScale(4, 5).toString();
    
    Object[] values = { username, totalMoney, lotteryMoney, baccaratMoney };
    action = String.format(action, values);
    
    return add(uEntity, request, action);
  }
  
  public boolean logRecoverUser(AdminUser uEntity, HttpServletRequest request, String username, int amount1, int amount2, int amount3)
  {
    String amount = amount1 + "," + amount2 + "," + amount3;
    String action = String.format("修改用户配额；用户名：%s；配额数量：[%s]", new Object[] { username, amount });
    return add(uEntity, request, action);
  }
  
  public boolean logModEqualCode(AdminUser uEntity, HttpServletRequest request, String username, int status)
  {
    String formatStatus = status == 1 ? "允许" : "不允许";
    String action = String.format("修改用户同级开号权限；用户名：%s；状态：%s", new Object[] { username, formatStatus });
    return add(uEntity, request, action);
  }
  
  public boolean logModTransfers(AdminUser uEntity, HttpServletRequest request, String username, int status)
  {
    String formatStatus = status == 1 ? "允许" : "不允许";
    String action = String.format("修改用户上下级转账权限；用户名：%s；状态：%s", new Object[] { username, formatStatus });
    return add(uEntity, request, action);
  }
  
  public boolean logModWithdraw(AdminUser uEntity, HttpServletRequest request, String username, int status)
  {
    String formatStatus = status == 1 ? "允许" : "不允许";
    String action = String.format("修改用户取款权限；用户名：%s；状态：%s", new Object[] { username, formatStatus });
    return add(uEntity, request, action);
  }
  
  public boolean logModPlatformTransfers(AdminUser uEntity, HttpServletRequest request, String username, int status)
  {
    String formatStatus = status == 1 ? "允许" : "不允许";
    String action = String.format("修改用户转账权限；用户名：%s；状态：%s", new Object[] { username, formatStatus });
    return add(uEntity, request, action);
  }
  
  public boolean logModWithdrawName(AdminUser uEntity, HttpServletRequest request, String username, String withdrawName)
  {
    String action = String.format("修改用户绑定取款人；用户名：%s；绑定取款人：%s", new Object[] { username, withdrawName });
    return add(uEntity, request, action);
  }
  
  public boolean logModEmail(AdminUser uEntity, HttpServletRequest request, String username, String email)
  {
    String action = String.format("修改用户绑定邮箱；用户名：%s；绑定邮箱：%s", new Object[] { username, email });
    return add(uEntity, request, action);
  }
  
  public boolean logResetLimit(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("清空用户提款消费量；用户名：%s", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean logChangeProxy(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("玩家转代理；用户名：%s", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean logChangeZhaoShang(AdminUser uEntity, HttpServletRequest request, String username, int isCJZhaoShang)
  {
    String action;
    if (isCJZhaoShang == 1) {
      action = String.format("超级招商转为招商；用户名：%s", new Object[] { username });
    } else {
      action = String.format("招商转为超级招商；用户名：%s", new Object[] { username });
    }
    return add(uEntity, request, action);
  }
  
  public boolean unbindGoogle(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("解绑谷歌身份验证器；用户名：%s", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean resetLockTime(AdminUser uEntity, HttpServletRequest request, String username)
  {
    String action = String.format("清空账户时间锁；用户名：%s", new Object[] { username });
    return add(uEntity, request, action);
  }
  
  public boolean logAddUserCard(AdminUser uEntity, HttpServletRequest request, String username, int bankId, String bankBranch, String cardId)
  {
    PaymentBank paymentBank = this.lotteryDataFactory.getPaymentBank(bankId);
    String bankName = paymentBank.getName();
    String action = String.format("添加用户银行卡；用户名：%s；开户行：%s；支行：%s；卡号：%s", new Object[] { username, bankName, bankBranch, cardId });
    return add(uEntity, request, action);
  }
  
  public boolean logModUserCard(AdminUser uEntity, HttpServletRequest request, String username, int bankId, String bankBranch, String cardId)
  {
    PaymentBank paymentBank = this.lotteryDataFactory.getPaymentBank(bankId);
    String bankName = paymentBank.getName();
    String action = String.format("修改用户银行卡资料；用户名：%s；开户行：%s；支行：%s；卡号：%s", new Object[] { username, bankName, bankBranch, cardId });
    return add(uEntity, request, action);
  }
  
  public boolean logPatchRecharge(AdminUser uEntity, HttpServletRequest request, String billno, String payBillno, String remarks)
  {
    String action = String.format("充值漏单补单；订单号：%s；支付单号：%s；说明：%s", new Object[] { billno, payBillno, remarks });
    return add(uEntity, request, action);
  }
  
  public boolean logCancelRecharge(AdminUser uEntity, HttpServletRequest request, String billno)
  {
    String action = String.format("充值订单撤销；订单号：%s", new Object[] { billno });
    return add(uEntity, request, action);
  }
  
  public boolean logCheckWithdraw(AdminUser uEntity, HttpServletRequest request, int id, int status)
  {
    String formatStatus = "未知";
    if (status == 1) {
      formatStatus = "已通过";
    }
    if (status == -1) {
      formatStatus = "未通过";
    }
    String action = String.format("审核用户提现；订单ID：%s；审核结果：%s", new Object[] { Integer.valueOf(id), formatStatus });
    return add(uEntity, request, action);
  }
  
  public boolean logManualPay(AdminUser uEntity, HttpServletRequest request, int id, String payBillno, String remarks)
  {
    String action = String.format("使用手动出款；订单ID：%s；支付单号：%s；备注说明：%s", new Object[] { Integer.valueOf(id), payBillno, remarks });
    return add(uEntity, request, action);
  }
  
  public boolean logAPIPay(AdminUser uEntity, HttpServletRequest request, int id, PaymentChannel paymentChannel)
  {
    String action = String.format("使用API代付；订单ID：%s；第三方：%s", new Object[] { Integer.valueOf(id), paymentChannel.getName() });
    return add(uEntity, request, action);
  }
  
  public boolean logRefuseWithdraw(AdminUser uEntity, HttpServletRequest request, int id, String reason, String remarks)
  {
    String action = String.format("拒绝支付用户提现；订单ID：%s；拒绝原因：%s；备注说明：%s", new Object[] { Integer.valueOf(id), reason, remarks });
    return add(uEntity, request, action);
  }
  
  public boolean reviewedFail(AdminUser uEntity, HttpServletRequest request, int id, String remarks)
  {
    String action = String.format("用户提现审核失败；订单ID：%s；备注说明：%s", new Object[] { Integer.valueOf(id), remarks });
    return add(uEntity, request, action);
  }
  
  public boolean logWithdrawFailure(AdminUser uEntity, HttpServletRequest request, int id, String remarks)
  {
    String action = String.format("确认用户提现失败；订单ID：%s；备注说明：%s", new Object[] { Integer.valueOf(id), remarks });
    return add(uEntity, request, action);
  }
  
  public boolean logCompleteRemitWithdraw(AdminUser uEntity, HttpServletRequest request, int id)
  {
    String action = String.format("确认用户提现到账；订单ID：%s；", new Object[] { Integer.valueOf(id) });
    return add(uEntity, request, action);
  }
  
  public boolean logLockWithdraw(AdminUser uEntity, HttpServletRequest request, int id)
  {
    String action = String.format("锁定用户提现；订单ID：%s；", new Object[] { Integer.valueOf(id) });
    return add(uEntity, request, action);
  }
  
  public boolean logUnLockWithdraw(AdminUser uEntity, HttpServletRequest request, int id)
  {
    String action = String.format("解锁用户提现；订单ID：%s；", new Object[] { Integer.valueOf(id) });
    return add(uEntity, request, action);
  }
  
  public boolean logCancelOrder(AdminUser uEntity, HttpServletRequest request, int id)
  {
    String action = String.format("撤销用户投注订单；订单ID：%s", new Object[] { Integer.valueOf(id) });
    return add(uEntity, request, action);
  }
  
  public boolean logBatchCancelOrder(AdminUser uEntity, HttpServletRequest request, int lotteryId, Integer ruleId, String expect, String match)
  {
    Lottery lottery = this.lotteryDataFactory.getLottery(lotteryId);
    if (lottery != null)
    {
      String formatMethod = "全部玩法";
      if (ruleId != null)
      {
        LotteryPlayRules rules = this.lotteryDataFactory.getLotteryPlayRules(ruleId.intValue());
        if (rules != null)
        {
          LotteryPlayRulesGroup group = this.lotteryDataFactory.getLotteryPlayRulesGroup(rules.getGroupId());
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
      String action = String.format("批量撤销用户订单；彩票类型：%s；玩法规则：%s；投注期号：%s", new Object[] { lottery.getShowName(), formatMethod, formatExpect });
      return add(uEntity, request, action);
    }
    return false;
  }
  
  public boolean logAgreeDividend(AdminUser uEntity, HttpServletRequest request, String username, UserDividendBill userDividendBill, String remarks)
  {
    String action = "同意发放彩票分红；用户名：%s；周期：%s~%s；备注：%s；";
    String startDate = userDividendBill.getIndicateStartDate();
    String endDate = userDividendBill.getIndicateEndDate();
    
    Object[] values = { username, startDate, endDate, remarks == null ? "" : remarks };
    action = String.format(action, values);
    
    return add(uEntity, request, action);
  }
  
  public boolean logDenyDividend(AdminUser uEntity, HttpServletRequest request, String username, UserDividendBill userDividendBill, String remarks)
  {
    String action = "拒绝发放彩票分红；用户名：%s；周期：%s~%s；拒绝前用户金额：%s；拒绝后用户金额：%s；备注：%s；";
    String startDate = userDividendBill.getIndicateStartDate();
    String endDate = userDividendBill.getIndicateEndDate();
    
    Object[] values = { username, startDate, endDate, remarks == null ? "" : remarks };
    action = String.format(action, values);
    
    return add(uEntity, request, action);
  }
  
  public boolean logDelDividend(AdminUser uEntity, HttpServletRequest request, String username, UserDividendBill userDividendBill, String remarks)
  {
    String action = "删除彩票分红数据；用户名：%s；周期：%s~%s；用户金额：%s；备注：%s；";
    String startDate = userDividendBill.getIndicateStartDate();
    String endDate = userDividendBill.getIndicateEndDate();
    String userAmount = new BigDecimal(userDividendBill.getUserAmount()).setScale(4, 5).toString();
    
    Object[] values = { username, startDate, endDate, userAmount, remarks == null ? "" : remarks };
    action = String.format(action, values);
    
    return add(uEntity, request, action);
  }
  
  public boolean logAgreeGameDividend(AdminUser uEntity, HttpServletRequest request, String username, UserGameDividendBill userDividendBill, double userAmount, String remarks)
  {
    String action = "同意发放老虎机真人体育分红；用户名：%s；周期：%s~%s；同意前用户金额：%s；同意后用户金额：%s；备注：%s；";
    String startDate = userDividendBill.getIndicateStartDate();
    String endDate = userDividendBill.getIndicateEndDate();
    String beforeUserAmount = new BigDecimal(userDividendBill.getUserAmount()).setScale(4, 5).toString();
    String afterUserAmount = new BigDecimal(userAmount).setScale(4, 5).toString();
    
    Object[] values = { username, startDate, endDate, beforeUserAmount, afterUserAmount, remarks == null ? "" : remarks };
    action = String.format(action, values);
    
    return add(uEntity, request, action);
  }
  
  public boolean logDenyGameDividend(AdminUser uEntity, HttpServletRequest request, String username, UserGameDividendBill userDividendBill, double userAmount, String remarks)
  {
    String action = "拒绝发放老虎机真人体育分红；用户名：%s；周期：%s~%s；拒绝前用户金额：%s；拒绝后用户金额：%s；备注：%s；";
    String startDate = userDividendBill.getIndicateStartDate();
    String endDate = userDividendBill.getIndicateEndDate();
    String beforeUserAmount = new BigDecimal(userDividendBill.getUserAmount()).setScale(4, 5).toString();
    String afterUserAmount = new BigDecimal(userAmount).setScale(4, 5).toString();
    
    Object[] values = { username, startDate, endDate, beforeUserAmount, afterUserAmount, remarks == null ? "" : remarks };
    action = String.format(action, values);
    
    return add(uEntity, request, action);
  }
  
  public boolean logDelGameDividend(AdminUser uEntity, HttpServletRequest request, String username, UserGameDividendBill userDividendBill, String remarks)
  {
    String action = "删除老虎机真人体育分红数据；用户名：%s；周期：%s~%s；用户金额：%s；备注：%s；";
    String startDate = userDividendBill.getIndicateStartDate();
    String endDate = userDividendBill.getIndicateEndDate();
    String userAmount = new BigDecimal(userDividendBill.getUserAmount()).setScale(4, 5).toString();
    
    Object[] values = { username, startDate, endDate, userAmount, remarks == null ? "" : remarks };
    action = String.format(action, values);
    
    return add(uEntity, request, action);
  }
  
  public boolean logResetDividend(AdminUser uEntity, HttpServletRequest request, String username, UserDividendBill userDividendBill, String remarks)
  {
    String action = "清零分红数据；用户名：%s；周期：%s~%s；清空金额：%s；备注：%s；";
    String startDate = userDividendBill.getIndicateStartDate();
    String endDate = userDividendBill.getIndicateEndDate();
    String availableAmount = new BigDecimal(userDividendBill.getAvailableAmount()).setScale(4, 5).toString();
    
    Object[] values = { username, startDate, endDate, availableAmount, remarks == null ? "" : remarks };
    action = String.format(action, values);
    
    return add(uEntity, request, action);
  }
  
  public boolean logAddGame(AdminUser uEntity, HttpServletRequest request, String gameName)
  {
    String action = String.format("添加第三方新游戏；游戏名：%s；", new Object[] { gameName });
    return add(uEntity, request, action);
  }
  
  public boolean logUpdateGame(AdminUser uEntity, HttpServletRequest request, String gameName)
  {
    String action = String.format("修改游戏；游戏名：%s；", new Object[] { gameName });
    return add(uEntity, request, action);
  }
  
  public boolean logDelGame(AdminUser uEntity, HttpServletRequest request, String gameName)
  {
    String action = String.format("删除第三方游戏；游戏名：%s；", new Object[] { gameName });
    return add(uEntity, request, action);
  }
  
  public boolean logUpdateGameStatus(AdminUser uEntity, HttpServletRequest request, String gameName, int status)
  {
    String statusCN = "未知";
    if (status == 0) {
      statusCN = "禁用";
    } else if (status == 1) {
      statusCN = "启用";
    }
    String action = String.format("修改第三方游戏状态；游戏名：%s；状态：%s；", new Object[] { gameName, statusCN });
    return add(uEntity, request, action);
  }
  
  public boolean logUpdateGameDisplay(AdminUser uEntity, HttpServletRequest request, String gameName, int display)
  {
    String displayCN = "未知";
    if (display == 0) {
      displayCN = "不显示";
    } else if (display == 1) {
      displayCN = "显示";
    }
    String action = String.format("修改第三方游戏显示状态；游戏名：%s；是否显示：%s；", new Object[] { gameName, displayCN });
    return add(uEntity, request, action);
  }
  
  public boolean logPlatformModStatus(AdminUser uEntity, HttpServletRequest request, int id, int status)
  {
    String statusCN;
    if (status == 1) {
      statusCN = "启用";
    } else {
      statusCN = "禁用";
    }
    String action = String.format("修改第三方游戏平台状态；平台ID：%s；状态：%s；", new Object[] { Integer.valueOf(id), statusCN });
    return add(uEntity, request, action);
  }
  
  public boolean logLockHighPrize(AdminUser uEntity, HttpServletRequest request, int id)
  {
    String action = String.format("锁定大额中奖记录；记录ID：%s；", new Object[] { Integer.valueOf(id) });
    return add(uEntity, request, action);
  }
  
  public boolean logUnLockHighPrize(AdminUser uEntity, HttpServletRequest request, int id)
  {
    String action = String.format("解锁大额中奖记录；记录ID：%s；", new Object[] { Integer.valueOf(id) });
    return add(uEntity, request, action);
  }
  
  public boolean logConfirmHighPrize(AdminUser uEntity, HttpServletRequest request, int id)
  {
    String action = String.format("确认大额中奖记录；记录ID：%s；", new Object[] { Integer.valueOf(id) });
    return add(uEntity, request, action);
  }
  
  public boolean logEditRedPacketRainConfig(AdminUser uEntity, HttpServletRequest request)
  {
    String action = String.format("修改红包雨配置；", new Object[0]);
    return add(uEntity, request, action);
  }
  
  public boolean logUpdateStatusRedPacketRain(AdminUser uEntity, HttpServletRequest request, int status)
  {
    String statusCN;
    if (status == 1) {
      statusCN = "启用";
    } else {
      statusCN = "禁用";
    }
    String action = String.format(statusCN + "红包雨活动", new Object[0]);
    return add(uEntity, request, action);
  }
  
  public boolean logEditFirstRechargeConfig(AdminUser uEntity, HttpServletRequest request, String rules)
  {
    String action = String.format("修改首充活动配置；规则：%s", new Object[] { rules });
    return add(uEntity, request, action);
  }
  
  public boolean logUpdateStatusFirstRecharge(AdminUser uEntity, HttpServletRequest request, int status)
  {
    String statusCN;
    if (status == 1) {
      statusCN = "启用";
    } else {
      statusCN = "禁用";
    }
    String action = String.format(statusCN + "首充活动", new Object[0]);
    return add(uEntity, request, action);
  }
  
  public boolean logModifyRelatedUpper(AdminUser uEntity, HttpServletRequest request, String username, String relatedUpUser, double relatedPoint, String remarks)
  {
    String action = String.format("修改关联上级；用户名：%s；关联上级：%s；关联返点：%s；备注：%s；", new Object[] { username, relatedUpUser, Double.valueOf(relatedPoint), remarks });
    return add(uEntity, request, action);
  }
  
  public boolean logReliveRelatedUpper(AdminUser uEntity, HttpServletRequest request, String username, String remarks)
  {
    String action = String.format("解除关联上级；用户名：%s；备注：%s；", new Object[] { username, remarks });
    return add(uEntity, request, action);
  }
  
  public boolean logModifyUpdateRelatedUsers(AdminUser uEntity, HttpServletRequest request, String username, String relatedUsers, String remarks)
  {
    String action = String.format("修改关联会员；用户名：%s；关联会员：%s；备注：%s；", new Object[] { username, relatedUsers, remarks });
    return add(uEntity, request, action);
  }
  
  public boolean logDelUserCardUnbindRecord(AdminUser uEntity, HttpServletRequest request, String cardId, String remarks)
  {
    String action = String.format("删除银行卡解锁记录；银行卡：%s；备注：%s；", new Object[] { cardId, remarks });
    return add(uEntity, request, action);
  }
  
  public boolean logModifyRefExpect(AdminUser uEntity, HttpServletRequest request, String lottery, int times)
  {
    String action = String.format("增减彩票期号；彩种：%s；增减期数：%s；", new Object[] { lottery, Integer.valueOf(times) });
    return add(uEntity, request, action);
  }
  
  public boolean logAddPaymenChannel(AdminUser uEntity, HttpServletRequest request, String name)
  {
    String action = String.format("增加充值通道账号；名称：%s；；", new Object[] { name });
    return add(uEntity, request, action);
  }
  
  public boolean logEditPaymenChannel(AdminUser uEntity, HttpServletRequest request, String name)
  {
    String action = String.format("编辑充值通道账号；名称：%s；", new Object[] { name });
    return add(uEntity, request, action);
  }
  
  public boolean logEditPaymenChannelStatus(AdminUser uEntity, HttpServletRequest request, int id, int status)
  {
    String statusStr = status == 0 ? "启用" : "禁用";
    String action = String.format("修改充值通道账号状态；ID：%s；修改为状态：%s；", new Object[] { Integer.valueOf(id), statusStr });
    return add(uEntity, request, action);
  }
  
  public boolean logDeletePaymenChannel(AdminUser uEntity, HttpServletRequest request, int id)
  {
    String action = String.format("删除充值通道账号；ID：%s；", new Object[] { Integer.valueOf(id) });
    return add(uEntity, request, action);
  }
  
  public boolean logEditDailySettle(AdminUser uEntity, HttpServletRequest request, UserDailySettle uds, String scale, String sales, String loss, String minValidUser)
  {
    String action = "编辑用户日结配置；ID：%s；用户名：%s；编辑前比例：%s；编辑后比例：%s；编辑前销量：%s；编辑后销量：%s；编辑前亏损：%s；编辑后亏损：%s；编辑前有效人数：%s；编辑后有效人数：%s；";
    
    UserVO user = this.lotteryDataFactory.getUser(uds.getUserId());
    String username;
    if (user != null) {
      username = user.getUsername();
    } else {
      username = "未知";
    }
    Object[] values = { Integer.valueOf(uds.getId()), username, uds.getScaleLevel(), scale, uds.getSalesLevel(), sales, uds.getLossLevel(), loss, uds.getUserLevel(), minValidUser };
    action = String.format(action, values);
    
    return add(uEntity, request, action);
  }
  
  public boolean logAddDailySettle(AdminUser uEntity, HttpServletRequest request, String username, String scaleLevel, String salesLevel, String lossLevel, String minValidUser, int status)
  {
    String action = "新增契约日结；用户名：%s；销量：%s；亏损：%s；比例：%s；有效人数：%s；状态：%s；";
    String statusCN;
    if (status == 1)
    {
      statusCN = "生效";
    }
    else
    {
      if (status == 2) {
        statusCN = "待同意";
      } else {
        statusCN = "未知";
      }
    }
    Object[] values = { username, salesLevel, lossLevel, scaleLevel, minValidUser, statusCN };
    action = String.format(action, values);
    
    return add(uEntity, request, action);
  }
  
  public boolean logEditDividend(AdminUser uEntity, HttpServletRequest request, UserDividend ud, String scaleLevel, String lossLevel, String salesLevel, String userLevel)
  {
    String action = "编辑用户分红配置；ID：%s；用户名：%s；编辑前比例：%s；编辑后比例：%s；编辑前最低有效人数：%s；编辑后最低有效人数：%s； 编辑前销量：%s; 编辑后销量：%s; 编辑前亏损：%s；编辑后亏损：%s；";
    
    UserVO user = this.lotteryDataFactory.getUser(ud.getUserId());
    String username;
    if (user != null) {
      username = user.getUsername();
    } else {
      username = "未知";
    }
    Object[] values = { Integer.valueOf(ud.getId()), username, ud.getScaleLevel(), scaleLevel, ud.getUserLevel(), userLevel, ud.getSalesLevel(), salesLevel, ud.getLossLevel(), lossLevel };
    action = String.format(action, values);
    
    return add(uEntity, request, action);
  }
  
  public boolean logAddDividend(AdminUser uEntity, HttpServletRequest request, String username, String scaleLevel, String lossLevel, String salesLevel, String userLevel, int status)
  {
    String action = "新增契约分红；用户名：%s；阶梯比例：%s；阶梯销量：%s,阶梯亏损：%s，最低有效人数：%s；状态：%s；";
    String statusCN;
    if (status == 1)
    {
      statusCN = "生效";
    }
    else
    {
      if (status == 2) {
        statusCN = "待同意";
      } else {
        statusCN = "未知";
      }
    }
    Object[] values = { username, scaleLevel, salesLevel, lossLevel, userLevel, statusCN };
    action = String.format(action, values);
    
    return add(uEntity, request, action);
  }
}
