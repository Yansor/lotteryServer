package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public abstract interface ActivitySignService
{
  public abstract PageList searchBill(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract PageList searchRecord(String paramString, int paramInt1, int paramInt2);
}
