package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_daily_settle_bill", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"user_id", "indicate_date"})})
public class UserDailySettleBill
{
  private int id;
  private int userId;
  private String indicateDate;
  private int minValidUser;
  private int validUser;
  private double scale;
  private double billingOrder;
  private double thisLoss;
  private double calAmount;
  private double userAmount;
  private double lowerTotalAmount;
  private double lowerPaidAmount;
  private double availableAmount;
  private double totalReceived;
  private String settleTime;
  private int issueType;
  private int status;
  private String remarks;
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id", unique=true, nullable=false)
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  @Column(name="user_id", nullable=false)
  public int getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(int userId)
  {
    this.userId = userId;
  }
  
  @Column(name="indicate_date", length=10)
  public String getIndicateDate()
  {
    return this.indicateDate;
  }
  
  public void setIndicateDate(String indicateDate)
  {
    this.indicateDate = indicateDate;
  }
  
  @Column(name="settle_time", length=19)
  public String getSettleTime()
  {
    return this.settleTime;
  }
  
  public void setSettleTime(String settleTime)
  {
    this.settleTime = settleTime;
  }
  
  @Column(name="min_valid_user", nullable=false)
  public int getMinValidUser()
  {
    return this.minValidUser;
  }
  
  public void setMinValidUser(int minValidUser)
  {
    this.minValidUser = minValidUser;
  }
  
  @Column(name="valid_user", nullable=false)
  public int getValidUser()
  {
    return this.validUser;
  }
  
  public void setValidUser(int validUser)
  {
    this.validUser = validUser;
  }
  
  @Column(name="scale", nullable=false, precision=5, scale=4)
  public double getScale()
  {
    return this.scale;
  }
  
  public void setScale(double scale)
  {
    this.scale = scale;
  }
  
  @Column(name="billing_order", nullable=false, precision=16, scale=4)
  public double getBillingOrder()
  {
    return this.billingOrder;
  }
  
  public void setBillingOrder(double billingOrder)
  {
    this.billingOrder = billingOrder;
  }
  
  @Column(name="cal_amount", nullable=false, precision=16, scale=4)
  public double getCalAmount()
  {
    return this.calAmount;
  }
  
  public void setCalAmount(double calAmount)
  {
    this.calAmount = calAmount;
  }
  
  @Column(name="user_amount", nullable=false, precision=16, scale=4)
  public double getUserAmount()
  {
    return this.userAmount;
  }
  
  public void setUserAmount(double userAmount)
  {
    this.userAmount = userAmount;
  }
  
  @Column(name="lower_total_amount", nullable=false, precision=16, scale=4)
  public double getLowerTotalAmount()
  {
    return this.lowerTotalAmount;
  }
  
  public void setLowerTotalAmount(double lowerTotalAmount)
  {
    this.lowerTotalAmount = lowerTotalAmount;
  }
  
  @Column(name="lower_paid_amount", nullable=false, precision=16, scale=4)
  public double getLowerPaidAmount()
  {
    return this.lowerPaidAmount;
  }
  
  public void setLowerPaidAmount(double lowerPaidAmount)
  {
    this.lowerPaidAmount = lowerPaidAmount;
  }
  
  @Column(name="total_received", nullable=false, precision=16, scale=4)
  public double getTotalReceived()
  {
    return this.totalReceived;
  }
  
  public void setTotalReceived(double totalReceived)
  {
    this.totalReceived = totalReceived;
  }
  
  @Column(name="available_amount", nullable=false, precision=16, scale=4)
  public double getAvailableAmount()
  {
    return this.availableAmount;
  }
  
  public void setAvailableAmount(double availableAmount)
  {
    this.availableAmount = availableAmount;
  }
  
  @Column(name="issue_type", nullable=false)
  public int getIssueType()
  {
    return this.issueType;
  }
  
  public void setIssueType(int issueType)
  {
    this.issueType = issueType;
  }
  
  @Column(name="status", nullable=false)
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  @Column(name="remarks", length=255, nullable=true)
  public String getRemarks()
  {
    return this.remarks;
  }
  
  public void setRemarks(String remarks)
  {
    this.remarks = remarks;
  }
  
  @Column(name="this_loss", nullable=false, precision=16, scale=4)
  public double getThisLoss()
  {
    return this.thisLoss;
  }
  
  public void setThisLoss(double thisLoss)
  {
    this.thisLoss = thisLoss;
  }
}
