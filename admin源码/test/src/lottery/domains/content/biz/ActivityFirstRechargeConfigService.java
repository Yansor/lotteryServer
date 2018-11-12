package lottery.domains.content.biz;

import lottery.domains.content.entity.ActivityFirstRechargeConfig;

public interface ActivityFirstRechargeConfigService
{
    ActivityFirstRechargeConfig getConfig();
    
    boolean updateConfig(final int p0, final String p1);
    
    boolean updateStatus(final int p0, final int p1);
}
