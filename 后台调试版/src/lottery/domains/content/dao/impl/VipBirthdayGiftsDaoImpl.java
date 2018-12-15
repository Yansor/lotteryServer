package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.VipBirthdayGiftsDao;
import lottery.domains.content.entity.VipBirthdayGifts;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VipBirthdayGiftsDaoImpl
  implements VipBirthdayGiftsDao
{
  private final String tab = VipBirthdayGifts.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<VipBirthdayGifts> superDao;
  
  public boolean add(VipBirthdayGifts entity)
  {
    return this.superDao.save(entity);
  }
  
  public VipBirthdayGifts getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (VipBirthdayGifts)this.superDao.unique(hql, values);
  }
  
  public int getWaitTodo()
  {
    String hql = "select count(id) from " + this.tab + " where status = 0";
    Object result = this.superDao.unique(hql);
    return result != null ? ((Number)result).intValue() : 0;
  }
  
  public boolean hasRecord(int userId, int year)
  {
    String hql = "from " + this.tab + " where userId = ?0 and birthday like ?1";
    Object[] values = { Integer.valueOf(userId), year + "%" };
    List<VipBirthdayGifts> list = this.superDao.list(hql, values);
    if ((list != null) && (list.size() > 0)) {
      return true;
    }
    return false;
  }
  
  public boolean update(VipBirthdayGifts entity)
  {
    return this.superDao.update(entity);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(VipBirthdayGifts.class, criterions, orders, start, limit);
  }
}
