package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.Game;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface GameDao
{
  public abstract PageList search(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract boolean add(Game paramGame);
  
  public abstract Game getById(int paramInt);
  
  public abstract Game getByGameName(String paramString);
  
  public abstract Game getByGameCode(String paramString);
  
  public abstract boolean deleteById(int paramInt);
  
  public abstract boolean update(Game paramGame);
  
  public abstract boolean updateSequence(int paramInt1, int paramInt2);
  
  public abstract boolean updateDisplay(int paramInt1, int paramInt2);
}
