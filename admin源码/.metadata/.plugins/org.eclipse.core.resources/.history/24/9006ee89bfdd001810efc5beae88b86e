package lottery.domains.content.dao;

import lottery.domains.content.vo.bill.UserProfitRankingVO;
import lottery.domains.content.vo.bill.HistoryUserLotteryReportVO;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import lottery.domains.content.entity.HistoryUserLotteryReport;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.UserLotteryReport;

public interface UserLotteryReportDao
{
    boolean add(final UserLotteryReport p0);
    
    UserLotteryReport get(final int p0, final String p1);
    
    List<UserLotteryReport> list(final int p0, final String p1, final String p2);
    
    boolean update(final UserLotteryReport p0);
    
    List<UserLotteryReport> find(final List<Criterion> p0, final List<Order> p1);
    
    List<HistoryUserLotteryReport> findHistory(final List<Criterion> p0, final List<Order> p1);
    
    List<UserLotteryReport> getDayList(final int[] p0, final String p1, final String p2);
    
    UserLotteryReportVO sumLowersAndSelf(final int p0, final String p1, final String p2);
    
    HistoryUserLotteryReportVO historySumLowersAndSelf(final int p0, final String p1, final String p2);
    
    UserLotteryReportVO sum(final int p0, final String p1, final String p2);
    
    List<UserProfitRankingVO> listUserProfitRanking(final String p0, final String p1, final int p2, final int p3);
    
    List<UserProfitRankingVO> listUserProfitRankingByDate(final int p0, final String p1, final String p2, final int p3, final int p4);
}
