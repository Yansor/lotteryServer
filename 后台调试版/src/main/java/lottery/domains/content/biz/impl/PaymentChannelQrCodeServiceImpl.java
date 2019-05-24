package lottery.domains.content.biz.impl;

import java.util.List;
import javautils.encrypt.PaymentChannelEncrypt;
import lottery.domains.content.biz.PaymentChannelQrCodeService;
import lottery.domains.content.dao.PaymentChannelQrCodeDao;
import lottery.domains.content.entity.PaymentChannelQrCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentChannelQrCodeServiceImpl
  implements PaymentChannelQrCodeService
{
  @Autowired
  private PaymentChannelQrCodeDao paymentChannelQrCodeDao;
  
  public List<PaymentChannelQrCode> listAll()
  {
    List<PaymentChannelQrCode> paymentChannelQrCodes = this.paymentChannelQrCodeDao.listAll();
    if (CollectionUtils.isNotEmpty(paymentChannelQrCodes)) {
      for (PaymentChannelQrCode paymentChannelQrCode : paymentChannelQrCodes) {
        decryptSensitiveProperties(paymentChannelQrCode);
      }
    }
    return paymentChannelQrCodes;
  }
  
  public List<PaymentChannelQrCode> listAll(List<Criterion> criterions, List<Order> orders)
  {
    List<PaymentChannelQrCode> paymentChannelQrCodes = this.paymentChannelQrCodeDao.listAll(criterions, orders);
    if (CollectionUtils.isNotEmpty(paymentChannelQrCodes)) {
      for (PaymentChannelQrCode paymentChannelQrCode : paymentChannelQrCodes) {
        decryptSensitiveProperties(paymentChannelQrCode);
      }
    }
    return paymentChannelQrCodes;
  }
  
  public List<PaymentChannelQrCode> getByChannelId(int channelId)
  {
    List<PaymentChannelQrCode> paymentChannelQrCodes = this.paymentChannelQrCodeDao.getByChannelId(channelId);
    if (CollectionUtils.isNotEmpty(paymentChannelQrCodes)) {
      for (PaymentChannelQrCode paymentChannelQrCode : paymentChannelQrCodes) {
        decryptSensitiveProperties(paymentChannelQrCode);
      }
    }
    return paymentChannelQrCodes;
  }
  
  public PaymentChannelQrCode getById(int id)
  {
    PaymentChannelQrCode paymentChannelQrCode = this.paymentChannelQrCodeDao.getById(id);
    if (paymentChannelQrCode != null) {
      decryptSensitiveProperties(paymentChannelQrCode);
    }
    return paymentChannelQrCode;
  }
  
  public boolean add(PaymentChannelQrCode entity)
  {
    return this.paymentChannelQrCodeDao.add(entity);
  }
  
  public boolean update(PaymentChannelQrCode entity)
  {
    return this.paymentChannelQrCodeDao.update(entity);
  }
  
  public boolean delete(int id)
  {
    return this.paymentChannelQrCodeDao.delete(id);
  }
  
  private void decryptSensitiveProperties(PaymentChannelQrCode qrCode)
  {
    if (StringUtils.isNotEmpty(qrCode.getQrUrlCode()))
    {
      String qrUrlCode = PaymentChannelEncrypt.decrypt(qrCode.getQrUrlCode());
      qrCode.setQrUrlCode(qrUrlCode);
    }
  }
}
