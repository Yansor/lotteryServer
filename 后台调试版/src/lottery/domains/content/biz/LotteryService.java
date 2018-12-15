package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.vo.lottery.LotteryVO;

public abstract interface LotteryService
{
  public abstract List<LotteryVO> list(String paramString);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean updateTimes(int paramInt1, int paramInt2);
  
  public abstract Lottery getByShortName(String paramString);
}
