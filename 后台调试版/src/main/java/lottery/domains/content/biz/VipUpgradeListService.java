package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public abstract interface VipUpgradeListService
{
  public abstract PageList search(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract boolean add(int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, String paramString);
}
