package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBetsHitRanking;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserBetsHitRankingDao
{
  public abstract UserBetsHitRanking getById(int paramInt);
  
  public abstract boolean add(UserBetsHitRanking paramUserBetsHitRanking);
  
  public abstract boolean update(UserBetsHitRanking paramUserBetsHitRanking);
  
  public abstract boolean delete(int paramInt);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
}
