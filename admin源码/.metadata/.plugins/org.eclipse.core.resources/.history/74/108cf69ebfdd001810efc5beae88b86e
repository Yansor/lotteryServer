package lottery.domains.content.vo.lottery;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.content.entity.Lottery;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.LotteryOpenCode;

public class LotteryOpenCodeVO
{
    private String lotteryName;
    private LotteryOpenCode bean;
    private String username;
    
    public LotteryOpenCodeVO(final LotteryOpenCode bean, final LotteryDataFactory df) {
        this.bean = bean;
        final Lottery lottery = df.getLottery(bean.getLottery());
        if (lottery != null) {
            this.lotteryName = lottery.getShowName();
        }
        if (bean.getUserId() != null) {
            final UserVO user = df.getUser(bean.getUserId());
            if (user != null) {
                this.username = user.getUsername();
            }
        }
    }
    
    public String getLotteryName() {
        return this.lotteryName;
    }
    
    public void setLotteryName(final String lotteryName) {
        this.lotteryName = lotteryName;
    }
    
    public LotteryOpenCode getBean() {
        return this.bean;
    }
    
    public void setBean(final LotteryOpenCode bean) {
        this.bean = bean;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
}
