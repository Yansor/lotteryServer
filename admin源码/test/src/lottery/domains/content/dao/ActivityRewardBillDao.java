package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.ActivityRewardBill;

public interface ActivityRewardBillDao
{
    boolean add(final ActivityRewardBill p0);
    
    ActivityRewardBill getById(final int p0);
    
    List<ActivityRewardBill> getUntreated(final String p0);
    
    List<ActivityRewardBill> getLatest(final int p0, final int p1, final int p2);
    
    boolean hasRecord(final int p0, final int p1, final int p2, final String p3);
    
    boolean update(final ActivityRewardBill p0);
    
    boolean delete(final int p0);
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    double total(final String p0, final String p1, final int p2);
}
