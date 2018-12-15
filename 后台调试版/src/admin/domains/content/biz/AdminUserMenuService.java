package admin.domains.content.biz;

import admin.domains.content.entity.AdminUserMenu;
import java.util.List;

public abstract interface AdminUserMenuService
{
  public abstract List<AdminUserMenu> listAll();
  
  public abstract AdminUserMenu getById(int paramInt);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean moveUp(int paramInt);
  
  public abstract boolean moveDown(int paramInt);
}
