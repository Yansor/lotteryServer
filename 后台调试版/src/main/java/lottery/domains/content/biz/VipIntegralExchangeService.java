package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public abstract interface VipIntegralExchangeService
{
  public abstract PageList search(String paramString1, String paramString2, int paramInt1, int paramInt2);
}
