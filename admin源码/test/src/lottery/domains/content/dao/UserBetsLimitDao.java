package lottery.domains.content.dao;

import lottery.domains.content.entity.UserBetsLimit;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;

public interface UserBetsLimitDao
{
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    boolean save(final UserBetsLimit p0);
    
    boolean update(final UserBetsLimit p0);
    
    boolean delete(final int p0);
    
    UserBetsLimit getByUserId(final int p0);
    
    UserBetsLimit getById(final int p0);
}
