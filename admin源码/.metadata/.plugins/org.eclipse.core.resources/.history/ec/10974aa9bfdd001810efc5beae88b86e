package lottery.domains.content.biz.impl;

import lottery.domains.content.vo.config.VipConfig;
import lottery.domains.content.entity.UserInfo;
import javautils.date.Moment;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.VipBirthdayGifts;
import lottery.domains.content.vo.vip.VipBirthdayGiftsVO;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.VipBirthdayGiftsDao;
import lottery.domains.content.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.VipBirthdayGiftsService;

@Service
public class VipBirthdayGiftsServiceImpl implements VipBirthdayGiftsService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserInfoDao uInfoDao;
    @Autowired
    private VipBirthdayGiftsDao vBirthdayGiftsDao;
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
            final List<VipBirthdayGiftsVO> list = new ArrayList<VipBirthdayGiftsVO>();
            final PageList pList = this.vBirthdayGiftsDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new VipBirthdayGiftsVO((VipBirthdayGifts)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public boolean calculate(final String birthday) {
        final String date = new Moment().fromDate(birthday).format("MM-dd");
        final List<UserInfo> list = this.uInfoDao.listByBirthday(date);
        final VipConfig vipConfig = this.lotteryDataFactory.getVipConfig();
        final double[] birthdayGifts = vipConfig.getBirthdayGifts();
        final String thisTime = new Moment().toSimpleTime();
        final int year = new Moment().fromDate(birthday).year();
        for (final UserInfo tmpBean : list) {
            try {
                final User uBean = this.uDao.getById(tmpBean.getUserId());
                if (uBean == null) {
                    continue;
                }
                final double birthdayMoney = birthdayGifts[uBean.getVipLevel()];
                if (birthdayMoney <= 0.0) {
                    continue;
                }
                final boolean hasRecord = this.vBirthdayGiftsDao.hasRecord(uBean.getId(), year);
                if (!hasRecord) {
                    final int status = 1;
                    final int isReceived = 0;
                    final VipBirthdayGifts entity = new VipBirthdayGifts(uBean.getId(), uBean.getVipLevel(), birthdayMoney, birthday, thisTime, status, isReceived);
                    return this.vBirthdayGiftsDao.add(entity);
                }
                continue;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
