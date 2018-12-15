package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.PaymentBank;

public abstract interface PaymentBankDao
{
  public abstract List<PaymentBank> listAll();
  
  public abstract PaymentBank getById(int paramInt);
  
  public abstract boolean update(PaymentBank paramPaymentBank);
}
