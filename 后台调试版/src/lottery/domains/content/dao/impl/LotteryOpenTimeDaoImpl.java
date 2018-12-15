package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.LotteryOpenTimeDao;
import lottery.domains.content.entity.LotteryOpenTime;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LotteryOpenTimeDaoImpl
  implements LotteryOpenTimeDao
{
  private final String tab = LotteryOpenTime.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<LotteryOpenTime> superDao;
  
  public List<LotteryOpenTime> listAll()
  {
    String hql = "from " + this.tab + " order by expect asc";
    return this.superDao.list(hql);
  }
  
  public List<LotteryOpenTime> list(String lottery)
  {
    String hql = "from " + this.tab + " where lottery = ?0";
    Object[] values = { lottery };
    return this.superDao.list(hql, values);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(LotteryOpenTime.class, criterions, orders, start, limit);
  }
  
  public LotteryOpenTime getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (LotteryOpenTime)this.superDao.unique(hql, values);
  }
  
  public LotteryOpenTime getByLottery(String lottery)
  {
    String hql = "from " + this.tab + " where lottery = ?0";
    Object[] values = { lottery };
    return (LotteryOpenTime)this.superDao.unique(hql, values);
  }
  
  public boolean update(LotteryOpenTime entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean save(LotteryOpenTime entity)
  {
    return this.superDao.save(entity);
  }
}
