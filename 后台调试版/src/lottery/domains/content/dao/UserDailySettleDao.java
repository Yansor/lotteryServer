package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserDailySettle;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserDailySettleDao
{
  public abstract PageList search(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract List<UserDailySettle> list(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract List<UserDailySettle> findByUserIds(List<Integer> paramList);
  
  public abstract UserDailySettle getByUserId(int paramInt);
  
  public abstract UserDailySettle getById(int paramInt);
  
  public abstract void add(UserDailySettle paramUserDailySettle);
  
  public abstract void deleteByUser(int paramInt);
  
  public abstract void deleteByTeam(int paramInt);
  
  public abstract void deleteLowers(int paramInt);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean updateSomeFields(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, String paramString4);
  
  public abstract boolean updateSomeFields(int paramInt1, double paramDouble1, int paramInt2, int paramInt3, int paramInt4, double paramDouble2, double paramDouble3);
  
  public abstract boolean updateTotalAmount(int paramInt, double paramDouble);
}
