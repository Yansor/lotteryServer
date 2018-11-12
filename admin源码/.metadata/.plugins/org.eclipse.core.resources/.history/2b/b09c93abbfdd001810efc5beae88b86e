package lottery.domains.content.biz;

import java.util.Map;
import lottery.domains.content.entity.GameBets;
import lottery.domains.content.entity.UserHighPrize;
import javautils.jdbc.PageList;

public interface UserHighPrizeService
{
    PageList search(final Integer p0, final String p1, final Integer p2, final String p3, final String p4, final String p5, final Double p6, final Double p7, final Double p8, final Double p9, final Double p10, final Double p11, final String p12, final String p13, final Integer p14, final String p15, final int p16, final int p17);
    
    UserHighPrize getById(final int p0);
    
    boolean lock(final int p0, final String p1);
    
    boolean unlock(final int p0, final String p1);
    
    boolean confirm(final int p0, final String p1);
    
    void addIfNecessary(final GameBets p0);
    
    int getUnProcessCount();
    
    Map<String, String> getAllHighPrizeNotices();
    
    void delHighPrizeNotice(final String p0);
}
