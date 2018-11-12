package lottery.domains.content.vo.user;

import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.UserGameDividendBill;

public class UserGameDividendBillVO
{
    private String username;
    private UserGameDividendBill bean;
    
    public UserGameDividendBillVO(final UserGameDividendBill bean, final LotteryDataFactory dataFactory) {
        final UserVO user = dataFactory.getUser(bean.getUserId());
        if (user == null) {
            this.username = "未知[" + bean.getUserId() + "]";
        }
        else {
            this.username = user.getUsername();
        }
        this.bean = bean;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public UserGameDividendBill getBean() {
        return this.bean;
    }
    
    public void setBean(final UserGameDividendBill bean) {
        this.bean = bean;
    }
}
