package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.PaymentChannelBankDao;
import lottery.domains.content.entity.PaymentChannelBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentChannelBankDaoImpl
  implements PaymentChannelBankDao
{
  private final String tab = PaymentChannelBank.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<PaymentChannelBank> superDao;
  
  public List<PaymentChannelBank> list(String channelCode)
  {
    String hql = "from " + this.tab + " where channelCode = ?0";
    Object[] values = { channelCode };
    return this.superDao.list(hql, values);
  }
  
  public PaymentChannelBank getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (PaymentChannelBank)this.superDao.unique(hql, values);
  }
  
  public boolean update(PaymentChannelBank entity)
  {
    return this.superDao.update(entity);
  }
  
  public PaymentChannelBank getByChannelAndBankId(String channelCode, int bankId)
  {
    String hql = "from " + this.tab + " where channelCode = ?0 and bankId=?1";
    Object[] values = { channelCode, Integer.valueOf(bankId) };
    return (PaymentChannelBank)this.superDao.unique(hql, values);
  }
}
