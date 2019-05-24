package lottery.domains.content.biz;

import java.util.List;
import java.util.Map;
import javautils.jdbc.PageList;

public abstract interface ActivityPacketService
{
  public abstract PageList searchBill(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract PageList searchPacketInfo(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract boolean generatePackets(int paramInt, double paramDouble);
  
  public abstract List<Map<Integer, Double>> statTotal();
}
