package admin.domains.content.biz;

import admin.domains.content.entity.AdminUserMenu;
import java.util.List;

public interface AdminUserMenuService
{
    List<AdminUserMenu> listAll();
    
    AdminUserMenu getById(final int p0);
    
    boolean updateStatus(final int p0, final int p1);
    
    boolean moveUp(final int p0);
    
    boolean moveDown(final int p0);
}
