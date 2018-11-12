package lottery.domains.content.biz;

import lottery.domains.content.entity.UserBalanceSnapshot;
import javautils.jdbc.PageList;

public interface UserBalanceSnapshotService
{
    PageList search(final String p0, final String p1, final int p2, final int p3);
    
    boolean add(final UserBalanceSnapshot p0);
}
