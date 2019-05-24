package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserDividend;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserDividendDao
{
  public abstract PageList search(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract List<UserDividend> findByUserIds(List<Integer> paramList);
  
  public abstract UserDividend getByUserId(int paramInt);
  
  public abstract UserDividend getById(int paramInt);
  
  public abstract void add(UserDividend paramUserDividend);
  
  public abstract void updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean updateSomeFields(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, double paramDouble1, double paramDouble2, String paramString4);
  
  public abstract boolean updateSomeFields(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, int paramInt4);
  
  public abstract void deleteByUser(int paramInt);
  
  public abstract void deleteByTeam(int paramInt);
  
  public abstract void deleteLowers(int paramInt);
}
