package lottery.domains.content.jobs;

import java.util.List;
import javautils.date.DateUtil;
import javautils.date.Moment;
import javautils.math.MathUtil;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserGameReportService;
import lottery.domains.content.biz.UserGameWaterBillService;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserGameWaterBill;
import lottery.domains.content.vo.bill.UserGameReportVO;
import lottery.domains.content.vo.config.GameDividendConfig;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GameWaterJob
{
  private static final Logger log = LoggerFactory.getLogger(GameWaterJob.class);
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
  
  @Scheduled(cron="0 0 10 0/1 * *")
  public void schedule()
  {
    try
    {
      if (!this.dataFactory.getGameDividendConfig().isEnable())
      {
        log.debug("老虎机真人体育分红没有开启，不发放游戏返水");
        return;
      }
      log.debug("游戏返水发放开始");
      
      String yesterday = DateUtil.getYesterday();
      String today = DateUtil.getCurrentDate();
      
      settleUp(yesterday, today);
      
      log.debug("游戏返水发放完成");
    }
    catch (Exception e)
    {
      log.error("游戏返水发放出错", e);
    }
  }
  
  private void settleUp(String sTime, String eTime)
  {
    List<UserGameReportVO> reports = this.uGameReportService.reportByUser(sTime, eTime);
    if (CollectionUtils.isEmpty(reports)) {
      return;
    }
    for (UserGameReportVO report : reports) {
      if (report.getBillingOrder() > 0.0D) {
        waterReturn(report, sTime);
      }
    }
  }
  
  private void waterReturn(UserGameReportVO report, String sTime)
  {
    User user = this.uDao.getById(report.getUserId());
    if (user == null) {
      return;
    }
    if (user.getUpid() == 0) {
      return;
    }
    waterReturnToUser(report, user, user, 1, 0.003D, sTime);
    
    User upperUser = this.uDao.getById(user.getUpid());
    if ((upperUser != null) && (upperUser.getId() != 72))
    {
      waterReturnToUser(report, user, upperUser, 2, 0.001D, sTime);
      if (upperUser.getUpid() != 72)
      {
        User upperUpperUser = this.uDao.getById(upperUser.getUpid());
        if (upperUpperUser != null) {
          waterReturnToUser(report, user, upperUpperUser, 2, 5.0E-4D, sTime);
        }
      }
    }
  }
  
  private void waterReturnToUser(UserGameReportVO report, User fromUser, User toUser, int type, double scale, String sTime)
  {
    if (fromUser.getId() == 72) {
      return;
    }
    UserGameWaterBill bill = new UserGameWaterBill();
    bill.setUserId(toUser.getId());
    bill.setIndicateDate(sTime);
    bill.setFromUser(fromUser.getId());
    bill.setSettleTime(new Moment().toSimpleTime());
    bill.setScale(scale);
    bill.setBillingOrder(report.getBillingOrder());
    
    double userAmount = MathUtil.multiply(report.getBillingOrder(), scale);
    bill.setUserAmount(userAmount);
    bill.setType(type);
    
    saveResult(bill, fromUser, toUser);
  }
  
  private void saveResult(UserGameWaterBill bill, User fromUser, User toUser)
  {
    if (bill.getUserAmount() <= 0.0D) {
      return;
    }
    if ((toUser.getAStatus() != 0) && (toUser.getAStatus() != -1))
    {
      bill.setStatus(2);
      bill.setRemark("用户永久冻结状态，不予发放");
    }
    else if ((fromUser.getAStatus() != 0) && (fromUser.getAStatus() != -1))
    {
      bill.setStatus(2);
      bill.setRemark("触发用户永久冻结状态，不予发放");
    }
    else
    {
      bill.setStatus(1);
    }
    this.uGameWaterBillService.add(bill);
    if (bill.getStatus() == 1)
    {
      if (bill.getType() == 1) {
        this.uBillService.addGameWaterBill(toUser, 2, bill.getType(), bill.getUserAmount(), "游戏返水");
      } else {
        this.uBillService.addGameWaterBill(toUser, 2, bill.getType(), bill.getUserAmount(), "游戏代理返水");
      }
      if (bill.getUserAmount() > 0.0D)
      {
        this.uDao.updateLotteryMoney(bill.getUserId(), bill.getUserAmount());
        this.uSysMessageService.addGameWaterBill(bill.getUserId(), bill.getIndicateDate(), fromUser.getUsername(), toUser.getUsername());
      }
    }
  }
}
