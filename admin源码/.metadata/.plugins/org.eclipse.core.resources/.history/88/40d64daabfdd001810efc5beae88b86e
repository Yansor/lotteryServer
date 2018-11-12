package lottery.domains.content.biz.impl;

import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.entity.SysConfig;
import java.util.List;
import org.springframework.context.annotation.Bean;
import javautils.http.EasyHttpClient;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.SysConfigDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.SysConfigService;

@Service
public class SysConfigServiceImpl implements SysConfigService
{
    @Autowired
    private SysConfigDao sysConfigDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Bean
    EasyHttpClient easyHttpClient() {
        final EasyHttpClient httpClient = new EasyHttpClient();
        httpClient.setRepeatTimes(1);
        httpClient.setTimeOut(1000, 1000);
        return httpClient;
    }
    
    @Override
    public List<SysConfig> listAll() {
        return this.sysConfigDao.listAll();
    }
    
    @Override
    public SysConfig get(final String group, final String key) {
        return this.sysConfigDao.get(group, key);
    }
    
    @Override
    public boolean update(final String group, final String key, final String value) {
        final SysConfig entity = this.sysConfigDao.get(group, key);
        if (entity != null) {
            entity.setValue(value);
            final boolean flag = this.sysConfigDao.update(entity);
            if (flag) {
                this.lotteryDataFactory.initSysConfig();
                this.dbServerSyncDao.update(DbServerSyncEnum.SYS_CONFIG);
            }
            return flag;
        }
        return false;
    }
    
    @Override
    public boolean initLotteryPrizeSysConfig(String url) {
        url = String.valueOf(url) + "/lottery/prize/init-sysconfig";
        final String result = this.easyHttpClient().get(url);
        return "success".equals(result);
    }
}
