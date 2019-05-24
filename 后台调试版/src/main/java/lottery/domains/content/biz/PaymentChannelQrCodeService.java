package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.PaymentChannelQrCode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface PaymentChannelQrCodeService
{
  public abstract List<PaymentChannelQrCode> listAll();
  
  public abstract List<PaymentChannelQrCode> listAll(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract List<PaymentChannelQrCode> getByChannelId(int paramInt);
  
  public abstract PaymentChannelQrCode getById(int paramInt);
  
  public abstract boolean add(PaymentChannelQrCode paramPaymentChannelQrCode);
  
  public abstract boolean update(PaymentChannelQrCode paramPaymentChannelQrCode);
  
  public abstract boolean delete(int paramInt);
}
