package lottery.domains.content.biz.impl;

import lottery.domains.content.biz.ActivityFirstRechargeConfigService;
import lottery.domains.content.dao.ActivityFirstRechargeConfigDao;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.entity.ActivityFirstRechargeConfig;
import lottery.domains.content.global.DbServerSyncEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityFirstRechargeConfigServiceImpl
  implements ActivityFirstRechargeConfigService
{
  @Autowired
  private ActivityFirstRechargeConfigDao configDao;
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  
  public ActivityFirstRechargeConfig getConfig()
  {
    return this.configDao.getConfig();
  }
  
  public boolean updateConfig(int id, String rules)
  {
    boolean updated = this.configDao.updateConfig(id, rules);
    this.dbServerSyncDao.update(DbServerSyncEnum.ACTIVITY);
    return updated;
  }
  
  public boolean updateStatus(int id, int status)
  {
    boolean updated = this.configDao.updateStatus(id, status);
    this.dbServerSyncDao.update(DbServerSyncEnum.ACTIVITY);
    return updated;
  }
}
