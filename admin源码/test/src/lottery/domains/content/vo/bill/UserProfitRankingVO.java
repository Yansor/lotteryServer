package lottery.domains.content.vo.bill;

public class UserProfitRankingVO
{
    private int userId;
    private String sTime;
    private String eTime;
    private String name;
    private double profit;
    
    public UserProfitRankingVO(final int userId, final String sTime, final String eTime, final double profit) {
        this.userId = userId;
        this.sTime = sTime;
        this.eTime = eTime;
        this.profit = profit;
    }
    
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    public String getsTime() {
        return this.sTime;
    }
    
    public void setsTime(final String sTime) {
        this.sTime = sTime;
    }
    
    public String geteTime() {
        return this.eTime;
    }
    
    public void seteTime(final String eTime) {
        this.eTime = eTime;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public double getProfit() {
        return this.profit;
    }
    
    public void setProfit(final double profit) {
        this.profit = profit;
    }
}
