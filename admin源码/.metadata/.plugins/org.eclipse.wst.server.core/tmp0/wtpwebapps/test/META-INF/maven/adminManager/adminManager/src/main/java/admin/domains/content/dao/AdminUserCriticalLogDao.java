package admin.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import admin.domains.content.entity.AdminUserCriticalLog;
import java.util.List;

public interface AdminUserCriticalLogDao
{
    boolean save(final List<AdminUserCriticalLog> p0);
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    List<AdminUserCriticalLog> findAction();
}
