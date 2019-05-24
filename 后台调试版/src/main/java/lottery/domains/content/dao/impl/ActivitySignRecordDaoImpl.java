package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivitySignRecordDao;
import lottery.domains.content.entity.ActivitySignRecord;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivitySignRecordDaoImpl
  implements ActivitySignRecordDao
{
  @Autowired
  private HibernateSuperDao<ActivitySignRecord> superDao;
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(ActivitySignRecord.class, criterions, orders, start, limit);
  }
}
