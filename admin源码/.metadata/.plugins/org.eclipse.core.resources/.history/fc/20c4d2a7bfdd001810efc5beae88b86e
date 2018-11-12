package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.ActivityRechargeBill;

public interface ActivityRechargeBillDao
{
    ActivityRechargeBill getById(final int p0);
    
    int getWaitTodo();
    
    boolean update(final ActivityRechargeBill p0);
    
    boolean add(final ActivityRechargeBill p0);
    
    boolean hasDateRecord(final int p0, final String p1);
    
    List<ActivityRechargeBill> get(final String p0, final String p1);
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    double total(final String p0, final String p1);
}
