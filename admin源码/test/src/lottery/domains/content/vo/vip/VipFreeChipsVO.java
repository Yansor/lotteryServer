package lottery.domains.content.vo.vip;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.VipFreeChips;

public class VipFreeChipsVO
{
    private String username;
    private VipFreeChips bean;
    
    public VipFreeChipsVO(final VipFreeChips bean, final LotteryDataFactory lotteryDataFactory) {
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
    
    public VipFreeChips getBean() {
        return this.bean;
    }
    
    public void setBean(final VipFreeChips bean) {
        this.bean = bean;
    }
}
