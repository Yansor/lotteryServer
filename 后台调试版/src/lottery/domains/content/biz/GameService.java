package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import lottery.domains.content.entity.Game;

public abstract interface GameService
{
  public abstract PageList search(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5, int paramInt1, int paramInt2);
  
  public abstract boolean add(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, String paramString3, int paramInt1, int paramInt2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5, String paramString4);
  
  public abstract Game getById(int paramInt);
  
  public abstract Game getByGameName(String paramString);
  
  public abstract Game getByGameCode(String paramString);
  
  public abstract boolean deleteById(int paramInt);
  
  public abstract boolean update(int paramInt, String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, String paramString3, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5, Integer paramInteger6, Integer paramInteger7, String paramString4);
  
  public abstract boolean updateSequence(int paramInt1, int paramInt2);
  
  public abstract boolean updateDisplay(int paramInt1, int paramInt2);
}
