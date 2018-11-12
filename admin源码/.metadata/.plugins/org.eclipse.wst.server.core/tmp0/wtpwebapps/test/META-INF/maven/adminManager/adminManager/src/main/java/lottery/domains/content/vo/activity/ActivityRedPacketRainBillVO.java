package lottery.domains.content.vo.activity;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.ActivityRedPacketRainBill;

public class ActivityRedPacketRainBillVO
{
    private String username;
    private ActivityRedPacketRainBill bean;
    
    public ActivityRedPacketRainBillVO(final ActivityRedPacketRainBill bean, final LotteryDataFactory lotteryDataFactory) {
        this.bean = bean;
        final UserVO tmpUser = lotteryDataFactory.getUser(bean.getUserId());
        if (tmpUser != null) {
            this.username = tmpUser.getUsername();
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public ActivityRedPacketRainBill getBean() {
        return this.bean;
    }
    
    public void setBean(final ActivityRedPacketRainBill bean) {
        this.bean = bean;
    }
}
