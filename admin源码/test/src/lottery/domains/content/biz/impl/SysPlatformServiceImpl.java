package lottery.domains.content.biz.impl;

import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.entity.SysPlatform;
import java.util.List;
import lottery.domains.content.dao.DbServerSyncDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.SysPlatformDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.SysPlatformService;

@Service
public class SysPlatformServiceImpl implements SysPlatformService
{
    @Autowired
    private SysPlatformDao sysPlatformDao;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Override
    public List<SysPlatform> listAll() {
        return this.sysPlatformDao.listAll();
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final boolean updated = this.sysPlatformDao.updateStatus(id, status);
        if (updated) {
            this.dbServerSyncDao.update(DbServerSyncEnum.SYS_PLATFORM);
        }
        return updated;
    }
}
