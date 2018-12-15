package lottery.domains.content.biz;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserRecharge;
import lottery.domains.content.vo.user.HistoryUserRechargeVO;
import lottery.domains.content.vo.user.UserRechargeVO;

public abstract interface UserRechargeService
{
  public abstract UserRechargeVO getById(int paramInt);
  
  public abstract HistoryUserRechargeVO getHistoryById(int paramInt);
  
  public abstract List<UserRechargeVO> getLatest(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract List<UserRecharge> listByPayTimeAndStatus(String paramString1, String paramString2, int paramInt);
  
  public abstract PageList search(Integer paramInteger1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Double paramDouble1, Double paramDouble2, Integer paramInteger2, Integer paramInteger3, int paramInt1, int paramInt2);
  
  public abstract PageList searchHistory(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Double paramDouble1, Double paramDouble2, Integer paramInteger1, Integer paramInteger2, int paramInt1, int paramInt2);
  
  public abstract boolean addSystemRecharge(String paramString1, int paramInt1, int paramInt2, double paramDouble, int paramInt3, String paramString2);
  
  public abstract boolean patchOrder(String paramString1, String paramString2, String paramString3);
  
  public abstract boolean cancelOrder(String paramString);
  
  public abstract double getTotalRecharge(Integer paramInteger1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Double paramDouble1, Double paramDouble2, Integer paramInteger2, Integer paramInteger3);
  
  public abstract double getHistoryTotalRecharge(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Double paramDouble1, Double paramDouble2, Integer paramInteger1, Integer paramInteger2);
}
