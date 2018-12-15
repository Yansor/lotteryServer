package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public abstract interface ActivityCostService
{
  public abstract PageList searchBill(String paramString1, String paramString2, int paramInt1, int paramInt2);
}
