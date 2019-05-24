package lottery.domains.content.payment;

import java.util.Date;

public class VerifyResult
{
  private String selfBillno;
  private String channelBillno;
  private Date payTime;
  private boolean success;
  private String output;
  private String successOutput;
  private String failedOutput;
  private Integer remitStatus;
  
  public String getSelfBillno()
  {
    return this.selfBillno;
  }
  
  public void setSelfBillno(String selfBillno)
  {
    this.selfBillno = selfBillno;
  }
  
  public String getChannelBillno()
  {
    return this.channelBillno;
  }
  
  public void setChannelBillno(String channelBillno)
  {
    this.channelBillno = channelBillno;
  }
  
  public Date getPayTime()
  {
    return this.payTime;
  }
  
  public void setPayTime(Date payTime)
  {
    this.payTime = payTime;
  }
  
  public boolean isSuccess()
  {
    return this.success;
  }
  
  public void setSuccess(boolean success)
  {
    this.success = success;
  }
  
  public String getOutput()
  {
    return this.output;
  }
  
  public void setOutput(String output)
  {
    this.output = output;
  }
  
  public String getSuccessOutput()
  {
    return this.successOutput;
  }
  
  public void setSuccessOutput(String successOutput)
  {
    this.successOutput = successOutput;
  }
  
  public String getFailedOutput()
  {
    return this.failedOutput;
  }
  
  public void setFailedOutput(String failedOutput)
  {
    this.failedOutput = failedOutput;
  }
  
  public Integer getRemitStatus()
  {
    return this.remitStatus;
  }
  
  public void setRemitStatus(Integer remitStatus)
  {
    this.remitStatus = remitStatus;
  }
}
