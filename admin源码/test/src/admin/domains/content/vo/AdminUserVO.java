package admin.domains.content.vo;

import admin.domains.content.entity.AdminUserRole;
import admin.domains.pool.AdminDataFactory;
import admin.domains.content.entity.AdminUser;

public class AdminUserVO
{
    private String role;
    private AdminUser bean;
    
    public AdminUserVO(final AdminUser bean, final AdminDataFactory df) {
        (this.bean = bean).setPassword("***");
        this.bean.setSecretKey("***");
        if (!"notset".equalsIgnoreCase(bean.getWithdrawPwd())) {
            this.bean.setWithdrawPwd("***");
        }
        final AdminUserRole role = df.getAdminUserRole(bean.getRoleId());
        this.role = role.getName();
    }
    
    public String getRole() {
        return this.role;
    }
    
    public void setRole(final String role) {
        this.role = role;
    }
    
    public AdminUser getBean() {
        return this.bean;
    }
    
    public void setBean(final AdminUser bean) {
        this.bean = bean;
    }
}
