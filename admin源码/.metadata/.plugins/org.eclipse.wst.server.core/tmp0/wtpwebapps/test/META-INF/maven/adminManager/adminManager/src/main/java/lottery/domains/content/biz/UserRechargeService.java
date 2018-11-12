package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserRecharge;
import java.util.List;
import lottery.domains.content.vo.user.HistoryUserRechargeVO;
import lottery.domains.content.vo.user.UserRechargeVO;

public interface UserRechargeService
{
    UserRechargeVO getById(final int p0);
    
    HistoryUserRechargeVO getHistoryById(final int p0);
    
    List<UserRechargeVO> getLatest(final int p0, final int p1, final int p2);
    
    List<UserRecharge> listByPayTimeAndStatus(final String p0, final String p1, final int p2);
    
    PageList search(final Integer p0, final String p1, final String p2, final String p3, final String p4, final String p5, final String p6, final Double p7, final Double p8, final Integer p9, final Integer p10, final int p11, final int p12);
    
    PageList searchHistory(final String p0, final String p1, final String p2, final String p3, final String p4, final String p5, final Double p6, final Double p7, final Integer p8, final Integer p9, final int p10, final int p11);
    
    boolean addSystemRecharge(final String p0, final int p1, final int p2, final double p3, final int p4, final String p5);
    
    boolean patchOrder(final String p0, final String p1, final String p2);
    
    boolean cancelOrder(final String p0);
    
    double getTotalRecharge(final Integer p0, final String p1, final String p2, final String p3, final String p4, final String p5, final String p6, final Double p7, final Double p8, final Integer p9, final Integer p10);
    
    double getHistoryTotalRecharge(final String p0, final String p1, final String p2, final String p3, final String p4, final String p5, final Double p6, final Double p7, final Integer p8, final Integer p9);
}
