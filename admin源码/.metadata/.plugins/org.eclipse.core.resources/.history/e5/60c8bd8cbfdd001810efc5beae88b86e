package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.vo.user.UserCardVO;
import javautils.jdbc.PageList;

public interface UserCardService
{
    PageList search(final String p0, final String p1, final Integer p2, final int p3, final int p4);
    
    UserCardVO getById(final int p0);
    
    UserCard getByUserAndCardId(final int p0, final String p1);
    
    List<UserCardVO> getByUserId(final int p0);
    
    boolean add(final String p0, final int p1, final String p2, final String p3, final String p4, final int p5);
    
    boolean edit(final int p0, final int p1, final String p2, final String p3);
    
    boolean updateStatus(final int p0, final int p1);
}
