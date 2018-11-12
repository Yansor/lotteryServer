package lottery.domains.content.biz;

import lottery.domains.content.vo.bill.UserBetsReportVO;
import lottery.domains.content.vo.bill.HistoryUserLotteryDetailsReportVO;
import lottery.domains.content.vo.bill.UserLotteryDetailsReportVO;
import java.util.List;

public interface UserLotteryDetailsReportService
{
    boolean update(final int p0, final int p1, final int p2, final int p3, final double p4, final String p5);
    
    List<UserLotteryDetailsReportVO> reportLowersAndSelf(final int p0, final Integer p1, final String p2, final String p3);
    
    List<HistoryUserLotteryDetailsReportVO> historyReportLowersAndSelf(final int p0, final Integer p1, final String p2, final String p3);
    
    List<UserLotteryDetailsReportVO> reportSelf(final int p0, final Integer p1, final String p2, final String p3);
    
    List<HistoryUserLotteryDetailsReportVO> historyReportSelf(final int p0, final Integer p1, final String p2, final String p3);
    
    List<UserBetsReportVO> sumUserBets(final Integer p0, final Integer p1, final Integer p2, final String p3, final String p4);
}
