package lottery.domains.content.biz.impl;

import java.util.List;
import lottery.domains.content.biz.SysPlatformService;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.SysPlatformDao;
import lottery.domains.content.entity.SysPlatform;
import lottery.domains.content.global.DbServerSyncEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysPlatformServiceImpl
  implements SysPlatformService
{
  @Autowired
  private SysPlatformDao sysPlatformDao;
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  
  public List<SysPlatform> listAll()
  {
    return this.sysPlatformDao.listAll();
  }
  
  public boolean updateStatus(int id, int status)
  {
    boolean updated = this.sysPlatformDao.updateStatus(id, status);
    if (updated) {
      this.dbServerSyncDao.update(DbServerSyncEnum.SYS_PLATFORM);
    }
    return updated;
  }
}
