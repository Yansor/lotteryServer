package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.LotteryCrawlerStatusDao;
import lottery.domains.content.entity.LotteryCrawlerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LotteryCrawlerStatusDaoImpl
  implements LotteryCrawlerStatusDao
{
  private final String tab = LotteryCrawlerStatus.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<LotteryCrawlerStatus> superDao;
  
  public List<LotteryCrawlerStatus> listAll()
  {
    String hql = "from " + this.tab;
    return this.superDao.list(hql);
  }
  
  public LotteryCrawlerStatus get(String shortName)
  {
    String hql = "from " + this.tab + " where shortName = ?0";
    Object[] values = { shortName };
    return (LotteryCrawlerStatus)this.superDao.unique(hql, values);
  }
  
  public boolean update(LotteryCrawlerStatus entity)
  {
    return this.superDao.update(entity);
  }
}
