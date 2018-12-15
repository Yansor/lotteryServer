package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.GameBets;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface GameBetsDao
{
  public abstract GameBets getById(int paramInt);
  
  public abstract GameBets get(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract boolean save(GameBets paramGameBets);
  
  public abstract boolean update(GameBets paramGameBets);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract double[] getTotalMoney(String paramString1, Integer paramInteger1, Integer paramInteger2, String paramString2, String paramString3, Double paramDouble1, Double paramDouble2, Double paramDouble3, Double paramDouble4, String paramString4, String paramString5, String paramString6);
  
  public abstract double getBillingOrder(int paramInt, String paramString1, String paramString2);
}
