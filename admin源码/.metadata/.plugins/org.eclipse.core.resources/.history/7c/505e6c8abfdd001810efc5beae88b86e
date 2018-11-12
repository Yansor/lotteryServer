package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.UserTransfers;

public interface UserTransfersDao
{
    boolean add(final UserTransfers p0);
    
    PageList search(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    double getTotalTransfers(final String p0, final String p1, final int p2);
}
