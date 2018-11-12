package lottery.domains.content.biz;

import lottery.domains.content.vo.user.UserMessageVO;
import javautils.jdbc.PageList;

public interface UserMessageService
{
    PageList search(final String p0, final String p1, final String p2, final String p3, final int p4, final int p5, final int p6);
    
    UserMessageVO getById(final int p0);
    
    boolean delete(final int p0);
    
    boolean save(final int p0, final String p1);
}
