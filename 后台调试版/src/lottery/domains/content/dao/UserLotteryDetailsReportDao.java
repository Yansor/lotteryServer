package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.UserLotteryDetailsReport;
import lottery.domains.content.vo.bill.HistoryUserLotteryDetailsReportVO;
import lottery.domains.content.vo.bill.UserBetsReportVO;
import lottery.domains.content.vo.bill.UserLotteryDetailsReportVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserLotteryDetailsReportDao
{
  public abstract boolean add(UserLotteryDetailsReport paramUserLotteryDetailsReport);
  
  public abstract UserLotteryDetailsReport get(int paramInt1, int paramInt2, int paramInt3, String paramString);
  
  public abstract boolean update(UserLotteryDetailsReport paramUserLotteryDetailsReport);
  
  public abstract List<UserLotteryDetailsReport> find(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract List<UserLotteryDetailsReportVO> sumLowersAndSelfByLottery(int paramInt, String paramString1, String paramString2);
  
  public abstract List<HistoryUserLotteryDetailsReportVO> historySumLowersAndSelfByLottery(int paramInt, String paramString1, String paramString2);
  
  public abstract List<UserLotteryDetailsReportVO> sumLowersAndSelfByRule(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract List<HistoryUserLotteryDetailsReportVO> historySumLowersAndSelfByRule(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract List<UserLotteryDetailsReportVO> sumSelfByLottery(int paramInt, String paramString1, String paramString2);
  
  public abstract List<HistoryUserLotteryDetailsReportVO> historySumSelfByLottery(int paramInt, String paramString1, String paramString2);
  
  public abstract List<UserLotteryDetailsReportVO> sumSelfByRule(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract List<HistoryUserLotteryDetailsReportVO> historySumSelfByRule(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract List<UserBetsReportVO> sumUserBets(List<Integer> paramList, Integer paramInteger, String paramString1, String paramString2);
}
