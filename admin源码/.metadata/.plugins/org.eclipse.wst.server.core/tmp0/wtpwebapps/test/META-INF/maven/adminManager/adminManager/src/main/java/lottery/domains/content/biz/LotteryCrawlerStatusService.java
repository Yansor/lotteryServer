package lottery.domains.content.biz;

import lottery.domains.content.entity.LotteryCrawlerStatus;
import lottery.domains.content.vo.lottery.LotteryCrawlerStatusVO;
import java.util.List;

public interface LotteryCrawlerStatusService
{
    List<LotteryCrawlerStatusVO> listAll();
    
    LotteryCrawlerStatus getByLottery(final String p0);
    
    boolean update(final String p0, final String p1, final String p2);
}
