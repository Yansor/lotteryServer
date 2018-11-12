package lottery.domains.content.vo.lottery;

import lottery.domains.content.entity.LotteryCrawlerStatus;
import lottery.domains.utils.lottery.open.OpenTime;

public class LotteryCrawlerStatusVO
{
    private OpenTime openTime;
    private LotteryCrawlerStatus bean;
    
    public OpenTime getOpenTime() {
        return this.openTime;
    }
    
    public void setOpenTime(final OpenTime openTime) {
        this.openTime = openTime;
    }
    
    public LotteryCrawlerStatus getBean() {
        return this.bean;
    }
    
    public void setBean(final LotteryCrawlerStatus bean) {
        this.bean = bean;
    }
}
