package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserInfoDao;
import lottery.domains.content.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDaoImpl
  implements UserInfoDao
{
  private final String tab = UserInfo.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserInfo> superDao;
  
  public UserInfo get(int userId)
  {
    String hql = "from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    return (UserInfo)this.superDao.unique(hql, values);
  }
  
  public List<UserInfo> listByBirthday(String date)
  {
    String hql = "from " + this.tab + " where birthday like ?0";
    Object[] values = { "%" + date };
    return this.superDao.list(hql, values);
  }
  
  public boolean add(UserInfo entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean update(UserInfo entity)
  {
    return this.superDao.update(entity);
  }
}
