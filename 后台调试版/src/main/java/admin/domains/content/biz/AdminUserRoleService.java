package admin.domains.content.biz;

import admin.domains.content.entity.AdminUserRole;
import admin.domains.content.vo.AdminUserRoleVO;
import java.util.List;

public abstract interface AdminUserRoleService
{
  public abstract List<AdminUserRole> listAll(int paramInt);
  
  public abstract List<AdminUserRole> listTree(int paramInt);
  
  public abstract boolean add(String paramString1, int paramInt1, String paramString2, int paramInt2);
  
  public abstract boolean edit(int paramInt1, String paramString1, int paramInt2, String paramString2, int paramInt3);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract AdminUserRoleVO getByName(String paramString);
  
  public abstract AdminUserRoleVO getById(int paramInt);
  
  public abstract boolean saveAccess(int paramInt, String paramString);
}
