package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.VipUpgradeGifts;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface VipUpgradeGiftsDao
{
  public abstract boolean add(VipUpgradeGifts paramVipUpgradeGifts);
  
  public abstract VipUpgradeGifts getById(int paramInt);
  
  public abstract int getWaitTodo();
  
  public abstract boolean hasRecord(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract boolean update(VipUpgradeGifts paramVipUpgradeGifts);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
}
