package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.UserSecurity;

public interface UserSecurityDao
{
    UserSecurity getById(final int p0);
    
    List<UserSecurity> getByUserId(final int p0);
    
    boolean delete(final int p0);
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    boolean updateValue(final int p0, final String p1);
}
