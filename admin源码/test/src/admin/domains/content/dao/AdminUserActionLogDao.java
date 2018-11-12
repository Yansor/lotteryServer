package admin.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import admin.domains.content.entity.AdminUserActionLog;

public interface AdminUserActionLogDao
{
    boolean save(final AdminUserActionLog p0);
    
    boolean save(final List<AdminUserActionLog> p0);
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
}
