package lottery.domains.content.dao;

import lottery.domains.content.entity.UserDailySettle;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;

public interface UserDailySettleDao
{
    PageList search(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    List<UserDailySettle> list(final List<Criterion> p0, final List<Order> p1);
    
    List<UserDailySettle> findByUserIds(final List<Integer> p0);
    
    UserDailySettle getByUserId(final int p0);
    
    UserDailySettle getById(final int p0);
    
    void add(final UserDailySettle p0);
    
    void deleteByUser(final int p0);
    
    void deleteByTeam(final int p0);
    
    void deleteLowers(final int p0);
    
    boolean updateStatus(final int p0, final int p1);
    
    boolean updateSomeFields(final int p0, final String p1, final String p2, final String p3, final int p4, final String p5);
    
    boolean updateSomeFields(final int p0, final double p1, final int p2, final int p3, final int p4, final double p5, final double p6);
    
    boolean updateTotalAmount(final int p0, final double p1);
}
