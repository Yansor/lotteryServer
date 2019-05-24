package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.LotteryType;

public abstract interface LotteryTypeDao
{
  public abstract List<LotteryType> listAll();
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
}
