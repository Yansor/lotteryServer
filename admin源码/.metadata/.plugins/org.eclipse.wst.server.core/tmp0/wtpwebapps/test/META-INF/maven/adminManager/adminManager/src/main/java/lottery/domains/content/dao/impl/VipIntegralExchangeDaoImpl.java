package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.VipIntegralExchange;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.VipIntegralExchangeDao;

@Repository
public class VipIntegralExchangeDaoImpl implements VipIntegralExchangeDao
{
    @Autowired
    private HibernateSuperDao<VipIntegralExchange> superDao;
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(VipIntegralExchange.class, criterions, orders, start, limit);
    }
}
