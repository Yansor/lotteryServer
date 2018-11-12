package lottery.domains.content.vo.vip;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.VipUpgradeList;

public class VipUpgradeListVO
{
    private String username;
    private VipUpgradeList bean;
    
    public VipUpgradeListVO(final VipUpgradeList bean, final LotteryDataFactory lotteryDataFactory) {
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
    
    public VipUpgradeList getBean() {
        return this.bean;
    }
    
    public void setBean(final VipUpgradeList bean) {
        this.bean = bean;
    }
}
