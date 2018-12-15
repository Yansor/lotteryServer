package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.PaymentCardDao;
import lottery.domains.content.entity.PaymentCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentCardDaoImpl
  implements PaymentCardDao
{
  private final String tab = PaymentCard.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<PaymentCard> superDao;
  
  public List<PaymentCard> listAll()
  {
    String hql = "from " + this.tab + " order by bankId";
    return this.superDao.list(hql);
  }
  
  public PaymentCard getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (PaymentCard)this.superDao.unique(hql, values);
  }
  
  public int getOverload()
  {
    String hql = "select count(id) from " + this.tab + " where usedCredits >= totalCredits";
    Object result = this.superDao.unique(hql);
    return result != null ? ((Number)result).intValue() : 0;
  }
  
  public boolean add(PaymentCard entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean update(PaymentCard entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean delete(int id)
  {
    String hql = "delete from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return this.superDao.delete(hql, values);
  }
  
  public boolean addUsedCredits(int cardId, double usedCredits)
  {
    String hql = "update " + this.tab + " set usedCredits = usedCredits + ?0 where id = ?1";
    Object[] values = { Double.valueOf(usedCredits), Integer.valueOf(cardId) };
    return this.superDao.update(hql, values);
  }
}
