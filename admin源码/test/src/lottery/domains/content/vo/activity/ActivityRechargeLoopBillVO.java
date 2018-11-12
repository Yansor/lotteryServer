package lottery.domains.content.vo.activity;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.ActivityRechargeLoopBill;

public class ActivityRechargeLoopBillVO
{
    private String username;
    private ActivityRechargeLoopBill bean;
    
    public ActivityRechargeLoopBillVO(final ActivityRechargeLoopBill bean, final LotteryDataFactory lotteryDataFactory) {
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
    
    public ActivityRechargeLoopBill getBean() {
        return this.bean;
    }
    
    public void setBean(final ActivityRechargeLoopBill bean) {
        this.bean = bean;
    }
}
