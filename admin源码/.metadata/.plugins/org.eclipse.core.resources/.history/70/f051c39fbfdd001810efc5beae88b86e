package lottery.domains.content.vo.activity;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.ActivitySalaryBill;

public class ActivitySalaryBillVO
{
    private String username;
    private ActivitySalaryBill bean;
    
    public ActivitySalaryBillVO(final ActivitySalaryBill bean, final LotteryDataFactory lotteryDataFactory) {
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
    
    public ActivitySalaryBill getBean() {
        return this.bean;
    }
    
    public void setBean(final ActivitySalaryBill bean) {
        this.bean = bean;
    }
}
