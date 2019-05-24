package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBalanceSnapshot;

public abstract interface UserBalanceSnapshotService
{
  public abstract PageList search(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract boolean add(UserBalanceSnapshot paramUserBalanceSnapshot);
}
