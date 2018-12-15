package lottery.domains.content.biz.impl;

import javautils.StringUtil;
import javautils.date.Moment;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.dao.UserSysMessageDao;
import lottery.domains.content.entity.UserSysMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSysMessageServiceImpl
  implements UserSysMessageService
{
  @Autowired
  private UserSysMessageDao uSysMessageDao;
  
  public boolean addTransToUser(int userId, double amount)
  {
    int type = 1;
    String content = String.format("上级代理已为您充值%s元。", new Object[] { Double.valueOf(amount) });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addFirstRecharge(int userId, double rechargeAmount, double amount)
  {
    int type = 0;
    String content = String.format("您已通过首充活动充值%s元，系统自动赠送%s元。", new Object[] { Double.valueOf(rechargeAmount), Double.valueOf(amount) });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addSysRecharge(int userId, double amount, String remarks)
  {
    int type = 1;
    if (StringUtil.isNotNull(remarks)) {
      remarks = "备注：" + remarks;
    }
    String content = String.format("管理员已为您充值%s元。%s", new Object[] { Double.valueOf(amount), remarks });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addOnlineRecharge(int userId, double amount)
  {
    int type = 1;
    String content = String.format("您已通过在线支付充值%s元。", new Object[] { Double.valueOf(amount) });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addTransfersRecharge(int userId, double amount)
  {
    int type = 1;
    String content = String.format("您已通过网银转账充值%s元。", new Object[] { Double.valueOf(amount) });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addConfirmWithdraw(int userId, double amount, double recAmount)
  {
    int type = 2;
    String content = String.format("您申请提现%s元已支付，实际到账%s元，请注意查收。", new Object[] { Double.valueOf(amount), Double.valueOf(recAmount) });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addRefuseWithdraw(int userId, double amount)
  {
    int type = 2;
    String content = String.format("您申请提现%s元已被拒绝，金额已返还！", new Object[] { Double.valueOf(amount) });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addRefuse(int userId, double amount)
  {
    int type = 2;
    String content = String.format("您申请提现%s元已失败，金额已返还！", new Object[] { Double.valueOf(amount) });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addShFail(int userId, double amount)
  {
    int type = 2;
    String content = String.format("您申请提现%s元审核未通过，金额已返还！", new Object[] { Double.valueOf(amount) });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addActivityBind(int userId, double amount)
  {
    int type = 0;
    String content = String.format("您参加的绑定资料体验金%s元，已经派发到您的账户。", new Object[] { Double.valueOf(amount) });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addActivityRecharge(int userId, double amount)
  {
    int type = 1;
    String content = String.format("您参加的开业大酬宾奖励%s元，已经派发到您的账户。", new Object[] { Double.valueOf(amount) });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addRewardMessage(int userId, String date)
  {
    int type = 0;
    String content = String.format("系统已发放%s佣金。", new Object[] { date });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addVipLevelUp(int userId, String level)
  {
    int type = 0;
    String content = String.format("尊敬的VIP会员您好，您已成功晋级为%s，从现在开始您可以享受%s待遇。", new Object[] { level, level });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addDividendBill(int userId, String startDate, String endDate)
  {
    int type = 0;
    String content = String.format("您的彩票分红已发放，周期%s~%s，请前往<代理管理->契约分红->契约分红账单>处领取！", new Object[] { startDate, endDate });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addGameDividendBill(int userId, String startDate, String endDate)
  {
    int type = 0;
    String content = String.format("您的老虎机真人体育分红已发放，周期%s~%s，请前往<代理管理->契约分红->老虎机/真人分红>处领取！", new Object[] { startDate, endDate });
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addDailySettleBill(int userId, String date)
  {
    int type = 0;
    String content = String.format("您昨日的彩票日结已发放，请前往<代理管理->契约日结->契约日结账单>处查看！", new Object[0]);
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
  
  public boolean addGameWaterBill(int userId, String date, String fromUser, String toUser)
  {
    int type = 0;
    String content;
    if (StringUtils.equalsIgnoreCase(fromUser, toUser)) {
      content = String.format("您昨日的老虎机真人体育返水已发放！", new Object[0]);
    } else {
      content = String.format("您昨日的老虎机真人体育返水已发放,来自用户：%s", new Object[] { fromUser });
    }
    String time = new Moment().toSimpleTime();
    int status = 0;
    UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
    return this.uSysMessageDao.add(entity);
  }
}
