package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.LotteryCrawlerStatus;
import lottery.domains.content.vo.lottery.LotteryCrawlerStatusVO;

public abstract interface LotteryCrawlerStatusService
{
  public abstract List<LotteryCrawlerStatusVO> listAll();
  
  public abstract LotteryCrawlerStatus getByLottery(String paramString);
  
  public abstract boolean update(String paramString1, String paramString2, String paramString3);
}
