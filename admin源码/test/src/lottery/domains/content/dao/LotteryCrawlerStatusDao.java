package lottery.domains.content.dao;

import lottery.domains.content.entity.LotteryCrawlerStatus;
import java.util.List;

public interface LotteryCrawlerStatusDao
{
    List<LotteryCrawlerStatus> listAll();
    
    LotteryCrawlerStatus get(final String p0);
    
    boolean update(final LotteryCrawlerStatus p0);
}
