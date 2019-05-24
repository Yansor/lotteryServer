package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBetsLimit;
import lottery.domains.content.vo.user.UserBetsLimitVO;

public abstract interface UserBetsLimitService
{
  public abstract boolean addUserBetsLimit(String paramString1, int paramInt, double paramDouble1, String paramString2, double paramDouble2);
  
  public abstract PageList search(String paramString, int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract boolean updateUserBetsLimit(UserBetsLimit paramUserBetsLimit);
  
  public abstract boolean deleteUserBetsLimit(int paramInt);
  
  public abstract boolean addOrUpdate(Integer paramInteger, String paramString1, int paramInt, double paramDouble1, String paramString2, double paramDouble2);
  
  public abstract UserBetsLimitVO getById(int paramInt);
}
