package lottery.domains.content.vo.config;

public class RechargeConfig
{
  private int status;
  private String serviceTime;
  private double feePercent;
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public String getServiceTime()
  {
    return this.serviceTime;
  }
  
  public void setServiceTime(String serviceTime)
  {
    this.serviceTime = serviceTime;
  }
  
  public double getFeePercent()
  {
    return this.feePercent;
  }
  
  public void setFeePercent(double feePercent)
  {
    this.feePercent = feePercent;
  }
}
