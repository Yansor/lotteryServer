package lottery.domains.content.biz;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.vo.user.UserCardVO;

public abstract interface UserCardService
{
  public abstract PageList search(String paramString1, String paramString2, Integer paramInteger, int paramInt1, int paramInt2);
  
  public abstract UserCardVO getById(int paramInt);
  
  public abstract UserCard getByUserAndCardId(int paramInt, String paramString);
  
  public abstract List<UserCardVO> getByUserId(int paramInt);
  
  public abstract boolean add(String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, int paramInt2);
  
  public abstract boolean edit(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
}
