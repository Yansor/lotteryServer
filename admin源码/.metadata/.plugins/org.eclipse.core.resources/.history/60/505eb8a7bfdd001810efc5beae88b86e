package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.UserMessage;

public interface UserMessageDao
{
    UserMessage getById(final int p0);
    
    boolean delete(final int p0);
    
    PageList search(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    boolean save(final UserMessage p0);
    
    void update(final UserMessage p0);
}
