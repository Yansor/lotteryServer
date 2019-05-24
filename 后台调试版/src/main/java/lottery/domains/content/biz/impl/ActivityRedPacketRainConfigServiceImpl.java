package lottery.domains.content.biz.impl;

import lottery.domains.content.biz.ActivityRedPacketRainConfigService;
import lottery.domains.content.biz.ActivityRedPacketRainTimeService;
import lottery.domains.content.dao.ActivityRedPacketRainConfigDao;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.entity.ActivityRedPacketRainConfig;
import lottery.domains.content.global.DbServerSyncEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityRedPacketRainConfigServiceImpl
  implements ActivityRedPacketRainConfigService
{
  @Autowired
  private ActivityRedPacketRainConfigDao configDao;
  @Autowired
  private ActivityRedPacketRainTimeService timeService;
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  
  public ActivityRedPacketRainConfig getConfig()
  {
    return this.configDao.getConfig();
  }
  
  public boolean updateConfig(int id, String rules, String hours, int durationMinutes)
  {
    boolean updated = this.configDao.updateConfig(id, rules, hours, durationMinutes);
    return updated;
  }
  
  public boolean updateStatus(int id, int status)
  {
    boolean updated = this.configDao.updateStatus(id, status);
    if (updated) {
      this.timeService.initTimes(2);
    }
    this.dbServerSyncDao.update(DbServerSyncEnum.ACTIVITY);
    return updated;
  }
}
