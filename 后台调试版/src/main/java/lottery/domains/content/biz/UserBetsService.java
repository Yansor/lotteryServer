package lottery.domains.content.biz;

import java.util.Date;
import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBets;
import lottery.domains.content.vo.user.HistoryUserBetsVO;
import lottery.domains.content.vo.user.UserBetsVO;

public abstract interface UserBetsService
{
  public abstract PageList search(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString3, Integer paramInteger4, String paramString4, String paramString5, String paramString6, String paramString7, Double paramDouble1, Double paramDouble2, Integer paramInteger5, Integer paramInteger6, Double paramDouble3, Double paramDouble4, Integer paramInteger7, Integer paramInteger8, String paramString8, int paramInt1, int paramInt2);
  
  public abstract UserBetsVO getById(int paramInt);
  
  public abstract HistoryUserBetsVO getHistoryById(int paramInt);
  
  public abstract UserBets getBetsById(int paramInt);
  
  public abstract PageList searchHistory(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString3, Integer paramInteger4, String paramString4, String paramString5, String paramString6, String paramString7, Double paramDouble1, Double paramDouble2, Integer paramInteger5, Integer paramInteger6, Double paramDouble3, Double paramDouble4, Integer paramInteger7, Integer paramInteger8, int paramInt1, int paramInt2);
  
  public abstract List<UserBets> notOpened(int paramInt, Integer paramInteger, String paramString1, String paramString2);
  
  public abstract boolean cancel(int paramInt, Integer paramInteger, String paramString1, String paramString2);
  
  public abstract boolean cancel(int paramInt);
  
  public abstract List<UserBetsVO> getSuspiciousOrder(int paramInt1, int paramInt2);
  
  public abstract int countUserOnline(Date paramDate);
  
  public abstract double[] getTotalMoney(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString3, Integer paramInteger4, String paramString4, String paramString5, String paramString6, String paramString7, Double paramDouble1, Double paramDouble2, Integer paramInteger5, Integer paramInteger6, Double paramDouble3, Double paramDouble4, Integer paramInteger7, Integer paramInteger8, String paramString8);
  
  public abstract double[] getHistoryTotalMoney(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString3, Integer paramInteger4, String paramString4, String paramString5, String paramString6, String paramString7, Double paramDouble1, Double paramDouble2, Integer paramInteger5, Integer paramInteger6, Double paramDouble3, Double paramDouble4, Integer paramInteger7, Integer paramInteger8);
  
  public abstract double getBillingOrder(int paramInt, String paramString1, String paramString2);
}
