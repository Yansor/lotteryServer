package lottery.domains.content.biz.impl;

import lottery.domains.content.entity.PaymentBank;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.PaymentBankDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.PaymentBankService;

@Service
public class PaymentBankServiceImpl implements PaymentBankService
{
    @Autowired
    private PaymentBankDao paymentBankDao;
    
    @Override
    public List<PaymentBank> listAll() {
        return this.paymentBankDao.listAll();
    }
    
    @Override
    public PaymentBank getById(final int id) {
        return this.paymentBankDao.getById(id);
    }
    
    @Override
    public boolean update(final int id, final String name, final String url) {
        final PaymentBank entity = this.paymentBankDao.getById(id);
        if (entity != null) {
            entity.setName(name);
            entity.setUrl(url);
            return this.paymentBankDao.update(entity);
        }
        return false;
    }
}
