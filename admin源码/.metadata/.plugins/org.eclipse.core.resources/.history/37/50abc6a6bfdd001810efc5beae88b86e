package lottery.domains.content.dao;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import lottery.domains.content.entity.PaymentChannel;
import java.util.List;

public interface PaymentChannelDao
{
    List<PaymentChannel> listAll();
    
    List<PaymentChannel> listAll(final List<Criterion> p0, final List<Order> p1);
    
    PaymentChannel getById(final int p0);
    
    int getOverload();
    
    boolean add(final PaymentChannel p0);
    
    boolean update(final PaymentChannel p0);
    
    boolean delete(final int p0);
    
    boolean modSequence(final int p0, final int p1);
    
    boolean batchModSequence(final int p0);
    
    boolean updateSequence(final int p0, final int p1);
    
    PaymentChannel getBySequence(final int p0);
    
    List<PaymentChannel> getBySequenceUp(final int p0);
    
    List<PaymentChannel> getBySequenceDown(final int p0);
    
    int getMaxSequence();
    
    int getTotal();
    
    boolean addUsedCredits(final int p0, final double p1);
}
