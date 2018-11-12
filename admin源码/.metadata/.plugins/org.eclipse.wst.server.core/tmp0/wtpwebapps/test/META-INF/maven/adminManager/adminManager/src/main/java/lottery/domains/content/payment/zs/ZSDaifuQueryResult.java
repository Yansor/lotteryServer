package lottery.domains.content.payment.zs;

import com.alibaba.fastjson.annotation.JSONField;

public class ZSDaifuQueryResult
{
    @JSONField(name = "merchantCode")
    private String merchantCode;
    @JSONField(name = "outOrderId")
    private String outOrderId;
    @JSONField(name = "orderId")
    private String orderId;
    @JSONField(name = "state")
    private String state;
    @JSONField(name = "transTime")
    private String transTime;
    @JSONField(name = "totalAmount")
    private Long totalAmount;
    @JSONField(name = "fee")
    private Long fee;
    @JSONField(name = "errorMsg")
    private String errorMsg;
    @JSONField(name = "sign")
    private String sign;
    
    public String getMerchantCode() {
        return this.merchantCode;
    }
    
    public void setMerchantCode(final String merchantCode) {
        this.merchantCode = merchantCode;
    }
    
    public String getOutOrderId() {
        return this.outOrderId;
    }
    
    public void setOutOrderId(final String outOrderId) {
        this.outOrderId = outOrderId;
    }
    
    public String getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }
    
    public String getState() {
        return this.state;
    }
    
    public void setState(final String state) {
        this.state = state;
    }
    
    public String getTransTime() {
        return this.transTime;
    }
    
    public void setTransTime(final String transTime) {
        this.transTime = transTime;
    }
    
    public Long getTotalAmount() {
        return this.totalAmount;
    }
    
    public void setTotalAmount(final Long totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Long getFee() {
        return this.fee;
    }
    
    public void setFee(final Long fee) {
        this.fee = fee;
    }
    
    public String getErrorMsg() {
        return this.errorMsg;
    }
    
    public void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public String getSign() {
        return this.sign;
    }
    
    public void setSign(final String sign) {
        this.sign = sign;
    }
}
