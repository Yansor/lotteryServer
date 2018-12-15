package lottery.domains.content.biz;

import admin.web.WebJSONObject;
import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserDividendBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserDividendBillService
{
  public abstract PageList search(List<Integer> paramList, String paramString1, String paramString2, Double paramDouble1, Double paramDouble2, Integer paramInteger1, Integer paramInteger2, int paramInt1, int paramInt2);
  
  public abstract PageList searchPlatformLoss(List<Integer> paramList, String paramString1, String paramString2, Double paramDouble1, Double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract double[] sumUserAmount(List<Integer> paramList, String paramString1, String paramString2, Double paramDouble1, Double paramDouble2);
  
  public abstract List<UserDividendBill> findByCriteria(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract boolean updateAllExpire();
  
  public abstract List<UserDividendBill> getLowerBills(int paramInt, String paramString1, String paramString2);
  
  public abstract List<UserDividendBill> getDirectLowerBills(int paramInt, String paramString1, String paramString2);
  
  public abstract UserDividendBill getById(int paramInt);
  
  public abstract UserDividendBill getBill(int paramInt, String paramString1, String paramString2);
  
  public abstract boolean addAvailableMoney(int paramInt, double paramDouble);
  
  public abstract boolean add(UserDividendBill paramUserDividendBill);
  
  public abstract void issueInsufficient(int paramInt);
  
  public abstract boolean agree(WebJSONObject paramWebJSONObject, int paramInt, String paramString);
  
  public abstract boolean deny(WebJSONObject paramWebJSONObject, int paramInt, String paramString);
  
  public abstract boolean del(WebJSONObject paramWebJSONObject, int paramInt);
  
  public abstract boolean reset(WebJSONObject paramWebJSONObject, int paramInt, String paramString);
  
  public abstract double queryPeriodCollect(int paramInt, String paramString1, String paramString2);
}
