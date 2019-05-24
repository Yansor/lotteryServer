package admin.domains.content.dao;

import admin.domains.content.entity.AdminUserLog;
import java.util.List;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface AdminUserLogDao
{
  public abstract boolean save(List<AdminUserLog> paramList);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
}
