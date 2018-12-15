package lottery.domains.content.biz;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserDailySettleBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserDailySettleBillService
{
  public abstract PageList search(List<Integer> paramList, String paramString1, String paramString2, Double paramDouble1, Double paramDouble2, Integer paramInteger, int paramInt1, int paramInt2);
  
  public abstract boolean add(UserDailySettleBill paramUserDailySettleBill);
  
  public abstract boolean update(UserDailySettleBill paramUserDailySettleBill);
  
  public abstract List<UserDailySettleBill> findByCriteria(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract List<UserDailySettleBill> getDirectLowerBills(int paramInt, String paramString, Integer[] paramArrayOfInteger, Integer paramInteger);
  
  public abstract UserDailySettleBill issue(int paramInt);
}
