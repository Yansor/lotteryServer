package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.PaymentBankDao;
import lottery.domains.content.entity.PaymentBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentBankDaoImpl
  implements PaymentBankDao
{
  private final String tab = PaymentBank.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<PaymentBank> superDao;
  
  public List<PaymentBank> listAll()
  {
    String hql = "from " + this.tab;
    return this.superDao.list(hql);
  }
  
  public PaymentBank getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (PaymentBank)this.superDao.unique(hql, values);
  }
  
  public boolean update(PaymentBank entity)
  {
    return this.superDao.update(entity);
  }
}
