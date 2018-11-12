package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import lottery.domains.content.entity.LotteryOpenTime;
import java.util.List;

public interface LotteryOpenTimeDao
{
    List<LotteryOpenTime> listAll();
    
    List<LotteryOpenTime> list(final String p0);
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    LotteryOpenTime getById(final int p0);
    
    LotteryOpenTime getByLottery(final String p0);
    
    boolean update(final LotteryOpenTime p0);
    
    boolean save(final LotteryOpenTime p0);
}
