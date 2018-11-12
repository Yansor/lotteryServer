package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserWithdrawLog;

public interface UserWithdrawLogService
{
    boolean add(final UserWithdrawLog p0);
    
    PageList search(final String p0, final String p1, final int p2, final int p3);
}
