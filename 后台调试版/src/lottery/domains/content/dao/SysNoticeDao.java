package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.SysNotice;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface SysNoticeDao
{
  public abstract SysNotice getById(int paramInt);
  
  public abstract boolean add(SysNotice paramSysNotice);
  
  public abstract boolean update(SysNotice paramSysNotice);
  
  public abstract boolean delete(int paramInt);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
}
