package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.UserSysMessage;

public abstract interface UserSysMessageDao
{
  public abstract boolean add(UserSysMessage paramUserSysMessage);
  
  public abstract List<UserSysMessage> listUnread(int paramInt);
  
  public abstract boolean updateUnread(int paramInt, int[] paramArrayOfInt);
}
