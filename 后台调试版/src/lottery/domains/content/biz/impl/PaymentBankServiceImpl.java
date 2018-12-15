package lottery.domains.content.biz.impl;

import java.util.List;
import lottery.domains.content.biz.PaymentBankService;
import lottery.domains.content.dao.PaymentBankDao;
import lottery.domains.content.entity.PaymentBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentBankServiceImpl
  implements PaymentBankService
{
  @Autowired
  private PaymentBankDao paymentBankDao;
  
  public List<PaymentBank> listAll()
  {
    return this.paymentBankDao.listAll();
  }
  
  public PaymentBank getById(int id)
  {
    return this.paymentBankDao.getById(id);
  }
  
  public boolean update(int id, String name, String url)
  {
    PaymentBank entity = this.paymentBankDao.getById(id);
    if (entity != null)
    {
      entity.setName(name);
      entity.setUrl(url);
      return this.paymentBankDao.update(entity);
    }
    return false;
  }
}
