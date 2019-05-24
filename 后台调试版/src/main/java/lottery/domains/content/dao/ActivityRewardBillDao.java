package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.ActivityRewardBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface ActivityRewardBillDao
{
  public abstract boolean add(ActivityRewardBill paramActivityRewardBill);
  
  public abstract ActivityRewardBill getById(int paramInt);
  
  public abstract List<ActivityRewardBill> getUntreated(String paramString);
  
  public abstract List<ActivityRewardBill> getLatest(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract boolean hasRecord(int paramInt1, int paramInt2, int paramInt3, String paramString);
  
  public abstract boolean update(ActivityRewardBill paramActivityRewardBill);
  
  public abstract boolean delete(int paramInt);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract double total(String paramString1, String paramString2, int paramInt);
}
