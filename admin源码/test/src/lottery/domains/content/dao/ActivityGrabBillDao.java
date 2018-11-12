package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import java.util.Map;
import lottery.domains.content.entity.ActivityGrabBill;

public interface ActivityGrabBillDao
{
    boolean add(final ActivityGrabBill p0);
    
    ActivityGrabBill get(final int p0);
    
    Map<String, Integer> getByPackageGroup();
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    Double getOutAmount(final String p0);
    
    double getGrabTotal(final String p0, final String p1);
}
