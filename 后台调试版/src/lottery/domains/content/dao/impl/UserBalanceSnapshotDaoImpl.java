package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserBalanceSnapshotDao;
import lottery.domains.content.entity.SysNotice;
import lottery.domains.content.entity.UserBalanceSnapshot;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserBalanceSnapshotDaoImpl
  implements UserBalanceSnapshotDao
{
  private final String tab = SysNotice.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserBalanceSnapshot> superDao;
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(UserBalanceSnapshot.class, criterions, orders, start, limit);
  }
  
  public boolean add(UserBalanceSnapshot entity)
  {
    return this.superDao.save(entity);
  }
}
