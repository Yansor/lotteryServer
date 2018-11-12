package lottery.domains.content.vo.activity;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.ActivityRewardBill;

public class ActivityRewardBillVO
{
    private String toUser;
    private String fromUser;
    private ActivityRewardBill bean;
    
    public ActivityRewardBillVO(final ActivityRewardBill bean, final LotteryDataFactory lotteryDataFactory) {
        this.bean = bean;
        final UserVO toUser = lotteryDataFactory.getUser(bean.getToUser());
        if (toUser != null) {
            this.toUser = toUser.getUsername();
        }
        final UserVO fromUser = lotteryDataFactory.getUser(bean.getFromUser());
        if (toUser != null) {
            this.fromUser = fromUser.getUsername();
        }
    }
    
    public String getToUser() {
        return this.toUser;
    }
    
    public void setToUser(final String toUser) {
        this.toUser = toUser;
    }
    
    public String getFromUser() {
        return this.fromUser;
    }
    
    public void setFromUser(final String fromUser) {
        this.fromUser = fromUser;
    }
    
    public ActivityRewardBill getBean() {
        return this.bean;
    }
    
    public void setBean(final ActivityRewardBill bean) {
        this.bean = bean;
    }
}
