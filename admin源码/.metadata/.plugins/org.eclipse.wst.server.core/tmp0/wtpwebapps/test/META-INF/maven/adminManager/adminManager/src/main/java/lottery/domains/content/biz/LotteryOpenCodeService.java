package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.LotteryOpenCode;
import admin.web.WebJSONObject;
import lottery.domains.content.vo.lottery.LotteryOpenCodeVO;
import javautils.jdbc.PageList;

public interface LotteryOpenCodeService
{
    PageList search(final String p0, final String p1, final int p2, final int p3);
    
    LotteryOpenCodeVO get(final String p0, final String p1);
    
    boolean add(final WebJSONObject p0, final String p1, final String p2, final String p3, final String p4);
    
    boolean delete(final LotteryOpenCode p0);
    
    int countByInterfaceTime(final String p0, final String p1, final String p2);
    
    LotteryOpenCode getFirstExpectByInterfaceTime(final String p0, final String p1, final String p2);
    
    List<LotteryOpenCode> getLatest(final String p0, final int p1);
}
