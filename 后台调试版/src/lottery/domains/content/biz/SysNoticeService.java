package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public abstract interface SysNoticeService
{
  public abstract PageList search(Integer paramInteger, int paramInt1, int paramInt2);
  
  public abstract boolean add(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, String paramString4);
  
  public abstract boolean edit(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, int paramInt3, String paramString4);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean updateSort(int paramInt1, int paramInt2);
  
  public abstract boolean delete(int paramInt);
}
