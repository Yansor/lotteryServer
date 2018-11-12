package lottery.domains.content.vo.user;

import lottery.domains.content.entity.SysPlatform;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.GameBets;

public class GameBetsVO
{
    private String username;
    private String platform;
    private GameBets bean;
    
    public GameBetsVO(final GameBets bean, final LotteryDataFactory lotteryDataFactory) {
        this.bean = bean;
        final UserVO tmpUser = lotteryDataFactory.getUser(bean.getUserId());
        if (tmpUser != null) {
            this.username = tmpUser.getUsername();
        }
        else {
            this.username = "未知[" + bean.getUserId() + "]";
        }
        final SysPlatform platform = lotteryDataFactory.getSysPlatform(bean.getPlatformId());
        if (platform != null) {
            this.platform = platform.getName();
        }
        else {
            this.platform = "未知";
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getPlatform() {
        return this.platform;
    }
    
    public void setPlatform(final String platform) {
        this.platform = platform;
    }
    
    public GameBets getBean() {
        return this.bean;
    }
    
    public void setBean(final GameBets bean) {
        this.bean = bean;
    }
}
