package lottery.domains.content.dao.impl;

import java.util.HashMap;
import java.util.List;
import javautils.array.ArrayUtils;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserBillDao;
import lottery.domains.content.entity.HistoryUserBill;
import lottery.domains.content.entity.UserBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserBillDaoImpl
  implements UserBillDao
{
  private final String tab = UserBill.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserBill> superDao;
  @Autowired
  private HibernateSuperDao<HistoryUserBill> dao;
  
  public boolean add(UserBill entity)
  {
    return this.superDao.save(entity);
  }
  
  public UserBill getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserBill)this.superDao.unique(hql, values);
  }
  
  public double getTotalMoney(String sTime, String eTime, int type, int[] refType)
  {
    String hql = "select sum(money) from " + this.tab + " where time >= ?0 and time < ?1 and type = ?2";
    if ((refType != null) && (refType.length > 0)) {
      hql = hql + " and refType in (" + ArrayUtils.transInIds(refType) + ")";
    }
    Object[] values = { sTime, eTime, Integer.valueOf(type) };
    Object result = this.superDao.unique(hql, values);
    return result != null ? ((Number)result).doubleValue() : 0.0D;
  }
  
  public List<UserBill> getLatest(int userId, int type, int count)
  {
    String hql = "from " + this.tab + " where userId = ?0 and type = ?1 order by id desc";
    Object[] values = { Integer.valueOf(userId), Integer.valueOf(type) };
    return this.superDao.list(hql, values, 0, count);
  }
  
  public List<UserBill> listByDateAndType(String date, int type, int[] refType)
  {
    String hql = "from " + this.tab + " where time like ?0 and type = ?1";
    if ((refType != null) && (refType.length > 0)) {
      hql = hql + " and refType in (" + ArrayUtils.transInIds(refType) + ")";
    }
    Object[] values = { "%" + date + "%", Integer.valueOf(type) };
    return this.superDao.list(hql, values);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(UserBill.class, propertyName, criterions, orders, start, limit);
  }
  
  public PageList findHistory(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.dao.findPageList(HistoryUserBill.class, propertyName, criterions, orders, start, limit);
  }
  
  public PageList findNoDemoUserBill(String sql, int start, int limit)
  {
    String hsql = "select b.* from user_bill b, user u where b.user_id = u.id  ";
    PageList pageList = this.superDao.findPageEntityList(hsql + sql, UserBill.class, new HashMap(), start, limit);
    return pageList;
  }
}
