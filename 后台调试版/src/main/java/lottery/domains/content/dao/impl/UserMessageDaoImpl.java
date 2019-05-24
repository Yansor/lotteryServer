package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserMessageDao;
import lottery.domains.content.entity.UserMessage;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserMessageDaoImpl
  implements UserMessageDao
{
  private final String tab = UserMessage.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserMessage> superDao;
  
  public UserMessage getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserMessage)this.superDao.unique(hql, values);
  }
  
  public boolean delete(int id)
  {
    String hql = "update " + this.tab + " set toStatus = -1, fromStatus = -1 where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
  
  public PageList search(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(UserMessage.class, propertyName, criterions, orders, start, limit);
  }
  
  public boolean save(UserMessage userMessage)
  {
    return this.superDao.save(userMessage);
  }
  
  public void update(UserMessage message)
  {
    this.superDao.update(message);
  }
}
