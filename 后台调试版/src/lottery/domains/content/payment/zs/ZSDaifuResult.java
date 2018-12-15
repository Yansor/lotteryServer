package lottery.domains.content.payment.zs;

import com.alibaba.fastjson.annotation.JSONField;

public class ZSDaifuResult
{
  @JSONField(name="merchantCode")
  private String merchantCode;
  @JSONField(name="outOrderId")
  private String outOrderId;
  @JSONField(name="orderId")
  private String orderId;
  @JSONField(name="totalAmount")
  private Long totalAmount;
  @JSONField(name="fee")
  private Long fee;
  @JSONField(name="sign")
  private String sign;
  
  public String getMerchantCode()
  {
    return this.merchantCode;
  }
  
  public void setMerchantCode(String merchantCode)
  {
    this.merchantCode = merchantCode;
  }
  
  public String getOutOrderId()
  {
    return this.outOrderId;
  }
  
  public void setOutOrderId(String outOrderId)
  {
    this.outOrderId = outOrderId;
  }
  
  public String getOrderId()
  {
    return this.orderId;
  }
  
  public void setOrderId(String orderId)
  {
    this.orderId = orderId;
  }
  
  public Long getTotalAmount()
  {
    return this.totalAmount;
  }
  
  public void setTotalAmount(Long totalAmount)
  {
    this.totalAmount = totalAmount;
  }
  
  public Long getFee()
  {
    return this.fee;
  }
  
  public void setFee(Long fee)
  {
    this.fee = fee;
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
