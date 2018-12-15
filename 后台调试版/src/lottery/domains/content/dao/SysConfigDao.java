package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.SysConfig;

public abstract interface SysConfigDao
{
  public abstract List<SysConfig> listAll();
  
  public abstract SysConfig get(String paramString1, String paramString2);
  
  public abstract boolean update(SysConfig paramSysConfig);
  
  public abstract boolean save(SysConfig paramSysConfig);
}
