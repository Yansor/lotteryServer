package lottery.domains.content.dao;

import lottery.domains.content.entity.UserHighPrize;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;

public interface UserHighPrizeDao
{
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    PageList find(final String p0, final int p1, final int p2);
    
    UserHighPrize getById(final int p0);
    
    boolean add(final UserHighPrize p0);
    
    boolean updateStatus(final int p0, final int p1);
    
    boolean updateStatusAndConfirmUsername(final int p0, final int p1, final String p2);
    
    int getUnProcessCount();
}
