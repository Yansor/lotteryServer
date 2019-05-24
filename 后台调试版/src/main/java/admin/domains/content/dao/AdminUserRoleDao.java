package admin.domains.content.dao;

import admin.domains.content.entity.AdminUserRole;
import java.util.List;

public abstract interface AdminUserRoleDao
{
  public abstract List<AdminUserRole> listAll();
  
  public abstract boolean update(AdminUserRole paramAdminUserRole);
  
  public abstract boolean save(AdminUserRole paramAdminUserRole);
  
  public abstract AdminUserRole getByName(String paramString);
  
  public abstract AdminUserRole getById(int paramInt);
  
  public abstract List<AdminUserRole> getByUpid(int paramInt);
}
