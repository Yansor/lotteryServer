package lottery.domains.content.vo.config;

public class WithdrawConfig
{
  private int status;
  private double minAmount;
  private double maxAmount;
  private String serviceTime;
  private String serviceMsg;
  private int maxTimes;
  private int freeTimes;
  private double fee;
  private double maxFee;
  private int registerHours;
  private double systemConsumptionPercent;
  private double transferConsumptionPercent;
  private String apiPayNotifyUrl;
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public double getMinAmount()
  {
    return this.minAmount;
  }
  
  public void setMinAmount(double minAmount)
  {
    this.minAmount = minAmount;
  }
  
  public double getMaxAmount()
  {
    return this.maxAmount;
  }
  
  public void setMaxAmount(double maxAmount)
  {
    this.maxAmount = maxAmount;
  }
  
  public String getServiceTime()
  {
    return this.serviceTime;
  }
  
  public void setServiceTime(String serviceTime)
  {
    this.serviceTime = serviceTime;
  }
  
  public String getServiceMsg()
  {
    return this.serviceMsg;
  }
  
  public void setServiceMsg(String serviceMsg)
  {
    this.serviceMsg = serviceMsg;
  }
  
  public int getMaxTimes()
  {
    return this.maxTimes;
  }
  
  public void setMaxTimes(int maxTimes)
  {
    this.maxTimes = maxTimes;
  }
  
  public int getFreeTimes()
  {
    return this.freeTimes;
  }
  
  public void setFreeTimes(int freeTimes)
  {
    this.freeTimes = freeTimes;
  }
  
  public double getFee()
  {
    return this.fee;
  }
  
  public void setFee(double fee)
  {
    this.fee = fee;
  }
  
  public double getMaxFee()
  {
    return this.maxFee;
  }
  
  public void setMaxFee(double maxFee)
  {
    this.maxFee = maxFee;
  }
  
  public int getRegisterHours()
  {
    return this.registerHours;
  }
  
  public void setRegisterHours(int registerHours)
  {
    this.registerHours = registerHours;
  }
  
  public double getSystemConsumptionPercent()
  {
    return this.systemConsumptionPercent;
  }
  
  public void setSystemConsumptionPercent(double systemConsumptionPercent)
  {
    this.systemConsumptionPercent = systemConsumptionPercent;
  }
  
  public double getTransferConsumptionPercent()
  {
    return this.transferConsumptionPercent;
  }
  
  public void setTransferConsumptionPercent(double transferConsumptionPercent)
  {
    this.transferConsumptionPercent = transferConsumptionPercent;
  }
  
  public String getApiPayNotifyUrl()
  {
    return this.apiPayNotifyUrl;
  }
  
  public void setApiPayNotifyUrl(String apiPayNotifyUrl)
  {
    this.apiPayNotifyUrl = apiPayNotifyUrl;
  }
}
