package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBalanceSnapshot;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserBalanceSnapshotDao
{
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract boolean add(UserBalanceSnapshot paramUserBalanceSnapshot);
}
