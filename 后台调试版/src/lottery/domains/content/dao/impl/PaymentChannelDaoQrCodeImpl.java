package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.PaymentChannelQrCodeDao;
import lottery.domains.content.entity.PaymentChannelQrCode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentChannelDaoQrCodeImpl
  implements PaymentChannelQrCodeDao
{
  private final String tab = PaymentChannelQrCode.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<PaymentChannelQrCode> superDao;
  
  public List<PaymentChannelQrCode> listAll()
  {
    String hql = "from " + this.tab + " order by sequence";
    return this.superDao.list(hql);
  }
  
  public List<PaymentChannelQrCode> listAll(List<Criterion> criterions, List<Order> orders)
  {
    return this.superDao.findByCriteria(PaymentChannelQrCode.class, criterions, orders);
  }
  
  public List<PaymentChannelQrCode> getByChannelId(int channelId)
  {
    String hql = "from " + this.tab + " where channelId = ?0";
    Object[] values = { Integer.valueOf(channelId) };
    return this.superDao.list(hql, values);
  }
  
  public PaymentChannelQrCode getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (PaymentChannelQrCode)this.superDao.unique(hql, values);
  }
  
  public boolean add(PaymentChannelQrCode entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean update(PaymentChannelQrCode entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean delete(int id)
  {
    String hql = "delete from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return this.superDao.delete(hql, values);
  }
}
