package lottery.domains.content.biz;

import lottery.domains.content.entity.LotteryOpenTime;
import javautils.jdbc.PageList;

public interface LotteryOpenTimeService
{
    PageList search(final String p0, final String p1, final int p2, final int p3);
    
    boolean modify(final int p0, final String p1, final String p2);
    
    boolean modifyRefExpect(final String p0, final int p1);
    
    boolean modify(final String p0, final int p1);
    
    LotteryOpenTime getByLottery(final String p0);
    
    boolean update(final LotteryOpenTime p0);
}
