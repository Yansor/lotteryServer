package lottery.domains.content.biz;

import admin.web.WebJSONObject;
import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserDailySettle;

public abstract interface UserDailySettleService
{
  public abstract PageList search(List<Integer> paramList, String paramString1, String paramString2, Double paramDouble1, Double paramDouble2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, int paramInt1, int paramInt2);
  
  public abstract UserDailySettle getByUserId(int paramInt);
  
  public abstract UserDailySettle getById(int paramInt);
  
  public abstract boolean deleteByTeam(String paramString);
  
  public abstract boolean changeZhaoShang(User paramUser, boolean paramBoolean);
  
  public abstract void checkDailySettle(String paramString);
  
  public abstract boolean update(WebJSONObject paramWebJSONObject, int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, String paramString4);
  
  public abstract boolean add(WebJSONObject paramWebJSONObject, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, String paramString5);
  
  public abstract boolean checkCanEdit(WebJSONObject paramWebJSONObject, User paramUser);
  
  public abstract boolean checkCanDel(WebJSONObject paramWebJSONObject, User paramUser);
  
  public abstract double[] getMinMaxScale(User paramUser);
  
  public abstract double[] getMinMaxSales(User paramUser);
  
  public abstract double[] getMinMaxLoss(User paramUser);
  
  public abstract int[] getMinMaxUsers(User paramUser);
  
  public abstract boolean checkValidLevel(String paramString1, String paramString2, String paramString3, UserDailySettle paramUserDailySettle, String paramString4);
}
