package lottery.domains.content.biz;

import lottery.domains.content.entity.Game;
import javautils.jdbc.PageList;

public interface GameService
{
    PageList search(final String p0, final String p1, final Integer p2, final Integer p3, final Integer p4, final Integer p5, final Integer p6, final int p7, final int p8);
    
    boolean add(final String p0, final String p1, final Integer p2, final Integer p3, final String p4, final int p5, final int p6, final Integer p7, final Integer p8, final Integer p9, final String p10);
    
    Game getById(final int p0);
    
    Game getByGameName(final String p0);
    
    Game getByGameCode(final String p0);
    
    boolean deleteById(final int p0);
    
    boolean update(final int p0, final String p1, final String p2, final Integer p3, final Integer p4, final String p5, final Integer p6, final Integer p7, final Integer p8, final Integer p9, final Integer p10, final String p11);
    
    boolean updateSequence(final int p0, final int p1);
    
    boolean updateDisplay(final int p0, final int p1);
}
