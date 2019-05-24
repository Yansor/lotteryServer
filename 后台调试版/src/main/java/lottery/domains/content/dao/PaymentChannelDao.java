package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.PaymentChannel;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface PaymentChannelDao
{
  public abstract List<PaymentChannel> listAll();
  
  public abstract List<PaymentChannel> listAll(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract PaymentChannel getById(int paramInt);
  
  public abstract int getOverload();
  
  public abstract boolean add(PaymentChannel paramPaymentChannel);
  
  public abstract boolean update(PaymentChannel paramPaymentChannel);
  
  public abstract boolean delete(int paramInt);
  
  public abstract boolean modSequence(int paramInt1, int paramInt2);
  
  public abstract boolean batchModSequence(int paramInt);
  
  public abstract boolean updateSequence(int paramInt1, int paramInt2);
  
  public abstract PaymentChannel getBySequence(int paramInt);
  
  public abstract List<PaymentChannel> getBySequenceUp(int paramInt);
  
  public abstract List<PaymentChannel> getBySequenceDown(int paramInt);
  
  public abstract int getMaxSequence();
  
  public abstract int getTotal();
  
  public abstract boolean addUsedCredits(int paramInt, double paramDouble);
}
