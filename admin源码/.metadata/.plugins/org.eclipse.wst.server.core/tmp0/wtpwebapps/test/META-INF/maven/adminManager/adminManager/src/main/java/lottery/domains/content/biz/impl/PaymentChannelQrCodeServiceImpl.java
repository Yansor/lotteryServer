package lottery.domains.content.biz.impl;

import javautils.encrypt.PaymentChannelEncrypt;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.Iterator;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import lottery.domains.content.entity.PaymentChannelQrCode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.PaymentChannelQrCodeDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.PaymentChannelQrCodeService;

@Service
public class PaymentChannelQrCodeServiceImpl implements PaymentChannelQrCodeService
{
    @Autowired
    private PaymentChannelQrCodeDao paymentChannelQrCodeDao;
    
    @Override
    public List<PaymentChannelQrCode> listAll() {
        final List<PaymentChannelQrCode> paymentChannelQrCodes = this.paymentChannelQrCodeDao.listAll();
        if (CollectionUtils.isNotEmpty((Collection)paymentChannelQrCodes)) {
            for (final PaymentChannelQrCode paymentChannelQrCode : paymentChannelQrCodes) {
                this.decryptSensitiveProperties(paymentChannelQrCode);
            }
        }
        return paymentChannelQrCodes;
    }
    
    @Override
    public List<PaymentChannelQrCode> listAll(final List<Criterion> criterions, final List<Order> orders) {
        final List<PaymentChannelQrCode> paymentChannelQrCodes = this.paymentChannelQrCodeDao.listAll(criterions, orders);
        if (CollectionUtils.isNotEmpty((Collection)paymentChannelQrCodes)) {
            for (final PaymentChannelQrCode paymentChannelQrCode : paymentChannelQrCodes) {
                this.decryptSensitiveProperties(paymentChannelQrCode);
            }
        }
        return paymentChannelQrCodes;
    }
    
    @Override
    public List<PaymentChannelQrCode> getByChannelId(final int channelId) {
        final List<PaymentChannelQrCode> paymentChannelQrCodes = this.paymentChannelQrCodeDao.getByChannelId(channelId);
        if (CollectionUtils.isNotEmpty((Collection)paymentChannelQrCodes)) {
            for (final PaymentChannelQrCode paymentChannelQrCode : paymentChannelQrCodes) {
                this.decryptSensitiveProperties(paymentChannelQrCode);
            }
        }
        return paymentChannelQrCodes;
    }
    
    @Override
    public PaymentChannelQrCode getById(final int id) {
        final PaymentChannelQrCode paymentChannelQrCode = this.paymentChannelQrCodeDao.getById(id);
        if (paymentChannelQrCode != null) {
            this.decryptSensitiveProperties(paymentChannelQrCode);
        }
        return paymentChannelQrCode;
    }
    
    @Override
    public boolean add(final PaymentChannelQrCode entity) {
        return this.paymentChannelQrCodeDao.add(entity);
    }
    
    @Override
    public boolean update(final PaymentChannelQrCode entity) {
        return this.paymentChannelQrCodeDao.update(entity);
    }
    
    @Override
    public boolean delete(final int id) {
        return this.paymentChannelQrCodeDao.delete(id);
    }
    
    private void decryptSensitiveProperties(final PaymentChannelQrCode qrCode) {
        if (StringUtils.isNotEmpty(qrCode.getQrUrlCode())) {
            final String qrUrlCode = PaymentChannelEncrypt.decrypt(qrCode.getQrUrlCode());
            qrCode.setQrUrlCode(qrUrlCode);
        }
    }
}
