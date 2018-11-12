package admin.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.entity.AdminUserLog;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import admin.domains.content.dao.AdminUserLogDao;

@Repository
public class AdminUserLogDaoImpl implements AdminUserLogDao
{
    @Autowired
    private HibernateSuperDao<AdminUserLog> superDao;
    
    @Override
    public boolean save(final List<AdminUserLog> list) {
        final String sql = "insert into `admin_user_log` (`user_id`, `ip`, `address`, `action`, `time`, `user_agent`) values (?, ?, ?, ?, ?, ?)";
        final List<Object[]> params = new ArrayList<Object[]>();
        for (final AdminUserLog tmpBean : list) {
            try {
                final Object[] param = { tmpBean.getUserId(), tmpBean.getIp(), tmpBean.getAddress(), tmpBean.getAction(), tmpBean.getTime(), tmpBean.getUserAgent() };
                params.add(param);
            }
            catch (Exception ex) {}
        }
        return this.superDao.doWork(sql, params);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(AdminUserLog.class, propertyName, criterions, orders, start, limit);
    }
}
