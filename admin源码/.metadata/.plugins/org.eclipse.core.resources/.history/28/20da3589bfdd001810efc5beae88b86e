package lottery.domains.content.dao;

import lottery.domains.content.entity.ActivityFirstRechargeBill;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;

public interface ActivityFirstRechargeBillDao
{
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    double sumAmount(final Integer p0, final String p1, final String p2, final String p3);
    
    ActivityFirstRechargeBill getByDateAndIp(final String p0, final String p1);
    
    ActivityFirstRechargeBill getByUserIdAndDate(final int p0, final String p1);
    
    boolean add(final ActivityFirstRechargeBill p0);
}
