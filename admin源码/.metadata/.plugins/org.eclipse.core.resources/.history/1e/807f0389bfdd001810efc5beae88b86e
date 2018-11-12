package lottery.domains.content.dao;

import lottery.domains.content.entity.UserDividendBill;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;

public interface UserDividendBillDao
{
    PageList search(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    double[] sumUserAmount(final List<Integer> p0, final String p1, final String p2, final Double p3, final Double p4);
    
    List<UserDividendBill> findByCriteria(final List<Criterion> p0, final List<Order> p1);
    
    boolean updateAllExpire();
    
    UserDividendBill getById(final int p0);
    
    UserDividendBill getByUserId(final int p0, final String p1, final String p2);
    
    boolean add(final UserDividendBill p0);
    
    boolean addAvailableMoney(final int p0, final double p1);
    
    boolean addUserAmount(final int p0, final double p1);
    
    boolean setAvailableMoney(final int p0, final double p1);
    
    boolean addTotalReceived(final int p0, final double p1);
    
    boolean addLowerPaidAmount(final int p0, final double p1);
    
    boolean update(final UserDividendBill p0);
    
    boolean update(final int p0, final int p1, final String p2);
    
    boolean update(final int p0, final int p1, final double p2, final String p3);
    
    boolean del(final int p0);
    
    boolean updateStatus(final int p0, final int p1);
    
    boolean updateStatus(final int p0, final int p1, final String p2);
}
