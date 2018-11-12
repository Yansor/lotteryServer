package admin.domains.content.biz;

import admin.domains.content.vo.AdminUserActionVO;
import java.util.List;
import javautils.jdbc.PageList;

public interface AdminUserCriticalLogService
{
    PageList search(final Integer p0, final String p1, final String p2, final String p3, final String p4, final String p5, final int p6, final int p7);
    
    PageList search(final String p0, final int p1, final int p2);
    
    List<AdminUserActionVO> findAction();
}
