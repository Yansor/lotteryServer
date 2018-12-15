package lottery.domains.content.biz.impl;

import lottery.domains.content.biz.UserInfoService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserInfoDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl
  implements UserInfoService
{
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserInfoDao uInfoDao;
  
  public boolean resetEmail(String username)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null)
    {
      UserInfo infoBean = this.uInfoDao.get(uBean.getId());
      if (infoBean != null)
      {
        infoBean.setEmail(null);
        return this.uInfoDao.update(infoBean);
      }
      return true;
    }
    return false;
  }
  
  public boolean modifyEmail(String username, String email)
  {
    User uBean = this.uDao.getByUsername(username);
    if (uBean != null)
    {
      UserInfo infoBean = this.uInfoDao.get(uBean.getId());
      if (infoBean == null)
      {
        infoBean = new UserInfo();
        infoBean.setUserId(uBean.getId());
        infoBean.setEmail(email);
        return this.uInfoDao.add(infoBean);
      }
      infoBean.setEmail(email);
      return this.uInfoDao.update(infoBean);
    }
    return false;
  }
}
