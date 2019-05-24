package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBetsOriginal;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserBetsOriginalDao
{
  public abstract UserBetsOriginal getById(int paramInt);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract PageList find(String paramString, int paramInt1, int paramInt2);
  
  public abstract double[] getTotalMoney(String paramString1, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, String paramString2, Integer paramInteger5, String paramString3, String paramString4, String paramString5, String paramString6, Double paramDouble1, Double paramDouble2, Integer paramInteger6, Integer paramInteger7, Double paramDouble3, Double paramDouble4, Integer paramInteger8);
}
