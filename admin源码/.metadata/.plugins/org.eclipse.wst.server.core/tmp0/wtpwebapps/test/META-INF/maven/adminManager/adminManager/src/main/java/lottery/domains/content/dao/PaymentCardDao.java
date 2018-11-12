package lottery.domains.content.dao;

import lottery.domains.content.entity.PaymentCard;
import java.util.List;

public interface PaymentCardDao
{
    List<PaymentCard> listAll();
    
    PaymentCard getById(final int p0);
    
    int getOverload();
    
    boolean add(final PaymentCard p0);
    
    boolean update(final PaymentCard p0);
    
    boolean addUsedCredits(final int p0, final double p1);
    
    boolean delete(final int p0);
}
