package lottery.domains.content.biz;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.vo.user.UserBankcardUnbindVO;
import lottery.domains.content.entity.UserBankcardUnbindRecord;

public interface UserBankcardUnbindService
{
    boolean add(final UserBankcardUnbindRecord p0);
    
    boolean update(final UserBankcardUnbindRecord p0);
    
    boolean delByCardId(final String p0);
    
    boolean updateByParam(final String p0, final String p1, final int p2, final String p3);
    
    UserBankcardUnbindVO getUnbindInfoById(final int p0);
    
    UserBankcardUnbindVO getUnbindInfoBycardId(final String p0);
    
    PageList search(final String p0, final String p1, final String p2, final int p3, final int p4);
    
    List<UserBankcardUnbindVO> listAll();
}
