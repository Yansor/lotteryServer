package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.vo.bill.HistoryUserGameReportVO;
import lottery.domains.content.vo.bill.UserGameReportVO;

public abstract interface UserGameReportService
{
  public abstract boolean update(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, String paramString);
  
  public abstract List<UserGameReportVO> report(String paramString1, String paramString2);
  
  public abstract List<UserGameReportVO> report(int paramInt, String paramString1, String paramString2);
  
  public abstract List<HistoryUserGameReportVO> historyReport(String paramString1, String paramString2);
  
  public abstract List<HistoryUserGameReportVO> historyReport(int paramInt, String paramString1, String paramString2);
  
  public abstract List<UserGameReportVO> reportByUser(String paramString1, String paramString2);
}
