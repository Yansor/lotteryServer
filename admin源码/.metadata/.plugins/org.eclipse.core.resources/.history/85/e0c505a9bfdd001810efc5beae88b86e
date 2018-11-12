package lottery.domains.content.biz.impl;

import lottery.domains.content.vo.config.VipConfig;
import javautils.date.Moment;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.VipFreeChips;
import lottery.domains.content.vo.vip.VipFreeChipsVO;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.VipFreeChipsDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.VipFreeChipsService;

@Service
public class VipFreeChipsServiceImpl implements VipFreeChipsService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private VipFreeChipsDao vFreeChipsDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String username, final Integer level, final String date, final Integer status, final int start, final int limit) {
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
        if (level != null) {
            criterions.add((Criterion)Restrictions.eq("level", (Object)level));
        }
        if (StringUtil.isNotNull(date)) {
            criterions.add((Criterion)Restrictions.like("time", date, MatchMode.ANYWHERE));
        }
        if (status != null) {
            criterions.add((Criterion)Restrictions.eq("status", (Object)status));
        }
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<VipFreeChipsVO> list = new ArrayList<VipFreeChipsVO>();
            final PageList pList = this.vFreeChipsDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new VipFreeChipsVO((VipFreeChips)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public boolean calculate() {
        final List<User> ulist = this.uDao.listAll();
        final String sTime = new Moment().day(10).toSimpleDate();
        final String eTime = new Moment().day(10).add(1, "months").toSimpleDate();
        final String thisTime = new Moment().toSimpleTime();
        final VipConfig vipConfig = this.lotteryDataFactory.getVipConfig();
        final double[] freeChips = vipConfig.getFreeChips();
        for (final User tmpBean : ulist) {
            try {
                final int vipLevel = tmpBean.getVipLevel();
                final double freeMoney = freeChips[vipLevel];
                if (freeMoney <= 0.0) {
                    continue;
                }
                final boolean hasRecord = this.vFreeChipsDao.hasRecord(tmpBean.getId(), sTime, eTime);
                if (hasRecord) {
                    continue;
                }
                final int status = 0;
                final int isReceived = 0;
                final VipFreeChips entity = new VipFreeChips(tmpBean.getId(), tmpBean.getVipLevel(), freeMoney, sTime, eTime, thisTime, status, isReceived);
                this.vFreeChipsDao.add(entity);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    
    @Override
    public boolean agreeAll() {
        final List<VipFreeChips> list = this.vFreeChipsDao.getUntreated();
        for (final VipFreeChips tmpBean : list) {
            if (tmpBean.getStatus() == 0) {
                tmpBean.setStatus(1);
                this.vFreeChipsDao.update(tmpBean);
            }
        }
        return true;
    }
}
