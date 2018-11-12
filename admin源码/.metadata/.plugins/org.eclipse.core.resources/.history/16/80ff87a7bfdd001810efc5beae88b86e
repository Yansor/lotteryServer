package lottery.domains.content.dao;

import java.util.Map;
import lottery.domains.content.entity.ActivityPacketInfo;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;

public interface ActivityPacketInfoDao
{
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    boolean save(final ActivityPacketInfo p0);
    
    List<Map<Integer, Double>> statTotal();
}
