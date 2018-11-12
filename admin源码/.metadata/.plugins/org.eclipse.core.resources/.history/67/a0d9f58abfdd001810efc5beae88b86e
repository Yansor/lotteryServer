package lottery.domains.content.biz.impl;

import lottery.domains.content.entity.ActivityRebate;
import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.dao.DbServerSyncDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.ActivityRebateDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.ActivityRebateService;

@Service
public class ActivityRebateServiceImpl implements ActivityRebateService
{
    @Autowired
    private ActivityRebateDao aRebateDao;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final ActivityRebate entity = this.aRebateDao.getById(id);
        if (entity != null) {
            entity.setStatus(status);
            final boolean update = this.aRebateDao.update(entity);
            if (update) {
                this.dbServerSyncDao.update(DbServerSyncEnum.ACTIVITY);
            }
            return update;
        }
        return false;
    }
    
    @Override
    public boolean edit(final int id, final String rules, final String startTime, final String endTime) {
        final ActivityRebate entity = this.aRebateDao.getById(id);
        if (entity != null) {
            entity.setRules(rules);
            entity.setStartTime(startTime);
            entity.setEndTime(endTime);
            final boolean update = this.aRebateDao.update(entity);
            if (update) {
                this.dbServerSyncDao.update(DbServerSyncEnum.ACTIVITY);
            }
            return update;
        }
        return false;
    }
    
    @Override
    public ActivityRebate getByType(final int type) {
        return this.aRebateDao.getByType(type);
    }
    
    @Override
    public ActivityRebate getById(final int id) {
        return this.aRebateDao.getById(id);
    }
}
