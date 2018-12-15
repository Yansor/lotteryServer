package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.array.ArrayUtils;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserDailySettleDao;
import lottery.domains.content.entity.UserDailySettle;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDailySettleDaoImpl
  implements UserDailySettleDao
{
  private final String tab = UserDailySettle.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserDailySettle> superDao;
  
  public PageList search(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(UserDailySettle.class, propertyName, criterions, orders, start, limit);
  }
  
  public List<UserDailySettle> list(List<Criterion> criterions, List<Order> orders)
  {
    return this.superDao.findByCriteria(UserDailySettle.class, criterions, orders);
  }
  
  public List<UserDailySettle> findByUserIds(List<Integer> userIds)
  {
    String hql = "from " + this.tab + " where userId in( " + ArrayUtils.transInIds(userIds) + ")";
    return this.superDao.list(hql);
  }
  
  public UserDailySettle getByUserId(int userId)
  {
    String hql = "from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    return (UserDailySettle)this.superDao.unique(hql, values);
  }
  
  public UserDailySettle getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserDailySettle)this.superDao.unique(hql, values);
  }
  
  public void add(UserDailySettle entity)
  {
    this.superDao.save(entity);
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
  
  public boolean updateStatus(int id, int status)
  {
    String hql = "update " + this.tab + " set status = ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateSomeFields(int id, String scaleLevel, String lossLevel, String salesLevel, int minValidUser, String userLevel)
  {
    String hql = "update " + this.tab + " set scaleLevel = ?0,lossLevel=?1,salesLevel=?2, minValidUser = ?3, userLevel = ?4, minScale=?5,maxScale=?6 where id = ?7";
    String[] scaleArr = scaleLevel.split(",");
    Object[] values = { scaleLevel, lossLevel, salesLevel, Integer.valueOf(minValidUser), userLevel, Double.valueOf(scaleArr[0]), Double.valueOf(scaleArr[(scaleArr.length - 1)]), Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateSomeFields(int id, double scale, int minValidUser, int status, int fixed, double minScale, double maxScale)
  {
    String hql = "update " + this.tab + " set scale = ?0, minValidUser = ?1, status = ?2, fixed = ?3, minScale = ?4, maxScale = ?5 where id = ?6";
    Object[] values = { Double.valueOf(scale), Integer.valueOf(minValidUser), Integer.valueOf(status), Integer.valueOf(fixed), Double.valueOf(minScale), Double.valueOf(maxScale), Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateTotalAmount(int userId, double amount)
  {
    String hql = "update " + this.tab + " set totalAmount = totalAmount + ?1 where userId = ?0";
    Object[] values = { Integer.valueOf(userId), Double.valueOf(amount) };
    return this.superDao.update(hql, values);
  }
}
