package lottery.domains.content.payment.htf;

public class HTFPayQueryResult
{
  private String retCode;
  private String retMsg;
  private String agentId;
  private String hyBillNo;
  private String batchNo;
  private String batchAmt;
  private String batchNum;
  private String detailData;
  private String extParam1;
  private String sign;
  
  public String getRetCode()
  {
    return this.retCode;
  }
  
  public void setRetCode(String retCode)
  {
    this.retCode = retCode;
  }
  
  public String getRetMsg()
  {
    return this.retMsg;
  }
  
  public void setRetMsg(String retMsg)
  {
    this.retMsg = retMsg;
  }
  
  public String getAgentId()
  {
    return this.agentId;
  }
  
  public void setAgentId(String agentId)
  {
    this.agentId = agentId;
  }
  
  public String getHyBillNo()
  {
    return this.hyBillNo;
  }
  
  public void setHyBillNo(String hyBillNo)
  {
    this.hyBillNo = hyBillNo;
  }
  
  public String getBatchNo()
  {
    return this.batchNo;
  }
  
  public void setBatchNo(String batchNo)
  {
    this.batchNo = batchNo;
  }
  
  public String getBatchAmt()
  {
    return this.batchAmt;
  }
  
  public void setBatchAmt(String batchAmt)
  {
    this.batchAmt = batchAmt;
  }
  
  public String getBatchNum()
  {
    return this.batchNum;
  }
  
  public void setBatchNum(String batchNum)
  {
    this.batchNum = batchNum;
  }
  
  public String getDetailData()
  {
    return this.detailData;
  }
  
  public void setDetailData(String detailData)
  {
    this.detailData = detailData;
  }
  
  public String getExtParam1()
  {
    return this.extParam1;
  }
  
  public void setExtParam1(String extParam1)
  {
    this.extParam1 = extParam1;
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
