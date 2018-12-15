package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserBankcardUnbindDao;
import lottery.domains.content.entity.UserBankcardUnbindRecord;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserBankcardUnbindDaoImpl
  implements UserBankcardUnbindDao
{
  public static final String tab = UserBankcardUnbindRecord.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserBankcardUnbindRecord> superDao;
  
  public boolean add(UserBankcardUnbindRecord entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean update(UserBankcardUnbindRecord entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean delByCardId(String cardId)
  {
    String hql = "delete from " + tab + " where cardId = ?0";
    Object[] values = { cardId };
    return this.superDao.delete(hql, values);
  }
  
  public boolean updateByParam(String userIds, String cardId, int unbindNum, String unbindTime)
  {
    String hql = "update " + tab + " set userIds =?0, unbindNum = ?1,unbindTime = ?2 where cardId = ?3";
    Object[] values = { userIds, Integer.valueOf(unbindNum), unbindTime, cardId };
    return this.superDao.update(hql, values);
  }
  
  public UserBankcardUnbindRecord getUnbindInfoById(int id)
  {
    String hql = "from " + tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserBankcardUnbindRecord)this.superDao.unique(hql, values);
  }
  
  public UserBankcardUnbindRecord getUnbindInfoBycardId(String cardId)
  {
    String hql = "from " + tab + " where cardId = ?0";
    Object[] values = { cardId };
    return (UserBankcardUnbindRecord)this.superDao.unique(hql, values);
  }
  
  public PageList search(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(UserBankcardUnbindRecord.class, criterions, orders, start, limit);
  }
  
  public List<UserBankcardUnbindRecord> listAll()
  {
    String hql = "from " + tab;
    return this.superDao.list(hql);
  }
}
