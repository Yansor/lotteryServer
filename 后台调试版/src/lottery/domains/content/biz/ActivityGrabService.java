package lottery.domains.content.biz;

import java.util.Map;
import javautils.jdbc.PageList;

public abstract interface ActivityGrabService
{
  public abstract PageList searchBill(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract Map<String, Double> getOutTotalInfo();
}
