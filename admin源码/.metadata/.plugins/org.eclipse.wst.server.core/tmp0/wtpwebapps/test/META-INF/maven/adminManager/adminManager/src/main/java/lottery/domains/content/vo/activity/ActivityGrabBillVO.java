package lottery.domains.content.vo.activity;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.ActivityGrabBill;

public class ActivityGrabBillVO
{
    private String username;
    public ActivityGrabBill bean;
    
    public ActivityGrabBillVO() {
    }
    
    public ActivityGrabBillVO(final ActivityGrabBill bean, final LotteryDataFactory df) {
        this.bean = bean;
        final UserVO user = df.getUser(bean.getUserId());
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
    
    public ActivityGrabBill getBean() {
        return this.bean;
    }
    
    public void setBean(final ActivityGrabBill bean) {
        this.bean = bean;
    }
}
