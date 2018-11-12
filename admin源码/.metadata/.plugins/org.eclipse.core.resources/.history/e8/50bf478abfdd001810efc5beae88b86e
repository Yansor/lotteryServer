package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.VipFreeChips;

public interface VipFreeChipsDao
{
    boolean add(final VipFreeChips p0);
    
    VipFreeChips getById(final int p0);
    
    List<VipFreeChips> getUntreated();
    
    boolean hasRecord(final int p0, final String p1, final String p2);
    
    boolean update(final VipFreeChips p0);
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
}
