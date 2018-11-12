package lottery.domains.content.biz;

import java.util.List;
import admin.web.WebJSONObject;
import javautils.jdbc.PageList;
import lottery.domains.content.vo.user.UserProfileVO;
import lottery.domains.content.entity.User;

public interface UserService
{
    User getById(final int p0);
    
    User getByUsername(final String p0);
    
    boolean aStatus(final String p0, final int p1, final String p2);
    
    boolean bStatus(final String p0, final int p1, final String p2);
    
    boolean modifyLoginPwd(final String p0, final String p1);
    
    boolean modifyWithdrawPwd(final String p0, final String p1);
    
    boolean modifyWithdrawName(final String p0, final String p1);
    
    boolean resetImagePwd(final String p0);
    
    UserProfileVO getUserProfile(final String p0);
    
    boolean changeLine(final int p0, final String p1, final String p2);
    
    boolean modifyLotteryPoint(final String p0, final int p1, final double p2, final double p3);
    
    boolean downLinePoint(final String p0);
    
    boolean modifyExtraPoint(final String p0, final double p1);
    
    boolean modifyEqualCode(final String p0, final int p1);
    
    boolean modifyTransfers(final String p0, final int p1);
    
    boolean modifyPlatformTransfers(final String p0, final int p1);
    
    boolean modifyWithdraw(final String p0, final int p1);
    
    boolean changeProxy(final String p0);
    
    boolean unbindGoogle(final String p0);
    
    boolean resetLockTime(final String p0);
    
    boolean modifyQuota(final String p0, final int p1, final int p2, final int p3);
    
    User recover(final String p0);
    
    PageList search(final String p0, final String p1, final String p2, final Double p3, final Double p4, final Double p5, final Double p6, final Integer p7, final Integer p8, final String p9, final String p10, final Integer p11, final Integer p12, final Integer p13, final Integer p14, final String p15, final int p16, final int p17);
    
    PageList listOnline(final String p0, final String p1, final int p2, final int p3);
    
    boolean addNewUser(final WebJSONObject p0, final String p1, final String p2, final String p3, final int p4, final String p5, final int p6, final int p7, final double p8, final double p9, final String p10);
    
    boolean addLowerUser(final WebJSONObject p0, final User p1, final String p2, final String p3, final String p4, final int p5, final int p6, final double p7, final double p8, final String p9);
    
    boolean modifyUserVipLevel(final String p0, final int p1);
    
    boolean kickOutUser(final int p0, final String p1);
    
    boolean modifyRelatedUpper(final WebJSONObject p0, final String p1, final String p2, final double p3);
    
    boolean reliveRelatedUpper(final WebJSONObject p0, final String p1);
    
    boolean modifyRelatedUsers(final WebJSONObject p0, final String p1, final String p2);
    
    boolean lockTeam(final WebJSONObject p0, final String p1, final String p2);
    
    boolean unLockTeam(final WebJSONObject p0, final String p1);
    
    boolean allowTeamWithdraw(final WebJSONObject p0, final String p1);
    
    boolean prohibitTeamWithdraw(final WebJSONObject p0, final String p1);
    
    boolean allowTeamTransfers(final WebJSONObject p0, final String p1);
    
    boolean prohibitTeamTransfers(final WebJSONObject p0, final String p1);
    
    boolean allowTeamPlatformTransfers(final WebJSONObject p0, final String p1);
    
    boolean prohibitTeamPlatformTransfers(final WebJSONObject p0, final String p1);
    
    boolean transfer(final WebJSONObject p0, final User p1, final User p2, final double p3, final String p4);
    
    boolean changeZhaoShang(final WebJSONObject p0, final String p1, final int p2);
    
    List<String> getUserLevels(final User p0);
    
    List<User> findNeibuZhaoShang();
    
    List<User> findZhaoShang(final List<User> p0);
    
    List<User> getUserDirectLower(final int p0);
}
