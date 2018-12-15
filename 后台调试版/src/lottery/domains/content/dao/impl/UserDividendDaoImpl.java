package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.array.ArrayUtils;
import javautils.date.Moment;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserDividendDao;
import lottery.domains.content.entity.UserDividend;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDividendDaoImpl
  implements UserDividendDao
{
  private final String tab = UserDividend.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserDividend> superDao;
  
  public PageList search(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(UserDividend.class, propertyName, criterions, orders, start, limit);
  }
  
  public List<UserDividend> findByUserIds(List<Integer> userIds)
  {
    String hql = "from " + this.tab + " where userId in( " + ArrayUtils.transInIds(userIds) + ")";
    return this.superDao.list(hql);
  }
  
  public UserDividend getByUserId(int userId)
  {
    String hql = "from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    return (UserDividend)this.superDao.unique(hql, values);
  }
  
  public UserDividend getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserDividend)this.superDao.unique(hql, values);
  }
  
  public void add(UserDividend entity)
  {
    this.superDao.save(entity);
  }
  
  public void updateStatus(int id, int status)
  {
    String hql = "update " + this.tab + " set status = ?1, agreeTime = ?2 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status), new Moment().toSimpleTime() };
    this.superDao.update(hql, values);
  }
  
  public boolean updateSomeFields(int id, String scaleLevel, String lossLevel, String salesLevel, int minValidUser, double minScale, double maxScale, String userLevel)
  {
    String hql = "update " + this.tab + " set scaleLevel = ?0, lossLevel = ?1, salesLevel = ?2, minValidUser = ?3, minScale = ?4, maxScale = ?5, agreeTime = ?6, status = ?7, userLevel = ?8 where id = ?9";
    Object[] values = { scaleLevel, lossLevel, salesLevel, Integer.valueOf(minValidUser), Double.valueOf(minScale), Double.valueOf(maxScale), "", Integer.valueOf(2), userLevel, Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateSomeFields(int id, String scaleLevel, String lossLevel, String salesLevel, int minValidUser, int fixed, double minScale, double maxScale, int status)
  {
    String agreeTime = new Moment().toSimpleTime();
    String hql = "update " + this.tab + " set scaleLevel = ?0, lossLevel = ?1, salesLevel = ?2, minValidUser = ?3, fixed = ?4, minScale = ?5, maxScale = ?6, status = ?7, agreeTime = ?8 where id = ?9";
    Object[] values = { scaleLevel, lossLevel, salesLevel, Integer.valueOf(minValidUser), Integer.valueOf(fixed), Double.valueOf(minScale), Double.valueOf(maxScale), Integer.valueOf(status), agreeTime, Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
  
  public void deleteByUser(int userId)
  {
    String hql = "delete from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    this.superDao.delete(hql, values);
  }
  
  public void deleteByTeam(int upUserId)
  {
    String hql = "delete from " + this.tab + " where userId in(select id from User where id = ?0 or upids like ?1)";
    Object[] values = { Integer.valueOf(upUserId), "%[" + upUserId + "]%" };
    this.superDao.delete(hql, values);
  }
  
  public void deleteLowers(int upUserId)
  {
    String hql = "delete from " + this.tab + " where userId in(select id from User where upids like ?0)";
    Object[] values = { "%[" + upUserId + "]%" };
    this.superDao.delete(hql, values);
  }
}
