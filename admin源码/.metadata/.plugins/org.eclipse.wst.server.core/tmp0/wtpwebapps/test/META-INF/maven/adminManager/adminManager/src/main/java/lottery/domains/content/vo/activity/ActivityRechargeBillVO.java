package lottery.domains.content.vo.activity;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.ActivityRechargeBill;

public class ActivityRechargeBillVO
{
    private String username;
    private ActivityRechargeBill bean;
    
    public ActivityRechargeBillVO(final ActivityRechargeBill bean, final LotteryDataFactory lotteryDataFactory) {
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
    
    public ActivityRechargeBill getBean() {
        return this.bean;
    }
    
    public void setBean(final ActivityRechargeBill bean) {
        this.bean = bean;
    }
}
