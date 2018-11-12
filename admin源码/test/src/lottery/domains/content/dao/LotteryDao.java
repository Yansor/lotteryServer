package lottery.domains.content.dao;

import lottery.domains.content.entity.Lottery;
import java.util.List;

public interface LotteryDao
{
    List<Lottery> listAll();
    
    boolean updateStatus(final int p0, final int p1);
    
    boolean updateTimes(final int p0, final int p1);
    
    Lottery getById(final int p0);
    
    Lottery getByName(final String p0);
    
    Lottery getByShortName(final String p0);
}
