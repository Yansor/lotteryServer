package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.SysPlatform;

public abstract interface SysPlatformDao
{
  public abstract List<SysPlatform> listAll();
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
}
