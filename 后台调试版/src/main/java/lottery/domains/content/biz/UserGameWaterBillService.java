package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserGameWaterBill;

public abstract interface UserGameWaterBillService
{
  public abstract PageList search(Integer paramInteger1, String paramString1, String paramString2, Double paramDouble1, Double paramDouble2, Integer paramInteger2, Integer paramInteger3, int paramInt1, int paramInt2);
  
  public abstract boolean add(UserGameWaterBill paramUserGameWaterBill);
}
