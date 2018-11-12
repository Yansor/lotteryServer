package lottery.domains.content.biz.impl;

import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.entity.LotteryType;
import java.util.List;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.LotteryTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.LotteryTypeService;

@Service
public class LotteryTypeServiceImpl implements LotteryTypeService
{
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    @Autowired
    private LotteryTypeDao lotteryTypeDao;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Override
    public List<LotteryType> listAll() {
        return this.lotteryDataFactory.listLotteryType();
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final boolean result = this.lotteryTypeDao.updateStatus(id, status);
        if (result) {
            this.lotteryDataFactory.initLotteryType();
            this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY_TYPE);
        }
        return result;
    }
}
