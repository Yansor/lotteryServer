package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserWithdrawLimitDao;
import lottery.domains.content.entity.UserWithdrawLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserWithdrawLimitDaoImpl
  implements UserWithdrawLimitDao
{
  private final String tab = UserWithdrawLimit.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserWithdrawLimit> superDao;
  
  public UserWithdrawLimit getByUserId(int userId)
  {
    String hql = "from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    return (UserWithdrawLimit)this.superDao.unique(hql, values);
  }
  
  public boolean add(UserWithdrawLimit entity)
  {
    return this.superDao.save(entity);
  }
  
  public List<UserWithdrawLimit> getUserWithdrawLimits(int userId, String time)
  {
    String hql = "from " + this.tab + " where userId = ?0 and rechargeTime <= ?1 order by rechargeTime asc";
    Object[] values = { Integer.valueOf(userId), time };
    return this.superDao.list(hql, values);
  }
  
  public boolean delByUserId(int userId)
  {
    String hql = "delete from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    this.superDao.delete(hql, values);
    return true;
  }
}
