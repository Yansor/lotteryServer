package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import lottery.domains.content.entity.UserLoginLog;
import java.util.List;

public interface UserLoginLogDao
{
    List<UserLoginLog> getByUserId(final int p0);
    
    UserLoginLog getLastLogin(final int p0);
    
    PageList find(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    PageList findHistory(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    List<?> getDayUserLogin(final String p0, final String p1);
    
    PageList searchSameIp(final Integer p0, final String p1, final int p2, final int p3);
}
