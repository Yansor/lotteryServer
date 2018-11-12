package admin.domains.content.vo;

import admin.domains.pool.AdminDataFactory;
import admin.domains.content.entity.AdminUserAction;

public class AdminUserActionVO
{
    private AdminUserAction bean;
    
    public AdminUserActionVO(final AdminUserAction bean, final AdminDataFactory df) {
        this.bean = bean;
    }
    
    public AdminUserAction getBean() {
        return this.bean;
    }
    
    public void setBean(final AdminUserAction bean) {
        this.bean = bean;
    }
}
