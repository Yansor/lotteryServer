package lottery.domains.content.vo.payment;

import lottery.domains.content.entity.PaymentBank;
import org.apache.commons.lang.StringUtils;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.PaymentCard;

public class PaymentCardVO
{
    private String bankName;
    private PaymentCard bean;
    
    public PaymentCardVO(final PaymentCard bean, final LotteryDataFactory df) {
        this.bean = bean;
        final PaymentBank bank = df.getPaymentBank(bean.getBankId());
        if (bank != null) {
            this.bankName = bank.getName();
        }
        if (StringUtils.isNotEmpty(bean.getCardId())) {
            final int length = bean.getCardId().length();
            int preAndSub;
            if (length <= 4) {
                preAndSub = 1;
            }
            else if (length <= 12) {
                preAndSub = 2;
            }
            else {
                preAndSub = 4;
            }
            final String start = bean.getCardId().substring(0, preAndSub);
            final String middle = " **** ";
            final String end = bean.getCardId().substring(length - preAndSub);
            bean.setCardId(String.valueOf(start) + middle + end);
        }
        if (StringUtils.isNotEmpty(bean.getCardName())) {
            bean.setCardName("*" + bean.getCardName().substring(bean.getCardName().length() - 1));
        }
        if (StringUtils.isNotEmpty(bean.getBranchName())) {
            final int length = bean.getBranchName().length();
            int preAndSub;
            if (length <= 5) {
                preAndSub = 1;
            }
            else {
                preAndSub = 2;
            }
            final String start = bean.getBranchName().substring(0, preAndSub);
            final String middle = "***";
            final String end = bean.getBranchName().substring(length - preAndSub);
            bean.setBranchName(String.valueOf(start) + middle + end);
        }
    }
    
    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }
    
    public PaymentCard getBean() {
        return this.bean;
    }
    
    public void setBean(final PaymentCard bean) {
        this.bean = bean;
    }
}
