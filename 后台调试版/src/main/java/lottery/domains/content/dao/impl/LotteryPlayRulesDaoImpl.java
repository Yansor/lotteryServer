package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.LotteryPlayRulesDao;
import lottery.domains.content.entity.LotteryPlayRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LotteryPlayRulesDaoImpl
  implements LotteryPlayRulesDao
{
  private final String tab = LotteryPlayRules.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<LotteryPlayRules> superDao;
  
  public List<LotteryPlayRules> listAll()
  {
    String hql = "from " + this.tab + " order by groupId";
    return this.superDao.list(hql);
  }
  
  public List<LotteryPlayRules> listByType(int typeId)
  {
    String hql = "from " + this.tab + " where typeId = ?0 order by groupId";
    Object[] values = { Integer.valueOf(typeId) };
    return this.superDao.list(hql, values);
  }
  
  public List<LotteryPlayRules> listByTypeAndGroup(int typeId, int groupId)
  {
    String hql = "from " + this.tab + " where typeId = ?0 and groupId = ?1 order by groupId";
    Object[] values = { Integer.valueOf(typeId), Integer.valueOf(groupId) };
    return this.superDao.list(hql, values);
  }
  
  public LotteryPlayRules getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (LotteryPlayRules)this.superDao.unique(hql, values);
  }
  
  public boolean update(LotteryPlayRules entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean updateStatus(int id, int status)
  {
    String hql = "update from " + this.tab + " set status = ?0 where id = ?1";
    Object[] values = { Integer.valueOf(status), Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
  
  public boolean update(int id, String minNum, String maxNum)
  {
    String hql = "update from " + this.tab + " set minNum = ?0, maxNum = ?1 where id = ?2";
    Object[] values = { minNum, maxNum, Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
}
