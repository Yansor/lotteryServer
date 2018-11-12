package lottery.domains.content.biz;

import admin.web.WebJSONObject;
import lottery.domains.content.entity.UserDividendBill;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import javautils.jdbc.PageList;
import java.util.List;

public interface UserDividendBillService
{
    PageList search(final List<Integer> p0, final String p1, final String p2, final Double p3, final Double p4, final Integer p5, final Integer p6, final int p7, final int p8);
    
    PageList searchPlatformLoss(final List<Integer> p0, final String p1, final String p2, final Double p3, final Double p4, final int p5, final int p6);
    
    double[] sumUserAmount(final List<Integer> p0, final String p1, final String p2, final Double p3, final Double p4);
    
    List<UserDividendBill> findByCriteria(final List<Criterion> p0, final List<Order> p1);
    
    boolean updateAllExpire();
    
    List<UserDividendBill> getLowerBills(final int p0, final String p1, final String p2);
    
    List<UserDividendBill> getDirectLowerBills(final int p0, final String p1, final String p2);
    
    UserDividendBill getById(final int p0);
    
    UserDividendBill getBill(final int p0, final String p1, final String p2);
    
    boolean addAvailableMoney(final int p0, final double p1);
    
    boolean add(final UserDividendBill p0);
    
    void issueInsufficient(final int p0);
    
    boolean agree(final WebJSONObject p0, final int p1, final String p2);
    
    boolean deny(final WebJSONObject p0, final int p1, final String p2);
    
    boolean del(final WebJSONObject p0, final int p1);
    
    boolean reset(final WebJSONObject p0, final int p1, final String p2);
    
    double queryPeriodCollect(final int p0, final String p1, final String p2);
}
