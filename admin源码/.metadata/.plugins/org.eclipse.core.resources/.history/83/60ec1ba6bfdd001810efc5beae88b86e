package lottery.domains.content.dao.impl;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.PaymentChannel;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.PaymentChannelDao;

@Repository
public class PaymentChannelDaoImpl implements PaymentChannelDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<PaymentChannel> superDao;
    
    public PaymentChannelDaoImpl() {
        this.tab = PaymentChannel.class.getSimpleName();
    }
    
    @Override
    public List<PaymentChannel> listAll() {
        final String hql = "from " + this.tab + " order by sequence";
        return this.superDao.list(hql);
    }
    
    @Override
    public List<PaymentChannel> listAll(final List<Criterion> criterions, final List<Order> orders) {
        return this.superDao.findByCriteria(PaymentChannel.class, criterions, orders);
    }
    
    @Override
    public PaymentChannel getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (PaymentChannel)this.superDao.unique(hql, values);
    }
    
    @Override
    public int getOverload() {
        final String hql = "select count(id) from " + this.tab + " where usedCredits >= totalCredits";
        final Object result = this.superDao.unique(hql);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public boolean add(final PaymentChannel entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean update(final PaymentChannel entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean delete(final int id) {
        final String hql = "delete from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return this.superDao.delete(hql, values);
    }
    
    @Override
    public boolean modSequence(final int id, final int sequence) {
        final String hql = "update " + this.tab + " set sequence = sequence + ?1 where id = ?0";
        final Object[] values = { id, sequence };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateSequence(final int id, final int sort) {
        final String hql = "update " + this.tab + " set  sequence= ?1 where id = ?0";
        final Object[] values = { id, sort };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean batchModSequence(final int sequence) {
        final String hql = "update " + this.tab + " set sequence = sequence - 1 where sequence > ?0";
        final Object[] values = { sequence };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public PaymentChannel getBySequence(final int sequence) {
        final String hql = "from " + this.tab + " where sequence = ?0";
        final Object[] values = { sequence };
        return (PaymentChannel)this.superDao.unique(hql, values);
    }
    
    @Override
    public int getTotal() {
        final String hql = "select count(id) from " + this.tab;
        final Object result = this.superDao.unique(hql);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public List<PaymentChannel> getBySequenceUp(final int sequence) {
        final String hql = "from " + this.tab + " where sequence < ?0 order by sequence desc";
        final Object[] values = { sequence };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public List<PaymentChannel> getBySequenceDown(final int sequence) {
        final String hql = "from " + this.tab + " where sequence > ?0 order by sequence asc";
        final Object[] values = { sequence };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public int getMaxSequence() {
        final String hql = "select max(sequence) from " + this.tab;
        final Object result = this.superDao.unique(hql);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public boolean addUsedCredits(final int id, final double credit) {
        final String hql = "update " + this.tab + " set usedCredits = usedCredits + ?0 where id = ?1";
        final Object[] values = { credit, id };
        return this.superDao.update(hql, values);
    }
}
