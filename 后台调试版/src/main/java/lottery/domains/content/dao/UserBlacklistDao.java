package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBlacklist;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserBlacklistDao
{
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract boolean add(UserBlacklist paramUserBlacklist);
  
  public abstract boolean delete(int paramInt);
  
  public abstract int getByIp(String paramString);
}
