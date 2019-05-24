package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public abstract interface UserBetsPlanService
{
  public abstract PageList search(String paramString1, String paramString2, Integer paramInteger1, String paramString3, Integer paramInteger2, String paramString4, String paramString5, Double paramDouble1, Double paramDouble2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5, int paramInt1, int paramInt2);
}
