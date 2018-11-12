package lottery.domains.content.dao;

import lottery.domains.content.entity.UserWhitelist;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;

public interface UserWhitelistDao
{
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    boolean add(final UserWhitelist p0);
    
    boolean delete(final int p0);
}
