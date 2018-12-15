package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.array.ArrayUtils;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserSysMessageDao;
import lottery.domains.content.entity.UserSysMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserSysMessageDaoImpl
  implements UserSysMessageDao
{
  private final String tab = UserSysMessage.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserSysMessage> superDao;
  
  public boolean add(UserSysMessage entity)
  {
    return this.superDao.save(entity);
  }
  
  public List<UserSysMessage> listUnread(int userId)
  {
    String hql = "from " + this.tab + " where userId = ?0 and status = 0";
    Object[] values = { Integer.valueOf(userId) };
    return this.superDao.list(hql, values);
  }
  
  public boolean updateUnread(int userId, int[] ids)
  {
    if (ids.length > 0)
    {
      String hql = "update " + this.tab + " set status = 1 where userId = ?0 and id in (" + ArrayUtils.transInIds(ids) + ")";
      Object[] values = { Integer.valueOf(userId) };
      return this.superDao.update(hql, values);
    }
    return false;
  }
}
