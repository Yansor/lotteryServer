package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.LotteryOpenTime;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface LotteryOpenTimeDao
{
  public abstract List<LotteryOpenTime> listAll();
  
  public abstract List<LotteryOpenTime> list(String paramString);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract LotteryOpenTime getById(int paramInt);
  
  public abstract LotteryOpenTime getByLottery(String paramString);
  
  public abstract boolean update(LotteryOpenTime paramLotteryOpenTime);
  
  public abstract boolean save(LotteryOpenTime paramLotteryOpenTime);
}
