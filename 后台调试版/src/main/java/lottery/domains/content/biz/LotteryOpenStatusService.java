package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.vo.lottery.LotteryOpenStatusVO;

public abstract interface LotteryOpenStatusService
{
  public abstract List<LotteryOpenStatusVO> search(String paramString1, String paramString2);
  
  public abstract boolean doManualControl(String paramString1, String paramString2);
}
