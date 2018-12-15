package lottery.domains.content.biz.impl;

import java.util.List;
import javautils.http.EasyHttpClient;
import lottery.domains.content.biz.SysConfigService;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.SysConfigDao;
import lottery.domains.content.entity.SysConfig;
import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl
  implements SysConfigService
{
  @Autowired
  private SysConfigDao sysConfigDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  
  @Bean
  EasyHttpClient easyHttpClient()
  {
    EasyHttpClient httpClient = new EasyHttpClient();
    httpClient.setRepeatTimes(1);
    httpClient.setTimeOut(1000, 1000);
    return httpClient;
  }
  
  public List<SysConfig> listAll()
  {
    return this.sysConfigDao.listAll();
  }
  
  public SysConfig get(String group, String key)
  {
    return this.sysConfigDao.get(group, key);
  }
  
  public boolean update(String group, String key, String value)
  {
    SysConfig entity = this.sysConfigDao.get(group, key);
    if (entity != null)
    {
      entity.setValue(value);
      boolean flag = this.sysConfigDao.update(entity);
      if (flag)
      {
        this.lotteryDataFactory.initSysConfig();
        this.dbServerSyncDao.update(DbServerSyncEnum.SYS_CONFIG);
      }
      return flag;
    }
    return false;
  }
  
  public boolean initLotteryPrizeSysConfig(String url)
  {
    url = url + "/lottery/prize/init-sysconfig";
    String result = easyHttpClient().get(url);
    if ("success".equals(result)) {
      return true;
    }
    return false;
  }
}
