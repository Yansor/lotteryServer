package admin.domains.pool;

import java.util.Set;
import admin.domains.content.entity.AdminUserMenu;
import admin.domains.content.entity.AdminUserAction;
import java.util.List;
import admin.domains.content.entity.AdminUserRole;
import admin.domains.content.vo.AdminUserBaseVO;

public interface AdminDataFactory
{
    void init();
    
    void initSysMessage();
    
    String getSysMessage(final String p0);
    
    void initAdminUser();
    
    AdminUserBaseVO getAdminUser(final int p0);
    
    void initAdminUserRole();
    
    AdminUserRole getAdminUserRole(final int p0);
    
    List<AdminUserRole> listAdminUserRole();
    
    void initAdminUserAction();
    
    List<AdminUserAction> listAdminUserAction();
    
    AdminUserAction getAdminUserAction(final int p0);
    
    AdminUserAction getAdminUserAction(final String p0);
    
    List<AdminUserAction> getAdminUserActionByRoleId(final int p0);
    
    void initAdminUserMenu();
    
    List<AdminUserMenu> listAdminUserMenu();
    
    AdminUserMenu getAdminUserMenuByLink(final String p0);
    
    List<AdminUserMenu> getAdminUserMenuByRoleId(final int p0);
    
    Set<Integer> getAdminUserMenuIdsByAction(final int p0);
}
