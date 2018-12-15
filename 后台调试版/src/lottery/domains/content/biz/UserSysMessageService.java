package lottery.domains.content.biz;

public abstract interface UserSysMessageService
{
  public abstract boolean addTransToUser(int paramInt, double paramDouble);
  
  public abstract boolean addSysRecharge(int paramInt, double paramDouble, String paramString);
  
  public abstract boolean addOnlineRecharge(int paramInt, double paramDouble);
  
  public abstract boolean addTransfersRecharge(int paramInt, double paramDouble);
  
  public abstract boolean addConfirmWithdraw(int paramInt, double paramDouble1, double paramDouble2);
  
  public abstract boolean addRefuseWithdraw(int paramInt, double paramDouble);
  
  public abstract boolean addRefuse(int paramInt, double paramDouble);
  
  public abstract boolean addShFail(int paramInt, double paramDouble);
  
  public abstract boolean addFirstRecharge(int paramInt, double paramDouble1, double paramDouble2);
  
  public abstract boolean addActivityBind(int paramInt, double paramDouble);
  
  public abstract boolean addActivityRecharge(int paramInt, double paramDouble);
  
  public abstract boolean addRewardMessage(int paramInt, String paramString);
  
  public abstract boolean addVipLevelUp(int paramInt, String paramString);
  
  public abstract boolean addDividendBill(int paramInt, String paramString1, String paramString2);
  
  public abstract boolean addGameDividendBill(int paramInt, String paramString1, String paramString2);
  
  public abstract boolean addDailySettleBill(int paramInt, String paramString);
  
  public abstract boolean addGameWaterBill(int paramInt, String paramString1, String paramString2, String paramString3);
}
