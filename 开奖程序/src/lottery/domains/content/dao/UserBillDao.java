package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserBillDao
{
  public abstract boolean add(UserBill paramUserBill);
  
  public abstract boolean addBills(List<UserBill> paramList);
  
  public abstract UserBill getById(int paramInt);
  
  public abstract PageList search(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract int clearUserBillNegateBeforeMoney();
  
  public abstract int clearUserBillNegateAfterMoney();
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/UserBillDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */