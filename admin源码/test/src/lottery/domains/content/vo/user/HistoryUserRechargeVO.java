package lottery.domains.content.vo.user;

import lottery.domains.content.vo.payment.PaymentChannelVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.vo.payment.PaymentCardVO;
import lottery.domains.content.entity.HistoryUserRecharge;

public class HistoryUserRechargeVO
{
    private HistoryUserRecharge bean;
    private String username;
    private String name;
    private PaymentCardVO receiveCard;
    
    public HistoryUserRechargeVO(final HistoryUserRecharge bean, final LotteryDataFactory df) {
        this.bean = bean;
        final UserVO user = df.getUser(bean.getUserId());
        if (user != null) {
            this.username = user.getUsername();
        }
        if (bean.getCardId() != null) {
            final PaymentCardVO paymentCard = df.getPaymentCard(bean.getCardId());
            this.receiveCard = paymentCard;
        }
        final PaymentChannelVO channel = df.getPaymentChannelVO(bean.getChannelId());
        if (channel != null) {
            this.name = channel.getName();
        }
    }
    
    public HistoryUserRecharge getBean() {
        return this.bean;
    }
    
    public void setBean(final HistoryUserRecharge bean) {
        this.bean = bean;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public PaymentCardVO getReceiveCard() {
        return this.receiveCard;
    }
    
    public void setReceiveCard(final PaymentCardVO receiveCard) {
        this.receiveCard = receiveCard;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
}
