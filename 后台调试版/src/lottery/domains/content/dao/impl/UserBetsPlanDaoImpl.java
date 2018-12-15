package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserBetsPlanDao;
import lottery.domains.content.entity.UserBetsPlan;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserBetsPlanDaoImpl
  implements UserBetsPlanDao
{
  private final String tab = UserBetsPlan.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserBetsPlan> superDao;
  
  public boolean add(UserBetsPlan entity)
  {
    return this.superDao.save(entity);
  }
  
  public UserBetsPlan get(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserBetsPlan)this.superDao.unique(hql, values);
  }
  
  public UserBetsPlan get(int id, int userId)
  {
    String hql = "from " + this.tab + " where id = ?0 and userId = ?1";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(userId) };
    return (UserBetsPlan)this.superDao.unique(hql, values);
  }
  
  public boolean hasRecord(int userId, int lotteryId, String expect)
  {
    String hql = "from " + this.tab + " where userId = ?0 and lotteryId = ?1 and expect = ?2";
    Object[] values = { Integer.valueOf(userId), Integer.valueOf(lotteryId), expect };
    List<UserBetsPlan> list = this.superDao.list(hql, values);
    if ((list != null) && (list.size() > 0)) {
      return true;
    }
    return false;
  }
  
  public boolean updateCount(int id, int count)
  {
    String hql = "update " + this.tab + " set count = count + ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(count) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateStatus(int id, int status)
  {
    String hql = "update " + this.tab + " set status = ?1 where id = ?0 and status = 0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateStatus(int id, int status, double prizeMoney)
  {
    String hql = "update " + this.tab + " set status = ?1, prizeMoney = ?2 where id = ?0 and status = 0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status), Double.valueOf(prizeMoney) };
    return this.superDao.update(hql, values);
  }
  
  public List<UserBetsPlan> listUnsettled()
  {
    String hql = "from " + this.tab + " where status = 0";
    return this.superDao.list(hql);
  }
  
  public List<UserBetsPlan> list(int lotteryId, String expect)
  {
    String hql = "from " + this.tab + " where lotteryId = ?0 and expect = ?1";
    Object[] values = { Integer.valueOf(lotteryId), expect };
    return this.superDao.list(hql, values);
  }
  
  public PageList search(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(UserBetsPlan.class, propertyName, criterions, orders, start, limit);
  }
}
