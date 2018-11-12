package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.VipUpgradeList;

public interface VipUpgradeListDao
{
    boolean add(final VipUpgradeList p0);
    
    boolean hasRecord(final int p0, final String p1);
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
}
