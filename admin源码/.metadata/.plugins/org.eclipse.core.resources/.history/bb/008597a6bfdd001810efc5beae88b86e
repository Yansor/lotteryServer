package lottery.domains.content.dao;

import lottery.domains.content.vo.bill.UserBetsReportVO;
import lottery.domains.content.vo.bill.HistoryUserLotteryDetailsReportVO;
import lottery.domains.content.vo.bill.UserLotteryDetailsReportVO;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.UserLotteryDetailsReport;

public interface UserLotteryDetailsReportDao
{
    boolean add(final UserLotteryDetailsReport p0);
    
    UserLotteryDetailsReport get(final int p0, final int p1, final int p2, final String p3);
    
    boolean update(final UserLotteryDetailsReport p0);
    
    List<UserLotteryDetailsReport> find(final List<Criterion> p0, final List<Order> p1);
    
    List<UserLotteryDetailsReportVO> sumLowersAndSelfByLottery(final int p0, final String p1, final String p2);
    
    List<HistoryUserLotteryDetailsReportVO> historySumLowersAndSelfByLottery(final int p0, final String p1, final String p2);
    
    List<UserLotteryDetailsReportVO> sumLowersAndSelfByRule(final int p0, final int p1, final String p2, final String p3);
    
    List<HistoryUserLotteryDetailsReportVO> historySumLowersAndSelfByRule(final int p0, final int p1, final String p2, final String p3);
    
    List<UserLotteryDetailsReportVO> sumSelfByLottery(final int p0, final String p1, final String p2);
    
    List<HistoryUserLotteryDetailsReportVO> historySumSelfByLottery(final int p0, final String p1, final String p2);
    
    List<UserLotteryDetailsReportVO> sumSelfByRule(final int p0, final int p1, final String p2, final String p3);
    
    List<HistoryUserLotteryDetailsReportVO> historySumSelfByRule(final int p0, final int p1, final String p2, final String p3);
    
    List<UserBetsReportVO> sumUserBets(final List<Integer> p0, final Integer p1, final String p2, final String p3);
}
