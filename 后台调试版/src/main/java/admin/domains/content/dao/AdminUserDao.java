package admin.domains.content.dao;

import admin.domains.content.entity.AdminUser;
import java.util.List;

public abstract interface AdminUserDao
{
  public abstract List<AdminUser> listAll();
  
  public abstract AdminUser getById(int paramInt);
  
  public abstract AdminUser getByUsername(String paramString);
  
  public abstract boolean add(AdminUser paramAdminUser);
  
  public abstract boolean update(AdminUser paramAdminUser);
}
