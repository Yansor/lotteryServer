package admin.domains.content.vo;

import admin.domains.pool.AdminDataFactory;
import admin.domains.content.entity.AdminUserLog;

public class AdminUserLogVO
{
    private String username;
    private AdminUserLog bean;
    
    public AdminUserLogVO(final AdminUserLog bean, final AdminDataFactory df) {
        this.bean = bean;
        final AdminUserBaseVO user = df.getAdminUser(bean.getUserId());
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
    
    public AdminUserLog getBean() {
        return this.bean;
    }
    
    public void setBean(final AdminUserLog bean) {
        this.bean = bean;
    }
}
