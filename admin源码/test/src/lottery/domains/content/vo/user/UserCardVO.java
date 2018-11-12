package lottery.domains.content.vo.user;

import lottery.domains.content.entity.PaymentBank;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.UserCard;

public class UserCardVO
{
    private UserCard bean;
    private String username;
    private String bankName;
    
    public UserCardVO(final UserCard bean, final LotteryDataFactory df) {
        this.bean = bean;
        final UserVO user = df.getUser(bean.getUserId());
        if (user != null) {
            this.username = user.getUsername();
        }
        final PaymentBank bank = df.getPaymentBank(bean.getBankId());
        if (bank != null) {
            this.bankName = bank.getName();
        }
    }
    
    public UserCard getBean() {
        return this.bean;
    }
    
    public void setBean(final UserCard bean) {
        this.bean = bean;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }
}
