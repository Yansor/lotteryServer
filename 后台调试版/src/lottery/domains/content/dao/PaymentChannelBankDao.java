package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.PaymentChannelBank;

public abstract interface PaymentChannelBankDao
{
  public abstract List<PaymentChannelBank> list(String paramString);
  
  public abstract PaymentChannelBank getById(int paramInt);
  
  public abstract boolean update(PaymentChannelBank paramPaymentChannelBank);
  
  public abstract PaymentChannelBank getByChannelAndBankId(String paramString, int paramInt);
}
