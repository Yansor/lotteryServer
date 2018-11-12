package lottery.domains.content.biz;

import lottery.domains.content.entity.UserGameDividendBill;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import javautils.jdbc.PageList;
import java.util.List;

public interface UserGameDividendBillService
{
    PageList search(final List<Integer> p0, final String p1, final String p2, final Double p3, final Double p4, final Integer p5, final int p6, final int p7);
    
    double sumUserAmount(final List<Integer> p0, final String p1, final String p2, final Double p3, final Double p4, final Integer p5);
    
    List<UserGameDividendBill> findByCriteria(final List<Criterion> p0, final List<Order> p1);
    
    UserGameDividendBill getById(final int p0);
    
    boolean agree(final int p0, final double p1, final String p2);
    
    boolean deny(final int p0, final double p1, final String p2);
    
    boolean del(final int p0);
}
