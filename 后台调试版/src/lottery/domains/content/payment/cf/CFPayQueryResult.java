package lottery.domains.content.payment.cf;

public class CFPayQueryResult
{
  private String batchContent;
  private String batchDate;
  private String batchNo;
  private String batchVersion;
  private String charset;
  private String merchantId;
  private String respCode;
  private String respMessage;
  private String signType;
  private String sign;
  
  public String getBatchContent()
  {
    return this.batchContent;
  }
  
  public void setBatchContent(String batchContent)
  {
    this.batchContent = batchContent;
  }
  
  public String getBatchDate()
  {
    return this.batchDate;
  }
  
  public void setBatchDate(String batchDate)
  {
    this.batchDate = batchDate;
  }
  
  public String getBatchNo()
  {
    return this.batchNo;
  }
  
  public void setBatchNo(String batchNo)
  {
    this.batchNo = batchNo;
  }
  
  public String getBatchVersion()
  {
    return this.batchVersion;
  }
  
  public void setBatchVersion(String batchVersion)
  {
    this.batchVersion = batchVersion;
  }
  
  public String getCharset()
  {
    return this.charset;
  }
  
  public void setCharset(String charset)
  {
    this.charset = charset;
  }
  
  public String getMerchantId()
  {
    return this.merchantId;
  }
  
  public void setMerchantId(String merchantId)
  {
    this.merchantId = merchantId;
  }
  
  public String getRespCode()
  {
    return this.respCode;
  }
  
  public void setRespCode(String respCode)
  {
    this.respCode = respCode;
  }
  
  public String getRespMessage()
  {
    return this.respMessage;
  }
  
  public void setRespMessage(String respMessage)
  {
    this.respMessage = respMessage;
  }
  
  public String getSignType()
  {
    return this.signType;
  }
  
  public void setSignType(String signType)
  {
    this.signType = signType;
  }
  
  public String getSign()
  {
    return this.sign;
  }
  
  public void setSign(String sign)
  {
    this.sign = sign;
  }
}
