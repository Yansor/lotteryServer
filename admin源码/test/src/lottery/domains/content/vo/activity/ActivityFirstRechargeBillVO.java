package lottery.domains.content.vo.activity;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.ActivityFirstRechargeBill;

public class ActivityFirstRechargeBillVO
{
    private String username;
    private ActivityFirstRechargeBill bean;
    
    public ActivityFirstRechargeBillVO(final ActivityFirstRechargeBill bean, final LotteryDataFactory lotteryDataFactory) {
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
    
    public ActivityFirstRechargeBill getBean() {
        return this.bean;
    }
    
    public void setBean(final ActivityFirstRechargeBill bean) {
        this.bean = bean;
    }
}
