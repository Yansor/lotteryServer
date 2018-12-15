package lottery.domains.content.dao;

import lottery.domains.content.entity.UserGameAccount;

public abstract interface UserGameAccountDao
{
  public abstract UserGameAccount get(String paramString, int paramInt);
  
  public abstract UserGameAccount get(int paramInt1, int paramInt2);
  
  public abstract UserGameAccount save(UserGameAccount paramUserGameAccount);
}
