package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public abstract interface ActivityBindService
{
  public abstract PageList search(String paramString1, String paramString2, String paramString3, String paramString4, Integer paramInteger, int paramInt1, int paramInt2);
  
  public abstract boolean refuse(int paramInt);
  
  public abstract boolean check(int paramInt);
}
