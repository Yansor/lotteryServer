package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.User;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserDao
{
  public abstract List<User> list(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract List<User> listAll();
  
  public abstract boolean updateLotteryMoney(int paramInt, double paramDouble1, double paramDouble2);
  
  public abstract boolean updateLotteryMoney(int paramInt, double paramDouble);
  
  public abstract boolean updateFreezeMoney(int paramInt, double paramDouble);
  
  public abstract User getById(int paramInt);
  
  public abstract int clearUserNegateFreezeMoney();
  
  public abstract List<User> getUserLower(int paramInt);
  
  public abstract List<User> getUserDirectLower(int paramInt);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/UserDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */