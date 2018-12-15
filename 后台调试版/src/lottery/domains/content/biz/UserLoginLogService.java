package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public abstract interface UserLoginLogService
{
  public abstract PageList search(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2);
  
  public abstract PageList searchHistory(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2);
  
  public abstract PageList searchSameIp(String paramString1, String paramString2, int paramInt1, int paramInt2);
}
