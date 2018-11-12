package lottery.domains.content.vo.vip;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.VipUpgradeGifts;

public class VipUpgradeGiftsVO
{
    private String username;
    private VipUpgradeGifts bean;
    
    public VipUpgradeGiftsVO(final VipUpgradeGifts bean, final LotteryDataFactory lotteryDataFactory) {
        this.bean = bean;
        final UserVO user = lotteryDataFactory.getUser(bean.getUserId());
        if (user != null) {
            this.username = user.getUsername();
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public VipUpgradeGifts getBean() {
        return this.bean;
    }
    
    public void setBean(final VipUpgradeGifts bean) {
        this.bean = bean;
    }
}
