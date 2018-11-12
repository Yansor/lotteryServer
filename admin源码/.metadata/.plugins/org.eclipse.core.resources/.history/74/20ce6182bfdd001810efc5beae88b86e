package lottery.domains.content.vo.user;

import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.UserSecurity;

public class UserSecurityVO
{
    private UserSecurity bean;
    private String username;
    
    public UserSecurityVO(final UserSecurity bean, final LotteryDataFactory df) {
        (this.bean = bean).setValue("***");
        final UserVO user = df.getUser(bean.getUserId());
        if (user != null) {
            this.username = user.getUsername();
        }
    }
    
    public UserSecurity getBean() {
        return this.bean;
    }
    
    public void setBean(final UserSecurity bean) {
        this.bean = bean;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
}
