package lottery.domains.content.vo.user;

import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.UserActionLog;

public class UserActionLogVO
{
    private String username;
    private UserActionLog bean;
    
    public UserActionLogVO(final UserActionLog bean, final LotteryDataFactory df) {
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
    
    public UserActionLog getBean() {
        return this.bean;
    }
    
    public void setBean(final UserActionLog bean) {
        this.bean = bean;
    }
}
