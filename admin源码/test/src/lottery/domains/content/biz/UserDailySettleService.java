package lottery.domains.content.biz;

import admin.web.WebJSONObject;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserDailySettle;
import javautils.jdbc.PageList;
import java.util.List;

public interface UserDailySettleService
{
    PageList search(final List<Integer> p0, final String p1, final String p2, final Double p3, final Double p4, final Integer p5, final Integer p6, final Integer p7, final int p8, final int p9);
    
    UserDailySettle getByUserId(final int p0);
    
    UserDailySettle getById(final int p0);
    
    boolean deleteByTeam(final String p0);
    
    boolean changeZhaoShang(final User p0, final boolean p1);
    
    void checkDailySettle(final String p0);
    
    boolean update(final WebJSONObject p0, final int p1, final String p2, final String p3, final String p4, final int p5, final String p6);
    
    boolean add(final WebJSONObject p0, final String p1, final String p2, final String p3, final String p4, final int p5, final int p6, final String p7);
    
    boolean checkCanEdit(final WebJSONObject p0, final User p1);
    
    boolean checkCanDel(final WebJSONObject p0, final User p1);
    
    double[] getMinMaxScale(final User p0);
    
    double[] getMinMaxSales(final User p0);
    
    double[] getMinMaxLoss(final User p0);
    
    int[] getMinMaxUsers(final User p0);
    
    boolean checkValidLevel(final String p0, final String p1, final String p2, final UserDailySettle p3, final String p4);
}
