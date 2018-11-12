package lottery.domains.content.biz;

import lottery.domains.content.vo.bill.UserProfitRankingVO;
import lottery.domains.content.vo.bill.UserBetsReportVO;
import lottery.domains.content.vo.bill.HistoryUserLotteryReportVO;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import java.util.List;

public interface UserLotteryReportService
{
    boolean update(final int p0, final int p1, final double p2, final String p3);
    
    boolean updateRechargeFee(final int p0, final double p1, final String p2);
    
    List<UserLotteryReportVO> report(final String p0, final String p1);
    
    List<UserLotteryReportVO> report(final int p0, final String p1, final String p2);
    
    List<UserLotteryReportVO> reportByType(final Integer p0, final String p1, final String p2);
    
    List<HistoryUserLotteryReportVO> historyReport(final String p0, final String p1);
    
    List<HistoryUserLotteryReportVO> historyReport(final int p0, final String p1, final String p2);
    
    List<UserBetsReportVO> bReport(final Integer p0, final Integer p1, final Integer p2, final String p3, final String p4);
    
    List<UserProfitRankingVO> listUserProfitRanking(final Integer p0, final String p1, final String p2, final int p3, final int p4);
}
