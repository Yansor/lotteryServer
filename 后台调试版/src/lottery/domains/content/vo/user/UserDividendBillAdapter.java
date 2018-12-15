package lottery.domains.content.vo.user;

import java.util.List;
import lottery.domains.content.entity.UserDividendBill;

public class UserDividendBillAdapter
{
  private UserDividendBill upperBill;
  private List<UserDividendBillAdapter> lowerBills;
  
  public UserDividendBillAdapter(UserDividendBill upperBill, List<UserDividendBillAdapter> lowerBills)
  {
    this.upperBill = upperBill;
    this.lowerBills = lowerBills;
  }
  
  public UserDividendBill getUpperBill()
  {
    return this.upperBill;
  }
  
  public void setUpperBill(UserDividendBill upperBill)
  {
    this.upperBill = upperBill;
  }
  
  public List<UserDividendBillAdapter> getLowerBills()
  {
    return this.lowerBills;
  }
  
  public void setLowerBills(List<UserDividendBillAdapter> lowerBills)
  {
    this.lowerBills = lowerBills;
  }
}
