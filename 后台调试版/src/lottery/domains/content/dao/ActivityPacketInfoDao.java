package lottery.domains.content.dao;

import java.util.List;
import java.util.Map;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.ActivityPacketInfo;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface ActivityPacketInfoDao
{
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract boolean save(ActivityPacketInfo paramActivityPacketInfo);
  
  public abstract List<Map<Integer, Double>> statTotal();
}
