package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.VipBirthdayGifts;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface VipBirthdayGiftsDao
{
  public abstract boolean add(VipBirthdayGifts paramVipBirthdayGifts);
  
  public abstract VipBirthdayGifts getById(int paramInt);
  
  public abstract int getWaitTodo();
  
  public abstract boolean hasRecord(int paramInt1, int paramInt2);
  
  public abstract boolean update(VipBirthdayGifts paramVipBirthdayGifts);
  
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
}
