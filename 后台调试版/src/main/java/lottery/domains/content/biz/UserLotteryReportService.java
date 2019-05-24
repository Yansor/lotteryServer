package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.vo.bill.HistoryUserLotteryReportVO;
import lottery.domains.content.vo.bill.UserBetsReportVO;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import lottery.domains.content.vo.bill.UserProfitRankingVO;

public abstract interface UserLotteryReportService
{
  public abstract boolean update(int paramInt1, int paramInt2, double paramDouble, String paramString);
  
  public abstract boolean updateRechargeFee(int paramInt, double paramDouble, String paramString);
  
  public abstract List<UserLotteryReportVO> report(String paramString1, String paramString2);
  
  public abstract List<UserLotteryReportVO> report(int paramInt, String paramString1, String paramString2);
  
  public abstract List<UserLotteryReportVO> reportByType(Integer paramInteger, String paramString1, String paramString2);
  
  public abstract List<HistoryUserLotteryReportVO> historyReport(String paramString1, String paramString2);
  
  public abstract List<HistoryUserLotteryReportVO> historyReport(int paramInt, String paramString1, String paramString2);
  
  public abstract List<UserBetsReportVO> bReport(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString1, String paramString2);
  
  public abstract List<UserProfitRankingVO> listUserProfitRanking(Integer paramInteger, String paramString1, String paramString2, int paramInt1, int paramInt2);
}
