package lottery.domains.content.biz;

import java.util.Date;
import java.util.List;
import lottery.domains.content.entity.UserBets;
import lottery.domains.content.vo.user.HistoryUserBetsVO;
import lottery.domains.content.vo.user.UserBetsVO;
import javautils.jdbc.PageList;

public interface UserBetsService
{
    PageList search(final String p0, final String p1, final Integer p2, final Integer p3, final Integer p4, final String p5, final Integer p6, final String p7, final String p8, final String p9, final String p10, final Double p11, final Double p12, final Integer p13, final Integer p14, final Double p15, final Double p16, final Integer p17, final Integer p18, final String p19, final int p20, final int p21);
    
    UserBetsVO getById(final int p0);
    
    HistoryUserBetsVO getHistoryById(final int p0);
    
    UserBets getBetsById(final int p0);
    
    PageList searchHistory(final String p0, final String p1, final Integer p2, final Integer p3, final Integer p4, final String p5, final Integer p6, final String p7, final String p8, final String p9, final String p10, final Double p11, final Double p12, final Integer p13, final Integer p14, final Double p15, final Double p16, final Integer p17, final Integer p18, final int p19, final int p20);
    
    List<UserBets> notOpened(final int p0, final Integer p1, final String p2, final String p3);
    
    boolean cancel(final int p0, final Integer p1, final String p2, final String p3);
    
    boolean cancel(final int p0);
    
    List<UserBetsVO> getSuspiciousOrder(final int p0, final int p1);
    
    int countUserOnline(final Date p0);
    
    double[] getTotalMoney(final String p0, final String p1, final Integer p2, final Integer p3, final Integer p4, final String p5, final Integer p6, final String p7, final String p8, final String p9, final String p10, final Double p11, final Double p12, final Integer p13, final Integer p14, final Double p15, final Double p16, final Integer p17, final Integer p18, final String p19);
    
    double[] getHistoryTotalMoney(final String p0, final String p1, final Integer p2, final Integer p3, final Integer p4, final String p5, final Integer p6, final String p7, final String p8, final String p9, final String p10, final Double p11, final Double p12, final Integer p13, final Integer p14, final Double p15, final Double p16, final Integer p17, final Integer p18);
    
    double getBillingOrder(final int p0, final String p1, final String p2);
}
