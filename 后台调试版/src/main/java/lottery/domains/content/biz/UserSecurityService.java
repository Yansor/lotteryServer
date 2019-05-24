package lottery.domains.content.biz;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.vo.user.UserSecurityVO;

public abstract interface UserSecurityService
{
  public abstract PageList search(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract List<UserSecurityVO> getByUserId(int paramInt);
  
  public abstract boolean reset(String paramString);
}
