package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserWithdrawLogDao;
import lottery.domains.content.entity.UserWithdrawLog;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserWithdrawLogDaoImpl
  implements UserWithdrawLogDao
{
  private final String tab = UserWithdrawLogDao.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserWithdrawLog> superDao;
  
  public boolean add(UserWithdrawLog entity)
  {
    return this.superDao.save(entity);
  }
  
  public List<UserWithdrawLog> getByUserId(int userId, int tpye)
  {
    String hql = "from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    return this.superDao.list(hql, values);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(UserWithdrawLog.class, propertyName, criterions, orders, start, limit);
  }
}
