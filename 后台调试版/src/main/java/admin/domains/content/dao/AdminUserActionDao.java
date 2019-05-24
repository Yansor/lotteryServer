package admin.domains.content.dao;

import admin.domains.content.entity.AdminUserAction;
import java.util.List;

public abstract interface AdminUserActionDao
{
  public abstract List<AdminUserAction> listAll();
  
  public abstract boolean update(AdminUserAction paramAdminUserAction);
  
  public abstract boolean save(AdminUserAction paramAdminUserAction);
  
  public abstract AdminUserAction getById(int paramInt);
}
