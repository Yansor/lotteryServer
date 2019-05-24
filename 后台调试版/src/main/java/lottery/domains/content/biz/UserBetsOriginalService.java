package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import lottery.domains.content.vo.user.UserBetsOriginalVO;

public abstract interface UserBetsOriginalService
{
  public abstract PageList search(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString3, Integer paramInteger4, String paramString4, String paramString5, String paramString6, String paramString7, Double paramDouble1, Double paramDouble2, Integer paramInteger5, Integer paramInteger6, Double paramDouble3, Double paramDouble4, Integer paramInteger7, int paramInt1, int paramInt2);
  
  public abstract UserBetsOriginalVO getById(int paramInt);
  
  public abstract double[] getTotalMoney(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString3, Integer paramInteger4, String paramString4, String paramString5, String paramString6, String paramString7, Double paramDouble1, Double paramDouble2, Integer paramInteger5, Integer paramInteger6, Double paramDouble3, Double paramDouble4, Integer paramInteger7);
}
