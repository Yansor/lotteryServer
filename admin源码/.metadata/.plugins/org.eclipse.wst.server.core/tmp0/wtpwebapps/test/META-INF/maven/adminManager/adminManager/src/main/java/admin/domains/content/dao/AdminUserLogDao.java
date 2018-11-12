package admin.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import admin.domains.content.entity.AdminUserLog;
import java.util.List;

public interface AdminUserLogDao
{
    boolean save(final List<AdminUserLog> p0);
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
}
