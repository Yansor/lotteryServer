package lottery.domains.content.vo.payment;

import lottery.domains.content.entity.PaymentBank;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.PaymentChannelBank;

public class PaymentChannelBankVO
{
    private String bankName;
    private PaymentChannelBank bean;
    
    public PaymentChannelBankVO(final PaymentChannelBank bean, final LotteryDataFactory lotteryDataFactory) {
        this.bean = bean;
        final PaymentBank paymentBank = lotteryDataFactory.getPaymentBank(bean.getBankId());
        if (paymentBank != null) {
            this.bankName = paymentBank.getName();
        }
    }
    
    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }
    
    public PaymentChannelBank getBean() {
        return this.bean;
    }
    
    public void setBean(final PaymentChannelBank bean) {
        this.bean = bean;
    }
}
