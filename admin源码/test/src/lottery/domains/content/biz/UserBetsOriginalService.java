package lottery.domains.content.biz;

import lottery.domains.content.vo.user.UserBetsOriginalVO;
import javautils.jdbc.PageList;

public interface UserBetsOriginalService
{
    PageList search(final String p0, final String p1, final Integer p2, final Integer p3, final Integer p4, final String p5, final Integer p6, final String p7, final String p8, final String p9, final String p10, final Double p11, final Double p12, final Integer p13, final Integer p14, final Double p15, final Double p16, final Integer p17, final int p18, final int p19);
    
    UserBetsOriginalVO getById(final int p0);
    
    double[] getTotalMoney(final String p0, final String p1, final Integer p2, final Integer p3, final Integer p4, final String p5, final Integer p6, final String p7, final String p8, final String p9, final String p10, final Double p11, final Double p12, final Integer p13, final Integer p14, final Double p15, final Double p16, final Integer p17);
}
