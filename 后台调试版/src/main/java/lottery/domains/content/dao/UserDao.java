package lottery.domains.content.dao;

import java.util.Collection;
import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.User;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract interface UserDao
{
  public abstract boolean add(User paramUser);
  
  public abstract boolean update(User paramUser);
  
  public abstract List<User> listAll();
  
  public abstract User getById(int paramInt);
  
  public abstract User getByUsername(String paramString);
  
  public abstract boolean updateType(int paramInt1, int paramInt2);
  
  public abstract boolean unbindGoogle(int paramInt);
  
  public abstract boolean resetLockTime(int paramInt);
  
  public abstract boolean updateWithdrawName(int paramInt, String paramString);
  
  public abstract boolean updateWithdrawPassword(int paramInt, String paramString);
  
  public abstract boolean updateLoginPwd(int paramInt, String paramString);
  
  public abstract boolean updateImgPwd(int paramInt, String paramString);
  
  public abstract List<User> getUserLower(int paramInt);
  
  public abstract List<User> getUserDirectLower(int paramInt);
  
  public abstract List<Integer> getUserDirectLowerId(int paramInt);
  
  public abstract List<User> getUserLowerWithoutCode(int paramInt1, int paramInt2);
  
  public abstract List<User> getUserDirectLowerWithoutCode(int paramInt1, int paramInt2);
  
  public abstract List<Integer> getUserDirectLowerIdWithoutCode(int paramInt1, int paramInt2);
  
  public abstract boolean updateTotalMoney(int paramInt, double paramDouble);
  
  public abstract boolean updateLotteryMoney(int paramInt, double paramDouble);
  
  public abstract boolean updateLotteryMoney(int paramInt, double paramDouble1, double paramDouble2);
  
  public abstract boolean updateBaccaratMoney(int paramInt, double paramDouble);
  
  public abstract boolean updateFreezeMoney(int paramInt, double paramDouble);
  
  public abstract boolean updateDividendMoney(int paramInt, double paramDouble);
  
  public abstract boolean updateMoney(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5);
  
  public abstract boolean updateLotteryPoint(int paramInt1, int paramInt2, double paramDouble1, int paramInt3, double paramDouble2);
  
  public abstract boolean updateExtraPoint(int paramInt, double paramDouble);
  
  public abstract boolean updateProxy(int paramInt1, int paramInt2, String paramString);
  
  public abstract boolean updateLockTime(int paramInt, String paramString);
  
  public abstract boolean updateAStatus(int paramInt1, int paramInt2, String paramString);
  
  public abstract boolean updateBStatus(int paramInt1, int paramInt2, String paramString);
  
  public abstract boolean updateAllowEqualCode(int paramInt1, int paramInt2);
  
  public abstract boolean updateAllowTransfers(int paramInt1, int paramInt2);
  
  public abstract boolean updateAllowPlatformTransfers(int paramInt1, int paramInt2);
  
  public abstract boolean updateAllowWithdraw(int paramInt1, int paramInt2);
  
  public abstract boolean updateVipLevel(int paramInt1, int paramInt2);
  
  public abstract List<User> list(List<Criterion> paramList, List<Order> paramList1);
  
  public abstract PageList search(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);
  
  public abstract int getTotalUserRegist(String paramString1, String paramString2);
  
  public abstract List<?> getDayUserRegist(String paramString1, String paramString2);
  
  public abstract Object[] getTotalMoney();
  
  public abstract int getOnlineCount(Integer[] paramArrayOfInteger);
  
  public abstract int getDemoUserCount();
  
  public abstract int getFictitiousUserCount();
  
  public abstract void updateOnlineStatusNotIn(Collection<String> paramCollection);
  
  public abstract void updateAllOffline();
  
  public abstract void updateOffline(int paramInt);
  
  public abstract boolean updateRelatedUpper(int paramInt1, int paramInt2, double paramDouble);
  
  public abstract boolean updateRelatedLowers(int paramInt, String paramString);
  
  public abstract boolean updateRelatedUsers(int paramInt, String paramString);
  
  public abstract boolean lockTeam(int paramInt1, int paramInt2, String paramString);
  
  public abstract boolean unLockTeam(int paramInt1, int paramInt2);
  
  public abstract boolean prohibitTeamWithdraw(int paramInt1, int paramInt2);
  
  public abstract boolean allowTeamWithdraw(int paramInt1, int paramInt2);
  
  public abstract boolean allowTeamTransfers(int paramInt1, int paramInt2);
  
  public abstract boolean prohibitTeamTransfers(int paramInt1, int paramInt2);
  
  public abstract boolean allowTeamPlatformTransfers(int paramInt1, int paramInt2);
  
  public abstract boolean prohibitTeamPlatformTransfers(int paramInt1, int paramInt2);
  
  public abstract boolean changeZhaoShang(int paramInt1, int paramInt2);
  
  public abstract boolean delete(int paramInt);
  
  public abstract List<User> listAllByType(int paramInt);
}
