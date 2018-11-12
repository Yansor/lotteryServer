package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.SysNotice;

public interface SysNoticeDao
{
    SysNotice getById(final int p0);
    
    boolean add(final SysNotice p0);
    
    boolean update(final SysNotice p0);
    
    boolean delete(final int p0);
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
}
