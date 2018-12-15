package admin.domains.content.biz;

import javautils.jdbc.PageList;

public abstract interface AdminUserActionLogService
{
  public abstract PageList search(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2);
}
