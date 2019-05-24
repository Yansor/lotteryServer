package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.ActivityFirstRechargeBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface ActivityFirstRechargeBillDao
{
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract double sumAmount(Integer paramInteger, String paramString1, String paramString2, String paramString3);
  
  public abstract ActivityFirstRechargeBill getByDateAndIp(String paramString1, String paramString2);
  
  public abstract ActivityFirstRechargeBill getByUserIdAndDate(int paramInt, String paramString);
  
  public abstract boolean add(ActivityFirstRechargeBill paramActivityFirstRechargeBill);
}
