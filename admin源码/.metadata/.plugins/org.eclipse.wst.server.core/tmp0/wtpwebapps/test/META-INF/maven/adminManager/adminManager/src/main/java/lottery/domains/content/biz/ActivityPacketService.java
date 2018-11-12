package lottery.domains.content.biz;

import java.util.Map;
import java.util.List;
import javautils.jdbc.PageList;

public interface ActivityPacketService
{
    PageList searchBill(final String p0, final String p1, final int p2, final int p3);
    
    PageList searchPacketInfo(final String p0, final String p1, final String p2, final int p3, final int p4);
    
    boolean generatePackets(final int p0, final double p1);
    
    List<Map<Integer, Double>> statTotal();
}
