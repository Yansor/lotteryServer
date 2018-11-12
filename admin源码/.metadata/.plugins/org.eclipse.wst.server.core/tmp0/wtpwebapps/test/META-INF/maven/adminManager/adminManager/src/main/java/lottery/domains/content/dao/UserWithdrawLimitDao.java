package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.UserWithdrawLimit;

public interface UserWithdrawLimitDao
{
    UserWithdrawLimit getByUserId(final int p0);
    
    List<UserWithdrawLimit> getUserWithdrawLimits(final int p0, final String p1);
    
    boolean add(final UserWithdrawLimit p0);
    
    boolean delByUserId(final int p0);
}
