package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.LotteryOpenTime;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface LotteryOpenTimeDao
{
  public abstract List<LotteryOpenTime> listAll();
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/LotteryOpenTimeDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */