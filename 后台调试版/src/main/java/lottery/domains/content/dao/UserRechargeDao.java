package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.HistoryUserRecharge;
import lottery.domains.content.entity.UserRecharge;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserRechargeDao
{
  public abstract boolean add(UserRecharge paramUserRecharge);
  
  public abstract boolean update(UserRecharge paramUserRecharge);
  
  public abstract boolean updateSuccess(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, String paramString1, String paramString2);
  
  public abstract UserRecharge getById(int paramInt);
  
  public abstract HistoryUserRecharge getHistoryById(int paramInt);
  
  public abstract UserRecharge getByBillno(String paramString);
  
  public abstract boolean isRecharge(int paramInt);
  
  public abstract List<UserRecharge> getLatest(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract List<UserRecharge> list(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract PageList find(String paramString, int paramInt1, int paramInt2);
  
  public abstract PageList findHistory(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract List<?> getDayRecharge(String paramString1, String paramString2);
  
  public abstract int getRechargeCount(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract double getTotalFee(String paramString1, String paramString2);
  
  public abstract double getThirdTotalRecharge(String paramString1, String paramString2);
  
  public abstract double getTotalRecharge(String paramString1, String paramString2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, Integer paramInteger);
  
  public abstract Object[] getTotalRechargeData(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2);
  
  public abstract List<?> getDayRecharge2(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2);
  
  public abstract double getTotalRecharge(String paramString1, Integer paramInteger1, String paramString2, String paramString3, String paramString4, String paramString5, Double paramDouble1, Double paramDouble2, Integer paramInteger2, Integer paramInteger3);
  
  public abstract double getHistoryTotalRecharge(String paramString1, Integer paramInteger1, String paramString2, String paramString3, String paramString4, String paramString5, Double paramDouble1, Double paramDouble2, Integer paramInteger2, Integer paramInteger3);
}
