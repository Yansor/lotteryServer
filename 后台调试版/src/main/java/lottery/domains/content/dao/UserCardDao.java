package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserCard;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserCardDao
{
  public abstract boolean add(UserCard paramUserCard);
  
  public abstract UserCard getById(int paramInt);
  
  public abstract List<UserCard> getByUserId(int paramInt);
  
  public abstract UserCard getByCardId(String paramString);
  
  public abstract UserCard getByUserAndCardId(int paramInt, String paramString);
  
  public abstract boolean update(UserCard paramUserCard);
  
  public abstract boolean updateCardName(int paramInt, String paramString);
  
  public abstract boolean delete(int paramInt);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
}
