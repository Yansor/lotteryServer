package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.UserLotteryDetailsReport;

public abstract interface UserLotteryDetailsReportService
{
  public abstract void addDetailsReports(List<UserLotteryDetailsReport> paramList);
  
  public abstract boolean update(int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble, String paramString);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/UserLotteryDetailsReportService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */