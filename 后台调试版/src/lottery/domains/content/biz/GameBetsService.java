package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import lottery.domains.content.vo.user.GameBetsVO;

public abstract interface GameBetsService
{
  public abstract GameBetsVO getById(int paramInt);
  
  public abstract PageList search(String paramString1, String paramString2, Integer paramInteger, String paramString3, String paramString4, Double paramDouble1, Double paramDouble2, Double paramDouble3, Double paramDouble4, String paramString5, String paramString6, String paramString7, int paramInt1, int paramInt2);
  
  public abstract double[] getTotalMoney(String paramString1, String paramString2, Integer paramInteger, String paramString3, String paramString4, Double paramDouble1, Double paramDouble2, Double paramDouble3, Double paramDouble4, String paramString5, String paramString6, String paramString7);
  
  public abstract double getBillingOrder(int paramInt, String paramString1, String paramString2);
}
