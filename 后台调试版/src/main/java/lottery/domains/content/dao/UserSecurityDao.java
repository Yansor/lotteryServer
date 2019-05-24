package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserSecurity;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserSecurityDao
{
  public abstract UserSecurity getById(int paramInt);
  
  public abstract List<UserSecurity> getByUserId(int paramInt);
  
  public abstract boolean delete(int paramInt);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract boolean updateValue(int paramInt, String paramString);
}
