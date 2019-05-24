package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserLoginLog;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserLoginLogDao
{
  public abstract List<UserLoginLog> getByUserId(int paramInt);
  
  public abstract UserLoginLog getLastLogin(int paramInt);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract PageList findHistory(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract List<?> getDayUserLogin(String paramString1, String paramString2);
  
  public abstract PageList searchSameIp(Integer paramInteger, String paramString, int paramInt1, int paramInt2);
}
