package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserMessage;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserMessageDao
{
  public abstract UserMessage getById(int paramInt);
  
  public abstract boolean delete(int paramInt);
  
  public abstract PageList search(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract boolean save(UserMessage paramUserMessage);
  
  public abstract void update(UserMessage paramUserMessage);
}
