package lottery.domains.content.biz;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserGameDividendBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserGameDividendBillService
{
  public abstract PageList search(List<Integer> paramList, String paramString1, String paramString2, Double paramDouble1, Double paramDouble2, Integer paramInteger, int paramInt1, int paramInt2);
  
  public abstract double sumUserAmount(List<Integer> paramList, String paramString1, String paramString2, Double paramDouble1, Double paramDouble2, Integer paramInteger);
  
  public abstract List<UserGameDividendBill> findByCriteria(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract UserGameDividendBill getById(int paramInt);
  
  public abstract boolean agree(int paramInt, double paramDouble, String paramString);
  
  public abstract boolean deny(int paramInt, double paramDouble, String paramString);
  
  public abstract boolean del(int paramInt);
}
