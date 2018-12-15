package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.PaymentCard;

public abstract interface PaymentCardDao
{
  public abstract List<PaymentCard> listAll();
  
  public abstract PaymentCard getById(int paramInt);
  
  public abstract int getOverload();
  
  public abstract boolean add(PaymentCard paramPaymentCard);
  
  public abstract boolean update(PaymentCard paramPaymentCard);
  
  public abstract boolean addUsedCredits(int paramInt, double paramDouble);
  
  public abstract boolean delete(int paramInt);
}
