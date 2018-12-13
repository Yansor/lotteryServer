package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.UserBetsHitRanking;

public abstract interface UserBetsHitRankingDao
{
  public abstract long getTotalSize(int paramInt);
  
  public abstract UserBetsHitRanking getMinRanking(int paramInt, String paramString1, String paramString2);
  
  public abstract boolean add(UserBetsHitRanking paramUserBetsHitRanking);
  
  public abstract List<Integer> getIds(int paramInt, String paramString1, String paramString2);
  
  public abstract List<Integer> getTotalIds(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract List<Integer> getIdsByTimeDesc(int paramInt1, int paramInt2);
  
  public abstract int delNotInIds(List<Integer> paramList, int paramInt);
  
  public abstract boolean delNotInIds(List<Integer> paramList, int paramInt, String paramString1, String paramString2);
  
  public abstract boolean delByTime(int paramInt, String paramString);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/UserBetsHitRankingDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */