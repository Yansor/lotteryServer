package lottery.domains.content.biz;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import lottery.domains.content.entity.UserDailySettleBill;
import javautils.jdbc.PageList;
import java.util.List;

public interface UserDailySettleBillService
{
    PageList search(final List<Integer> p0, final String p1, final String p2, final Double p3, final Double p4, final Integer p5, final int p6, final int p7);
    
    boolean add(final UserDailySettleBill p0);
    
    boolean update(final UserDailySettleBill p0);
    
    List<UserDailySettleBill> findByCriteria(final List<Criterion> p0, final List<Order> p1);
    
    List<UserDailySettleBill> getDirectLowerBills(final int p0, final String p1, final Integer[] p2, final Integer p3);
    
    UserDailySettleBill issue(final int p0);
}
