package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserBaccaratReportDao;
import lottery.domains.content.entity.UserBaccaratReport;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserBaccaratReportDaoImpl
  implements UserBaccaratReportDao
{
  private final String tab = UserBaccaratReport.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserBaccaratReport> superDao;
  
  public boolean add(UserBaccaratReport entity)
  {
    return this.superDao.save(entity);
  }
  
  public UserBaccaratReport get(int userId, String time)
  {
    String hql = "from " + this.tab + " where userId = ?0 and time = ?1";
    Object[] values = { Integer.valueOf(userId), time };
    return (UserBaccaratReport)this.superDao.unique(hql, values);
  }
  
  public boolean update(UserBaccaratReport entity)
  {
    String hql = "update " + this.tab + " set transIn = transIn + ?1, transOut = transOut + ?2, spend = spend + ?3, prize = prize + ?4, waterReturn = waterReturn + ?5, proxyReturn = proxyReturn + ?6, cancelOrder = cancelOrder + ?7, activity = activity + ?8, billingOrder = billingOrder + ?9 where id = ?0";
    Object[] values = { Integer.valueOf(entity.getId()), Double.valueOf(entity.getTransIn()), Double.valueOf(entity.getTransOut()), Double.valueOf(entity.getSpend()), Double.valueOf(entity.getPrize()), Double.valueOf(entity.getWaterReturn()), Double.valueOf(entity.getProxyReturn()), Double.valueOf(entity.getCancelOrder()), Double.valueOf(entity.getActivity()), Double.valueOf(entity.getBillingOrder()) };
    return this.superDao.update(hql, values);
  }
  
  public List<UserBaccaratReport> find(List<Criterion> criterions, List<Order> orders)
  {
    return this.superDao.findByCriteria(UserBaccaratReport.class, criterions, orders);
  }
}
