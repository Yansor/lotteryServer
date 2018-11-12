package admin.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.entity.AdminUserCriticalLog;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import admin.domains.content.dao.AdminUserCriticalLogDao;

@Repository
public class AdminUserCriticalLogDaoImpl implements AdminUserCriticalLogDao
{
    @Autowired
    private HibernateSuperDao<AdminUserCriticalLog> superDao;
    private final String tab;
    
    public AdminUserCriticalLogDaoImpl() {
        this.tab = AdminUserCriticalLog.class.getSimpleName();
    }
    
    @Override
    public boolean save(final List<AdminUserCriticalLog> list) {
        final String sql = "insert into `admin_user_critical_log` (`admin_user_id`,`user_id`,`action_id`, `ip`, `address`, `action`, `time`, `user_agent`) values (?,?, ?, ?, ?, ?, ?, ?)";
        final List<Object[]> params = new ArrayList<Object[]>();
        for (final AdminUserCriticalLog tmpBean : list) {
            try {
                final Object[] param = { tmpBean.getAdminUserId(), tmpBean.getUserId(), tmpBean.getActionId(), tmpBean.getIp(), tmpBean.getAddress(), tmpBean.getAction(), tmpBean.getTime(), tmpBean.getUserAgent() };
                params.add(param);
            }
            catch (Exception ex) {}
        }
        return this.superDao.doWork(sql, params);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(AdminUserCriticalLog.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public List<AdminUserCriticalLog> findAction() {
        final String hql = "from " + this.tab + " where 1 = 1 group by actionId";
        return this.superDao.list(hql);
    }
}
