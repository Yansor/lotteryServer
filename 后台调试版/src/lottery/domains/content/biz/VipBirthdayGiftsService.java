package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public abstract interface VipBirthdayGiftsService
{
  public abstract PageList search(String paramString1, Integer paramInteger1, String paramString2, Integer paramInteger2, int paramInt1, int paramInt2);
  
  public abstract boolean calculate(String paramString);
}
