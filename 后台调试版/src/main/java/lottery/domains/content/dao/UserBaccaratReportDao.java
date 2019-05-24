package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.UserBaccaratReport;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserBaccaratReportDao
{
  public abstract boolean add(UserBaccaratReport paramUserBaccaratReport);
  
  public abstract UserBaccaratReport get(int paramInt, String paramString);
  
  public abstract boolean update(UserBaccaratReport paramUserBaccaratReport);
  
  public abstract List<UserBaccaratReport> find(List<Criterion> paramList, List<Order> paramList1);
}
