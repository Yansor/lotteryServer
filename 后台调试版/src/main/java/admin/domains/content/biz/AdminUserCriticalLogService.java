package admin.domains.content.biz;

import admin.domains.content.vo.AdminUserActionVO;
import java.util.List;
import javautils.jdbc.PageList;

public abstract interface AdminUserCriticalLogService
{
  public abstract PageList search(Integer paramInteger, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2);
  
  public abstract PageList search(String paramString, int paramInt1, int paramInt2);
  
  public abstract List<AdminUserActionVO> findAction();
}
