package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserDailySettleBillDao;
import lottery.domains.content.entity.UserDailySettleBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDailySettleBillDaoImpl
  implements UserDailySettleBillDao
{
  private final String tab = UserDailySettleBill.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserDailySettleBill> superDao;
  
  public PageList search(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(UserDailySettleBill.class, propertyName, criterions, orders, start, limit);
  }
  
  public List<UserDailySettleBill> findByCriteria(List<Criterion> criterions, List<Order> orders)
  {
    return this.superDao.findByCriteria(UserDailySettleBill.class, criterions, orders);
  }
  
  public boolean add(UserDailySettleBill settleBill)
  {
    return this.superDao.save(settleBill);
  }
  
  public UserDailySettleBill getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserDailySettleBill)this.superDao.unique(hql, values);
  }
  
  public boolean update(UserDailySettleBill settleBill)
  {
    return this.superDao.update(settleBill);
  }
}
