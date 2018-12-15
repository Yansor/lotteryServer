package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserBetsSameIpLogDao;
import lottery.domains.content.entity.UserBetsSameIpLog;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserBetsSameIpLogDaoImpl
  implements UserBetsSameIpLogDao
{
  private final String tab = UserBetsSameIpLog.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserBetsSameIpLog> superDao;
  
  public PageList search(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(UserBetsSameIpLog.class, criterions, orders, start, limit);
  }
}
