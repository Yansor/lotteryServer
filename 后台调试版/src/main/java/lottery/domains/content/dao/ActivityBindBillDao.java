package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.ActivityBindBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface ActivityBindBillDao
{
  public abstract ActivityBindBill getById(int paramInt);
  
  public abstract int getWaitTodo();
  
  public abstract List<ActivityBindBill> get(String paramString1, String paramString2, String paramString3);
  
  public abstract boolean update(ActivityBindBill paramActivityBindBill);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract double total(String paramString1, String paramString2);
}
