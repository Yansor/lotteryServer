package admin.domains.content.biz;

import admin.domains.content.vo.AdminUserActionVO;
import java.util.List;

public abstract interface AdminUserActionService
{
  public abstract List<AdminUserActionVO> listAll();
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
}
