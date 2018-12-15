package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.LotteryCrawlerStatus;

public abstract interface LotteryCrawlerStatusDao
{
  public abstract List<LotteryCrawlerStatus> listAll();
  
  public abstract LotteryCrawlerStatus get(String paramString);
  
  public abstract boolean update(LotteryCrawlerStatus paramLotteryCrawlerStatus);
}
