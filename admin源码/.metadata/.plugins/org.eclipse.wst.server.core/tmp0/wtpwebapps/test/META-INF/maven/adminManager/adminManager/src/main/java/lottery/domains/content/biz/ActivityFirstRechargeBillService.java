package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public interface ActivityFirstRechargeBillService
{
    PageList find(final String p0, final String p1, final String p2, final String p3, final int p4, final int p5);
    
    double sumAmount(final String p0, final String p1, final String p2, final String p3);
    
    double tryCollect(final int p0, final double p1, final String p2);
}
