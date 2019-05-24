package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.Lottery;

public abstract interface LotteryDao
{
  public abstract List<Lottery> listAll();
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean updateTimes(int paramInt1, int paramInt2);
  
  public abstract Lottery getById(int paramInt);
  
  public abstract Lottery getByName(String paramString);
  
  public abstract Lottery getByShortName(String paramString);
}
