package lottery.domains.content.vo.lottery;

import lottery.domains.content.entity.Lottery;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.LotteryOpenTime;

public class LotteryOpenTimeVO
{
    private LotteryOpenTime bean;
    private String lottery;
    
    public LotteryOpenTimeVO(final LotteryOpenTime bean, final LotteryDataFactory df) {
        this.bean = bean;
        final Lottery lottery = df.getLottery(bean.getLottery());
        if (lottery != null) {
            this.lottery = lottery.getShowName();
        }
    }
    
    public LotteryOpenTime getBean() {
        return this.bean;
    }
    
    public void setBean(final LotteryOpenTime bean) {
        this.bean = bean;
    }
    
    public String getLottery() {
        return this.lottery;
    }
    
    public void setLottery(final String lottery) {
        this.lottery = lottery;
    }
}
