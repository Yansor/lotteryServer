package lottery.domains.content.dao.impl;

import java.util.HashMap;
import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserHighPrizeDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserHighPrize;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserHighPrizeDaoImpl
  implements UserHighPrizeDao
{
  private final String tab = UserHighPrize.class.getSimpleName();
  private final String utab = User.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserHighPrize> superDao;
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(UserHighPrize.class, propertyName, criterions, orders, start, limit);
  }
  
  public UserHighPrize getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserHighPrize)this.superDao.unique(hql, values);
  }
  
  public boolean add(UserHighPrize entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean updateStatus(int id, int status)
  {
    String hql = "update " + this.tab + " set status = ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateStatusAndConfirmUsername(int id, int status, String confirmUsername)
  {
    String hql = "update " + this.tab + " set status = ?1,confirmUsername=?2 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status), confirmUsername };
    return this.superDao.update(hql, values);
  }
  
  public int getUnProcessCount()
  {
    String hql = "select count(b.id) from " + this.tab + " b  ," + this.utab + " u  where u.id = b.userId  and u.upid !=0 and b.status = 0";
    Object result = this.superDao.unique(hql);
    return result != null ? ((Number)result).intValue() : 0;
  }
  
  public PageList find(String sql, int start, int limit)
  {
    String hsql = "select b.* from user_high_prize b, user u where b.user_id = u.id  ";
    PageList pageList = this.superDao.findPageEntityList(hsql + sql, UserHighPrize.class, new HashMap(), start, limit);
    return pageList;
  }
}
