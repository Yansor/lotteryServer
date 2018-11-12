package lottery.domains.content.vo.vip;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.VipBirthdayGifts;

public class VipBirthdayGiftsVO
{
    private String username;
    private VipBirthdayGifts bean;
    
    public VipBirthdayGiftsVO(final VipBirthdayGifts bean, final LotteryDataFactory lotteryDataFactory) {
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
    
    public VipBirthdayGifts getBean() {
        return this.bean;
    }
    
    public void setBean(final VipBirthdayGifts bean) {
        this.bean = bean;
    }
}
