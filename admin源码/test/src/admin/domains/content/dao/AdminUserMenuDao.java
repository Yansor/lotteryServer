package admin.domains.content.dao;

import admin.domains.content.entity.AdminUserMenu;
import java.util.List;

public interface AdminUserMenuDao
{
    List<AdminUserMenu> listAll();
    
    boolean update(final AdminUserMenu p0);
    
    AdminUserMenu getById(final int p0);
    
    boolean updateSort(final int p0, final int p1);
    
    boolean modsort(final int p0, final int p1);
    
    List<AdminUserMenu> getBySortUp(final int p0, final int p1);
    
    List<AdminUserMenu> getBySortDown(final int p0, final int p1);
    
    int getMaxSort(final int p0);
}
