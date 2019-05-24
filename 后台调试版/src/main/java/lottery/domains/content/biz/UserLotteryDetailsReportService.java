package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.vo.bill.HistoryUserLotteryDetailsReportVO;
import lottery.domains.content.vo.bill.UserBetsReportVO;
import lottery.domains.content.vo.bill.UserLotteryDetailsReportVO;

public abstract interface UserLotteryDetailsReportService
{
  public abstract boolean update(int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble, String paramString);
  
  public abstract List<UserLotteryDetailsReportVO> reportLowersAndSelf(int paramInt, Integer paramInteger, String paramString1, String paramString2);
  
  public abstract List<HistoryUserLotteryDetailsReportVO> historyReportLowersAndSelf(int paramInt, Integer paramInteger, String paramString1, String paramString2);
  
  public abstract List<UserLotteryDetailsReportVO> reportSelf(int paramInt, Integer paramInteger, String paramString1, String paramString2);
  
  public abstract List<HistoryUserLotteryDetailsReportVO> historyReportSelf(int paramInt, Integer paramInteger, String paramString1, String paramString2);
  
  public abstract List<UserBetsReportVO> sumUserBets(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString1, String paramString2);
}
