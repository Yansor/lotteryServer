package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserBillDao
{
  public abstract boolean add(UserBill paramUserBill);
  
  public abstract UserBill getById(int paramInt);
  
  public abstract double getTotalMoney(String paramString1, String paramString2, int paramInt, int[] paramArrayOfInt);
  
  public abstract List<UserBill> getLatest(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract List<UserBill> listByDateAndType(String paramString, int paramInt, int[] paramArrayOfInt);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract PageList findNoDemoUserBill(String paramString, int paramInt1, int paramInt2);
  
  public abstract PageList findHistory(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
}
