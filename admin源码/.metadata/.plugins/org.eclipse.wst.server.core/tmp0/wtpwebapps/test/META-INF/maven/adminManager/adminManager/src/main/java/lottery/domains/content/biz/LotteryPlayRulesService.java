package lottery.domains.content.biz;

import lottery.domains.content.vo.lottery.LotteryPlayRulesSimpleVO;
import lottery.domains.content.vo.lottery.LotteryPlayRulesVO;
import java.util.List;

public interface LotteryPlayRulesService
{
    List<LotteryPlayRulesVO> list(final int p0, final Integer p1);
    
    List<LotteryPlayRulesSimpleVO> listSimple(final int p0, final Integer p1);
    
    LotteryPlayRulesVO get(final int p0, final int p1);
    
    boolean edit(final int p0, final Integer p1, final String p2, final String p3);
    
    boolean updateStatus(final int p0, final Integer p1, final boolean p2);
}
