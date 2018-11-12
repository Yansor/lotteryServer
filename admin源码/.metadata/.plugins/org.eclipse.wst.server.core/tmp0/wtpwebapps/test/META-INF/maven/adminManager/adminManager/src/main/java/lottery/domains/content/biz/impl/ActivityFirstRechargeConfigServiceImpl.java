package lottery.domains.content.biz.impl;

import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.entity.ActivityFirstRechargeConfig;
import lottery.domains.content.dao.DbServerSyncDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.ActivityFirstRechargeConfigDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.ActivityFirstRechargeConfigService;

@Service
public class ActivityFirstRechargeConfigServiceImpl implements ActivityFirstRechargeConfigService
{
    @Autowired
    private ActivityFirstRechargeConfigDao configDao;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Override
    public ActivityFirstRechargeConfig getConfig() {
        return this.configDao.getConfig();
    }
    
    @Override
    public boolean updateConfig(final int id, final String rules) {
        final boolean updated = this.configDao.updateConfig(id, rules);
        this.dbServerSyncDao.update(DbServerSyncEnum.ACTIVITY);
        return updated;
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final boolean updated = this.configDao.updateStatus(id, status);
        this.dbServerSyncDao.update(DbServerSyncEnum.ACTIVITY);
        return updated;
    }
}
