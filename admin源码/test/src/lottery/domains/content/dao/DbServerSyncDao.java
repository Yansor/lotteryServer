package lottery.domains.content.dao;

import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.entity.DbServerSync;
import java.util.List;

public interface DbServerSyncDao
{
    List<DbServerSync> listAll();
    
    boolean update(final DbServerSyncEnum p0);
    
    boolean update(final String p0, final String p1);
    
    DbServerSync getByKey(final String p0);
    
    boolean save(final DbServerSync p0);
}
