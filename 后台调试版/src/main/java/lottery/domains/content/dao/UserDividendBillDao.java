package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserDividendBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserDividendBillDao
{
  public abstract PageList search(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract double[] sumUserAmount(List<Integer> paramList, String paramString1, String paramString2, Double paramDouble1, Double paramDouble2);
  
  public abstract List<UserDividendBill> findByCriteria(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract boolean updateAllExpire();
  
  public abstract UserDividendBill getById(int paramInt);
  
  public abstract UserDividendBill getByUserId(int paramInt, String paramString1, String paramString2);
  
  public abstract boolean add(UserDividendBill paramUserDividendBill);
  
  public abstract boolean addAvailableMoney(int paramInt, double paramDouble);
  
  public abstract boolean addUserAmount(int paramInt, double paramDouble);
  
  public abstract boolean setAvailableMoney(int paramInt, double paramDouble);
  
  public abstract boolean addTotalReceived(int paramInt, double paramDouble);
  
  public abstract boolean addLowerPaidAmount(int paramInt, double paramDouble);
  
  public abstract boolean update(UserDividendBill paramUserDividendBill);
  
  public abstract boolean update(int paramInt1, int paramInt2, String paramString);
  
  public abstract boolean update(int paramInt1, int paramInt2, double paramDouble, String paramString);
  
  public abstract boolean del(int paramInt);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2, String paramString);
}
