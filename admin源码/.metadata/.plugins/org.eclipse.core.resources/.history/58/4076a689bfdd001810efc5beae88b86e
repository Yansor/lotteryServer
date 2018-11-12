package lottery.domains.content.dao;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.UserBankcardUnbindRecord;

public interface UserBankcardUnbindDao
{
    boolean add(final UserBankcardUnbindRecord p0);
    
    boolean update(final UserBankcardUnbindRecord p0);
    
    boolean delByCardId(final String p0);
    
    boolean updateByParam(final String p0, final String p1, final int p2, final String p3);
    
    UserBankcardUnbindRecord getUnbindInfoById(final int p0);
    
    UserBankcardUnbindRecord getUnbindInfoBycardId(final String p0);
    
    PageList search(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    List<UserBankcardUnbindRecord> listAll();
}
