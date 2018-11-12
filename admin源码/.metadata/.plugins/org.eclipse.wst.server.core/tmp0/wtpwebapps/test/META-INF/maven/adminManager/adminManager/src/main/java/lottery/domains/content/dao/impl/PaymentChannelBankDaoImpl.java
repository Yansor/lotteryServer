package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.PaymentChannelBank;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.PaymentChannelBankDao;

@Repository
public class PaymentChannelBankDaoImpl implements PaymentChannelBankDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<PaymentChannelBank> superDao;
    
    public PaymentChannelBankDaoImpl() {
        this.tab = PaymentChannelBank.class.getSimpleName();
    }
    
    @Override
    public List<PaymentChannelBank> list(final String channelCode) {
        final String hql = "from " + this.tab + " where channelCode = ?0";
        final Object[] values = { channelCode };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public PaymentChannelBank getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (PaymentChannelBank)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean update(final PaymentChannelBank entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public PaymentChannelBank getByChannelAndBankId(final String channelCode, final int bankId) {
        final String hql = "from " + this.tab + " where channelCode = ?0 and bankId=?1";
        final Object[] values = { channelCode, bankId };
        return (PaymentChannelBank)this.superDao.unique(hql, values);
    }
}
