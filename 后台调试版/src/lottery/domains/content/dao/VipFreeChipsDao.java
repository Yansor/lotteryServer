package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.VipFreeChips;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface VipFreeChipsDao
{
  public abstract boolean add(VipFreeChips paramVipFreeChips);
  
  public abstract VipFreeChips getById(int paramInt);
  
  public abstract List<VipFreeChips> getUntreated();
  
  public abstract boolean hasRecord(int paramInt, String paramString1, String paramString2);
  
  public abstract boolean update(VipFreeChips paramVipFreeChips);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
}
