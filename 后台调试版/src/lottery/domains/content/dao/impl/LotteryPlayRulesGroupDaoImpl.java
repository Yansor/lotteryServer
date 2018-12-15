package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.LotteryPlayRulesGroupDao;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LotteryPlayRulesGroupDaoImpl
  implements LotteryPlayRulesGroupDao
{
  private final String tab = LotteryPlayRulesGroup.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<LotteryPlayRulesGroup> superDao;
  
  public List<LotteryPlayRulesGroup> listAll()
  {
    String hql = "from " + this.tab + " order by typeId,sort";
    return this.superDao.list(hql);
  }
  
  public List<LotteryPlayRulesGroup> listByType(int typeId)
  {
    String hql = "from " + this.tab + " where typeId = ?0 order by typeId,sort";
    Object[] values = { Integer.valueOf(typeId) };
    return this.superDao.list(hql, values);
  }
  
  public LotteryPlayRulesGroup getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (LotteryPlayRulesGroup)this.superDao.unique(hql, values);
  }
  
  public boolean update(LotteryPlayRulesGroup entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean updateStatus(int id, int status)
  {
    String hql = "update from " + this.tab + " set status = ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status) };
    return this.superDao.update(hql, values);
  }
}
