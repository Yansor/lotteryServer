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
    
    public String getBatchContent() {
        return this.batchContent;
    }
    
    public void setBatchContent(final String batchContent) {
        this.batchContent = batchContent;
    }
    
    public String getBatchDate() {
        return this.batchDate;
    }
    
    public void setBatchDate(final String batchDate) {
        this.batchDate = batchDate;
    }
    
    public String getBatchNo() {
        return this.batchNo;
    }
    
    public void setBatchNo(final String batchNo) {
        this.batchNo = batchNo;
    }
    
    public String getBatchVersion() {
        return this.batchVersion;
    }
    
    public void setBatchVersion(final String batchVersion) {
        this.batchVersion = batchVersion;
    }
    
    public String getCharset() {
        return this.charset;
    }
    
    public void setCharset(final String charset) {
        this.charset = charset;
    }
    
    public String getMerchantId() {
        return this.merchantId;
    }
    
    public void setMerchantId(final String merchantId) {
        this.merchantId = merchantId;
    }
    
    public String getRespCode() {
        return this.respCode;
    }
    
    public void setRespCode(final String respCode) {
        this.respCode = respCode;
    }
    
    public String getRespMessage() {
        return this.respMessage;
    }
    
    public void setRespMessage(final String respMessage) {
        this.respMessage = respMessage;
    }
    
    public String getSignType() {
        return this.signType;
    }
    
    public void setSignType(final String signType) {
        this.signType = signType;
    }
    
    public String getSign() {
        return this.sign;
    }
    
    public void setSign(final String sign) {
        this.sign = sign;
    }
}
