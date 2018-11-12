package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.PaymentBank;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.PaymentBankDao;

@Repository
public class PaymentBankDaoImpl implements PaymentBankDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<PaymentBank> superDao;
    
    public PaymentBankDaoImpl() {
        this.tab = PaymentBank.class.getSimpleName();
    }
    
    @Override
    public List<PaymentBank> listAll() {
        final String hql = "from " + this.tab;
        return this.superDao.list(hql);
    }
    
    @Override
    public PaymentBank getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (PaymentBank)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean update(final PaymentBank entity) {
        return this.superDao.update(entity);
    }
}
