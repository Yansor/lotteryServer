package admin.domains.content.biz;

import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import admin.domains.content.vo.AdminUserVO;
import java.util.List;

public interface AdminUserService
{
    List<AdminUserVO> listAll(final int p0);
    
    boolean add(final String p0, final String p1, final int p2, final Integer p3, final String p4);
    
    boolean edit(final String p0, final String p1, final int p2, final Integer p3, final String p4);
    
    boolean closeWithdrawPwd(final String p0);
    
    boolean openWithdrawPwd(final String p0, final String p1);
    
    boolean updateStatus(final int p0, final int p1);
    
    boolean updateLoginTime(final int p0, final String p1);
    
    boolean modUserLoginPwd(final int p0, final String p1);
    
    boolean modUserWithdrawPwd(final int p0, final String p1);
    
    boolean updatePwdError(final int p0, final int p1);
    
    boolean updateIps(final int p0, final String p1);
    
    GoogleAuthenticatorKey createCredentialsForUser(final String p0);
    
    boolean authoriseUser(final String p0, final int p1);
}
