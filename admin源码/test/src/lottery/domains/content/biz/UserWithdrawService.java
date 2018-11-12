package lottery.domains.content.biz;

import lottery.domains.content.entity.PaymentChannel;
import admin.web.WebJSONObject;
import admin.domains.content.entity.AdminUser;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserWithdraw;
import java.util.List;
import lottery.domains.content.vo.user.HistoryUserWithdrawVO;
import lottery.domains.content.vo.user.UserWithdrawVO;

public interface UserWithdrawService
{
    UserWithdrawVO getById(final int p0);
    
    HistoryUserWithdrawVO getHistoryById(final int p0);
    
    List<UserWithdrawVO> getLatest(final int p0, final int p1, final int p2);
    
    List<UserWithdraw> listByRemitStatus(final int[] p0, final boolean p1, final String p2, final String p3);
    
    PageList search(final Integer p0, final String p1, final String p2, final String p3, final String p4, final String p5, final String p6, final Double p7, final Double p8, final String p9, final Integer p10, final Integer p11, final Integer p12, final Integer p13, final int p14, final int p15);
    
    PageList searchHistory(final String p0, final String p1, final String p2, final String p3, final String p4, final String p5, final Double p6, final Double p7, final String p8, final Integer p9, final Integer p10, final Integer p11, final Integer p12, final int p13, final int p14);
    
    boolean manualPay(final AdminUser p0, final WebJSONObject p1, final int p2, final String p3, final String p4, final String p5);
    
    boolean completeRemit(final AdminUser p0, final WebJSONObject p1, final int p2, final String p3);
    
    boolean apiPay(final AdminUser p0, final WebJSONObject p1, final int p2, final PaymentChannel p3);
    
    boolean check(final AdminUser p0, final WebJSONObject p1, final int p2, final int p3);
    
    boolean refuse(final AdminUser p0, final WebJSONObject p1, final int p2, final String p3, final String p4, final String p5);
    
    boolean withdrawFailure(final AdminUser p0, final WebJSONObject p1, final int p2, final String p3, final String p4);
    
    boolean reviewedFail(final AdminUser p0, final WebJSONObject p1, final int p2, final String p3, final String p4);
    
    boolean lock(final AdminUser p0, final WebJSONObject p1, final int p2, final String p3);
    
    boolean unlock(final AdminUser p0, final WebJSONObject p1, final int p2, final String p3);
    
    boolean update(final UserWithdraw p0);
    
    double[] getTotalWithdraw(final String p0, final String p1, final String p2, final String p3, final String p4, final String p5, final Double p6, final Double p7, final String p8, final Integer p9, final Integer p10, final Integer p11, final Integer p12);
    
    double[] getHistoryTotalWithdraw(final String p0, final String p1, final String p2, final String p3, final String p4, final String p5, final Double p6, final Double p7, final String p8, final Integer p9, final Integer p10, final Integer p11, final Integer p12);
}
