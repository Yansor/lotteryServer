package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.Lottery;

public abstract interface LotteryDao
{
  public abstract List<Lottery> listAll();
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/LotteryDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */