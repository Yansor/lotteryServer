package lottery.domains.content.biz.impl;

import lottery.domains.content.global.DbServerSyncEnum;
import java.util.Iterator;
import lottery.domains.content.entity.Lottery;
import java.util.ArrayList;
import javautils.StringUtil;
import lottery.domains.content.vo.lottery.LotteryVO;
import java.util.List;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.LotteryDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.LotteryService;

@Service
public class LotteryServiceImpl implements LotteryService
{
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    @Autowired
    private LotteryDao lotteryDao;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Override
    public List<LotteryVO> list(final String type) {
        List<Lottery> lotteryList;
        if (StringUtil.isNotNull(type) && StringUtil.isInteger(type)) {
            lotteryList = this.lotteryDataFactory.listLottery(Integer.parseInt(type));
        }
        else {
            lotteryList = this.lotteryDataFactory.listLottery();
        }
        final List<LotteryVO> list = new ArrayList<LotteryVO>();
        for (final Lottery tmpBean : lotteryList) {
            list.add(new LotteryVO(tmpBean, this.lotteryDataFactory));
        }
        return list;
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final boolean result = this.lotteryDao.updateStatus(id, status);
        if (result) {
            this.lotteryDataFactory.initLottery();
            this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY);
        }
        return result;
    }
    
    @Override
    public boolean updateTimes(final int id, final int times) {
        final boolean result = this.lotteryDao.updateTimes(id, times);
        if (result) {
            this.lotteryDataFactory.initLottery();
            this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY);
        }
        return result;
    }
    
    @Override
    public Lottery getByShortName(final String shortName) {
        return this.lotteryDataFactory.getLottery(shortName);
    }
}
