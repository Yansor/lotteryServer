package lottery.domains.content.dao;

import lottery.domains.content.entity.UserDividend;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;

public interface UserDividendDao
{
    PageList search(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    List<UserDividend> findByUserIds(final List<Integer> p0);
    
    UserDividend getByUserId(final int p0);
    
    UserDividend getById(final int p0);
    
    void add(final UserDividend p0);
    
    void updateStatus(final int p0, final int p1);
    
    boolean updateSomeFields(final int p0, final String p1, final String p2, final String p3, final int p4, final double p5, final double p6, final String p7);
    
    boolean updateSomeFields(final int p0, final String p1, final String p2, final String p3, final int p4, final int p5, final double p6, final double p7, final int p8);
    
    void deleteByUser(final int p0);
    
    void deleteByTeam(final int p0);
    
    void deleteLowers(final int p0);
}
