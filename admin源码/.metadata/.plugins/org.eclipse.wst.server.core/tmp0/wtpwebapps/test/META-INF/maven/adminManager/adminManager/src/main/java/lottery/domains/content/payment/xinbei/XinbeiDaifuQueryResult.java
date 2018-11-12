package lottery.domains.content.payment.xinbei;

import com.alibaba.fastjson.annotation.JSONField;

public class XinbeiDaifuQueryResult
{
    @JSONField(name = "Amount")
    private String Amount;
    @JSONField(name = "ErrInfo")
    private String ErrInfo;
    @JSONField(name = "MerchantCode")
    private String MerchantCode;
    @JSONField(name = "MerchantOrder")
    private String MerchantOrder;
    @JSONField(name = "sign")
    private String sign;
    @JSONField(name = "Status")
    private String Status;
    @JSONField(name = "XbeiOrderId")
    private String XbeiOrderId;
    
    public String getAmount() {
        return this.Amount;
    }
    
    public void setAmount(final String amount) {
        this.Amount = amount;
    }
    
    public String getErrInfo() {
        return this.ErrInfo;
    }
    
    public void setErrInfo(final String errInfo) {
        this.ErrInfo = errInfo;
    }
    
    public String getMerchantCode() {
        return this.MerchantCode;
    }
    
    public void setMerchantCode(final String merchantCode) {
        this.MerchantCode = merchantCode;
    }
    
    public String getMerchantOrder() {
        return this.MerchantOrder;
    }
    
    public void setMerchantOrder(final String merchantOrder) {
        this.MerchantOrder = merchantOrder;
    }
    
    public String getSign() {
        return this.sign;
    }
    
    public void setSign(final String sign) {
        this.sign = sign;
    }
    
    public String getStatus() {
        return this.Status;
    }
    
    public void setStatus(final String status) {
        this.Status = status;
    }
    
    public String getXbeiOrderId() {
        return this.XbeiOrderId;
    }
    
    public void setXbeiOrderId(final String xbeiOrderId) {
        this.XbeiOrderId = xbeiOrderId;
    }
}
