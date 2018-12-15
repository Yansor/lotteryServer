package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import lottery.domains.content.biz.UserLotteryDetailsReportService;
import lottery.domains.content.dao.UserLotteryDetailsReportDao;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.UserLotteryDetailsReport;
import lottery.domains.content.vo.bill.HistoryUserLotteryDetailsReportVO;
import lottery.domains.content.vo.bill.UserBetsReportVO;
import lottery.domains.content.vo.bill.UserLotteryDetailsReportVO;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLotteryDetailsReportServiceImpl
  implements UserLotteryDetailsReportService
{
  @Autowired
  private UserLotteryDetailsReportDao uLotteryDetailsReportDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public boolean update(int userId, int lotteryId, int ruleId, int type, double amount, String time)
  {
    UserLotteryDetailsReport entity = new UserLotteryDetailsReport();
    switch (type)
    {
    case 6: 
      entity.setSpend(amount);
      break;
    case 7: 
      entity.setPrize(amount);
      break;
    case 8: 
      entity.setSpendReturn(amount);
      break;
    case 9: 
      entity.setProxyReturn(amount);
      break;
    case 10: 
      entity.setCancelOrder(amount);
      break;
    default: 
      return false;
    }
    UserLotteryDetailsReport bean = this.uLotteryDetailsReportDao.get(userId, lotteryId, ruleId, time);
    if (bean != null)
    {
      entity.setId(bean.getId());
      return this.uLotteryDetailsReportDao.update(entity);
    }
    entity.setUserId(userId);
    entity.setLotteryId(lotteryId);
    entity.setRuleId(ruleId);
    entity.setTime(time);
    return this.uLotteryDetailsReportDao.add(entity);
  }
  
  public List<UserLotteryDetailsReportVO> reportLowersAndSelf(int userId, Integer lotteryId, String sTime, String eTime)
  {
    if (lotteryId == null) {
      return this.uLotteryDetailsReportDao.sumLowersAndSelfByLottery(userId, sTime, eTime);
    }
    return this.uLotteryDetailsReportDao.sumLowersAndSelfByRule(userId, lotteryId.intValue(), sTime, eTime);
  }
  
  public List<HistoryUserLotteryDetailsReportVO> historyReportLowersAndSelf(int userId, Integer lotteryId, String sTime, String eTime)
  {
    if (lotteryId == null) {
      return this.uLotteryDetailsReportDao.historySumLowersAndSelfByLottery(userId, sTime, eTime);
    }
    return this.uLotteryDetailsReportDao.historySumLowersAndSelfByRule(userId, lotteryId.intValue(), sTime, eTime);
  }
  
  public List<UserLotteryDetailsReportVO> reportSelf(int userId, Integer lotteryId, String sTime, String eTime)
  {
    if (lotteryId == null) {
      return this.uLotteryDetailsReportDao.sumSelfByLottery(userId, sTime, eTime);
    }
    return this.uLotteryDetailsReportDao.sumSelfByRule(userId, lotteryId.intValue(), sTime, eTime);
  }
  
  public List<HistoryUserLotteryDetailsReportVO> historyReportSelf(int userId, Integer lotteryId, String sTime, String eTime)
  {
    if (lotteryId == null) {
      return this.uLotteryDetailsReportDao.historySumSelfByLottery(userId, sTime, eTime);
    }
    return this.uLotteryDetailsReportDao.historySumSelfByRule(userId, lotteryId.intValue(), sTime, eTime);
  }
  
  public List<UserBetsReportVO> sumUserBets(Integer type, Integer lottery, Integer ruleId, String sTime, String eTime)
  {
    List<Integer> lids = new ArrayList();
    if (lottery != null)
    {
      lids.add(lottery);
    }
    else if (type != null)
    {
      List<Lottery> llist = this.lotteryDataFactory.listLottery(type.intValue());
      for (Lottery tmpBean : llist) {
        lids.add(Integer.valueOf(tmpBean.getId()));
      }
    }
    return this.uLotteryDetailsReportDao.sumUserBets(lids, ruleId, sTime, eTime);
  }
}
