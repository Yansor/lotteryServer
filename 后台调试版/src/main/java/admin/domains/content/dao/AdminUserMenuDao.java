package admin.domains.content.dao;

import admin.domains.content.entity.AdminUserMenu;
import java.util.List;

public abstract interface AdminUserMenuDao
{
  public abstract List<AdminUserMenu> listAll();
  
  public abstract boolean update(AdminUserMenu paramAdminUserMenu);
  
  public abstract AdminUserMenu getById(int paramInt);
  
  public abstract boolean updateSort(int paramInt1, int paramInt2);
  
  public abstract boolean modsort(int paramInt1, int paramInt2);
  
  public abstract List<AdminUserMenu> getBySortUp(int paramInt1, int paramInt2);
  
  public abstract List<AdminUserMenu> getBySortDown(int paramInt1, int paramInt2);
  
  public abstract int getMaxSort(int paramInt);
}
