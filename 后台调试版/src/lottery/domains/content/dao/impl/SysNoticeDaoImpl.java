package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.SysNoticeDao;
import lottery.domains.content.entity.SysNotice;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SysNoticeDaoImpl
  implements SysNoticeDao
{
  private final String tab = SysNotice.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<SysNotice> superDao;
  
  public SysNotice getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (SysNotice)this.superDao.unique(hql, values);
  }
  
  public boolean add(SysNotice entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean update(SysNotice entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean delete(int id)
  {
    String hql = "delete from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return this.superDao.delete(hql, values);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(SysNotice.class, criterions, orders, start, limit);
  }
}
