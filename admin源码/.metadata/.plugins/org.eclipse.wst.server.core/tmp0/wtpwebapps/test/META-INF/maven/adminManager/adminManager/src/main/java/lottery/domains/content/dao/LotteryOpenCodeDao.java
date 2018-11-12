package lottery.domains.content.dao;

import lottery.domains.content.entity.LotteryOpenCode;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;

public interface LotteryOpenCodeDao
{
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    LotteryOpenCode get(final String p0, final String p1);
    
    boolean add(final LotteryOpenCode p0);
    
    List<LotteryOpenCode> list(final String p0, final String[] p1);
    
    boolean update(final LotteryOpenCode p0);
    
    boolean delete(final LotteryOpenCode p0);
    
    int countByInterfaceTime(final String p0, final String p1, final String p2);
    
    LotteryOpenCode getFirstExpectByInterfaceTime(final String p0, final String p1, final String p2);
    
    List<LotteryOpenCode> getLatest(final String p0, final int p1);
}
