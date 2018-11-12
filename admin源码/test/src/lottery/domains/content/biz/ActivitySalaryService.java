package lottery.domains.content.biz;

import javautils.jdbc.PageList;

public interface ActivitySalaryService
{
    PageList search(final String p0, final String p1, final Integer p2, final int p3, final int p4);
}
