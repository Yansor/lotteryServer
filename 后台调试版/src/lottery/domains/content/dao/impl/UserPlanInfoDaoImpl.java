package lottery.domains.content.dao.impl;

import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserPlanInfoDao;
import lottery.domains.content.entity.UserPlanInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserPlanInfoDaoImpl
  implements UserPlanInfoDao
{
  private final String tab = UserPlanInfo.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserPlanInfo> superDao;
  
  public boolean add(UserPlanInfo entity)
  {
    return this.superDao.save(entity);
  }
  
  public UserPlanInfo get(int userId)
  {
    String hql = "from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    return (UserPlanInfo)this.superDao.unique(hql, values);
  }
  
  public boolean update(UserPlanInfo entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean update(int userId, int planCount, int prizeCount, double totalMoney, double totalPrize)
  {
    String hql = "update " + this.tab + " set planCount = planCount + ?1, prizeCount = prizeCount + ?2, totalMoney = totalMoney + ?3, totalPrize = totalPrize + ?4 where userId = ?0";
    Object[] values = { Integer.valueOf(userId), Integer.valueOf(planCount), Integer.valueOf(prizeCount), Double.valueOf(totalMoney), Double.valueOf(totalPrize) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateLevel(int userId, int level)
  {
    String hql = "update " + this.tab + " set level = ?1 where userId = ?0";
    Object[] values = { Integer.valueOf(userId), Integer.valueOf(level) };
    return this.superDao.update(hql, values);
  }
}
