package lottery.domains.content.vo.user;

import java.util.List;
import lottery.domains.content.entity.UserDailySettleBill;

public class UserDailySettleBillAdapter
{
    private UserDailySettleBill upperBill;
    private List<UserDailySettleBillAdapter> lowerBills;
    
    public UserDailySettleBillAdapter(final UserDailySettleBill upperBill, final List<UserDailySettleBillAdapter> lowerBills) {
        this.upperBill = upperBill;
        this.lowerBills = lowerBills;
    }
    
    public UserDailySettleBill getUpperBill() {
        return this.upperBill;
    }
    
    public void setUpperBill(final UserDailySettleBill upperBill) {
        this.upperBill = upperBill;
    }
    
    public List<UserDailySettleBillAdapter> getLowerBills() {
        return this.lowerBills;
    }
    
    public void setLowerBills(final List<UserDailySettleBillAdapter> lowerBills) {
        this.lowerBills = lowerBills;
    }
}
