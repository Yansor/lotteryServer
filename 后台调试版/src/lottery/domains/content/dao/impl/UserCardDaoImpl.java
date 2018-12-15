package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserCardDao;
import lottery.domains.content.entity.UserCard;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserCardDaoImpl
  implements UserCardDao
{
  private final String tab = UserCard.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserCard> superDao;
  
  public boolean add(UserCard entity)
  {
    return this.superDao.save(entity);
  }
  
  public UserCard getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserCard)this.superDao.unique(hql, values);
  }
  
  public List<UserCard> getByUserId(int id)
  {
    String hql = "from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(id) };
    return this.superDao.list(hql, values);
  }
  
  public UserCard getByCardId(String cardId)
  {
    String hql = "from " + this.tab + " where cardId = ?0";
    Object[] values = { cardId };
    return (UserCard)this.superDao.unique(hql, values);
  }
  
  public UserCard getByUserAndCardId(int userId, String cardId)
  {
    String hql = "from " + this.tab + " where userId = ?0 and cardId = ?1";
    Object[] values = { Integer.valueOf(userId), cardId };
    List<UserCard> list = this.superDao.list(hql, values);
    return CollectionUtils.isEmpty(list) ? null : (UserCard)list.get(0);
  }
  
  public boolean update(UserCard entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean updateCardName(int userId, String cardName)
  {
    String hql = "update " + this.tab + " set cardName = ?1 where userId = ?0";
    Object[] values = { Integer.valueOf(userId), cardName };
    return this.superDao.update(hql, values);
  }
  
  public boolean delete(int userId)
  {
    String hql = "delete from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    return this.superDao.delete(hql, values);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(UserCard.class, criterions, orders, start, limit);
  }
}
