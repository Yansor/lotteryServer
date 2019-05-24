package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserTransfers;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserTransfersDao
{
  public abstract boolean add(UserTransfers paramUserTransfers);
  
  public abstract PageList search(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract double getTotalTransfers(String paramString1, String paramString2, int paramInt);
}
