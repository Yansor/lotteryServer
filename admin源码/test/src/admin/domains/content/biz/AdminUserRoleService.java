package admin.domains.content.biz;

import admin.domains.content.vo.AdminUserRoleVO;
import admin.domains.content.entity.AdminUserRole;
import java.util.List;

public interface AdminUserRoleService
{
    List<AdminUserRole> listAll(final int p0);
    
    List<AdminUserRole> listTree(final int p0);
    
    boolean add(final String p0, final int p1, final String p2, final int p3);
    
    boolean edit(final int p0, final String p1, final int p2, final String p3, final int p4);
    
    boolean updateStatus(final int p0, final int p1);
    
    AdminUserRoleVO getByName(final String p0);
    
    AdminUserRoleVO getById(final int p0);
    
    boolean saveAccess(final int p0, final String p1);
}
