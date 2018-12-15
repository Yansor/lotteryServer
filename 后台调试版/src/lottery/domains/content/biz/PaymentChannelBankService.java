package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.vo.payment.PaymentChannelBankVO;

public abstract interface PaymentChannelBankService
{
  public abstract List<PaymentChannelBankVO> list(String paramString);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract PaymentChannelBank getByChannelAndBankId(String paramString, int paramInt);
}
