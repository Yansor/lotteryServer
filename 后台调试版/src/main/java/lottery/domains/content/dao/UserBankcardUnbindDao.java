package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBankcardUnbindRecord;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserBankcardUnbindDao
{
  public abstract boolean add(UserBankcardUnbindRecord paramUserBankcardUnbindRecord);
  
  public abstract boolean update(UserBankcardUnbindRecord paramUserBankcardUnbindRecord);
  
  public abstract boolean delByCardId(String paramString);
  
  public abstract boolean updateByParam(String paramString1, String paramString2, int paramInt, String paramString3);
  
  public abstract UserBankcardUnbindRecord getUnbindInfoById(int paramInt);
  
  public abstract UserBankcardUnbindRecord getUnbindInfoBycardId(String paramString);
  
  public abstract PageList search(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract List<UserBankcardUnbindRecord> listAll();
}
