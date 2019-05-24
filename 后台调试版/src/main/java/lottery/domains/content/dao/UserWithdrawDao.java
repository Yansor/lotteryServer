package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.HistoryUserWithdraw;
import lottery.domains.content.entity.UserWithdraw;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserWithdrawDao
{
  public abstract boolean update(UserWithdraw paramUserWithdraw);
  
  public abstract int getWaitTodo();
  
  public abstract UserWithdraw getById(int paramInt);
  
  public abstract HistoryUserWithdraw getHistoryById(int paramInt);
  
  public abstract UserWithdraw getByBillno(String paramString);
  
  public abstract List<UserWithdraw> listByOperatorTime(String paramString1, String paramString2);
  
  public abstract List<UserWithdraw> listByRemitStatus(int[] paramArrayOfInt, boolean paramBoolean, String paramString1, String paramString2);
  
  public abstract List<UserWithdraw> getLatest(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract PageList find(String paramString, int paramInt1, int paramInt2);
  
  public abstract PageList findHistory(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract double getTotalWithdraw(String paramString1, String paramString2);
  
  public abstract Object[] getTotalWithdrawData(String paramString1, String paramString2);
  
  public abstract double[] getTotalWithdraw(String paramString1, Integer paramInteger1, String paramString2, String paramString3, String paramString4, String paramString5, Double paramDouble1, Double paramDouble2, String paramString6, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5);
  
  public abstract double[] getHistoryTotalWithdraw(String paramString1, Integer paramInteger1, String paramString2, String paramString3, String paramString4, String paramString5, Double paramDouble1, Double paramDouble2, String paramString6, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5);
  
  public abstract double getTotalAutoRemit(String paramString1, String paramString2);
  
  public abstract double getTotalFee(String paramString1, String paramString2);
  
  public abstract List<?> getDayWithdraw(String paramString1, String paramString2);
  
  public abstract List<?> getDayWithdraw2(String paramString1, String paramString2);
  
  public abstract boolean lock(String paramString1, String paramString2, String paramString3);
  
  public abstract boolean unlock(String paramString1, String paramString2);
}
