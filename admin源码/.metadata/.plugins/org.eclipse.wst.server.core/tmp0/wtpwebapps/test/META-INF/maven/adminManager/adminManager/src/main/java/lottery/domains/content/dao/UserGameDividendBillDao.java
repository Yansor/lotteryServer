package lottery.domains.content.dao;

import lottery.domains.content.entity.UserGameDividendBill;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;

public interface UserGameDividendBillDao
{
    PageList search(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    double sumUserAmount(final List<Integer> p0, final String p1, final String p2, final Double p3, final Double p4, final Integer p5);
    
    List<UserGameDividendBill> findByCriteria(final List<Criterion> p0, final List<Order> p1);
    
    UserGameDividendBill getById(final int p0);
    
    boolean add(final UserGameDividendBill p0);
    
    boolean update(final int p0, final int p1, final double p2, final String p3);
    
    boolean del(final int p0);
}
