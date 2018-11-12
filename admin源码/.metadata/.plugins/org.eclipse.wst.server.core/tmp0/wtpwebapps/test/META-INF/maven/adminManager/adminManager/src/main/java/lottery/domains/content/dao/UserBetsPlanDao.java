package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.UserBetsPlan;

public interface UserBetsPlanDao
{
    boolean add(final UserBetsPlan p0);
    
    UserBetsPlan get(final int p0);
    
    UserBetsPlan get(final int p0, final int p1);
    
    boolean hasRecord(final int p0, final int p1, final String p2);
    
    boolean updateCount(final int p0, final int p1);
    
    boolean updateStatus(final int p0, final int p1);
    
    boolean updateStatus(final int p0, final int p1, final double p2);
    
    List<UserBetsPlan> listUnsettled();
    
    List<UserBetsPlan> list(final int p0, final String p1);
    
    PageList search(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
}
