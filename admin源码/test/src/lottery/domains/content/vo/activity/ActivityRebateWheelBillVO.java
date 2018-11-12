package lottery.domains.content.vo.activity;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.ActivityRebateWheelBill;

public class ActivityRebateWheelBillVO
{
    private String username;
    private ActivityRebateWheelBill bean;
    
    public ActivityRebateWheelBillVO(final ActivityRebateWheelBill bean, final LotteryDataFactory lotteryDataFactory) {
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
    
    public ActivityRebateWheelBill getBean() {
        return this.bean;
    }
    
    public void setBean(final ActivityRebateWheelBill bean) {
        this.bean = bean;
    }
}
