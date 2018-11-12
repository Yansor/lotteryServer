package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import org.springframework.stereotype.Service;

@Service
public interface UserWhitelistService
{
    PageList search(final String p0, final int p1, final int p2);
    
    boolean add(final String p0, final String p1, final String p2, final Integer p3, final String p4, final String p5, final String p6, final String p7);
    
    boolean delete(final int p0);
}
