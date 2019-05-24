package lottery.domains.content.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserDividendBillDao;
import lottery.domains.content.entity.UserDividendBill;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDividendBillDaoImpl
  implements UserDividendBillDao
{
  private final String tab = UserDividendBill.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserDividendBill> superDao;
  
  public PageList search(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(UserDividendBill.class, propertyName, criterions, orders, start, limit);
  }
  
  public double[] sumUserAmount(List<Integer> userIds, String sTime, String eTime, Double minUserAmount, Double maxUserAmount)
  {
    String hql = "select sum(case when issueType = 1 and status in (1,2,3,6) and totalLoss < 0 then totalLoss else 0 end), sum(case when issueType = 1 and status in (1,2,3,6) then calAmount else 0 end),sum(case when issueType = 2 and status in (1,2,3,6,7) then calAmount else 0 end) from " + this.tab + " where 1=1";
    
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
    Object result = this.superDao.uniqueWithParams(hql, params);
    if (result == null) {
      return new double[] { 0.0D, 0.0D };
    }
    Object[] results = (Object[])result;
    int index = 0;
    double totalLoss = results[index] == null ? 0.0D : ((Double)results[index]).doubleValue();index++;
    double platformTotalUserAmount = results[index] == null ? 0.0D : ((Double)results[index]).doubleValue();index++;
    double upperTotalUserAmount = results[index] == null ? 0.0D : ((Double)results[index]).doubleValue();index++;
    return new double[] { totalLoss, platformTotalUserAmount, upperTotalUserAmount };
  }
  
  public List<UserDividendBill> findByCriteria(List<Criterion> criterions, List<Order> orders)
  {
    return this.superDao.findByCriteria(UserDividendBill.class, criterions, orders);
  }
  
  public boolean updateAllExpire()
  {
    String hql = "update " + this.tab + " set status = 8 where status in (3, 6, 7)";
    Object[] values = new Object[0];
    return this.superDao.update(hql, values);
  }
  
  public UserDividendBill getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserDividendBill)this.superDao.unique(hql, values);
  }
  
  public UserDividendBill getByUserId(int userId, String indicateStartDate, String indicateEndDate)
  {
    String hql = "from " + this.tab + " where userId = ?0 and indicateStartDate = ?1 and indicateEndDate = ?2";
    Object[] values = { Integer.valueOf(userId), indicateStartDate, indicateEndDate };
    return (UserDividendBill)this.superDao.unique(hql, values);
  }
  
  public boolean add(UserDividendBill dividendBill)
  {
    return this.superDao.save(dividendBill);
  }
  
  public boolean addAvailableMoney(int id, double money)
  {
    String hql = "update " + this.tab + " set availableAmount = availableAmount+?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Double.valueOf(money) };
    return this.superDao.update(hql, values);
  }
  
  public boolean addUserAmount(int id, double money)
  {
    String hql = "update " + this.tab + " set userAmount = userAmount+?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Double.valueOf(money) };
    return this.superDao.update(hql, values);
  }
  
  public boolean setAvailableMoney(int id, double money)
  {
    String hql = "update " + this.tab + " set availableAmount = ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Double.valueOf(money) };
    return this.superDao.update(hql, values);
  }
  
  public boolean addTotalReceived(int id, double money)
  {
    String hql = "update " + this.tab + " set totalReceived = totalReceived+?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Double.valueOf(money) };
    return this.superDao.update(hql, values);
  }
  
  public boolean addLowerPaidAmount(int id, double money)
  {
    String hql = "update " + this.tab + " set lowerPaidAmount = lowerPaidAmount+?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Double.valueOf(money) };
    return this.superDao.update(hql, values);
  }
  
  public boolean update(UserDividendBill dividendBill)
  {
    return this.superDao.update(dividendBill);
  }
  
  public boolean update(int id, int status, String remarks)
  {
    String hql = "update " + this.tab + " set status = ?1, remarks = ?2 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status), remarks };
    return this.superDao.update(hql, values);
  }
  
  public boolean update(int id, int status, double availableAmount, String remarks)
  {
    String hql = "update " + this.tab + " set status = ?1, availableAmount = ?2, remarks = ?3 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status), Double.valueOf(availableAmount), remarks };
    return this.superDao.update(hql, values);
  }
  
  public boolean del(int id)
  {
    String hql = "delete from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return this.superDao.delete(hql, values);
  }
  
  public boolean updateStatus(int id, int status)
  {
    String hql = "update " + this.tab + " set status = ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateStatus(int id, int status, String remarks)
  {
    String hql = "update " + this.tab + " set status = ?1, remarks = ?2 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status), remarks };
    return this.superDao.update(hql, values);
  }
}
