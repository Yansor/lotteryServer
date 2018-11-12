package lottery.domains.content.vo.activity;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.ActivityCostBill;

public class ActivityCostBillVO
{
    private String username;
    public ActivityCostBill bean;
    
    public ActivityCostBillVO() {
    }
    
    public ActivityCostBillVO(final ActivityCostBill bean, final LotteryDataFactory df) {
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
    
    public ActivityCostBill getBean() {
        return this.bean;
    }
    
    public void setBean(final ActivityCostBill bean) {
        this.bean = bean;
    }
}
