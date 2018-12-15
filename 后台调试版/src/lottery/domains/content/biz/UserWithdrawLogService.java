package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserWithdrawLog;

public abstract interface UserWithdrawLogService
{
  public abstract boolean add(UserWithdrawLog paramUserWithdrawLog);
  
  public abstract PageList search(String paramString1, String paramString2, int paramInt1, int paramInt2);
}
