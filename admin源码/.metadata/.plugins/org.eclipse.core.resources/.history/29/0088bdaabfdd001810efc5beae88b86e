package lottery.domains.content.biz;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import lottery.domains.content.entity.PaymentChannelQrCode;
import java.util.List;

public interface PaymentChannelQrCodeService
{
    List<PaymentChannelQrCode> listAll();
    
    List<PaymentChannelQrCode> listAll(final List<Criterion> p0, final List<Order> p1);
    
    List<PaymentChannelQrCode> getByChannelId(final int p0);
    
    PaymentChannelQrCode getById(final int p0);
    
    boolean add(final PaymentChannelQrCode p0);
    
    boolean update(final PaymentChannelQrCode p0);
    
    boolean delete(final int p0);
}
