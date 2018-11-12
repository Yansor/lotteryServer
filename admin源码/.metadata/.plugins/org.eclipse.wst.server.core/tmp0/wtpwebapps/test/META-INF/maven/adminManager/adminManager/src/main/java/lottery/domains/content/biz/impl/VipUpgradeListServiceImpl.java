package lottery.domains.content.biz.impl;

import javautils.date.Moment;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.VipUpgradeList;
import lottery.domains.content.vo.vip.VipUpgradeListVO;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.VipUpgradeListDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.VipUpgradeListService;

@Service
public class VipUpgradeListServiceImpl implements VipUpgradeListService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private VipUpgradeListDao vUpgradeListDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String username, final String month, final int start, final int limit) {
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
        if (StringUtil.isNotNull(month)) {
            criterions.add((Criterion)Restrictions.eq("month", (Object)month));
        }
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<VipUpgradeListVO> list = new ArrayList<VipUpgradeListVO>();
            final PageList pList = this.vUpgradeListDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new VipUpgradeListVO((VipUpgradeList)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public boolean add(final int userId, final int beforeLevel, final int afterLevel, final double recharge, final double cost, final String month) {
        final boolean hasRecord = this.vUpgradeListDao.hasRecord(userId, month);
        if (!hasRecord) {
            final String thisTime = new Moment().toSimpleTime();
            final VipUpgradeList entity = new VipUpgradeList(userId, beforeLevel, afterLevel, recharge, cost, month, thisTime);
            return this.vUpgradeListDao.add(entity);
        }
        return false;
    }
}
