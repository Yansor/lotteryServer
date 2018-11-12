package lottery.domains.content.biz;

import lottery.domains.content.entity.UserBetsHitRanking;
import javautils.jdbc.PageList;

public interface UserBetsHitRankingService
{
    PageList search(final int p0, final int p1);
    
    boolean add(final String p0, final String p1, final int p2, final String p3, final String p4, final String p5, final int p6);
    
    boolean edit(final int p0, final String p1, final String p2, final int p3, final String p4, final String p5, final String p6, final int p7);
    
    boolean delete(final int p0);
    
    UserBetsHitRanking getById(final int p0);
}
