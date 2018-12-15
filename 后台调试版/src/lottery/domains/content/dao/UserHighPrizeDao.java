package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserHighPrize;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserHighPrizeDao
{
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract PageList find(String paramString, int paramInt1, int paramInt2);
  
  public abstract UserHighPrize getById(int paramInt);
  
  public abstract boolean add(UserHighPrize paramUserHighPrize);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean updateStatusAndConfirmUsername(int paramInt1, int paramInt2, String paramString);
  
  public abstract int getUnProcessCount();
}
