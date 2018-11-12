package lottery.domains.content.biz;

import lottery.domains.content.entity.PaymentBank;
import java.util.List;

public interface PaymentBankService
{
    List<PaymentBank> listAll();
    
    PaymentBank getById(final int p0);
    
    boolean update(final int p0, final String p1, final String p2);
}
