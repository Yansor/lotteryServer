package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.SysPlatform;

public abstract interface SysPlatformService
{
  public abstract List<SysPlatform> listAll();
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
}
