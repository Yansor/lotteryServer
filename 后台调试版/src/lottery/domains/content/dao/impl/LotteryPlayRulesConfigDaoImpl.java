package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.array.ArrayUtils;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.LotteryPlayRulesConfigDao;
import lottery.domains.content.entity.LotteryPlayRulesConfig;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LotteryPlayRulesConfigDaoImpl
  implements LotteryPlayRulesConfigDao
{
  private final String tab = LotteryPlayRulesConfig.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<LotteryPlayRulesConfig> superDao;
  
  public List<LotteryPlayRulesConfig> listAll()
  {
    String hql = "from " + this.tab + " order by id";
    return this.superDao.list(hql);
  }
  
  public List<LotteryPlayRulesConfig> listByLottery(int lotteryId)
  {
    String hql = "from " + this.tab + " where lotteryId = ?0 order by id";
    Object[] values = { Integer.valueOf(lotteryId) };
    return this.superDao.list(hql, values);
  }
  
  public List<LotteryPlayRulesConfig> listByLotteryAndRule(int lotteryId, List<Integer> ruleIds)
  {
    if (CollectionUtils.isEmpty(ruleIds))
    {
      String hql = "from " + this.tab + " where lotteryId = ?0 order by id";
      Object[] values = { Integer.valueOf(lotteryId) };
      return this.superDao.list(hql, values);
    }
    String hql = "from " + this.tab + " where lotteryId = ?0 and ruleId in (" + ArrayUtils.transInIds(ruleIds) + ") order by id";
    Object[] values = { Integer.valueOf(lotteryId) };
    return this.superDao.list(hql, values);
  }
  
  public LotteryPlayRulesConfig get(int lotteryId, int ruleId)
  {
    String hql = "from " + this.tab + " where lotteryId = ?0 and ruleId = ?1";
    Object[] values = { Integer.valueOf(lotteryId), Integer.valueOf(ruleId) };
    return (LotteryPlayRulesConfig)this.superDao.unique(hql, values);
  }
  
  public boolean save(LotteryPlayRulesConfig entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean update(LotteryPlayRulesConfig entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean updateStatus(int lotteryId, int ruleId, int status)
  {
    String hql = "update from " + this.tab + " set status = ?0 where lotteryId = ?1 and ruleId = ?2";
    Object[] values = { Integer.valueOf(status), Integer.valueOf(lotteryId), Integer.valueOf(ruleId) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateStatus(int ruleId, int status)
  {
    String hql = "update from " + this.tab + " set status = ?0 where ruleId = ?1";
    Object[] values = { Integer.valueOf(status), Integer.valueOf(ruleId) };
    return this.superDao.update(hql, values);
  }
  
  public boolean update(int id, String minNum, String maxNum)
  {
    String hql = "update from " + this.tab + " set minNum = ?0, maxNum = ?1 where id = ?2";
    Object[] values = { minNum, maxNum, Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
}
