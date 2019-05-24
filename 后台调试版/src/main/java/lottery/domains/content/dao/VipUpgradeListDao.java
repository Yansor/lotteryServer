package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.VipUpgradeList;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface VipUpgradeListDao
{
  public abstract boolean add(VipUpgradeList paramVipUpgradeList);
  
  public abstract boolean hasRecord(int paramInt, String paramString);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
}
