package lottery.domains.content.biz;

public interface UserSysMessageService
{
    boolean addTransToUser(final int p0, final double p1);
    
    boolean addSysRecharge(final int p0, final double p1, final String p2);
    
    boolean addOnlineRecharge(final int p0, final double p1);
    
    boolean addTransfersRecharge(final int p0, final double p1);
    
    boolean addConfirmWithdraw(final int p0, final double p1, final double p2);
    
    boolean addRefuseWithdraw(final int p0, final double p1);
    
    boolean addRefuse(final int p0, final double p1);
    
    boolean addShFail(final int p0, final double p1);
    
    boolean addFirstRecharge(final int p0, final double p1, final double p2);
    
    boolean addActivityBind(final int p0, final double p1);
    
    boolean addActivityRecharge(final int p0, final double p1);
    
    boolean addRewardMessage(final int p0, final String p1);
    
    boolean addVipLevelUp(final int p0, final String p1);
    
    boolean addDividendBill(final int p0, final String p1, final String p2);
    
    boolean addGameDividendBill(final int p0, final String p1, final String p2);
    
    boolean addDailySettleBill(final int p0, final String p1);
    
    boolean addGameWaterBill(final int p0, final String p1, final String p2, final String p3);
}
