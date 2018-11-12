package lottery.domains.content.biz.impl;

import org.apache.commons.lang.StringUtils;
import javautils.StringUtil;
import lottery.domains.content.entity.UserSysMessage;
import javautils.date.Moment;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserSysMessageDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserSysMessageService;

@Service
public class UserSysMessageServiceImpl implements UserSysMessageService
{
    @Autowired
    private UserSysMessageDao uSysMessageDao;
    
    @Override
    public boolean addTransToUser(final int userId, final double amount) {
        final int type = 1;
        final String content = String.format("上级代理已为您充值%s元。", amount);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addFirstRecharge(final int userId, final double rechargeAmount, final double amount) {
        final int type = 0;
        final String content = String.format("您已通过首充活动充值%s元，系统自动赠送%s元。", rechargeAmount, amount);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addSysRecharge(final int userId, final double amount, String remarks) {
        final int type = 1;
        if (StringUtil.isNotNull(remarks)) {
            remarks = "备注：" + remarks;
        }
        final String content = String.format("管理员已为您充值%s元。%s", amount, remarks);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addOnlineRecharge(final int userId, final double amount) {
        final int type = 1;
        final String content = String.format("您已通过在线支付充值%s元。", amount);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addTransfersRecharge(final int userId, final double amount) {
        final int type = 1;
        final String content = String.format("您已通过网银转账充值%s元。", amount);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addConfirmWithdraw(final int userId, final double amount, final double recAmount) {
        final int type = 2;
        final String content = String.format("您申请提现%s元已支付，实际到账%s元，请注意查收。", amount, recAmount);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addRefuseWithdraw(final int userId, final double amount) {
        final int type = 2;
        final String content = String.format("您申请提现%s元已被拒绝，金额已返还！", amount);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addRefuse(final int userId, final double amount) {
        final int type = 2;
        final String content = String.format("您申请提现%s元已失败，金额已返还！", amount);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addShFail(final int userId, final double amount) {
        final int type = 2;
        final String content = String.format("您申请提现%s元审核未通过，金额已返还！", amount);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addActivityBind(final int userId, final double amount) {
        final int type = 0;
        final String content = String.format("您参加的绑定资料体验金%s元，已经派发到您的账户。", amount);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addActivityRecharge(final int userId, final double amount) {
        final int type = 1;
        final String content = String.format("您参加的开业大酬宾奖励%s元，已经派发到您的账户。", amount);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addRewardMessage(final int userId, final String date) {
        final int type = 0;
        final String content = String.format("系统已发放%s佣金。", date);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addVipLevelUp(final int userId, final String level) {
        final int type = 0;
        final String content = String.format("尊敬的VIP会员您好，您已成功晋级为%s，从现在开始您可以享受%s待遇。", level, level);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addDividendBill(final int userId, final String startDate, final String endDate) {
        final int type = 0;
        final String content = String.format("您的彩票分红已发放，周期%s~%s，请前往<代理管理->契约分红->契约分红账单>处领取！", startDate, endDate);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addGameDividendBill(final int userId, final String startDate, final String endDate) {
        final int type = 0;
        final String content = String.format("您的老虎机真人体育分红已发放，周期%s~%s，请前往<代理管理->契约分红->老虎机/真人分红>处领取！", startDate, endDate);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addDailySettleBill(final int userId, final String date) {
        final int type = 0;
        final String content = String.format("您昨日的彩票日结已发放，请前往<代理管理->契约日结->契约日结账单>处查看！", new Object[0]);
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
    
    @Override
    public boolean addGameWaterBill(final int userId, final String date, final String fromUser, final String toUser) {
        final int type = 0;
        String content;
        if (StringUtils.equalsIgnoreCase(fromUser, toUser)) {
            content = String.format("您昨日的老虎机真人体育返水已发放！", new Object[0]);
        }
        else {
            content = String.format("您昨日的老虎机真人体育返水已发放,来自用户：%s", fromUser);
        }
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final UserSysMessage entity = new UserSysMessage(userId, type, content, time, status);
        return this.uSysMessageDao.add(entity);
    }
}
