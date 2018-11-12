package lottery.domains.content.vo.user;

import lottery.domains.content.entity.SysPlatform;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.UserHighPrize;

public class UserHighPrizeTimesVO
{
    private String username;
    private String platform;
    private UserHighPrize bean;
    
    public UserHighPrizeTimesVO(final UserHighPrize bean, final LotteryDataFactory lotteryDataFactory) {
        this.bean = bean;
        final UserVO tmpUser = lotteryDataFactory.getUser(bean.getUserId());
        if (tmpUser != null) {
            this.username = tmpUser.getUsername();
        }
        final SysPlatform platform = lotteryDataFactory.getSysPlatform(bean.getPlatform());
        if (platform != null) {
            this.platform = platform.getName();
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public UserHighPrize getBean() {
        return this.bean;
    }
    
    public void setBean(final UserHighPrize bean) {
        this.bean = bean;
    }
    
    public String getPlatform() {
        return this.platform;
    }
    
    public void setPlatform(final String platform) {
        this.platform = platform;
    }
}
