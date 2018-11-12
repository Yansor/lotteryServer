package lottery.domains.content.vo.user;

import lottery.domains.content.entity.Lottery;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.UserBetsLimit;

public class UserBetsLimitVO
{
    private String userName;
    private String lotteryName;
    private UserBetsLimit bean;
    
    public UserBetsLimitVO(final UserBetsLimit bean, final LotteryDataFactory lotteryDataFactory) {
        this.bean = bean;
        final UserVO user = lotteryDataFactory.getUser(bean.getUserId());
        if (user != null) {
            this.userName = user.getUsername();
        }
        final Lottery lottery = lotteryDataFactory.getLottery(bean.getLotteryId());
        if (lottery != null) {
            this.lotteryName = lottery.getShowName();
        }
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(final String userName) {
        this.userName = userName;
    }
    
    public String getLotteryName() {
        return this.lotteryName;
    }
    
    public void setLotteryName(final String lotteryName) {
        this.lotteryName = lotteryName;
    }
    
    public UserBetsLimit getBean() {
        return this.bean;
    }
    
    public void setBean(final UserBetsLimit bean) {
        this.bean = bean;
    }
}
