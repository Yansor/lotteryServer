package lottery.domains.content.biz;

import lottery.domains.content.entity.PaymentCard;
import java.util.List;

public interface PaymentCardService
{
    List<PaymentCard> listAll();
    
    PaymentCard getById(final int p0);
    
    boolean add(final int p0, final String p1, final String p2, final String p3, final double p4, final double p5, final double p6, final String p7, final String p8, final double p9, final double p10);
    
    boolean edit(final int p0, final int p1, final String p2, final String p3, final String p4, final double p5, final double p6, final double p7, final String p8, final String p9, final double p10, final double p11);
    
    boolean updateStatus(final int p0, final int p1);
    
    boolean resetCredits(final int p0);
    
    boolean addUsedCredits(final int p0, final double p1);
    
    boolean delete(final int p0);
}
