package lottery.domains.content.vo.bill;

public class UserBetsReportVO
{
    private String field;
    private double money;
    private double returnMoney;
    private double prizeMoney;
    
    public String getField() {
        return this.field;
    }
    
    public void setField(final String field) {
        this.field = field;
    }
    
    public double getMoney() {
        return this.money;
    }
    
    public void setMoney(final double money) {
        this.money = money;
    }
    
    public double getReturnMoney() {
        return this.returnMoney;
    }
    
    public void setReturnMoney(final double returnMoney) {
        this.returnMoney = returnMoney;
    }
    
    public double getPrizeMoney() {
        return this.prizeMoney;
    }
    
    public void setPrizeMoney(final double prizeMoney) {
        this.prizeMoney = prizeMoney;
    }
}
