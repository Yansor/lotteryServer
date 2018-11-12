package lottery.domains.content.vo.user;

import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.UserGameWaterBill;

public class UserGameWaterBillVO
{
    private String username;
    private String fromUsername;
    private UserGameWaterBill bean;
    
    public UserGameWaterBillVO(final UserGameWaterBill bean, final LotteryDataFactory dataFactory) {
        final UserVO user = dataFactory.getUser(bean.getUserId());
        if (user == null) {
            this.username = "未知[" + bean.getUserId() + "]";
        }
        else {
            this.username = user.getUsername();
        }
        final UserVO fromUser = dataFactory.getUser(bean.getFromUser());
        if (fromUser == null) {
            this.fromUsername = "未知[" + bean.getFromUser() + "]";
        }
        else {
            this.fromUsername = fromUser.getUsername();
        }
        this.bean = bean;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getFromUsername() {
        return this.fromUsername;
    }
    
    public void setFromUsername(final String fromUsername) {
        this.fromUsername = fromUsername;
    }
    
    public UserGameWaterBill getBean() {
        return this.bean;
    }
    
    public void setBean(final UserGameWaterBill bean) {
        this.bean = bean;
    }
}
