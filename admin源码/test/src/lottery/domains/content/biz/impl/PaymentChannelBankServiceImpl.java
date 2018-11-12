package lottery.domains.content.biz.impl;

import lottery.domains.content.global.DbServerSyncEnum;
import java.util.Iterator;
import lottery.domains.content.entity.PaymentChannelBank;
import java.util.ArrayList;
import lottery.domains.content.vo.payment.PaymentChannelBankVO;
import java.util.List;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.PaymentChannelBankDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.PaymentChannelBankService;

@Service
public class PaymentChannelBankServiceImpl implements PaymentChannelBankService
{
    @Autowired
    private PaymentChannelBankDao paymentChannelBankDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Override
    public List<PaymentChannelBankVO> list(final String type) {
        final List<PaymentChannelBank> blist = this.paymentChannelBankDao.list(type);
        final List<PaymentChannelBankVO> list = new ArrayList<PaymentChannelBankVO>();
        for (final PaymentChannelBank tmpBean : blist) {
            list.add(new PaymentChannelBankVO(tmpBean, this.lotteryDataFactory));
        }
        return list;
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final PaymentChannelBank entity = this.paymentChannelBankDao.getById(id);
        if (entity != null) {
            entity.setStatus(status);
            final boolean result = this.paymentChannelBankDao.update(entity);
            if (result) {
                this.dbServerSyncDao.update(DbServerSyncEnum.PAYMENT_CHANNEL_BANK);
            }
            return result;
        }
        return false;
    }
    
    @Override
    public PaymentChannelBank getByChannelAndBankId(final String channelCode, final int bankId) {
        return this.paymentChannelBankDao.getByChannelAndBankId(channelCode, bankId);
    }
}
