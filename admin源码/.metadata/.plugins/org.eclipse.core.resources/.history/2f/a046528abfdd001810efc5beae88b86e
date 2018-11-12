package lottery.domains.content.dao;

import lottery.domains.content.vo.bill.HistoryUserGameReportVO;
import lottery.domains.content.vo.bill.UserGameReportVO;
import lottery.domains.content.entity.HistoryUserGameReport;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.UserGameReport;

public interface UserGameReportDao
{
    boolean save(final UserGameReport p0);
    
    UserGameReport get(final int p0, final int p1, final String p2);
    
    boolean update(final UserGameReport p0);
    
    List<UserGameReport> list(final int p0, final String p1, final String p2);
    
    List<UserGameReport> find(final List<Criterion> p0, final List<Order> p1);
    
    List<HistoryUserGameReport> findHistory(final List<Criterion> p0, final List<Order> p1);
    
    List<UserGameReportVO> reportByUser(final String p0, final String p1);
    
    UserGameReportVO sumLowersAndSelf(final int p0, final String p1, final String p2);
    
    HistoryUserGameReportVO historySumLowersAndSelf(final int p0, final String p1, final String p2);
    
    UserGameReportVO sum(final int p0, final String p1, final String p2);
}
