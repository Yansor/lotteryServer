package lottery.domains.content.dao.impl;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserBaccaratReport;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserBaccaratReportDao;

@Repository
public class UserBaccaratReportDaoImpl implements UserBaccaratReportDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserBaccaratReport> superDao;
    
    public UserBaccaratReportDaoImpl() {
        this.tab = UserBaccaratReport.class.getSimpleName();
    }
    
    @Override
    public boolean add(final UserBaccaratReport entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public UserBaccaratReport get(final int userId, final String time) {
        final String hql = "from " + this.tab + " where userId = ?0 and time = ?1";
        final Object[] values = { userId, time };
        return (UserBaccaratReport)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean update(final UserBaccaratReport entity) {
        final String hql = "update " + this.tab + " set transIn = transIn + ?1, transOut = transOut + ?2, spend = spend + ?3, prize = prize + ?4, waterReturn = waterReturn + ?5, proxyReturn = proxyReturn + ?6, cancelOrder = cancelOrder + ?7, activity = activity + ?8, billingOrder = billingOrder + ?9 where id = ?0";
        final Object[] values = { entity.getId(), entity.getTransIn(), entity.getTransOut(), entity.getSpend(), entity.getPrize(), entity.getWaterReturn(), entity.getProxyReturn(), entity.getCancelOrder(), entity.getActivity(), entity.getBillingOrder() };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public List<UserBaccaratReport> find(final List<Criterion> criterions, final List<Order> orders) {
        return this.superDao.findByCriteria(UserBaccaratReport.class, criterions, orders);
    }
}
