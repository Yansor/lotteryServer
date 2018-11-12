package lottery.domains.content.biz;

import lottery.domains.content.vo.user.UserBetsLimitVO;
import lottery.domains.content.entity.UserBetsLimit;
import javautils.jdbc.PageList;

public interface UserBetsLimitService
{
    boolean addUserBetsLimit(final String p0, final int p1, final double p2, final String p3, final double p4);
    
    PageList search(final String p0, final int p1, final int p2, final boolean p3);
    
    boolean updateUserBetsLimit(final UserBetsLimit p0);
    
    boolean deleteUserBetsLimit(final int p0);
    
    boolean addOrUpdate(final Integer p0, final String p1, final int p2, final double p3, final String p4, final double p5);
    
    UserBetsLimitVO getById(final int p0);
}
