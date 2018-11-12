package lottery.domains.content.biz;

import lottery.domains.content.entity.LotteryType;
import java.util.List;

public interface LotteryTypeService
{
    List<LotteryType> listAll();
    
    boolean updateStatus(final int p0, final int p1);
}
