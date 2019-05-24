package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.VipUpgradeListDao;
import lottery.domains.content.entity.VipUpgradeList;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VipUpgradeListDaoImpl
  implements VipUpgradeListDao
{
  private final String tab = VipUpgradeList.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<VipUpgradeList> superDao;
  
  public boolean add(VipUpgradeList entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean hasRecord(int userId, String month)
  {
    String hql = "from " + this.tab + " where userId = ?0 and month = ?1";
    Object[] values = { Integer.valueOf(userId), month };
    List<VipUpgradeList> list = this.superDao.list(hql, values);
    if ((list != null) && (list.size() > 0)) {
      return true;
    }
    return false;
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(VipUpgradeList.class, criterions, orders, start, limit);
  }
}
