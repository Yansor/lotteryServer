package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public abstract interface ActivityRechargeService
{
  public abstract PageList search(String paramString1, String paramString2, String paramString3, Integer paramInteger, int paramInt1, int paramInt2);
  
  public abstract boolean agree(int paramInt);
  
  public abstract boolean refuse(int paramInt);
  
  public abstract boolean check(int paramInt);
}
