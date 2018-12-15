package lottery.domains.content.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserBetsOriginalDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserBetsOriginal;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserBetsOriginalDaoImpl
  implements UserBetsOriginalDao
{
  private final String tab = UserBetsOriginal.class.getSimpleName();
  private final String utab = User.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserBetsOriginal> superDao;
  
  public UserBetsOriginal getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserBetsOriginal)this.superDao.unique(hql, values);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(UserBetsOriginal.class, propertyName, criterions, orders, start, limit);
  }
  
  public double[] getTotalMoney(String keyword, Integer userId, Integer utype, Integer type, Integer lotteryId, String expect, Integer ruleId, String minTime, String maxTime, String minPrizeTime, String maxPrizeTime, Double minMoney, Double maxMoney, Integer minMultiple, Integer maxMultiple, Double minPrizeMoney, Double maxPrizeMoney, Integer status)
  {
    String hql = "select sum(b.money), sum(b.prizeMoney) from " + this.tab + " b , " + this.utab + "  u  where b.userId = u.id ";
    
    Map<String, Object> params = new HashMap();
    if (StringUtil.isNotNull(keyword)) {
      if (StringUtil.isInteger(keyword))
      {
        hql = hql + " and (b.id = :id  or b.billno like :billno or b.chaseBillno like :chaseBillno)";
        params.put("b.id", Integer.valueOf(keyword));
        params.put("b.billno", "%" + keyword + "%");
        params.put("b.chaseBillno", "%" + keyword + "%");
      }
      else
      {
        hql = hql + " and (b.billno like :billno or b.chaseBillno like :chaseBillno)";
        params.put("billno", "%" + keyword + "%");
        params.put("chaseBillno", "%" + keyword + "%");
      }
    }
    if (userId != null)
    {
      hql = hql + " and b.userId = :userId";
      params.put("b.userId", userId);
    }
    if (type != null)
    {
      hql = hql + " and b.type = :type";
      params.put("type", type);
    }
    if (utype != null) {
      hql = hql + "  and u.type =" + utype;
    } else {
      hql = hql + "  and u.upid != 0";
    }
    if (lotteryId != null)
    {
      hql = hql + " and b.lotteryId = :lotteryId";
      params.put("lotteryId", lotteryId);
    }
    if (StringUtil.isNotNull(expect))
    {
      hql = hql + " and b.expect like :expect";
      params.put("expect", expect);
    }
    if (ruleId != null)
    {
      hql = hql + " and b.ruleId = :ruleId";
      params.put("ruleId", ruleId);
    }
    if (StringUtil.isNotNull(minTime))
    {
      hql = hql + " and b.time > :minTime";
      params.put("minTime", minTime);
    }
    if (StringUtil.isNotNull(maxTime))
    {
      hql = hql + " and b. time < :maxTime";
      params.put("maxTime", maxTime);
    }
    if (StringUtil.isNotNull(minPrizeTime))
    {
      hql = hql + " and b.prizeTime > :minPrizeTime";
      params.put("minPrizeTime", minPrizeTime);
    }
    if (StringUtil.isNotNull(maxPrizeTime))
    {
      hql = hql + " and b.prizeTime < :maxPrizeTime";
      params.put("maxPrizeTime", maxPrizeTime);
    }
    if (minMoney != null)
    {
      hql = hql + " and b.money >= :minMoney";
      params.put("minMoney", minMoney);
    }
    if (maxMoney != null)
    {
      hql = hql + " and b.money <= :maxMoney";
      params.put("maxMoney", maxMoney);
    }
    if (minMultiple != null)
    {
      hql = hql + " and b.multiple >= :minMultiple";
      params.put("minMultiple", minMultiple);
    }
    if (maxMultiple != null)
    {
      hql = hql + " and b.multiple <= :maxMultiple";
      params.put("maxMultiple", maxMultiple);
    }
    if (minPrizeMoney != null)
    {
      hql = hql + " and b.prizeMoney >= :minPrizeMoney";
      params.put("minPrizeMoney", minPrizeMoney);
    }
    if (maxPrizeMoney != null)
    {
      hql = hql + " and b.prizeMoney <= :maxPrizeMoney";
      params.put("maxPrizeMoney", maxPrizeMoney);
    }
    if (status != null)
    {
      hql = hql + " and b.status = :status";
      params.put("status", status);
    }
    Object result = this.superDao.uniqueWithParams(hql, params);
    if (result == null) {
      return new double[] { 0.0D, 0.0D };
    }
    Object[] results = (Object[])result;
    double totalMoney = results[0] == null ? 0.0D : ((Double)results[0]).doubleValue();
    double totalPrizeMoney = results[1] == null ? 0.0D : ((Double)results[1]).doubleValue();
    return new double[] { totalMoney, totalPrizeMoney };
  }
  
  public PageList find(String sql, int start, int limit)
  {
    String hsql = "select b.* from user_bets_original b, user u where b.user_id = u.id  ";
    PageList pageList = this.superDao.findPageEntityList(hsql + sql, UserBetsOriginal.class, new HashMap(), start, limit);
    return pageList;
  }
}
