package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.DbServerSync;
import lottery.domains.content.global.DbServerSyncEnum;

public abstract interface DbServerSyncDao
{
  public abstract List<DbServerSync> listAll();
  
  public abstract boolean update(DbServerSyncEnum paramDbServerSyncEnum);
  
  public abstract boolean update(String paramString1, String paramString2);
  
  public abstract DbServerSync getByKey(String paramString);
  
  public abstract boolean save(DbServerSync paramDbServerSync);
}
