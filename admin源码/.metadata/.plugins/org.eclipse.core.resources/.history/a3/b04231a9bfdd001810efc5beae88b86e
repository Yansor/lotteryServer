package lottery.domains.content.biz.impl;

import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.entity.ActivityRedPacketRainConfig;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.biz.ActivityRedPacketRainTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.ActivityRedPacketRainConfigDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.ActivityRedPacketRainConfigService;

@Service
public class ActivityRedPacketRainConfigServiceImpl implements ActivityRedPacketRainConfigService
{
    @Autowired
    private ActivityRedPacketRainConfigDao configDao;
    @Autowired
    private ActivityRedPacketRainTimeService timeService;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Override
    public ActivityRedPacketRainConfig getConfig() {
        return this.configDao.getConfig();
    }
    
    @Override
    public boolean updateConfig(final int id, final String rules, final String hours, final int durationMinutes) {
        final boolean updated = this.configDao.updateConfig(id, rules, hours, durationMinutes);
        return updated;
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final boolean updated = this.configDao.updateStatus(id, status);
        if (updated) {
            this.timeService.initTimes(2);
        }
        this.dbServerSyncDao.update(DbServerSyncEnum.ACTIVITY);
        return updated;
    }
}
