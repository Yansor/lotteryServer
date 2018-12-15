package admin.domains.content.biz;

import admin.domains.content.vo.AdminUserVO;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import java.util.List;

public abstract interface AdminUserService
{
  public abstract List<AdminUserVO> listAll(int paramInt);
  
  public abstract boolean add(String paramString1, String paramString2, int paramInt, Integer paramInteger, String paramString3);
  
  public abstract boolean edit(String paramString1, String paramString2, int paramInt, Integer paramInteger, String paramString3);
  
  public abstract boolean closeWithdrawPwd(String paramString);
  
  public abstract boolean openWithdrawPwd(String paramString1, String paramString2);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean updateLoginTime(int paramInt, String paramString);
  
  public abstract boolean modUserLoginPwd(int paramInt, String paramString);
  
  public abstract boolean modUserWithdrawPwd(int paramInt, String paramString);
  
  public abstract boolean updatePwdError(int paramInt1, int paramInt2);
  
  public abstract boolean updateIps(int paramInt, String paramString);
  
  public abstract GoogleAuthenticatorKey createCredentialsForUser(String paramString);
  
  public abstract boolean authoriseUser(String paramString, int paramInt);
}
