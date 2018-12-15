package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.PaymentBank;

public abstract interface PaymentBankService
{
  public abstract List<PaymentBank> listAll();
  
  public abstract PaymentBank getById(int paramInt);
  
  public abstract boolean update(int paramInt, String paramString1, String paramString2);
}
