package lottery.domains.content.biz.impl;

import lottery.domains.content.entity.UserInfo;
import lottery.domains.content.entity.User;
import lottery.domains.content.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserInfoDao uInfoDao;
    
    @Override
    public boolean resetEmail(final String username) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean == null) {
            return false;
        }
        final UserInfo infoBean = this.uInfoDao.get(uBean.getId());
        if (infoBean != null) {
            infoBean.setEmail(null);
            return this.uInfoDao.update(infoBean);
        }
        return true;
    }
    
    @Override
    public boolean modifyEmail(final String username, final String email) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean == null) {
            return false;
        }
        UserInfo infoBean = this.uInfoDao.get(uBean.getId());
        if (infoBean == null) {
            infoBean = new UserInfo();
            infoBean.setUserId(uBean.getId());
            infoBean.setEmail(email);
            return this.uInfoDao.add(infoBean);
        }
        infoBean.setEmail(email);
        return this.uInfoDao.update(infoBean);
    }
}
