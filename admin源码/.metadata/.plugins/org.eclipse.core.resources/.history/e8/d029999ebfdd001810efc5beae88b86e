package lottery.domains.content.vo.user;

import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.HistoryUserWithdraw;

public class HistoryUserWithdrawVO
{
    private HistoryUserWithdraw bean;
    private String username;
    
    public HistoryUserWithdrawVO(final HistoryUserWithdraw bean, final LotteryDataFactory df) {
        this.bean = bean;
        final UserVO user = df.getUser(bean.getUserId());
        if (user != null) {
            this.username = user.getUsername();
        }
    }
    
    public HistoryUserWithdraw getBean() {
        return this.bean;
    }
    
    public void setBean(final HistoryUserWithdraw bean) {
        this.bean = bean;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
}
