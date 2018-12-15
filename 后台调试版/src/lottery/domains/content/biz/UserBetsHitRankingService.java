package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBetsHitRanking;

public abstract interface UserBetsHitRankingService
{
  public abstract PageList search(int paramInt1, int paramInt2);
  
  public abstract boolean add(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, String paramString5, int paramInt2);
  
  public abstract boolean edit(int paramInt1, String paramString1, String paramString2, int paramInt2, String paramString3, String paramString4, String paramString5, int paramInt3);
  
  public abstract boolean delete(int paramInt);
  
  public abstract UserBetsHitRanking getById(int paramInt);
}
