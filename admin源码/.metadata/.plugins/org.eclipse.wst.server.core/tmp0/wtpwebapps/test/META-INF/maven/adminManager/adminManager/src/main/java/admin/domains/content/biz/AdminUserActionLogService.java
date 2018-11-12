package admin.domains.content.biz;

import javautils.jdbc.PageList;

public interface AdminUserActionLogService
{
    PageList search(final String p0, final String p1, final String p2, final String p3, final String p4, final int p5, final int p6);
}
