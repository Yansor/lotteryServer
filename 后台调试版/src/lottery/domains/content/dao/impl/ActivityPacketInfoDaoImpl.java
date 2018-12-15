package lottery.domains.content.dao.impl;

import java.util.List;
import java.util.Map;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivityPacketInfoDao;
import lottery.domains.content.entity.ActivityPacketInfo;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityPacketInfoDaoImpl
  implements ActivityPacketInfoDao
{
  private final String tab = ActivityPacketInfo.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<ActivityPacketInfo> superDao;
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(ActivityPacketInfo.class, criterions, orders, start, limit);
  }
  
  public boolean save(ActivityPacketInfo entity)
  {
    return this.superDao.save(entity);
  }
  
  public List<Map<Integer, Double>> statTotal()
  {
    String hql = "select type , sum(amount) from " + this.tab + " group by type";
    Object[] values = new Object[0];
    return (List<Map<Integer, Double>>)this.superDao.listObject(hql, values);
  }
}
