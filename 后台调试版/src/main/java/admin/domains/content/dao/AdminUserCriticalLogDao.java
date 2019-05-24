package admin.domains.content.dao;

import admin.domains.content.entity.AdminUserCriticalLog;
import java.util.List;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface AdminUserCriticalLogDao
{
  public abstract boolean save(List<AdminUserCriticalLog> paramList);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract List<AdminUserCriticalLog> findAction();
}
