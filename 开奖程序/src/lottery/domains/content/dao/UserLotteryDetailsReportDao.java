package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.UserLotteryDetailsReport;

public abstract interface UserLotteryDetailsReportDao
{
  public abstract boolean add(UserLotteryDetailsReport paramUserLotteryDetailsReport);
  
  public abstract UserLotteryDetailsReport get(int paramInt1, int paramInt2, int paramInt3, String paramString);
  
  public abstract boolean update(UserLotteryDetailsReport paramUserLotteryDetailsReport);
  
  public abstract void addDetailsReports(List<UserLotteryDetailsReport> paramList);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/UserLotteryDetailsReportDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */