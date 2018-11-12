package lottery.domains.content.vo.user;

import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.UserWithdraw;

public class UserWithdrawVO
{
    private UserWithdraw bean;
    private String username;
    
    public UserWithdrawVO(final UserWithdraw bean, final LotteryDataFactory df) {
        this.bean = bean;
        final UserVO user = df.getUser(bean.getUserId());
        if (user != null) {
            this.username = user.getUsername();
        }
    }
    
    public UserWithdraw getBean() {
        return this.bean;
    }
    
    public void setBean(final UserWithdraw bean) {
        this.bean = bean;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
}
