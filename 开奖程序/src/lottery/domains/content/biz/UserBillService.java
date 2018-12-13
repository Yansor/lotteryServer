package lottery.domains.content.biz;

import lottery.domains.content.entity.UserBets;

public abstract interface UserBillService
{
  public abstract boolean addSpendBill(UserBets paramUserBets);
  
  public abstract boolean addCancelOrderBill(UserBets paramUserBets, String paramString);
  
  public abstract boolean addUserWinBill(UserBets paramUserBets, double paramDouble, String paramString);
  
  public abstract boolean addProxyReturnBill(UserBets paramUserBets, int paramInt, double paramDouble, String paramString);
  
  public abstract boolean addSpendReturnBill(UserBets paramUserBets, double paramDouble, String paramString);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/UserBillService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */