package lottery.domains.content.vo.payment;

import lottery.domains.content.entity.PaymentChannel;

public class PaymentChannelSimpleVO
{
  private int id;
  private String name;
  private String channelCode;
  private int status;
  private int type;
  private int subType;
  private int apiPayStatus;
  
  public PaymentChannelSimpleVO(PaymentChannel channel)
  {
    this.id = channel.getId();
    this.name = channel.getName();
    this.channelCode = channel.getChannelCode();
    this.status = channel.getStatus();
    this.type = channel.getType();
    this.subType = channel.getSubType();
    this.apiPayStatus = channel.getApiPayStatus();
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getChannelCode()
  {
    return this.channelCode;
  }
  
  public void setChannelCode(String channelCode)
  {
    this.channelCode = channelCode;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int type)
  {
    this.type = type;
  }
  
  public int getSubType()
  {
    return this.subType;
  }
  
  public void setSubType(int subType)
  {
    this.subType = subType;
  }
  
  public int getApiPayStatus()
  {
    return this.apiPayStatus;
  }
  
  public void setApiPayStatus(int apiPayStatus)
  {
    this.apiPayStatus = apiPayStatus;
  }
}
