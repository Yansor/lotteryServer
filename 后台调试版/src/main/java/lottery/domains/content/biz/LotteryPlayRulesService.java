package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.vo.lottery.LotteryPlayRulesSimpleVO;
import lottery.domains.content.vo.lottery.LotteryPlayRulesVO;

public abstract interface LotteryPlayRulesService
{
  public abstract List<LotteryPlayRulesVO> list(int paramInt, Integer paramInteger);
  
  public abstract List<LotteryPlayRulesSimpleVO> listSimple(int paramInt, Integer paramInteger);
  
  public abstract LotteryPlayRulesVO get(int paramInt1, int paramInt2);
  
  public abstract boolean edit(int paramInt, Integer paramInteger, String paramString1, String paramString2);
  
  public abstract boolean updateStatus(int paramInt, Integer paramInteger, boolean paramBoolean);
}
