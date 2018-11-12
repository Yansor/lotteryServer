package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public interface SysNoticeService
{
    PageList search(final Integer p0, final int p1, final int p2);
    
    boolean add(final String p0, final String p1, final String p2, final int p3, final int p4, final String p5);
    
    boolean edit(final int p0, final String p1, final String p2, final String p3, final int p4, final int p5, final String p6);
    
    boolean updateStatus(final int p0, final int p1);
    
    boolean updateSort(final int p0, final int p1);
    
    boolean delete(final int p0);
}
