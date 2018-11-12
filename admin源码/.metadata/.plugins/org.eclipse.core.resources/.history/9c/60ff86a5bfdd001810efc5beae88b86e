package lottery.domains.content.dao.impl;

import java.util.Map;
import org.apache.commons.lang.StringUtils;
import java.util.HashMap;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserLoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.HistoryUserLoginLog;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserLoginLogDao;

@Repository
public class UserLoginLogDaoImpl implements UserLoginLogDao
{
    private final String tab;
    private final String utab;
    @Autowired
    private HibernateSuperDao<HistoryUserLoginLog> historySuperDao;
    @Autowired
    private HibernateSuperDao<UserLoginLog> superDao;
    
    public UserLoginLogDaoImpl() {
        this.tab = UserLoginLog.class.getSimpleName();
        this.utab = User.class.getSimpleName();
    }
    
    @Override
    public List<UserLoginLog> getByUserId(final int userId) {
        final String hql = "from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public UserLoginLog getLastLogin(final int userId) {
        final String hql = "from " + this.tab + " where userId = ?0 order by id desc";
        final Object[] values = { userId };
        final List<UserLoginLog> list = this.superDao.list(hql, values, 0, 1);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(UserLoginLog.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public PageList findHistory(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.historySuperDao.findPageList(HistoryUserLoginLog.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public List<?> getDayUserLogin(final String sTime, final String eTime) {
        final String hql = "select substring(l.time, 1, 10), count(distinct l.userId) from " + this.tab + "  l  , " + this.utab + " u  where l.userId = u.id and  l.time >= ?0 and l.time < ?1  and u.upid !=?2 group by substring(l.time, 1, 10)";
        final Object[] values = { sTime, eTime, 0 };
        return this.superDao.listObject(hql, values);
    }
    
    @Override
    public PageList searchSameIp(final Integer userId, final String ip, final int start, final int limit) {
        final Map<String, Object> params = new HashMap<String, Object>();
        if (userId == null && StringUtils.isEmpty(ip)) {
            return null;
        }
        String sql;
        if (userId != null && StringUtils.isEmpty(ip)) {
            sql = "select t1.ip,ull.address,group_concat(distinct u.username) from (select distinct ip from user_login_log where user_id = :userId and id > 0 limit 0,1000) t1,user_login_log ull,user u where t1.ip = ull.ip and ull.user_id = u.id group by t1.ip,ull.address";
            params.put("userId", userId);
        }
        else if (userId == null && StringUtils.isNotEmpty(ip)) {
            sql = "select t1.ip,ull.address,group_concat(distinct u.username) from (select distinct ip from user_login_log where ip = :ip and id > 0 limit 0,1000) t1,user_login_log ull,user u where t1.ip = ull.ip and ull.user_id = u.id group by t1.ip,ull.address";
            params.put("ip", ip);
        }
        else {
            sql = "select t1.ip,ull.address,group_concat(distinct u.username) from (select distinct ip from user_login_log where user_id = :userId and ip = :ip and id > 0 limit 0,1000) t1,user_login_log ull,user u where t1.ip = ull.ip and ull.user_id = u.id group by t1.ip,ull.address";
            params.put("userId", userId);
            params.put("ip", ip);
        }
        return this.superDao.findPageList(sql, params, start, limit);
    }
}
