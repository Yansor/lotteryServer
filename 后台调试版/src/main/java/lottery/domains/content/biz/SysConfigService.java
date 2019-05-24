package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.SysConfig;

public abstract interface SysConfigService
{
  public abstract List<SysConfig> listAll();
  
  public abstract SysConfig get(String paramString1, String paramString2);
  
  public abstract boolean update(String paramString1, String paramString2, String paramString3);
  
  public abstract boolean initLotteryPrizeSysConfig(String paramString);
}
