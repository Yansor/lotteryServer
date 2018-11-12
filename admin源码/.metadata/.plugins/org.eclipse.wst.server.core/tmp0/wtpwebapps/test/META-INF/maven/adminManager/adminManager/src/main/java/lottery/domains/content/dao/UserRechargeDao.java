package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.HistoryUserRecharge;
import lottery.domains.content.entity.UserRecharge;

public interface UserRechargeDao
{
    boolean add(final UserRecharge p0);
    
    boolean update(final UserRecharge p0);
    
    boolean updateSuccess(final int p0, final double p1, final double p2, final double p3, final String p4, final String p5);
    
    UserRecharge getById(final int p0);
    
    HistoryUserRecharge getHistoryById(final int p0);
    
    UserRecharge getByBillno(final String p0);
    
    boolean isRecharge(final int p0);
    
    List<UserRecharge> getLatest(final int p0, final int p1, final int p2);
    
    List<UserRecharge> list(final List<Criterion> p0, final List<Order> p1);
    
    PageList find(final String p0, final int p1, final int p2);
    
    PageList findHistory(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    List<?> getDayRecharge(final String p0, final String p1);
    
    int getRechargeCount(final int p0, final int p1, final int p2);
    
    double getTotalFee(final String p0, final String p1);
    
    double getThirdTotalRecharge(final String p0, final String p1);
    
    double getTotalRecharge(final String p0, final String p1, final int[] p2, final int[] p3, final Integer p4);
    
    Object[] getTotalRechargeData(final String p0, final String p1, final Integer p2, final Integer p3);
    
    List<?> getDayRecharge2(final String p0, final String p1, final Integer p2, final Integer p3);
    
    double getTotalRecharge(final String p0, final Integer p1, final String p2, final String p3, final String p4, final String p5, final Double p6, final Double p7, final Integer p8, final Integer p9);
    
    double getHistoryTotalRecharge(final String p0, final Integer p1, final String p2, final String p3, final String p4, final String p5, final Double p6, final Double p7, final Integer p8, final Integer p9);
}
