package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBetsPlan;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserBetsPlanDao
{
  public abstract boolean add(UserBetsPlan paramUserBetsPlan);
  
  public abstract UserBetsPlan get(int paramInt);
  
  public abstract UserBetsPlan get(int paramInt1, int paramInt2);
  
  public abstract boolean hasRecord(int paramInt1, int paramInt2, String paramString);
  
  public abstract boolean updateCount(int paramInt1, int paramInt2);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2, double paramDouble);
  
  public abstract List<UserBetsPlan> listUnsettled();
  
  public abstract List<UserBetsPlan> list(int paramInt, String paramString);
  
  public abstract PageList search(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
}
