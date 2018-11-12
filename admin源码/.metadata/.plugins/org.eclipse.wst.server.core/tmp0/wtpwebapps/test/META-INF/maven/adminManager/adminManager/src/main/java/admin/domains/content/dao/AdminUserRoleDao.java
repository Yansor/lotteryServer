package admin.domains.content.dao;

import admin.domains.content.entity.AdminUserRole;
import java.util.List;

public interface AdminUserRoleDao
{
    List<AdminUserRole> listAll();
    
    boolean update(final AdminUserRole p0);
    
    boolean save(final AdminUserRole p0);
    
    AdminUserRole getByName(final String p0);
    
    AdminUserRole getById(final int p0);
    
    List<AdminUserRole> getByUpid(final int p0);
}
