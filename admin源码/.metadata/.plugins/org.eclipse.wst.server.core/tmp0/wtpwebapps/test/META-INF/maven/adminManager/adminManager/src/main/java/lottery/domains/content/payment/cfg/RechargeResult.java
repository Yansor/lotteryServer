package lottery.domains.content.payment.cfg;

import java.util.HashMap;
import org.apache.http.message.BasicNameValuePair;
import java.util.List;
import net.sf.json.JSONObject;
import java.util.Date;

public class RechargeResult
{
    private String tradeId;
    private String redirectUrl;
    private String keyword;
    private String payUserName;
    private String message;
    private String signature;
    private String payMethod;
    private String payUrl;
    private int errorCode;
    private String errorMsg;
    private Date createTime;
    private JSONObject jsonValue;
    private List<BasicNameValuePair> weChatParam;
    private HashMap<String, String> paramsMap;
    
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(final Date createTime) {
        this.createTime = createTime;
    }
    
    public String getPayUrl() {
        return this.payUrl;
    }
    
    public void setPayUrl(final String payUrl) {
        this.payUrl = payUrl;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public String getSignature() {
        return this.signature;
    }
    
    public void setSignature(final String signature) {
        this.signature = signature;
    }
    
    public String getPayMethod() {
        return this.payMethod;
    }
    
    public void setPayMethod(final String payMethod) {
        this.payMethod = payMethod;
    }
    
    public String getKeyword() {
        return this.keyword;
    }
    
    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }
    
    public String getTradeId() {
        return this.tradeId;
    }
    
    public void setTradeId(final String tradeId) {
        this.tradeId = tradeId;
    }
    
    public String getRedirectUrl() {
        return this.redirectUrl;
    }
    
    public void setRedirectUrl(final String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
    
    public int getErrorCode() {
        return this.errorCode;
    }
    
    public void setErrorCode(final int errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getErrorMsg() {
        return this.errorMsg;
    }
    
    public void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public String getPayUserName() {
        return this.payUserName;
    }
    
    public void setPayUserName(final String payUserName) {
        this.payUserName = payUserName;
    }
    
    public JSONObject getJsonValue() {
        return this.jsonValue;
    }
    
    public void setJsonValue(final JSONObject jsonValue) {
        this.jsonValue = jsonValue;
    }
    
    public List<BasicNameValuePair> getWeChatParam() {
        return this.weChatParam;
    }
    
    public void setWeChatParam(final List<BasicNameValuePair> weChatParam) {
        this.weChatParam = weChatParam;
    }
    
    public HashMap<String, String> getParamsMap() {
        return this.paramsMap;
    }
    
    public void setParamsMap(final HashMap<String, String> paramsMap) {
        this.paramsMap = paramsMap;
    }
}
