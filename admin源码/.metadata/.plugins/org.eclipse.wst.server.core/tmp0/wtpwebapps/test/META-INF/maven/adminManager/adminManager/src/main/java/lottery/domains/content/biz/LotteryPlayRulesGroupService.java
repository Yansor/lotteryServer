package lottery.domains.content.biz;

import lottery.domains.content.vo.lottery.LotteryPlayRulesGroupVO;
import lottery.domains.content.vo.lottery.LotteryPlayRulesGroupSimpleVO;
import java.util.List;

public interface LotteryPlayRulesGroupService
{
    List<LotteryPlayRulesGroupSimpleVO> listSimpleByType(final int p0);
    
    List<LotteryPlayRulesGroupVO> list(final int p0);
    
    boolean updateStatus(final int p0, final Integer p1, final boolean p2);
}
