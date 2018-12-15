package lottery.domains.content.dao;

import java.util.List;
import java.util.Map;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.ActivityGrabBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface ActivityGrabBillDao
{
  public abstract boolean add(ActivityGrabBill paramActivityGrabBill);
  
  public abstract ActivityGrabBill get(int paramInt);
  
  public abstract Map<String, Integer> getByPackageGroup();
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract Double getOutAmount(String paramString);
  
  public abstract double getGrabTotal(String paramString1, String paramString2);
}
