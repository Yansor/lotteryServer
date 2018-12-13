package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBets;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserBetsDao
{
  public abstract boolean add(UserBets paramUserBets);
  
  public abstract UserBets getById(int paramInt);
  
  public abstract UserBets getById(int paramInt1, int paramInt2);
  
  public abstract List<UserBets> getByChaseBillno(String paramString1, int paramInt, String paramString2);
  
  public abstract List<UserBets> getByFollowBillno(String paramString);
  
  public abstract boolean updateToPlan(int paramInt1, int paramInt2, String paramString);
  
  public abstract List<UserBets> list(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract PageList search(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2, String paramString1, double paramDouble, String paramString2);
  
  public abstract boolean cancel(int paramInt);
  
  public abstract boolean cancelAndSetOpenCode(int paramInt, String paramString);
  
  public abstract List<?> sumUserProfitGroupByUserId(int paramInt, String paramString1, String paramString2, List<Integer> paramList);
  
  public abstract double[] sumProfit(int paramInt, String paramString1, String paramString2);
  
  public abstract List<UserBets> getLatest(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract List<UserBets> getByExpect(int paramInt, String paramString);
  
  public abstract List<UserBets> getByUserIdAndExpect(int paramInt1, int paramInt2, String paramString);
  
  public abstract List<UserBets> getNoDemoUserBetsByExpect(String paramString, Object[] paramArrayOfObject);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/UserBetsDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */