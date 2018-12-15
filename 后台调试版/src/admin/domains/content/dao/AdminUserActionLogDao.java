package admin.domains.content.dao;

import admin.domains.content.entity.AdminUserActionLog;
import java.util.List;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface AdminUserActionLogDao
{
  public abstract boolean save(AdminUserActionLog paramAdminUserActionLog);
  
  public abstract boolean save(List<AdminUserActionLog> paramList);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
}
