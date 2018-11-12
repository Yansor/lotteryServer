package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.UserBill;

public interface UserBillDao
{
    boolean add(final UserBill p0);
    
    UserBill getById(final int p0);
    
    double getTotalMoney(final String p0, final String p1, final int p2, final int[] p3);
    
    List<UserBill> getLatest(final int p0, final int p1, final int p2);
    
    List<UserBill> listByDateAndType(final String p0, final int p1, final int[] p2);
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    PageList findNoDemoUserBill(final String p0, final int p1, final int p2);
    
    PageList findHistory(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
}
