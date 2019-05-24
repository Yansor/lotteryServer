package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserDailySettleBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserDailySettleBillDao
{
  public abstract PageList search(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract List<UserDailySettleBill> findByCriteria(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract boolean add(UserDailySettleBill paramUserDailySettleBill);
  
  public abstract UserDailySettleBill getById(int paramInt);
  
  public abstract boolean update(UserDailySettleBill paramUserDailySettleBill);
}
