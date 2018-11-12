package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.PaymentCard;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.PaymentCardDao;

@Repository
public class PaymentCardDaoImpl implements PaymentCardDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<PaymentCard> superDao;
    
    public PaymentCardDaoImpl() {
        this.tab = PaymentCard.class.getSimpleName();
    }
    
    @Override
    public List<PaymentCard> listAll() {
        final String hql = "from " + this.tab + " order by bankId";
        return this.superDao.list(hql);
    }
    
    @Override
    public PaymentCard getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (PaymentCard)this.superDao.unique(hql, values);
    }
    
    @Override
    public int getOverload() {
        final String hql = "select count(id) from " + this.tab + " where usedCredits >= totalCredits";
        final Object result = this.superDao.unique(hql);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public boolean add(final PaymentCard entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean update(final PaymentCard entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean delete(final int id) {
        final String hql = "delete from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return this.superDao.delete(hql, values);
    }
    
    @Override
    public boolean addUsedCredits(final int cardId, final double usedCredits) {
        final String hql = "update " + this.tab + " set usedCredits = usedCredits + ?0 where id = ?1";
        final Object[] values = { usedCredits, cardId };
        return this.superDao.update(hql, values);
    }
}
