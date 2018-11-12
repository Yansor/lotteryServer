package lottery.domains.content.vo.lottery;

import lottery.domains.content.entity.LotteryType;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.Lottery;

public class LotteryVO
{
    private Lottery bean;
    private String lotteryType;
    
    public LotteryVO(final Lottery bean, final LotteryDataFactory df) {
        this.bean = bean;
        final LotteryType lotteryType = df.getLotteryType(bean.getType());
        if (lotteryType != null) {
            this.lotteryType = lotteryType.getName();
        }
    }
    
    public Lottery getBean() {
        return this.bean;
    }
    
    public void setBean(final Lottery bean) {
        this.bean = bean;
    }
    
    public String getLotteryType() {
        return this.lotteryType;
    }
    
    public void setLotteryType(final String lotteryType) {
        this.lotteryType = lotteryType;
    }
}
