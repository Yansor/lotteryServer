package lottery.domains.content.vo.payment;

import lottery.domains.content.entity.PaymentChannel;

public class PaymentChannelVO
{
  private int id;
  private String name;
  private String mobileName;
  private String frontName;
  private String channelCode;
  private String merCode;
  private double totalCredits;
  private double usedCredits;
  private double minTotalRecharge;
  private double maxTotalRecharge;
  private double minUnitRecharge;
  private double maxUnitRecharge;
  private int status;
  private int sequence;
  private String maxRegisterTime;
  private String qrUrlCode;
  private int fixedQRAmount;
  private int type;
  private int subType;
  private double consumptionPercent;
  private String whiteUsernames;
  private String startTime;
  private String endTime;
  private int addMoneyType;
  private String apiPayBankChannelCode;
  private int apiPayStatus;
  
  public PaymentChannelVO(PaymentChannel channel)
  {
    this.id = channel.getId();
    this.name = channel.getName();
    this.mobileName = channel.getMobileName();
    this.frontName = channel.getFrontName();
    this.channelCode = channel.getChannelCode();
    this.merCode = channel.getMerCode();
    this.totalCredits = channel.getTotalCredits();
    this.usedCredits = channel.getUsedCredits();
    this.minTotalRecharge = channel.getMinTotalRecharge();
    this.maxTotalRecharge = channel.getMaxTotalRecharge();
    this.minUnitRecharge = channel.getMinUnitRecharge();
    this.maxUnitRecharge = channel.getMaxUnitRecharge();
    this.status = channel.getStatus();
    this.sequence = channel.getSequence();
    this.maxRegisterTime = channel.getMaxRegisterTime();
    this.qrUrlCode = channel.getQrUrlCode();
    this.fixedQRAmount = channel.getFixedQRAmount();
    this.type = channel.getType();
    this.subType = channel.getSubType();
    this.consumptionPercent = channel.getConsumptionPercent();
    this.whiteUsernames = channel.getWhiteUsernames();
    this.startTime = channel.getStartTime();
    this.endTime = channel.getEndTime();
    this.addMoneyType = channel.getAddMoneyType();
    this.apiPayBankChannelCode = channel.getApiPayBankChannelCode();
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
  
  public String getMobileName()
  {
    return this.mobileName;
  }
  
  public void setMobileName(String mobileName)
  {
    this.mobileName = mobileName;
  }
  
  public String getFrontName()
  {
    return this.frontName;
  }
  
  public void setFrontName(String frontName)
  {
    this.frontName = frontName;
  }
  
  public String getChannelCode()
  {
    return this.channelCode;
  }
  
  public void setChannelCode(String channelCode)
  {
    this.channelCode = channelCode;
  }
  
  public String getMerCode()
  {
    return this.merCode;
  }
  
  public void setMerCode(String merCode)
  {
    this.merCode = merCode;
  }
  
  public double getTotalCredits()
  {
    return this.totalCredits;
  }
  
  public void setTotalCredits(double totalCredits)
  {
    this.totalCredits = totalCredits;
  }
  
  public double getUsedCredits()
  {
    return this.usedCredits;
  }
  
  public void setUsedCredits(double usedCredits)
  {
    this.usedCredits = usedCredits;
  }
  
  public double getMinTotalRecharge()
  {
    return this.minTotalRecharge;
  }
  
  public void setMinTotalRecharge(double minTotalRecharge)
  {
    this.minTotalRecharge = minTotalRecharge;
  }
  
  public double getMaxTotalRecharge()
  {
    return this.maxTotalRecharge;
  }
  
  public void setMaxTotalRecharge(double maxTotalRecharge)
  {
    this.maxTotalRecharge = maxTotalRecharge;
  }
  
  public double getMinUnitRecharge()
  {
    return this.minUnitRecharge;
  }
  
  public void setMinUnitRecharge(double minUnitRecharge)
  {
    this.minUnitRecharge = minUnitRecharge;
  }
  
  public double getMaxUnitRecharge()
  {
    return this.maxUnitRecharge;
  }
  
  public void setMaxUnitRecharge(double maxUnitRecharge)
  {
    this.maxUnitRecharge = maxUnitRecharge;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public int getSequence()
  {
    return this.sequence;
  }
  
  public void setSequence(int sequence)
  {
    this.sequence = sequence;
  }
  
  public String getMaxRegisterTime()
  {
    return this.maxRegisterTime;
  }
  
  public void setMaxRegisterTime(String maxRegisterTime)
  {
    this.maxRegisterTime = maxRegisterTime;
  }
  
  public String getQrUrlCode()
  {
    return this.qrUrlCode;
  }
  
  public void setQrUrlCode(String qrUrlCode)
  {
    this.qrUrlCode = qrUrlCode;
  }
  
  public int getFixedQRAmount()
  {
    return this.fixedQRAmount;
  }
  
  public void setFixedQRAmount(int fixedQRAmount)
  {
    this.fixedQRAmount = fixedQRAmount;
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
  
  public double getConsumptionPercent()
  {
    return this.consumptionPercent;
  }
  
  public void setConsumptionPercent(double consumptionPercent)
  {
    this.consumptionPercent = consumptionPercent;
  }
  
  public String getWhiteUsernames()
  {
    return this.whiteUsernames;
  }
  
  public void setWhiteUsernames(String whiteUsernames)
  {
    this.whiteUsernames = whiteUsernames;
  }
  
  public String getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(String startTime)
  {
    this.startTime = startTime;
  }
  
  public String getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }
  
  public int getAddMoneyType()
  {
    return this.addMoneyType;
  }
  
  public void setAddMoneyType(int addMoneyType)
  {
    this.addMoneyType = addMoneyType;
  }
  
  public String getApiPayBankChannelCode()
  {
    return this.apiPayBankChannelCode;
  }
  
  public void setApiPayBankChannelCode(String apiPayBankChannelCode)
  {
    this.apiPayBankChannelCode = apiPayBankChannelCode;
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
