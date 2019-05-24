package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.LotteryPlayRulesGroupConfigDao;
import lottery.domains.content.entity.LotteryPlayRulesGroupConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LotteryPlayRulesGroupConfigDaoImpl
  implements LotteryPlayRulesGroupConfigDao
{
  private final String tab = LotteryPlayRulesGroupConfig.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<LotteryPlayRulesGroupConfig> superDao;
  
  public List<LotteryPlayRulesGroupConfig> listAll()
  {
    String hql = "from " + this.tab + " order by id";
    return this.superDao.list(hql);
  }
  
  public List<LotteryPlayRulesGroupConfig> listByLottery(int lotteryId)
  {
    String hql = "from " + this.tab + " where lotteryId = ?0 order by id";
    Object[] values = { Integer.valueOf(lotteryId) };
    return this.superDao.list(hql, values);
  }
  
  public LotteryPlayRulesGroupConfig get(int lotteryId, int groupId)
  {
    String hql = "from " + this.tab + " where lotteryId = ?0 and groupId = ?1";
    Object[] values = { Integer.valueOf(lotteryId), Integer.valueOf(groupId) };
    return (LotteryPlayRulesGroupConfig)this.superDao.unique(hql, values);
  }
  
  public boolean save(LotteryPlayRulesGroupConfig entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean update(LotteryPlayRulesGroupConfig entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean updateStatus(int lotteryId, int groupId, int status)
  {
    String hql = "update from " + this.tab + " set status = ?0 where lotteryId = ?1 and groupId = ?2";
    Object[] values = { Integer.valueOf(status), Integer.valueOf(lotteryId), Integer.valueOf(groupId) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateStatus(int groupId, int status)
  {
    String hql = "update from " + this.tab + " set status = ?0 where groupId = ?1";
    Object[] values = { Integer.valueOf(status), Integer.valueOf(groupId) };
    return this.superDao.update(hql, values);
  }
}
