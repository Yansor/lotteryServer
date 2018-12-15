package lottery.domains.content.vo.user;

import java.util.LinkedList;
import java.util.List;
import javautils.StringUtil;
import lottery.domains.content.entity.User;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.UserCodePointUtil;
import org.apache.commons.lang.math.NumberUtils;

public class UserProfileVO
{
  private String upUser;
  private List<String> levelUsers = new LinkedList();
  private List<String> relatedUsers = new LinkedList();
  private String relatedUpUser;
  private int lowerUsers;
  private User bean;
  private boolean isZhaoShang;
  
  public UserProfileVO(User bean, List<User> lowerUsers, LotteryDataFactory df, UserCodePointUtil uCodePointUtil)
  {
    this.bean = bean;
    this.bean.setPassword("***");
    this.bean.setSecretKey("***");
    if (StringUtil.isNotNull(this.bean.getWithdrawPassword())) {
      this.bean.setWithdrawPassword("***");
    }
    if (StringUtil.isNotNull(this.bean.getImgPassword())) {
      this.bean.setImgPassword("***");
    }
    this.lowerUsers = lowerUsers.size();
    if (bean.getUpid() != 0)
    {
      UserVO user = df.getUser(bean.getUpid());
      this.upUser = user.getUsername();
    }
    String[] arrayOfString1;
    int j;
    int i;
    if (StringUtil.isNotNull(bean.getUpids()))
    {
      String[] ids = bean.getUpids().replaceAll("\\[|\\]", "").split(",");
      j = (arrayOfString1 = ids).length;
      for (i = 0; i < j; i++)
      {
        String id = arrayOfString1[i];
        UserVO user = df.getUser(Integer.parseInt(id));
        if (user != null) {
          this.levelUsers.add(user.getUsername());
        } else {
          this.levelUsers.add("unknown");
        }
      }
    }
    if (bean.getRelatedUpid() != 0)
    {
      UserVO user = df.getUser(bean.getRelatedUpid());
      if (user != null) {
        this.relatedUpUser = user.getUsername();
      }
    }
    if ((this.bean.getType() == 3) && (StringUtil.isNotNull(this.bean.getRelatedUsers())))
    {
      String[] ids = bean.getRelatedUsers().replaceAll("\\[|\\]", "").split(",");
      j = (arrayOfString1 = ids).length;
      for (i = 0; i < j; i++)
      {
        String id = arrayOfString1[i];
        if (NumberUtils.isDigits(id))
        {
          UserVO relatedUser = df.getUser(Integer.valueOf(id).intValue());
          if (relatedUser != null) {
            this.relatedUsers.add(relatedUser.getUsername());
          }
        }
      }
    }
    this.isZhaoShang = uCodePointUtil.isLevel2Proxy(bean);
  }
  
  public String getUpUser()
  {
    return this.upUser;
  }
  
  public void setUpUser(String upUser)
  {
    this.upUser = upUser;
  }
  
  public List<String> getLevelUsers()
  {
    return this.levelUsers;
  }
  
  public void setLevelUsers(List<String> levelUsers)
  {
    this.levelUsers = levelUsers;
  }
  
  public String getRelatedUpUser()
  {
    return this.relatedUpUser;
  }
  
  public void setRelatedUpUser(String relatedUpUser)
  {
    this.relatedUpUser = relatedUpUser;
  }
  
  public int getLowerUsers()
  {
    return this.lowerUsers;
  }
  
  public void setLowerUsers(int lowerUsers)
  {
    this.lowerUsers = lowerUsers;
  }
  
  public User getBean()
  {
    return this.bean;
  }
  
  public void setBean(User bean)
  {
    this.bean = bean;
  }
  
  public List<String> getRelatedUsers()
  {
    return this.relatedUsers;
  }
  
  public void setRelatedUsers(List<String> relatedUsers)
  {
    this.relatedUsers = relatedUsers;
  }
  
  public boolean isZhaoShang()
  {
    return this.isZhaoShang;
  }
  
  public void setZhaoShang(boolean zhaoShang)
  {
    this.isZhaoShang = zhaoShang;
  }
}
