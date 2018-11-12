package lottery.domains.content.dao.impl;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.PaymentChannelQrCode;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.PaymentChannelQrCodeDao;

@Repository
public class PaymentChannelDaoQrCodeImpl implements PaymentChannelQrCodeDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<PaymentChannelQrCode> superDao;
    
    public PaymentChannelDaoQrCodeImpl() {
        this.tab = PaymentChannelQrCode.class.getSimpleName();
    }
    
    @Override
    public List<PaymentChannelQrCode> listAll() {
        final String hql = "from " + this.tab + " order by sequence";
        return this.superDao.list(hql);
    }
    
    @Override
    public List<PaymentChannelQrCode> listAll(final List<Criterion> criterions, final List<Order> orders) {
        return this.superDao.findByCriteria(PaymentChannelQrCode.class, criterions, orders);
    }
    
    @Override
    public List<PaymentChannelQrCode> getByChannelId(final int channelId) {
        final String hql = "from " + this.tab + " where channelId = ?0";
        final Object[] values = { channelId };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public PaymentChannelQrCode getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (PaymentChannelQrCode)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean add(final PaymentChannelQrCode entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean update(final PaymentChannelQrCode entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean delete(final int id) {
        final String hql = "delete from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return this.superDao.delete(hql, values);
    }
}
