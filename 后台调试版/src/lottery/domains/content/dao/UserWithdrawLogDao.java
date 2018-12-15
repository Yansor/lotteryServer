package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserWithdrawLog;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserWithdrawLogDao
{
  public abstract boolean add(UserWithdrawLog paramUserWithdrawLog);
  
  public abstract List<UserWithdrawLog> getByUserId(int paramInt1, int paramInt2);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
}
