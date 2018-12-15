package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.vo.lottery.LotteryPlayRulesGroupSimpleVO;
import lottery.domains.content.vo.lottery.LotteryPlayRulesGroupVO;

public abstract interface LotteryPlayRulesGroupService
{
  public abstract List<LotteryPlayRulesGroupSimpleVO> listSimpleByType(int paramInt);
  
  public abstract List<LotteryPlayRulesGroupVO> list(int paramInt);
  
  public abstract boolean updateStatus(int paramInt, Integer paramInteger, boolean paramBoolean);
}
