package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.LotteryType;

public abstract interface LotteryTypeService
{
  public abstract List<LotteryType> listAll();
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
}
