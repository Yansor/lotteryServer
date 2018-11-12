package lottery.domains.content.vo.bill;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.content.entity.SysPlatform;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.HistoryUserBill;

public class HistoryUserBillVO
{
    private String username;
    private String account;
    private HistoryUserBill bean;
    
    public HistoryUserBillVO(final HistoryUserBill bean, final LotteryDataFactory lotteryDataFactory) {
        this.bean = bean;
        final SysPlatform platform = lotteryDataFactory.getSysPlatform(bean.getAccount());
        if (platform != null) {
            this.account = platform.getName();
        }
        final UserVO uBean = lotteryDataFactory.getUser(bean.getUserId());
        if (uBean != null) {
            this.username = uBean.getUsername();
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getAccount() {
        return this.account;
    }
    
    public void setAccount(final String account) {
        this.account = account;
    }
    
    public HistoryUserBill getBean() {
        return this.bean;
    }
    
    public void setBean(final HistoryUserBill bean) {
        this.bean = bean;
    }
}
