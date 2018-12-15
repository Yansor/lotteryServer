package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public abstract interface VipUpgradeGiftsService
{
  public abstract PageList search(String paramString1, String paramString2, Integer paramInteger, int paramInt1, int paramInt2);
  
  public abstract boolean doIssuingGift(int paramInt1, int paramInt2, int paramInt3);
}
