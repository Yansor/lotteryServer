package admin.domains.pool;

import admin.domains.content.entity.AdminUserAction;
import admin.domains.content.entity.AdminUserMenu;
import admin.domains.content.entity.AdminUserRole;
import admin.domains.content.vo.AdminUserBaseVO;
import java.util.List;
import java.util.Set;

public abstract interface AdminDataFactory
{
  public abstract void init();
  
  public abstract void initSysMessage();
  
  public abstract String getSysMessage(String paramString);
  
  public abstract void initAdminUser();
  
  public abstract AdminUserBaseVO getAdminUser(int paramInt);
  
  public abstract void initAdminUserRole();
  
  public abstract AdminUserRole getAdminUserRole(int paramInt);
  
  public abstract List<AdminUserRole> listAdminUserRole();
  
  public abstract void initAdminUserAction();
  
  public abstract List<AdminUserAction> listAdminUserAction();
  
  public abstract AdminUserAction getAdminUserAction(int paramInt);
  
  public abstract AdminUserAction getAdminUserAction(String paramString);
  
  public abstract List<AdminUserAction> getAdminUserActionByRoleId(int paramInt);
  
  public abstract void initAdminUserMenu();
  
  public abstract List<AdminUserMenu> listAdminUserMenu();
  
  public abstract AdminUserMenu getAdminUserMenuByLink(String paramString);
  
  public abstract List<AdminUserMenu> getAdminUserMenuByRoleId(int paramInt);
  
  public abstract Set<Integer> getAdminUserMenuIdsByAction(int paramInt);
}
