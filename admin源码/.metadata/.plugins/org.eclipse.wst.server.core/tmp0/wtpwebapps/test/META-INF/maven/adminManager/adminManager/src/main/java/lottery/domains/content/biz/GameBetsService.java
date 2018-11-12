package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import lottery.domains.content.vo.user.GameBetsVO;

public interface GameBetsService
{
    GameBetsVO getById(final int p0);
    
    PageList search(final String p0, final String p1, final Integer p2, final String p3, final String p4, final Double p5, final Double p6, final Double p7, final Double p8, final String p9, final String p10, final String p11, final int p12, final int p13);
    
    double[] getTotalMoney(final String p0, final String p1, final Integer p2, final String p3, final String p4, final Double p5, final Double p6, final Double p7, final Double p8, final String p9, final String p10, final String p11);
    
    double getBillingOrder(final int p0, final String p1, final String p2);
}
