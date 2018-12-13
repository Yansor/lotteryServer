package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.UserLotteryReport;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserLotteryReportDao
{
  public abstract UserLotteryReport get(int paramInt, String paramString);
  
  public abstract boolean update(UserLotteryReport paramUserLotteryReport);
  
  public abstract boolean add(UserLotteryReport paramUserLotteryReport);
  
  public abstract List<UserLotteryReport> find(List<Criterion> paramList, List<Order> paramList1);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/UserLotteryReportDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */