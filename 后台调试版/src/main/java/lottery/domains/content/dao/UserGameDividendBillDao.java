package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserGameDividendBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserGameDividendBillDao
{
  public abstract PageList search(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract double sumUserAmount(List<Integer> paramList, String paramString1, String paramString2, Double paramDouble1, Double paramDouble2, Integer paramInteger);
  
  public abstract List<UserGameDividendBill> findByCriteria(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract UserGameDividendBill getById(int paramInt);
  
  public abstract boolean add(UserGameDividendBill paramUserGameDividendBill);
  
  public abstract boolean update(int paramInt1, int paramInt2, double paramDouble, String paramString);
  
  public abstract boolean del(int paramInt);
}
