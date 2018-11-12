package lottery.domains.content.dao.impl;

import java.util.Map;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.ActivityPacketInfo;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.ActivityPacketInfoDao;

@Repository
public class ActivityPacketInfoDaoImpl implements ActivityPacketInfoDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<ActivityPacketInfo> superDao;
    
    public ActivityPacketInfoDaoImpl() {
        this.tab = ActivityPacketInfo.class.getSimpleName();
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(ActivityPacketInfo.class, criterions, orders, start, limit);
    }
    
    @Override
    public boolean save(final ActivityPacketInfo entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public List<Map<Integer, Double>> statTotal() {
        final String hql = "select type , sum(amount) from " + this.tab + " group by type";
        final Object[] values = new Object[0];
        return (List<Map<Integer, Double>>)this.superDao.listObject(hql, values);
    }
}
