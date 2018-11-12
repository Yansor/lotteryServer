package lottery.domains.content.dao;

import lottery.domains.content.entity.Game;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;

public interface GameDao
{
    PageList search(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    boolean add(final Game p0);
    
    Game getById(final int p0);
    
    Game getByGameName(final String p0);
    
    Game getByGameCode(final String p0);
    
    boolean deleteById(final int p0);
    
    boolean update(final Game p0);
    
    boolean updateSequence(final int p0, final int p1);
    
    boolean updateDisplay(final int p0, final int p1);
}
