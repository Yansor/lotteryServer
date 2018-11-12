package lottery.domains.content.vo.vip;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.VipIntegralExchange;

public class VipIntegralExchangeVO
{
    private String username;
    private VipIntegralExchange bean;
    
    public VipIntegralExchangeVO(final VipIntegralExchange bean, final LotteryDataFactory lotteryDataFactory) {
        this.bean = bean;
        final UserVO user = lotteryDataFactory.getUser(bean.getUserId());
        if (user != null) {
            this.username = user.getUsername();
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public VipIntegralExchange getBean() {
        return this.bean;
    }
    
    public void setBean(final VipIntegralExchange bean) {
        this.bean = bean;
    }
}
