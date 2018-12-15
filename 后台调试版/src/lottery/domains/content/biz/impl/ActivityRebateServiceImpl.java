package lottery.domains.content.biz.impl;

import lottery.domains.content.biz.ActivityRebateService;
import lottery.domains.content.dao.ActivityRebateDao;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.entity.ActivityRebate;
import lottery.domains.content.global.DbServerSyncEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityRebateServiceImpl
  implements ActivityRebateService
{
  @Autowired
  private ActivityRebateDao aRebateDao;
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  
  public boolean updateStatus(int id, int status)
  {
    ActivityRebate entity = this.aRebateDao.getById(id);
    if (entity != null)
    {
      entity.setStatus(status);
      boolean update = this.aRebateDao.update(entity);
      if (update) {
        this.dbServerSyncDao.update(DbServerSyncEnum.ACTIVITY);
      }
      return update;
    }
    return false;
  }
  
  public boolean edit(int id, String rules, String startTime, String endTime)
  {
    ActivityRebate entity = this.aRebateDao.getById(id);
    if (entity != null)
    {
      entity.setRules(rules);
      entity.setStartTime(startTime);
      entity.setEndTime(endTime);
      boolean update = this.aRebateDao.update(entity);
      if (update) {
        this.dbServerSyncDao.update(DbServerSyncEnum.ACTIVITY);
      }
      return update;
    }
    return false;
  }
  
  public ActivityRebate getByType(int type)
  {
    return this.aRebateDao.getByType(type);
  }
  
  public ActivityRebate getById(int id)
  {
    return this.aRebateDao.getById(id);
  }
}
