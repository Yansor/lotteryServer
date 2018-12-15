package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.VipFreeChipsDao;
import lottery.domains.content.entity.VipFreeChips;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VipFreeChipsDaoImpl
  implements VipFreeChipsDao
{
  private final String tab = VipFreeChips.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<VipFreeChips> superDao;
  
  public boolean add(VipFreeChips entity)
  {
    return this.superDao.save(entity);
  }
  
  public VipFreeChips getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (VipFreeChips)this.superDao.unique(hql, values);
  }
  
  public List<VipFreeChips> getUntreated()
  {
    String hql = "from " + this.tab + " where status = 0";
    return this.superDao.list(hql);
  }
  
  public boolean hasRecord(int userId, String sTime, String eTime)
  {
    String hql = "from " + this.tab + " where userId = ?0 and startTime = ?1 and endTime = ?2";
    Object[] values = { Integer.valueOf(userId), sTime, eTime };
    List<VipFreeChips> list = this.superDao.list(hql, values);
    if ((list != null) && (list.size() > 0)) {
      return true;
    }
    return false;
  }
  
  public boolean update(VipFreeChips entity)
  {
    return this.superDao.update(entity);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(VipFreeChips.class, criterions, orders, start, limit);
  }
}
