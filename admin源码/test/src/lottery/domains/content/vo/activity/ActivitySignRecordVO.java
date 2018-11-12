package lottery.domains.content.vo.activity;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.ActivitySignRecord;

public class ActivitySignRecordVO
{
    private String username;
    public ActivitySignRecord bean;
    
    public ActivitySignRecordVO() {
    }
    
    public ActivitySignRecordVO(final ActivitySignRecord bean, final LotteryDataFactory df) {
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
    
    public ActivitySignRecord getBean() {
        return this.bean;
    }
    
    public void setBean(final ActivitySignRecord bean) {
        this.bean = bean;
    }
}
