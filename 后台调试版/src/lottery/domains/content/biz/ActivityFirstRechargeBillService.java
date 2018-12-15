package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public abstract interface ActivityFirstRechargeBillService
{
  public abstract PageList find(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2);
  
  public abstract double sumAmount(String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract double tryCollect(int paramInt, double paramDouble, String paramString);
}
