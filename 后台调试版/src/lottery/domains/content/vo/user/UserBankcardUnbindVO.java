package lottery.domains.content.vo.user;

import lottery.domains.content.entity.UserBankcardUnbindRecord;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.lang.StringUtils;

public class UserBankcardUnbindVO
{
  private int id;
  private String userIds;
  private String cardId;
  private int unbindNum;
  private String unbindTime;
  private String username;
  
  public UserBankcardUnbindVO(UserBankcardUnbindRecord entity, LotteryDataFactory df)
  {
    this.id = entity.getId();
    this.userIds = entity.getUserIds();
    this.cardId = entity.getCardId();
    this.unbindNum = entity.getUnbindNum();
    this.unbindTime = entity.getUnbindTime();
    String usernames = "";
    if ((entity.getUserIds() != null) && (!entity.getUserIds().equals(""))) {
      if (entity.getUserIds().contains("#"))
      {
        String[] ids = entity.getUserIds().split("#");
        StringBuffer nameapp = new StringBuffer();
        String[] arrayOfString1;
        int j = (arrayOfString1 = ids).length;
        for (int i = 0; i < j; i++)
        {
          String string = arrayOfString1[i];
          if (StringUtils.isNotBlank(string))
          {
            UserVO user = df.getUser(Integer.parseInt(string));
            if (user != null) {
              nameapp.append(user.getUsername()).append(",");
            }
          }
        }
        String res = nameapp.toString();
        usernames = res.substring(0, res.length() - 1);
      }
      else
      {
        UserVO user = df.getUser(Integer.parseInt(entity.getUserIds()));
        if (user != null) {
          usernames = user.getUsername();
        }
      }
    }
    this.username = usernames;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getUserIds()
  {
    return this.userIds;
  }
  
  public void setUserIds(String userIds)
  {
    this.userIds = userIds;
  }
  
  public String getCardId()
  {
    return this.cardId;
  }
  
  public void setCardId(String cardId)
  {
    this.cardId = cardId;
  }
  
  public int getUnbindNum()
  {
    return this.unbindNum;
  }
  
  public void setUnbindNum(int unbindNum)
  {
    this.unbindNum = unbindNum;
  }
  
  public String getUnbindTime()
  {
    return this.unbindTime;
  }
  
  public void setUnbindTime(String unbindTime)
  {
    this.unbindTime = unbindTime;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
}
