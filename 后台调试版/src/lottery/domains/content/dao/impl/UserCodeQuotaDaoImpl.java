package lottery.domains.content.dao.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.array.ArrayUtils;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserCodeQuotaDao;
import lottery.domains.content.entity.UserCodeQuota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserCodeQuotaDaoImpl
  implements UserCodeQuotaDao
{
  private final String tab = UserCodeQuota.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserCodeQuota> superDao;
  
  public boolean add(UserCodeQuota entity)
  {
    return this.superDao.save(entity);
  }
  
  public UserCodeQuota get(int userId)
  {
    String hql = "from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    return (UserCodeQuota)this.superDao.unique(hql, values);
  }
  
  public List<UserCodeQuota> list(int[] userIds)
  {
    List<UserCodeQuota> result = new ArrayList();
    if (userIds.length > 0)
    {
      String hql = "from " + this.tab + " where userId in (" + ArrayUtils.transInIds(userIds) + ")";
      result = this.superDao.list(hql);
    }
    return result;
  }
  
  public boolean update(UserCodeQuota entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean delete(int userId)
  {
    String hql = "delete from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    return this.superDao.delete(hql, values);
  }
}
