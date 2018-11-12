package lottery.domains.content.dao;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import javautils.jdbc.PageList;
import java.util.List;
import lottery.domains.content.entity.HistoryUserWithdraw;
import lottery.domains.content.entity.UserWithdraw;

public interface UserWithdrawDao
{
    boolean update(final UserWithdraw p0);
    
    int getWaitTodo();
    
    UserWithdraw getById(final int p0);
    
    HistoryUserWithdraw getHistoryById(final int p0);
    
    UserWithdraw getByBillno(final String p0);
    
    List<UserWithdraw> listByOperatorTime(final String p0, final String p1);
    
    List<UserWithdraw> listByRemitStatus(final int[] p0, final boolean p1, final String p2, final String p3);
    
    List<UserWithdraw> getLatest(final int p0, final int p1, final int p2);
    
    PageList find(final String p0, final int p1, final int p2);
    
    PageList findHistory(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    double getTotalWithdraw(final String p0, final String p1);
    
    Object[] getTotalWithdrawData(final String p0, final String p1);
    
    double[] getTotalWithdraw(final String p0, final Integer p1, final String p2, final String p3, final String p4, final String p5, final Double p6, final Double p7, final String p8, final Integer p9, final Integer p10, final Integer p11, final Integer p12);
    
    double[] getHistoryTotalWithdraw(final String p0, final Integer p1, final String p2, final String p3, final String p4, final String p5, final Double p6, final Double p7, final String p8, final Integer p9, final Integer p10, final Integer p11, final Integer p12);
    
    double getTotalAutoRemit(final String p0, final String p1);
    
    double getTotalFee(final String p0, final String p1);
    
    List<?> getDayWithdraw(final String p0, final String p1);
    
    List<?> getDayWithdraw2(final String p0, final String p1);
    
    boolean lock(final String p0, final String p1, final String p2);
    
    boolean unlock(final String p0, final String p1);
}
