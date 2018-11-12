package lottery.domains.content.biz;

import lottery.domains.content.entity.SysConfig;
import java.util.List;

public interface SysConfigService
{
    List<SysConfig> listAll();
    
    SysConfig get(final String p0, final String p1);
    
    boolean update(final String p0, final String p1, final String p2);
    
    boolean initLotteryPrizeSysConfig(final String p0);
}
