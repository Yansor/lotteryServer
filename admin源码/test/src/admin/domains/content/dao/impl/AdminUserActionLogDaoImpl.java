package admin.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.entity.AdminUserActionLog;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import admin.domains.content.dao.AdminUserActionLogDao;

@Repository
public class AdminUserActionLogDaoImpl implements AdminUserActionLogDao
{
    @Autowired
    private HibernateSuperDao<AdminUserActionLog> superDao;
    
    @Override
    public boolean save(final AdminUserActionLog entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean save(final List<AdminUserActionLog> list) {
        final String sql = "insert into `admin_user_action_log`(`user_id`, `action_id`, `data`, `millisecond`, `error`, `message`, `time`, `user_agent`) values(?,?,?,?,?,?,?,?)";
        final List<Object[]> params = new ArrayList<Object[]>();
        for (final AdminUserActionLog tmp : list) {
            try {
                final Object[] param = { tmp.getUserId(), tmp.getActionId(), tmp.getData(), tmp.getMillisecond(), tmp.getError(), tmp.getMessage(), tmp.getTime(), tmp.getUserAgent() };
                params.add(param);
            }
            catch (Exception ex) {}
        }
        return this.superDao.doWork(sql, params);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(AdminUserActionLog.class, propertyName, criterions, orders, start, limit);
    }
}
