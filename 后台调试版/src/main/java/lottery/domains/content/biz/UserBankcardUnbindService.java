package lottery.domains.content.biz;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBankcardUnbindRecord;
import lottery.domains.content.vo.user.UserBankcardUnbindVO;

public abstract interface UserBankcardUnbindService
{
  public abstract boolean add(UserBankcardUnbindRecord paramUserBankcardUnbindRecord);
  
  public abstract boolean update(UserBankcardUnbindRecord paramUserBankcardUnbindRecord);
  
  public abstract boolean delByCardId(String paramString);
  
  public abstract boolean updateByParam(String paramString1, String paramString2, int paramInt, String paramString3);
  
  public abstract UserBankcardUnbindVO getUnbindInfoById(int paramInt);
  
  public abstract UserBankcardUnbindVO getUnbindInfoBycardId(String paramString);
  
  public abstract PageList search(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract List<UserBankcardUnbindVO> listAll();
}
