package lottery.domains.content.biz.impl;

import lottery.domains.content.vo.config.VipConfig;
import javautils.date.Moment;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.VipUpgradeGifts;
import lottery.domains.content.vo.vip.VipUpgradeGiftsVO;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.VipUpgradeGiftsDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.VipUpgradeGiftsService;

@Service
public class VipUpgradeGiftsServiceImpl implements VipUpgradeGiftsService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private VipUpgradeGiftsDao vUpgradeGiftsDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String username, final String date, final Integer status, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        boolean isSearch = true;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)user.getId()));
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(date)) {
            criterions.add((Criterion)Restrictions.like("time", date, MatchMode.ANYWHERE));
        }
        if (status != null) {
            criterions.add((Criterion)Restrictions.eq("status", (Object)status));
        }
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<VipUpgradeGiftsVO> list = new ArrayList<VipUpgradeGiftsVO>();
            final PageList pList = this.vUpgradeGiftsDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new VipUpgradeGiftsVO((VipUpgradeGifts)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public boolean doIssuingGift(final int userId, final int beforeLevel, final int afterLevel) {
        final VipConfig vipConfig = this.lotteryDataFactory.getVipConfig();
        final double[] upgradeGifts = vipConfig.getUpgradeGifts();
        final String thisTime = new Moment().toSimpleTime();
        for (int i = beforeLevel; i < afterLevel; ++i) {
            try {
                final double upgradeMoney = upgradeGifts[i + 1];
                if (upgradeMoney > 0.0) {
                    final boolean hasRecord = this.vUpgradeGiftsDao.hasRecord(userId, beforeLevel, afterLevel);
                    if (!hasRecord) {
                        final int status = 1;
                        final int isReceived = 0;
                        final VipUpgradeGifts entity = new VipUpgradeGifts(userId, i, i + 1, upgradeMoney, thisTime, status, isReceived);
                        this.vUpgradeGiftsDao.add(entity);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
