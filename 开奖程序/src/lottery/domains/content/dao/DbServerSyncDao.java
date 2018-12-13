package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.DbServerSync;
import lottery.domains.content.global.DbServerSyncEnum;

public abstract interface DbServerSyncDao
{
  public abstract List<DbServerSync> listAll();
  
  public abstract boolean update(DbServerSyncEnum paramDbServerSyncEnum);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/DbServerSyncDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */