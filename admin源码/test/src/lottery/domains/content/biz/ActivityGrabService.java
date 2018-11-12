package lottery.domains.content.biz;

import java.util.Map;
import javautils.jdbc.PageList;

public interface ActivityGrabService
{
    PageList searchBill(final String p0, final String p1, final int p2, final int p3);
    
    Map<String, Double> getOutTotalInfo();
}
