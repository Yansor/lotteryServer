package lottery.domains.content.biz;

import lottery.domains.content.vo.InstantStatVO;

public abstract interface LotteryInstantStatService
{
  public abstract InstantStatVO getInstantStat(String paramString1, String paramString2);
}
