package lottery.domains.content.biz;

import lottery.domains.content.vo.activity.ActivityRewardBillVO;
import java.util.List;
import javautils.jdbc.PageList;

public interface ActivityRewardService
{
    PageList search(final String p0, final String p1, final Integer p2, final Integer p3, final int p4, final int p5);
    
    boolean add(final int p0, final int p1, final int p2, final double p3, final double p4, final String p5);
    
    List<ActivityRewardBillVO> getLatest(final int p0, final int p1, final int p2);
    
    boolean agreeAll(final String p0);
    
    boolean calculate(final int p0, final String p1);
}
