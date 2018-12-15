package lottery.domains.content.biz;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserBets;
import lottery.domains.content.entity.UserRecharge;
import lottery.domains.content.entity.UserTransfers;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.vo.bill.UserBillVO;

public abstract interface UserBillService
{
  public abstract boolean addRechargeBill(UserRecharge paramUserRecharge, User paramUser, String paramString);
  
  public abstract boolean addWithdrawReport(UserWithdraw paramUserWithdraw);
  
  public abstract boolean addDrawBackBill(UserWithdraw paramUserWithdraw, User paramUser, String paramString);
  
  public abstract boolean addTransInBill(UserTransfers paramUserTransfers, User paramUser, int paramInt, String paramString);
  
  public abstract boolean addTransOutBill(UserTransfers paramUserTransfers, User paramUser, int paramInt, String paramString);
  
  public abstract boolean addActivityBill(User paramUser, int paramInt1, double paramDouble, int paramInt2, String paramString);
  
  public abstract boolean addAdminAddBill(User paramUser, int paramInt, double paramDouble, String paramString);
  
  public abstract boolean addAdminMinusBill(User paramUser, int paramInt, double paramDouble, String paramString);
  
  public abstract boolean addSpendBill(UserBets paramUserBets, User paramUser);
  
  public abstract boolean addCancelOrderBill(UserBets paramUserBets, User paramUser);
  
  public abstract boolean addDividendBill(User paramUser, int paramInt, double paramDouble, String paramString, boolean paramBoolean);
  
  public abstract boolean addRewardPayBill(User paramUser, int paramInt, double paramDouble, String paramString);
  
  public abstract boolean addRewardIncomeBill(User paramUser, int paramInt, double paramDouble, String paramString);
  
  public abstract boolean addRewardReturnBill(User paramUser, int paramInt, double paramDouble, String paramString);
  
  public abstract boolean addDailySettleBill(User paramUser, int paramInt, double paramDouble, String paramString, boolean paramBoolean);
  
  public abstract boolean addGameWaterBill(User paramUser, int paramInt1, int paramInt2, double paramDouble, String paramString);
  
  public abstract PageList search(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, String paramString3, String paramString4, Double paramDouble1, Double paramDouble2, Integer paramInteger3, int paramInt1, int paramInt2);
  
  public abstract PageList searchHistory(String paramString1, String paramString2, Integer paramInteger1, String paramString3, String paramString4, Double paramDouble1, Double paramDouble2, Integer paramInteger2, int paramInt1, int paramInt2);
  
  public abstract List<UserBillVO> getLatest(int paramInt1, int paramInt2, int paramInt3);
}
