package lottery.domains.content.dao.impl;

import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserGameAccountDao;
import lottery.domains.content.entity.UserGameAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserGameAccountDaoImpl
  implements UserGameAccountDao
{
  private final String tab = UserGameAccount.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserGameAccount> superDao;
  
  public UserGameAccount get(String platformName, int platformId)
  {
    String hql = "from " + this.tab + " where username=?0 and platformId=?1 and model = 1";
    Object[] values = { platformName, Integer.valueOf(platformId) };
    return (UserGameAccount)this.superDao.unique(hql, values);
  }
  
  public UserGameAccount get(int userId, int platformId)
  {
    String hql = "from " + this.tab + " where userId=?0 and platformId=?1 and model = 1";
    Object[] values = { Integer.valueOf(userId), Integer.valueOf(platformId) };
    return (UserGameAccount)this.superDao.unique(hql, values);
  }
  
  public UserGameAccount save(UserGameAccount account)
  {
    this.superDao.save(account);
    return account;
  }
}
