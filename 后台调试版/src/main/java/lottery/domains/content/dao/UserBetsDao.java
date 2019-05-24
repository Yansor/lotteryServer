package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.HistoryUserBets;
import lottery.domains.content.entity.UserBets;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserBetsDao
{
  public abstract boolean updateStatus(int paramInt1, int paramInt2, String paramString1, String paramString2, double paramDouble, String paramString3);
  
  public abstract boolean updateLocked(int paramInt1, int paramInt2);
  
  public abstract UserBets getById(int paramInt);
  
  public abstract HistoryUserBets getHistoryById(int paramInt);
  
  public abstract List<UserBets> getByBillno(String paramString, boolean paramBoolean);
  
  public abstract List<HistoryUserBets> getHistoryByBillno(String paramString, boolean paramBoolean);
  
  public abstract UserBets getBybillno(int paramInt, String paramString);
  
  public abstract boolean cancel(int paramInt);
  
  public abstract boolean delete(int paramInt);
  
  public abstract boolean isCost(int paramInt);
  
  public abstract List<UserBets> getSuspiciousOrder(int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract List<UserBets> getByFollowBillno(String paramString, boolean paramBoolean);
  
  public abstract PageList find(String paramString, int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract PageList findHistory(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract List<UserBets> find(List<Criterion> paramList, List<Order> paramList1, boolean paramBoolean);
  
  public abstract long getTotalBetsMoney(String paramString1, String paramString2);
  
  public abstract int getTotalOrderCount(String paramString1, String paramString2);
  
  public abstract List<?> getDayUserBets(int[] paramArrayOfInt, String paramString1, String paramString2);
  
  public abstract List<?> getDayBetsMoney(int[] paramArrayOfInt, String paramString1, String paramString2);
  
  public abstract List<?> getDayPrizeMoney(int[] paramArrayOfInt, String paramString1, String paramString2);
  
  public abstract List<?> getLotteryHot(int[] paramArrayOfInt, String paramString1, String paramString2);
  
  public abstract List<?> report(List<Integer> paramList, Integer paramInteger, String paramString1, String paramString2);
  
  public abstract int countUserOnline(String paramString);
  
  public abstract double[] getTotalMoney(String paramString1, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, String paramString2, Integer paramInteger5, String paramString3, String paramString4, String paramString5, String paramString6, Double paramDouble1, Double paramDouble2, Integer paramInteger6, Integer paramInteger7, Double paramDouble3, Double paramDouble4, Integer paramInteger8, Integer paramInteger9, String paramString7);
  
  public abstract double[] getHistoryTotalMoney(String paramString1, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, String paramString2, Integer paramInteger5, String paramString3, String paramString4, String paramString5, String paramString6, Double paramDouble1, Double paramDouble2, Integer paramInteger6, Integer paramInteger7, Double paramDouble3, Double paramDouble4, Integer paramInteger8, Integer paramInteger9);
  
  public abstract double getBillingOrder(int paramInt, String paramString1, String paramString2);
}
