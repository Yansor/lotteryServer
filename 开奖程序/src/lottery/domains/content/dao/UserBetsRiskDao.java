package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.UserBetsRisk;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserBetsRiskDao
{
  public abstract List<UserBetsRisk> list(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2, int paramInt3, String paramString1, double paramDouble, String paramString2, int paramInt4);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/UserBetsRiskDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */