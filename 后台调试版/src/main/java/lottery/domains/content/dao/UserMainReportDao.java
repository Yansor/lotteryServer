package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.UserMainReport;
import lottery.domains.content.vo.bill.UserMainReportVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserMainReportDao
{
  public abstract boolean add(UserMainReport paramUserMainReport);
  
  public abstract UserMainReport get(int paramInt, String paramString);
  
  public abstract List<UserMainReport> list(int paramInt, String paramString1, String paramString2);
  
  public abstract boolean update(UserMainReport paramUserMainReport);
  
  public abstract List<UserMainReport> find(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract UserMainReportVO sumLowersAndSelf(int paramInt, String paramString1, String paramString2);
}
