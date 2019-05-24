package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.LotteryTypeDao;
import lottery.domains.content.entity.LotteryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LotteryTypeDaoImpl
  implements LotteryTypeDao
{
  private final String tab = LotteryType.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<LotteryType> superDao;
  
  public List<LotteryType> listAll()
  {
    String hql = "from " + this.tab + " order by sort";
    return this.superDao.list(hql);
  }
  
  public boolean updateStatus(int id, int status)
  {
    String hql = "update " + this.tab + " set status = ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status) };
    return this.superDao.update(hql, values);
  }
}
