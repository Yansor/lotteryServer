package lottery.domains.content.dao;

import lottery.domains.content.entity.UserDailySettleBill;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;

public interface UserDailySettleBillDao
{
    PageList search(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    List<UserDailySettleBill> findByCriteria(final List<Criterion> p0, final List<Order> p1);
    
    boolean add(final UserDailySettleBill p0);
    
    UserDailySettleBill getById(final int p0);
    
    boolean update(final UserDailySettleBill p0);
}
