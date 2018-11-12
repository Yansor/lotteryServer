package lottery.domains.content.jobs;

import javautils.math.MathUtil;
import javautils.date.Moment;
import lottery.domains.content.entity.UserGameWaterBill;
import lottery.domains.content.entity.User;
import java.util.Iterator;
import java.util.List;
import lottery.domains.content.vo.bill.UserGameReportVO;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import javautils.date.DateUtil;
import org.slf4j.LoggerFactory;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserGameWaterBillService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.biz.UserGameReportService;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class GameWaterJob
{
    private static final Logger log;
    @Autowired
    private UserGameReportService uGameReportService;
    @Autowired
    private UserGameWaterBillService uGameWaterBillService;
    @Autowired
    private UserBillService uBillService;
    @Autowired
    private UserSysMessageService uSysMessageService;
    @Autowired
    private UserDao uDao;
    @Autowired
    private LotteryDataFactory dataFactory;
    
    static {
        log = LoggerFactory.getLogger((Class)GameWaterJob.class);
    }
    
    @Scheduled(cron = "0 0 10 0/1 * *")
    public void schedule() {
        try {
            if (!this.dataFactory.getGameDividendConfig().isEnable()) {
                GameWaterJob.log.debug("老虎机真人体育分红没有开启，不发放游戏返水");
                return;
            }
            GameWaterJob.log.debug("游戏返水发放开始");
            final String yesterday = DateUtil.getYesterday();
            final String today = DateUtil.getCurrentDate();
            this.settleUp(yesterday, today);
            GameWaterJob.log.debug("游戏返水发放完成");
        }
        catch (Exception e) {
            GameWaterJob.log.error("游戏返水发放出错", (Throwable)e);
        }
    }
    
    private void settleUp(final String sTime, final String eTime) {
        final List<UserGameReportVO> reports = this.uGameReportService.reportByUser(sTime, eTime);
        if (CollectionUtils.isEmpty((Collection)reports)) {
            return;
        }
        for (final UserGameReportVO report : reports) {
            if (report.getBillingOrder() > 0.0) {
                this.waterReturn(report, sTime);
            }
        }
    }
    
    private void waterReturn(final UserGameReportVO report, final String sTime) {
        final User user = this.uDao.getById(report.getUserId());
        if (user == null) {
            return;
        }
        if (user.getUpid() == 0) {
            return;
        }
        this.waterReturnToUser(report, user, user, 1, 0.003, sTime);
        final User upperUser = this.uDao.getById(user.getUpid());
        if (upperUser != null && upperUser.getId() != 72) {
            this.waterReturnToUser(report, user, upperUser, 2, 0.001, sTime);
            if (upperUser.getUpid() != 72) {
                final User upperUpperUser = this.uDao.getById(upperUser.getUpid());
                if (upperUpperUser != null) {
                    this.waterReturnToUser(report, user, upperUpperUser, 2, 5.0E-4, sTime);
                }
            }
        }
    }
    
    private void waterReturnToUser(final UserGameReportVO report, final User fromUser, final User toUser, final int type, final double scale, final String sTime) {
        if (fromUser.getId() == 72) {
            return;
        }
        final UserGameWaterBill bill = new UserGameWaterBill();
        bill.setUserId(toUser.getId());
        bill.setIndicateDate(sTime);
        bill.setFromUser(fromUser.getId());
        bill.setSettleTime(new Moment().toSimpleTime());
        bill.setScale(scale);
        bill.setBillingOrder(report.getBillingOrder());
        final double userAmount = MathUtil.multiply(report.getBillingOrder(), scale);
        bill.setUserAmount(userAmount);
        bill.setType(type);
        this.saveResult(bill, fromUser, toUser);
    }
    
    private void saveResult(final UserGameWaterBill bill, final User fromUser, final User toUser) {
        if (bill.getUserAmount() <= 0.0) {
            return;
        }
        if (toUser.getAStatus() != 0 && toUser.getAStatus() != -1) {
            bill.setStatus(2);
            bill.setRemark("用户永久冻结状态，不予发放");
        }
        else if (fromUser.getAStatus() != 0 && fromUser.getAStatus() != -1) {
            bill.setStatus(2);
            bill.setRemark("触发用户永久冻结状态，不予发放");
        }
        else {
            bill.setStatus(1);
        }
        this.uGameWaterBillService.add(bill);
        if (bill.getStatus() == 1) {
            if (bill.getType() == 1) {
                this.uBillService.addGameWaterBill(toUser, 2, bill.getType(), bill.getUserAmount(), "游戏返水");
            }
            else {
                this.uBillService.addGameWaterBill(toUser, 2, bill.getType(), bill.getUserAmount(), "游戏代理返水");
            }
            if (bill.getUserAmount() > 0.0) {
                this.uDao.updateLotteryMoney(bill.getUserId(), bill.getUserAmount());
                this.uSysMessageService.addGameWaterBill(bill.getUserId(), bill.getIndicateDate(), fromUser.getUsername(), toUser.getUsername());
            }
        }
    }
}
