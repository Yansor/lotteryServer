package lottery.domains.content.dao;

import java.util.Collection;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.User;

public interface UserDao
{
    boolean add(final User p0);
    
    boolean update(final User p0);
    
    List<User> listAll();
    
    User getById(final int p0);
    
    User getByUsername(final String p0);
    
    boolean updateType(final int p0, final int p1);
    
    boolean unbindGoogle(final int p0);
    
    boolean resetLockTime(final int p0);
    
    boolean updateWithdrawName(final int p0, final String p1);
    
    boolean updateWithdrawPassword(final int p0, final String p1);
    
    boolean updateLoginPwd(final int p0, final String p1);
    
    boolean updateImgPwd(final int p0, final String p1);
    
    List<User> getUserLower(final int p0);
    
    List<User> getUserDirectLower(final int p0);
    
    List<Integer> getUserDirectLowerId(final int p0);
    
    List<User> getUserLowerWithoutCode(final int p0, final int p1);
    
    List<User> getUserDirectLowerWithoutCode(final int p0, final int p1);
    
    List<Integer> getUserDirectLowerIdWithoutCode(final int p0, final int p1);
    
    boolean updateTotalMoney(final int p0, final double p1);
    
    boolean updateLotteryMoney(final int p0, final double p1);
    
    boolean updateLotteryMoney(final int p0, final double p1, final double p2);
    
    boolean updateBaccaratMoney(final int p0, final double p1);
    
    boolean updateFreezeMoney(final int p0, final double p1);
    
    boolean updateDividendMoney(final int p0, final double p1);
    
    boolean updateMoney(final int p0, final double p1, final double p2, final double p3, final double p4, final double p5);
    
    boolean updateLotteryPoint(final int p0, final int p1, final double p2, final int p3, final double p4);
    
    boolean updateExtraPoint(final int p0, final double p1);
    
    boolean updateProxy(final int p0, final int p1, final String p2);
    
    boolean updateLockTime(final int p0, final String p1);
    
    boolean updateAStatus(final int p0, final int p1, final String p2);
    
    boolean updateBStatus(final int p0, final int p1, final String p2);
    
    boolean updateAllowEqualCode(final int p0, final int p1);
    
    boolean updateAllowTransfers(final int p0, final int p1);
    
    boolean updateAllowPlatformTransfers(final int p0, final int p1);
    
    boolean updateAllowWithdraw(final int p0, final int p1);
    
    boolean updateVipLevel(final int p0, final int p1);
    
    List<User> list(final List<Criterion> p0, final List<Order> p1);
    
    PageList search(final List<Criterion> p0, final List<Order> p1, final int p2, final int p3);
    
    int getTotalUserRegist(final String p0, final String p1);
    
    List<?> getDayUserRegist(final String p0, final String p1);
    
    Object[] getTotalMoney();
    
    int getOnlineCount(final Integer[] p0);
    
    int getDemoUserCount();
    
    int getFictitiousUserCount();
    
    void updateOnlineStatusNotIn(final Collection<String> p0);
    
    void updateAllOffline();
    
    void updateOffline(final int p0);
    
    boolean updateRelatedUpper(final int p0, final int p1, final double p2);
    
    boolean updateRelatedLowers(final int p0, final String p1);
    
    boolean updateRelatedUsers(final int p0, final String p1);
    
    boolean lockTeam(final int p0, final int p1, final String p2);
    
    boolean unLockTeam(final int p0, final int p1);
    
    boolean prohibitTeamWithdraw(final int p0, final int p1);
    
    boolean allowTeamWithdraw(final int p0, final int p1);
    
    boolean allowTeamTransfers(final int p0, final int p1);
    
    boolean prohibitTeamTransfers(final int p0, final int p1);
    
    boolean allowTeamPlatformTransfers(final int p0, final int p1);
    
    boolean prohibitTeamPlatformTransfers(final int p0, final int p1);
    
    boolean changeZhaoShang(final int p0, final int p1);
    
    boolean delete(final int p0);
    
    List<User> listAllByType(final int p0);
}
