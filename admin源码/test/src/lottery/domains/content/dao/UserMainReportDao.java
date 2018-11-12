package lottery.domains.content.dao;

import lottery.domains.content.vo.bill.UserMainReportVO;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.UserMainReport;

public interface UserMainReportDao
{
    boolean add(final UserMainReport p0);
    
    UserMainReport get(final int p0, final String p1);
    
    List<UserMainReport> list(final int p0, final String p1, final String p2);
    
    boolean update(final UserMainReport p0);
    
    List<UserMainReport> find(final List<Criterion> p0, final List<Order> p1);
    
    UserMainReportVO sumLowersAndSelf(final int p0, final String p1, final String p2);
}
