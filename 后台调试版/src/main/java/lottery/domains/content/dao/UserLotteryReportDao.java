package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.HistoryUserLotteryReport;
import lottery.domains.content.entity.UserLotteryReport;
import lottery.domains.content.vo.bill.HistoryUserLotteryReportVO;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import lottery.domains.content.vo.bill.UserProfitRankingVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserLotteryReportDao
{
  public abstract boolean add(UserLotteryReport paramUserLotteryReport);
  
  public abstract UserLotteryReport get(int paramInt, String paramString);
  
  public abstract List<UserLotteryReport> list(int paramInt, String paramString1, String paramString2);
  
  public abstract boolean update(UserLotteryReport paramUserLotteryReport);
  
  public abstract List<UserLotteryReport> find(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract List<HistoryUserLotteryReport> findHistory(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract List<UserLotteryReport> getDayList(int[] paramArrayOfInt, String paramString1, String paramString2);
  
  public abstract UserLotteryReportVO sumLowersAndSelf(int paramInt, String paramString1, String paramString2);
  
  public abstract HistoryUserLotteryReportVO historySumLowersAndSelf(int paramInt, String paramString1, String paramString2);
  
  public abstract UserLotteryReportVO sum(int paramInt, String paramString1, String paramString2);
  
  public abstract List<UserProfitRankingVO> listUserProfitRanking(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract List<UserProfitRankingVO> listUserProfitRankingByDate(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3);
}
