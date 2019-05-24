package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.UserInfo;

public abstract interface UserInfoDao
{
  public abstract UserInfo get(int paramInt);
  
  public abstract List<UserInfo> listByBirthday(String paramString);
  
  public abstract boolean add(UserInfo paramUserInfo);
  
  public abstract boolean update(UserInfo paramUserInfo);
}
