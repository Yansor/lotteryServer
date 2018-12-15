package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.ActivityRechargeBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface ActivityRechargeBillDao
{
  public abstract ActivityRechargeBill getById(int paramInt);
  
  public abstract int getWaitTodo();
  
  public abstract boolean update(ActivityRechargeBill paramActivityRechargeBill);
  
  public abstract boolean add(ActivityRechargeBill paramActivityRechargeBill);
  
  public abstract boolean hasDateRecord(int paramInt, String paramString);
  
  public abstract List<ActivityRechargeBill> get(String paramString1, String paramString2);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract double total(String paramString1, String paramString2);
}
