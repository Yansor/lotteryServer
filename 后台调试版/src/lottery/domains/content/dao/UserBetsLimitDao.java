package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBetsLimit;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserBetsLimitDao
{
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract boolean save(UserBetsLimit paramUserBetsLimit);
  
  public abstract boolean update(UserBetsLimit paramUserBetsLimit);
  
  public abstract boolean delete(int paramInt);
  
  public abstract UserBetsLimit getByUserId(int paramInt);
  
  public abstract UserBetsLimit getById(int paramInt);
}
