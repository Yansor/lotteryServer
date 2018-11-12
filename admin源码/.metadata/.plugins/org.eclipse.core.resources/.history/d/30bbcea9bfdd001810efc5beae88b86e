package lottery.domains.content.biz.impl;

import java.util.Iterator;
import lottery.domains.content.entity.Lottery;
import java.util.ArrayList;
import lottery.domains.content.vo.bill.UserBetsReportVO;
import lottery.domains.content.vo.bill.HistoryUserLotteryDetailsReportVO;
import lottery.domains.content.vo.bill.UserLotteryDetailsReportVO;
import java.util.List;
import lottery.domains.content.entity.UserLotteryDetailsReport;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserLotteryDetailsReportDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserLotteryDetailsReportService;

@Service
public class UserLotteryDetailsReportServiceImpl implements UserLotteryDetailsReportService
{
    @Autowired
    private UserLotteryDetailsReportDao uLotteryDetailsReportDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public boolean update(final int userId, final int lotteryId, final int ruleId, final int type, final double amount, final String time) {
        final UserLotteryDetailsReport entity = new UserLotteryDetailsReport();
        switch (type) {
            case 6: {
                entity.setSpend(amount);
                break;
            }
            case 7: {
                entity.setPrize(amount);
                break;
            }
            case 8: {
                entity.setSpendReturn(amount);
                break;
            }
            case 9: {
                entity.setProxyReturn(amount);
                break;
            }
            case 10: {
                entity.setCancelOrder(amount);
                break;
            }
            default: {
                return false;
            }
        }
        final UserLotteryDetailsReport bean = this.uLotteryDetailsReportDao.get(userId, lotteryId, ruleId, time);
        if (bean != null) {
            entity.setId(bean.getId());
            return this.uLotteryDetailsReportDao.update(entity);
        }
        entity.setUserId(userId);
        entity.setLotteryId(lotteryId);
        entity.setRuleId(ruleId);
        entity.setTime(time);
        return this.uLotteryDetailsReportDao.add(entity);
    }
    
    @Override
    public List<UserLotteryDetailsReportVO> reportLowersAndSelf(final int userId, final Integer lotteryId, final String sTime, final String eTime) {
        if (lotteryId == null) {
            return this.uLotteryDetailsReportDao.sumLowersAndSelfByLottery(userId, sTime, eTime);
        }
        return this.uLotteryDetailsReportDao.sumLowersAndSelfByRule(userId, lotteryId, sTime, eTime);
    }
    
    @Override
    public List<HistoryUserLotteryDetailsReportVO> historyReportLowersAndSelf(final int userId, final Integer lotteryId, final String sTime, final String eTime) {
        if (lotteryId == null) {
            return this.uLotteryDetailsReportDao.historySumLowersAndSelfByLottery(userId, sTime, eTime);
        }
        return this.uLotteryDetailsReportDao.historySumLowersAndSelfByRule(userId, lotteryId, sTime, eTime);
    }
    
    @Override
    public List<UserLotteryDetailsReportVO> reportSelf(final int userId, final Integer lotteryId, final String sTime, final String eTime) {
        if (lotteryId == null) {
            return this.uLotteryDetailsReportDao.sumSelfByLottery(userId, sTime, eTime);
        }
        return this.uLotteryDetailsReportDao.sumSelfByRule(userId, lotteryId, sTime, eTime);
    }
    
    @Override
    public List<HistoryUserLotteryDetailsReportVO> historyReportSelf(final int userId, final Integer lotteryId, final String sTime, final String eTime) {
        if (lotteryId == null) {
            return this.uLotteryDetailsReportDao.historySumSelfByLottery(userId, sTime, eTime);
        }
        return this.uLotteryDetailsReportDao.historySumSelfByRule(userId, lotteryId, sTime, eTime);
    }
    
    @Override
    public List<UserBetsReportVO> sumUserBets(final Integer type, final Integer lottery, final Integer ruleId, final String sTime, final String eTime) {
        final List<Integer> lids = new ArrayList<Integer>();
        if (lottery != null) {
            lids.add(lottery);
        }
        else if (type != null) {
            final List<Lottery> llist = this.lotteryDataFactory.listLottery(type);
            for (final Lottery tmpBean : llist) {
                lids.add(tmpBean.getId());
            }
        }
        return this.uLotteryDetailsReportDao.sumUserBets(lids, ruleId, sTime, eTime);
    }
}
