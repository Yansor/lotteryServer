package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.GameBets;

public interface GameBetsDao
{
    GameBets getById(final int p0);
    
    GameBets get(final int p0, final int p1, final String p2, final String p3);
    
    boolean save(final GameBets p0);
    
    boolean update(final GameBets p0);
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    double[] getTotalMoney(final String p0, final Integer p1, final Integer p2, final String p3, final String p4, final Double p5, final Double p6, final Double p7, final Double p8, final String p9, final String p10, final String p11);
    
    double getBillingOrder(final int p0, final String p1, final String p2);
}
