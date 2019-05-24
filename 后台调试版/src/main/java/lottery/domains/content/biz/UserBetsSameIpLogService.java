package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public abstract interface UserBetsSameIpLogService
{
  public abstract PageList search(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2);
}
