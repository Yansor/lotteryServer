package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import lottery.domains.content.vo.user.UserMessageVO;

public abstract interface UserMessageService
{
  public abstract PageList search(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract UserMessageVO getById(int paramInt);
  
  public abstract boolean delete(int paramInt);
  
  public abstract boolean save(int paramInt, String paramString);
}
