package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.UserCodeQuota;

public interface UserCodeQuotaDao
{
    boolean add(final UserCodeQuota p0);
    
    UserCodeQuota get(final int p0);
    
    List<UserCodeQuota> list(final int[] p0);
    
    boolean update(final UserCodeQuota p0);
    
    boolean delete(final int p0);
}
