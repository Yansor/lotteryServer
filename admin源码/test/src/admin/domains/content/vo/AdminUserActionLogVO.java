package admin.domains.content.vo;

import admin.domains.content.entity.AdminUserAction;
import admin.domains.pool.AdminDataFactory;
import admin.domains.content.entity.AdminUserActionLog;

public class AdminUserActionLogVO
{
    private String username;
    private String actionName;
    private AdminUserActionLog bean;
    
    public AdminUserActionLogVO(final AdminUserActionLog bean, final AdminDataFactory df) {
        this.bean = bean;
        final AdminUserBaseVO user = df.getAdminUser(bean.getUserId());
        if (user != null) {
            this.username = user.getUsername();
        }
        final AdminUserAction action = df.getAdminUserAction(bean.getActionId());
        if (action != null) {
            this.actionName = action.getName();
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getActionName() {
        return this.actionName;
    }
    
    public void setActionName(final String actionName) {
        this.actionName = actionName;
    }
    
    public AdminUserActionLog getBean() {
        return this.bean;
    }
    
    public void setBean(final AdminUserActionLog bean) {
        this.bean = bean;
    }
}
