package lottery.domains.content.biz;

import lottery.domains.content.vo.bill.UserBillVO;
import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBets;
import lottery.domains.content.entity.UserTransfers;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserRecharge;

public interface UserBillService
{
    boolean addRechargeBill(final UserRecharge p0, final User p1, final String p2);
    
    boolean addWithdrawReport(final UserWithdraw p0);
    
    boolean addDrawBackBill(final UserWithdraw p0, final User p1, final String p2);
    
    boolean addTransInBill(final UserTransfers p0, final User p1, final int p2, final String p3);
    
    boolean addTransOutBill(final UserTransfers p0, final User p1, final int p2, final String p3);
    
    boolean addActivityBill(final User p0, final int p1, final double p2, final int p3, final String p4);
    
    boolean addAdminAddBill(final User p0, final int p1, final double p2, final String p3);
    
    boolean addAdminMinusBill(final User p0, final int p1, final double p2, final String p3);
    
    boolean addSpendBill(final UserBets p0, final User p1);
    
    boolean addCancelOrderBill(final UserBets p0, final User p1);
    
    boolean addDividendBill(final User p0, final int p1, final double p2, final String p3, final boolean p4);
    
    boolean addRewardPayBill(final User p0, final int p1, final double p2, final String p3);
    
    boolean addRewardIncomeBill(final User p0, final int p1, final double p2, final String p3);
    
    boolean addRewardReturnBill(final User p0, final int p1, final double p2, final String p3);
    
    boolean addDailySettleBill(final User p0, final int p1, final double p2, final String p3, final boolean p4);
    
    boolean addGameWaterBill(final User p0, final int p1, final int p2, final double p3, final String p4);
    
    PageList search(final String p0, final String p1, final Integer p2, final Integer p3, final String p4, final String p5, final Double p6, final Double p7, final Integer p8, final int p9, final int p10);
    
    PageList searchHistory(final String p0, final String p1, final Integer p2, final String p3, final String p4, final Double p5, final Double p6, final Integer p7, final int p8, final int p9);
    
    List<UserBillVO> getLatest(final int p0, final int p1, final int p2);
}
