package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.VipUpgradeGiftsDao;
import lottery.domains.content.entity.VipUpgradeGifts;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VipUpgradeGiftsDaoImpl
  implements VipUpgradeGiftsDao
{
  private final String tab = VipUpgradeGifts.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<VipUpgradeGifts> superDao;
  
  public boolean add(VipUpgradeGifts entity)
  {
    return this.superDao.save(entity);
  }
  
  public VipUpgradeGifts getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (VipUpgradeGifts)this.superDao.unique(hql, values);
  }
  
  public int getWaitTodo()
  {
    String hql = "select count(id) from " + this.tab + " where status = 0";
    Object result = this.superDao.unique(hql);
    return result != null ? ((Number)result).intValue() : 0;
  }
  
  public boolean hasRecord(int userId, int beforeLevel, int afterLevel)
  {
    String hql = "from " + this.tab + " where userId = ?0 and beforeLevel = ?1 and afterLevel = ?2";
    Object[] values = { Integer.valueOf(userId), Integer.valueOf(beforeLevel), Integer.valueOf(afterLevel) };
    List<VipUpgradeGifts> list = this.superDao.list(hql, values);
    if ((list != null) && (list.size() > 0)) {
      return true;
    }
    return false;
  }
  
  public boolean update(VipUpgradeGifts entity)
  {
    return this.superDao.update(entity);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(VipUpgradeGifts.class, criterions, orders, start, limit);
  }
}
