package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import lottery.domains.content.biz.PaymentChannelBankService;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.PaymentChannelBankDao;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.vo.payment.PaymentChannelBankVO;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentChannelBankServiceImpl
  implements PaymentChannelBankService
{
  @Autowired
  private PaymentChannelBankDao paymentChannelBankDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  
  public List<PaymentChannelBankVO> list(String type)
  {
    List<PaymentChannelBank> blist = this.paymentChannelBankDao.list(type);
    List<PaymentChannelBankVO> list = new ArrayList();
    for (PaymentChannelBank tmpBean : blist) {
      list.add(new PaymentChannelBankVO(tmpBean, this.lotteryDataFactory));
    }
    return list;
  }
  
  public boolean updateStatus(int id, int status)
  {
    PaymentChannelBank entity = this.paymentChannelBankDao.getById(id);
    if (entity != null)
    {
      entity.setStatus(status);
      boolean result = this.paymentChannelBankDao.update(entity);
      if (result) {
        this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CHANNEL_BANK);
      }
      return result;
    }
    return false;
  }
  
  public PaymentChannelBank getByChannelAndBankId(String channelCode, int bankId)
  {
    return this.paymentChannelBankDao.getByChannelAndBankId(channelCode, bankId);
  }
}
