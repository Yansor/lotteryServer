package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public interface ActivityCostService
{
    PageList searchBill(final String p0, final String p1, final int p2, final int p3);
}
