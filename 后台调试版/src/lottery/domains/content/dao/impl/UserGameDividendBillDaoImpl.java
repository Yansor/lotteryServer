package lottery.domains.content.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserGameDividendBillDao;
import lottery.domains.content.entity.UserGameDividendBill;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserGameDividendBillDaoImpl
  implements UserGameDividendBillDao
{
  private final String tab = UserGameDividendBill.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserGameDividendBill> superDao;
  
  public PageList search(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(UserGameDividendBill.class, "id", criterions, orders, start, limit);
  }
  
  public double sumUserAmount(List<Integer> userIds, String sTime, String eTime, Double minUserAmount, Double maxUserAmount, Integer status)
  {
    String hql = "select sum(userAmount) from " + this.tab + " where 1=1";
    
    Map<String, Object> params = new HashMap();
    if (CollectionUtils.isNotEmpty(userIds))
    {
      hql = hql + " and userId in :userId";
      params.put("userId", userIds);
    }
    if (StringUtils.isNotEmpty(sTime))
    {
      hql = hql + " and indicateStartDate >= :sTime";
      params.put("sTime", sTime);
    }
    if (StringUtils.isNotEmpty(eTime))
    {
      hql = hql + " and indicateEndDate <= :eTime";
      params.put("eTime", eTime);
    }
    if (minUserAmount != null)
    {
      hql = hql + " and userAmount >= :minUserAmount";
      params.put("minUserAmount", minUserAmount);
    }
    if (maxUserAmount != null)
    {
      hql = hql + " and userAmount <= :maxUserAmount";
      params.put("maxUserAmount", maxUserAmount);
    }
    if (status != null)
    {
      hql = hql + " and status = :status";
      params.put("status", status);
    }
    Object result = this.superDao.uniqueWithParams(hql, params);
    if (result == null) {
      return 0.0D;
    }
    return ((Double)result).doubleValue();
  }
  
  public List<UserGameDividendBill> findByCriteria(List<Criterion> criterions, List<Order> orders)
  {
    return this.superDao.findByCriteria(UserGameDividendBill.class, criterions, orders);
  }
  
  public UserGameDividendBill getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserGameDividendBill)this.superDao.unique(hql, values);
  }
  
  public boolean add(UserGameDividendBill dividendBill)
  {
    return this.superDao.save(dividendBill);
  }
  
  public boolean update(int id, int status, double userAmount, String remarks)
  {
    String hql = "update " + this.tab + " set status = ?1, userAmount = ?2, remarks = ?3 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status), Double.valueOf(userAmount), remarks };
    return this.superDao.update(hql, values);
  }
  
  public boolean del(int id)
  {
    String hql = "delete from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return this.superDao.delete(hql, values);
  }
}
