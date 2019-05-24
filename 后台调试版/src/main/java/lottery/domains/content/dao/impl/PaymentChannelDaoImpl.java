package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.PaymentChannelDao;
import lottery.domains.content.entity.PaymentChannel;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentChannelDaoImpl
  implements PaymentChannelDao
{
  private final String tab = PaymentChannel.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<PaymentChannel> superDao;
  
  public List<PaymentChannel> listAll()
  {
    String hql = "from " + this.tab + " order by sequence";
    return this.superDao.list(hql);
  }
  
  public List<PaymentChannel> listAll(List<Criterion> criterions, List<Order> orders)
  {
    return this.superDao.findByCriteria(PaymentChannel.class, criterions, orders);
  }
  
  public PaymentChannel getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (PaymentChannel)this.superDao.unique(hql, values);
  }
  
  public int getOverload()
  {
    String hql = "select count(id) from " + this.tab + " where usedCredits >= totalCredits";
    Object result = this.superDao.unique(hql);
    return result != null ? ((Number)result).intValue() : 0;
  }
  
  public boolean add(PaymentChannel entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean update(PaymentChannel entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean delete(int id)
  {
    String hql = "delete from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return this.superDao.delete(hql, values);
  }
  
  public boolean modSequence(int id, int sequence)
  {
    String hql = "update " + this.tab + " set sequence = sequence + ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(sequence) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateSequence(int id, int sort)
  {
    String hql = "update " + this.tab + " set  sequence= ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(sort) };
    return this.superDao.update(hql, values);
  }
  
  public boolean batchModSequence(int sequence)
  {
    String hql = "update " + this.tab + " set sequence = sequence - 1 where sequence > ?0";
    Object[] values = { Integer.valueOf(sequence) };
    return this.superDao.update(hql, values);
  }
  
  public PaymentChannel getBySequence(int sequence)
  {
    String hql = "from " + this.tab + " where sequence = ?0";
    Object[] values = { Integer.valueOf(sequence) };
    return (PaymentChannel)this.superDao.unique(hql, values);
  }
  
  public int getTotal()
  {
    String hql = "select count(id) from " + this.tab;
    Object result = this.superDao.unique(hql);
    return result != null ? ((Number)result).intValue() : 0;
  }
  
  public List<PaymentChannel> getBySequenceUp(int sequence)
  {
    String hql = "from " + this.tab + " where sequence < ?0 order by sequence desc";
    Object[] values = { Integer.valueOf(sequence) };
    return this.superDao.list(hql, values);
  }
  
  public List<PaymentChannel> getBySequenceDown(int sequence)
  {
    String hql = "from " + this.tab + " where sequence > ?0 order by sequence asc";
    Object[] values = { Integer.valueOf(sequence) };
    return this.superDao.list(hql, values);
  }
  
  public int getMaxSequence()
  {
    String hql = "select max(sequence) from " + this.tab;
    Object result = this.superDao.unique(hql);
    return result != null ? ((Number)result).intValue() : 0;
  }
  
  public boolean addUsedCredits(int id, double credit)
  {
    String hql = "update " + this.tab + " set usedCredits = usedCredits + ?0 where id = ?1";
    Object[] values = { Double.valueOf(credit), Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
}
