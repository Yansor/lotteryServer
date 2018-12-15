package lottery.domains.content.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserLoginLogDao;
import lottery.domains.content.entity.HistoryUserLoginLog;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserLoginLog;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserLoginLogDaoImpl
  implements UserLoginLogDao
{
  private final String tab = UserLoginLog.class.getSimpleName();
  private final String utab = User.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<HistoryUserLoginLog> historySuperDao;
  @Autowired
  private HibernateSuperDao<UserLoginLog> superDao;
  
  public List<UserLoginLog> getByUserId(int userId)
  {
    String hql = "from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    return this.superDao.list(hql, values);
  }
  
  public UserLoginLog getLastLogin(int userId)
  {
    String hql = "from " + this.tab + " where userId = ?0 order by id desc";
    Object[] values = { Integer.valueOf(userId) };
    List<UserLoginLog> list = this.superDao.list(hql, values, 0, 1);
    if ((list != null) && (list.size() > 0)) {
      return (UserLoginLog)list.get(0);
    }
    return null;
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(UserLoginLog.class, propertyName, criterions, orders, start, limit);
  }
  
  public PageList findHistory(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.historySuperDao.findPageList(HistoryUserLoginLog.class, propertyName, criterions, orders, start, limit);
  }
  
  public List<?> getDayUserLogin(String sTime, String eTime)
  {
    String hql = "select substring(l.time, 1, 10), count(distinct l.userId) from " + this.tab + "  l  , " + this.utab + " u  where l.userId = u.id and  l.time >= ?0 and l.time < ?1  and u.upid !=?2 group by substring(l.time, 1, 10)";
    Object[] values = { sTime, eTime, Integer.valueOf(0) };
    return this.superDao.listObject(hql, values);
  }
  
  public PageList searchSameIp(Integer userId, String ip, int start, int limit)
  {
    Map<String, Object> params = new HashMap();
    if ((userId == null) && (StringUtils.isEmpty(ip))) {
      return null;
    }
    String sql;
    if ((userId != null) && (StringUtils.isEmpty(ip)))
    {
      sql = "select t1.ip,ull.address,group_concat(distinct u.username) from (select distinct ip from user_login_log where user_id = :userId and id > 0 limit 0,1000) t1,user_login_log ull,user u where t1.ip = ull.ip and ull.user_id = u.id group by t1.ip,ull.address";
      params.put("userId", userId);
    }
    else if ((userId == null) && (StringUtils.isNotEmpty(ip)))
    {
      sql = "select t1.ip,ull.address,group_concat(distinct u.username) from (select distinct ip from user_login_log where ip = :ip and id > 0 limit 0,1000) t1,user_login_log ull,user u where t1.ip = ull.ip and ull.user_id = u.id group by t1.ip,ull.address";
      params.put("ip", ip);
    }
    else
    {
      sql = "select t1.ip,ull.address,group_concat(distinct u.username) from (select distinct ip from user_login_log where user_id = :userId and ip = :ip and id > 0 limit 0,1000) t1,user_login_log ull,user u where t1.ip = ull.ip and ull.user_id = u.id group by t1.ip,ull.address";
      params.put("userId", userId);
      params.put("ip", ip);
    }
    return this.superDao.findPageList(sql, params, start, limit);
  }
}
