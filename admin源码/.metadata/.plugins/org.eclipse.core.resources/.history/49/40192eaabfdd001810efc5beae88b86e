package lottery.domains.content.biz.impl;

import javautils.date.Moment;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.global.DbServerSyncEnum;
import java.util.Iterator;
import java.util.List;
import lottery.domains.content.entity.LotteryOpenTime;
import lottery.domains.content.vo.lottery.LotteryOpenTimeVO;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.LotteryOpenTimeDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.LotteryOpenTimeService;

@Service
public class LotteryOpenTimeServiceImpl implements LotteryOpenTimeService
{
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    @Autowired
    private LotteryOpenTimeDao lotteryOpenTimeDao;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Override
    public PageList search(final String lottery, final String expect, int start, int limit) {
        if (start < 0) {
            start = 0;
        }
        if (limit < 0) {
            limit = 10;
        }
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        if (StringUtil.isNotNull(lottery)) {
            criterions.add((Criterion)Restrictions.eq("lottery", (Object)lottery));
        }
        if (StringUtil.isNotNull(expect)) {
            criterions.add((Criterion)Restrictions.eq("expect", (Object)expect));
        }
        orders.add(Order.asc("lottery"));
        orders.add(Order.asc("expect"));
        final PageList pList = this.lotteryOpenTimeDao.find(criterions, orders, start, limit);
        final List<LotteryOpenTimeVO> list = new ArrayList<LotteryOpenTimeVO>();
        for (final Object tmpBean : pList.getList()) {
            list.add(new LotteryOpenTimeVO((LotteryOpenTime)tmpBean, this.lotteryDataFactory));
        }
        pList.setList(list);
        return pList;
    }
    
    @Override
    public boolean modify(final int id, final String startTime, final String stopTime) {
        final LotteryOpenTime entity = this.lotteryOpenTimeDao.getById(id);
        if (entity != null) {
            entity.setStartTime(startTime);
            entity.setStopTime(stopTime);
            final boolean flag = this.lotteryOpenTimeDao.update(entity);
            if (flag) {
                this.lotteryDataFactory.initLotteryOpenTime();
                this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY_OPEN_TIME);
            }
            return flag;
        }
        return false;
    }
    
    @Override
    public boolean modifyRefExpect(final String lottery, final int count) {
        final LotteryOpenTime entity = this.lotteryOpenTimeDao.getByLottery(String.valueOf(lottery) + "_ref");
        if (entity != null) {
            int expect = Integer.valueOf(entity.getExpect());
            expect += count;
            entity.setExpect(new StringBuilder(String.valueOf(expect)).toString());
            final boolean flag = this.lotteryOpenTimeDao.update(entity);
            if (flag) {
                this.lotteryDataFactory.initLotteryOpenTime();
                this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY_OPEN_TIME);
            }
            return flag;
        }
        return false;
    }
    
    @Override
    public boolean modify(final String lottery, final int seconds) {
        boolean allowModify = false;
        final Lottery thisLottery = this.lotteryDataFactory.getLottery(lottery);
        if (thisLottery != null) {
            switch (thisLottery.getType()) {
                case 1:
                case 2:
                case 3:
                case 5:
                case 6: {
                    allowModify = true;
                    break;
                }
            }
        }
        if (allowModify) {
            final List<LotteryOpenTime> list = this.lotteryOpenTimeDao.list(lottery);
            if (list.size() > 0) {
                for (final LotteryOpenTime entity : list) {
                    entity.setStartTime(add(entity.getStartTime(), seconds));
                    entity.setStopTime(add(entity.getStopTime(), seconds));
                    this.lotteryOpenTimeDao.update(entity);
                }
                this.lotteryDataFactory.initLotteryOpenTime();
                this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY_OPEN_TIME);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public LotteryOpenTime getByLottery(final String lottery) {
        return this.lotteryOpenTimeDao.getByLottery(lottery);
    }
    
    @Override
    public boolean update(final LotteryOpenTime entity) {
        final boolean updated = this.lotteryOpenTimeDao.update(entity);
        if (updated) {
            this.lotteryDataFactory.initLotteryOpenTime();
            this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY_OPEN_TIME);
        }
        return updated;
    }
    
    public static String add(final String time, final int seconds) {
        final String date = new Moment().toSimpleDate();
        final Moment moment = new Moment().fromTime(String.valueOf(date) + " " + time);
        moment.add(seconds, "seconds");
        return moment.format("HH:mm:ss");
    }
}
