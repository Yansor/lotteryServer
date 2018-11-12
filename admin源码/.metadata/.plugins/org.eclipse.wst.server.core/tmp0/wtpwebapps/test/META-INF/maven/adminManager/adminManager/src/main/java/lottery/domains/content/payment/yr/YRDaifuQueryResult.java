package lottery.domains.content.payment.yr;

import com.alibaba.fastjson.annotation.JSONField;

public class YRDaifuQueryResult
{
    @JSONField(name = "resultCode")
    private String resultCode;
    @JSONField(name = "outTradeNo")
    private String outTradeNo;
    @JSONField(name = "remitStatus")
    private String remitStatus;
    @JSONField(name = "settAmount")
    private String settAmount;
    @JSONField(name = "completeDate")
    private String completeDate;
    @JSONField(name = "errMsg")
    private String errMsg;
    @JSONField(name = "sign")
    private String sign;
    
    public String getResultCode() {
        return this.resultCode;
    }
    
    public void setResultCode(final String resultCode) {
        this.resultCode = resultCode;
    }
    
    public String getOutTradeNo() {
        return this.outTradeNo;
    }
    
    public void setOutTradeNo(final String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
    
    public String getRemitStatus() {
        return this.remitStatus;
    }
    
    public void setRemitStatus(final String remitStatus) {
        this.remitStatus = remitStatus;
    }
    
    public String getSettAmount() {
        return this.settAmount;
    }
    
    public void setSettAmount(final String settAmount) {
        this.settAmount = settAmount;
    }
    
    public String getCompleteDate() {
        return this.completeDate;
    }
    
    public void setCompleteDate(final String completeDate) {
        this.completeDate = completeDate;
    }
    
    public String getErrMsg() {
        return this.errMsg;
    }
    
    public void setErrMsg(final String errMsg) {
        this.errMsg = errMsg;
    }
    
    public String getSign() {
        return this.sign;
    }
    
    public void setSign(final String sign) {
        this.sign = sign;
    }
}
