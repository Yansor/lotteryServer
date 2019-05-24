package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserBetsHitRankingDao;
import lottery.domains.content.entity.UserBetsHitRanking;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserBetsHitRankingDaoImpl
  implements UserBetsHitRankingDao
{
  private final String tab = UserBetsHitRanking.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserBetsHitRanking> superDao;
  
  public UserBetsHitRanking getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserBetsHitRanking)this.superDao.unique(hql, values);
  }
  
  public boolean add(UserBetsHitRanking entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean update(UserBetsHitRanking entity)
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
    return this.superDao.findPageList(UserBetsHitRanking.class, criterions, orders, start, limit);
  }
}
