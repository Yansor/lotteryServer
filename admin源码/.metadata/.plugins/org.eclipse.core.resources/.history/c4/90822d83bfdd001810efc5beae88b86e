package lottery.domains.content.vo.activity;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.ActivitySignBill;

public class ActivitySignBillVO
{
    private String username;
    public ActivitySignBill bean;
    
    public ActivitySignBillVO() {
    }
    
    public ActivitySignBillVO(final ActivitySignBill bean, final LotteryDataFactory df) {
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
    
    public ActivitySignBill getBean() {
        return this.bean;
    }
    
    public void setBean(final ActivitySignBill bean) {
        this.bean = bean;
    }
}
