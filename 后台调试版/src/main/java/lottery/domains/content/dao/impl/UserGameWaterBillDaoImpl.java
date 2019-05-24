package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserGameWaterBillDao;
import lottery.domains.content.entity.UserGameWaterBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserGameWaterBillDaoImpl
  implements UserGameWaterBillDao
{
  private final String tab = UserGameWaterBill.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserGameWaterBill> superDao;
  
  public PageList search(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(UserGameWaterBill.class, propertyName, criterions, orders, start, limit);
  }
  
  public boolean add(UserGameWaterBill bill)
  {
    return this.superDao.save(bill);
  }
}
