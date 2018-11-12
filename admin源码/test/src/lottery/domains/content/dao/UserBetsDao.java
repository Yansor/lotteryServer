package lottery.domains.content.dao;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import javautils.jdbc.PageList;
import java.util.List;
import lottery.domains.content.entity.HistoryUserBets;
import lottery.domains.content.entity.UserBets;

public interface UserBetsDao
{
    boolean updateStatus(final int p0, final int p1, final String p2, final String p3, final double p4, final String p5);
    
    boolean updateLocked(final int p0, final int p1);
    
    UserBets getById(final int p0);
    
    HistoryUserBets getHistoryById(final int p0);
    
    List<UserBets> getByBillno(final String p0, final boolean p1);
    
    List<HistoryUserBets> getHistoryByBillno(final String p0, final boolean p1);
    
    UserBets getBybillno(final int p0, final String p1);
    
    boolean cancel(final int p0);
    
    boolean delete(final int p0);
    
    boolean isCost(final int p0);
    
    List<UserBets> getSuspiciousOrder(final int p0, final int p1, final boolean p2);
    
    List<UserBets> getByFollowBillno(final String p0, final boolean p1);
    
    PageList find(final String p0, final int p1, final int p2, final boolean p3);
    
    PageList findHistory(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3, final boolean p4);
    
    List<UserBets> find(final List<Criterion> p0, final List<Order> p1, final boolean p2);
    
    long getTotalBetsMoney(final String p0, final String p1);
    
    int getTotalOrderCount(final String p0, final String p1);
    
    List<?> getDayUserBets(final int[] p0, final String p1, final String p2);
    
    List<?> getDayBetsMoney(final int[] p0, final String p1, final String p2);
    
    List<?> getDayPrizeMoney(final int[] p0, final String p1, final String p2);
    
    List<?> getLotteryHot(final int[] p0, final String p1, final String p2);
    
    List<?> report(final List<Integer> p0, final Integer p1, final String p2, final String p3);
    
    int countUserOnline(final String p0);
    
    double[] getTotalMoney(final String p0, final Integer p1, final Integer p2, final Integer p3, final Integer p4, final String p5, final Integer p6, final String p7, final String p8, final String p9, final String p10, final Double p11, final Double p12, final Integer p13, final Integer p14, final Double p15, final Double p16, final Integer p17, final Integer p18, final String p19);
    
    double[] getHistoryTotalMoney(final String p0, final Integer p1, final Integer p2, final Integer p3, final Integer p4, final String p5, final Integer p6, final String p7, final String p8, final String p9, final String p10, final Double p11, final Double p12, final Integer p13, final Integer p14, final Double p15, final Double p16, final Integer p17, final Integer p18);
    
    double getBillingOrder(final int p0, final String p1, final String p2);
}
