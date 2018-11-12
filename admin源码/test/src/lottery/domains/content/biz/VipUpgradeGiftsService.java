package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public interface VipUpgradeGiftsService
{
    PageList search(final String p0, final String p1, final Integer p2, final int p3, final int p4);
    
    boolean doIssuingGift(final int p0, final int p1, final int p2);
}
