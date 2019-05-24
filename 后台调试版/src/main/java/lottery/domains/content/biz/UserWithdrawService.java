package lottery.domains.content.biz;

import admin.domains.content.entity.AdminUser;
import admin.web.WebJSONObject;
import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.vo.user.HistoryUserWithdrawVO;
import lottery.domains.content.vo.user.UserWithdrawVO;

public abstract interface UserWithdrawService
{
  public abstract UserWithdrawVO getById(int paramInt);
  
  public abstract HistoryUserWithdrawVO getHistoryById(int paramInt);
  
  public abstract List<UserWithdrawVO> getLatest(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract List<UserWithdraw> listByRemitStatus(int[] paramArrayOfInt, boolean paramBoolean, String paramString1, String paramString2);
  
  public abstract PageList search(Integer paramInteger1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Double paramDouble1, Double paramDouble2, String paramString7, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5, int paramInt1, int paramInt2);
  
  public abstract PageList searchHistory(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Double paramDouble1, Double paramDouble2, String paramString7, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, int paramInt1, int paramInt2);
  
  public abstract boolean manualPay(AdminUser paramAdminUser, WebJSONObject paramWebJSONObject, int paramInt, String paramString1, String paramString2, String paramString3);
  
  public abstract boolean completeRemit(AdminUser paramAdminUser, WebJSONObject paramWebJSONObject, int paramInt, String paramString);
  
  public abstract boolean apiPay(AdminUser paramAdminUser, WebJSONObject paramWebJSONObject, int paramInt, PaymentChannel paramPaymentChannel);
  
  public abstract boolean check(AdminUser paramAdminUser, WebJSONObject paramWebJSONObject, int paramInt1, int paramInt2);
  
  public abstract boolean refuse(AdminUser paramAdminUser, WebJSONObject paramWebJSONObject, int paramInt, String paramString1, String paramString2, String paramString3);
  
  public abstract boolean withdrawFailure(AdminUser paramAdminUser, WebJSONObject paramWebJSONObject, int paramInt, String paramString1, String paramString2);
  
  public abstract boolean reviewedFail(AdminUser paramAdminUser, WebJSONObject paramWebJSONObject, int paramInt, String paramString1, String paramString2);
  
  public abstract boolean lock(AdminUser paramAdminUser, WebJSONObject paramWebJSONObject, int paramInt, String paramString);
  
  public abstract boolean unlock(AdminUser paramAdminUser, WebJSONObject paramWebJSONObject, int paramInt, String paramString);
  
  public abstract boolean update(UserWithdraw paramUserWithdraw);
  
  public abstract double[] getTotalWithdraw(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Double paramDouble1, Double paramDouble2, String paramString7, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract double[] getHistoryTotalWithdraw(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Double paramDouble1, Double paramDouble2, String paramString7, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
}
