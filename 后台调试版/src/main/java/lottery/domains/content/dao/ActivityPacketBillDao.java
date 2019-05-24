package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.ActivityPacketBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface ActivityPacketBillDao
{
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract double total(String paramString1, String paramString2);
  
  public abstract boolean save(ActivityPacketBill paramActivityPacketBill);
}
