package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.UserCard;

public interface UserCardDao
{
    boolean add(final UserCard p0);
    
    UserCard getById(final int p0);
    
    List<UserCard> getByUserId(final int p0);
    
    UserCard getByCardId(final String p0);
    
    UserCard getByUserAndCardId(final int p0, final String p1);
    
    boolean update(final UserCard p0);
    
    boolean updateCardName(final int p0, final String p1);
    
    boolean delete(final int p0);
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
}
