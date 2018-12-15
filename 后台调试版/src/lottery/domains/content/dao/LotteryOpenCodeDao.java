package lottery.domains.content.dao;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.LotteryOpenCode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface LotteryOpenCodeDao
{
  public abstract PageList find(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract LotteryOpenCode get(String paramString1, String paramString2);
  
  public abstract boolean add(LotteryOpenCode paramLotteryOpenCode);
  
  public abstract List<LotteryOpenCode> list(String paramString, String[] paramArrayOfString);
  
  public abstract boolean update(LotteryOpenCode paramLotteryOpenCode);
  
  public abstract boolean delete(LotteryOpenCode paramLotteryOpenCode);
  
  public abstract int countByInterfaceTime(String paramString1, String paramString2, String paramString3);
  
  public abstract LotteryOpenCode getFirstExpectByInterfaceTime(String paramString1, String paramString2, String paramString3);
  
  public abstract List<LotteryOpenCode> getLatest(String paramString, int paramInt);
}
