package lottery.domains.content.dao;

import lottery.domains.content.entity.UserGameAccount;

public interface UserGameAccountDao
{
    UserGameAccount get(final String p0, final int p1);
    
    UserGameAccount get(final int p0, final int p1);
    
    UserGameAccount save(final UserGameAccount p0);
}
