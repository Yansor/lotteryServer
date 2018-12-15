package lottery.domains.content.biz;

import admin.web.WebJSONObject;
import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.User;
import lottery.domains.content.vo.user.UserProfileVO;

public abstract interface UserService
{
  public abstract User getById(int paramInt);
  
  public abstract User getByUsername(String paramString);
  
  public abstract boolean aStatus(String paramString1, int paramInt, String paramString2);
  
  public abstract boolean bStatus(String paramString1, int paramInt, String paramString2);
  
  public abstract boolean modifyLoginPwd(String paramString1, String paramString2);
  
  public abstract boolean modifyWithdrawPwd(String paramString1, String paramString2);
  
  public abstract boolean modifyWithdrawName(String paramString1, String paramString2);
  
  public abstract boolean resetImagePwd(String paramString);
  
  public abstract UserProfileVO getUserProfile(String paramString);
  
  public abstract boolean changeLine(int paramInt, String paramString1, String paramString2);
  
  public abstract boolean modifyLotteryPoint(String paramString, int paramInt, double paramDouble1, double paramDouble2);
  
  public abstract boolean downLinePoint(String paramString);
  
  public abstract boolean modifyExtraPoint(String paramString, double paramDouble);
  
  public abstract boolean modifyEqualCode(String paramString, int paramInt);
  
  public abstract boolean modifyTransfers(String paramString, int paramInt);
  
  public abstract boolean modifyPlatformTransfers(String paramString, int paramInt);
  
  public abstract boolean modifyWithdraw(String paramString, int paramInt);
  
  public abstract boolean changeProxy(String paramString);
  
  public abstract boolean unbindGoogle(String paramString);
  
  public abstract boolean resetLockTime(String paramString);
  
  public abstract boolean modifyQuota(String paramString, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract User recover(String paramString);
  
  public abstract PageList search(String paramString1, String paramString2, String paramString3, Double paramDouble1, Double paramDouble2, Double paramDouble3, Double paramDouble4, Integer paramInteger1, Integer paramInteger2, String paramString4, String paramString5, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5, Integer paramInteger6, String paramString6, int paramInt1, int paramInt2);
  
  public abstract PageList listOnline(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract boolean addNewUser(WebJSONObject paramWebJSONObject, String paramString1, String paramString2, String paramString3, int paramInt1, String paramString4, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, String paramString5);
  
  public abstract boolean addLowerUser(WebJSONObject paramWebJSONObject, User paramUser, String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, double paramDouble1, double paramDouble2, String paramString4);
  
  public abstract boolean modifyUserVipLevel(String paramString, int paramInt);
  
  public abstract boolean kickOutUser(int paramInt, String paramString);
  
  public abstract boolean modifyRelatedUpper(WebJSONObject paramWebJSONObject, String paramString1, String paramString2, double paramDouble);
  
  public abstract boolean reliveRelatedUpper(WebJSONObject paramWebJSONObject, String paramString);
  
  public abstract boolean modifyRelatedUsers(WebJSONObject paramWebJSONObject, String paramString1, String paramString2);
  
  public abstract boolean lockTeam(WebJSONObject paramWebJSONObject, String paramString1, String paramString2);
  
  public abstract boolean unLockTeam(WebJSONObject paramWebJSONObject, String paramString);
  
  public abstract boolean allowTeamWithdraw(WebJSONObject paramWebJSONObject, String paramString);
  
  public abstract boolean prohibitTeamWithdraw(WebJSONObject paramWebJSONObject, String paramString);
  
  public abstract boolean allowTeamTransfers(WebJSONObject paramWebJSONObject, String paramString);
  
  public abstract boolean prohibitTeamTransfers(WebJSONObject paramWebJSONObject, String paramString);
  
  public abstract boolean allowTeamPlatformTransfers(WebJSONObject paramWebJSONObject, String paramString);
  
  public abstract boolean prohibitTeamPlatformTransfers(WebJSONObject paramWebJSONObject, String paramString);
  
  public abstract boolean transfer(WebJSONObject paramWebJSONObject, User paramUser1, User paramUser2, double paramDouble, String paramString);
  
  public abstract boolean changeZhaoShang(WebJSONObject paramWebJSONObject, String paramString, int paramInt);
  
  public abstract List<String> getUserLevels(User paramUser);
  
  public abstract List<User> findNeibuZhaoShang();
  
  public abstract List<User> findZhaoShang(List<User> paramList);
  
  public abstract List<User> getUserDirectLower(int paramInt);
}
