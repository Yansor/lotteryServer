package admin.domains.content.vo;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import admin.domains.pool.AdminDataFactory;
import admin.domains.content.entity.AdminUserCriticalLog;

public class AdminUserCriticalLogVO
{
    private String username;
    private String adminUsername;
    private AdminUserCriticalLog bean;
    
    public AdminUserCriticalLogVO(final AdminUserCriticalLog bean, final AdminDataFactory df, final LotteryDataFactory ldf) {
        this.bean = bean;
        final AdminUserBaseVO adminUser = df.getAdminUser(bean.getAdminUserId());
        if (adminUser != null) {
            this.adminUsername = adminUser.getUsername();
        }
        final UserVO user = ldf.getUser(bean.getUserId());
        if (user != null) {
            this.username = user.getUsername();
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public AdminUserCriticalLog getBean() {
        return this.bean;
    }
    
    public void setBean(final AdminUserCriticalLog bean) {
        this.bean = bean;
    }
    
    public String getAdminUsername() {
        return this.adminUsername;
    }
    
    public void setAdminUsername(final String adminUsername) {
        this.adminUsername = adminUsername;
    }
}
