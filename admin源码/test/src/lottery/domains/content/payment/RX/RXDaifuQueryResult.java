package lottery.domains.content.payment.RX;

import com.alibaba.fastjson.annotation.JSONField;

public class RXDaifuQueryResult
{
    @JSONField(name = "userid")
    private String userid;
    @JSONField(name = "orderId")
    private String orderId;
    @JSONField(name = "state")
    private String state;
    @JSONField(name = "orderId_state")
    private String orderId_state;
    @JSONField(name = "money")
    private String money;
    @JSONField(name = "fee")
    private String fee;
    @JSONField(name = "message")
    private String message;
    @JSONField(name = "type")
    private String type;
    
    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(final String userid) {
        this.userid = userid;
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
    
    public String getOrderId_state() {
        return this.orderId_state;
    }
    
    public void setOrderId_state(final String orderId_state) {
        this.orderId_state = orderId_state;
    }
    
    public String getMoney() {
        return this.money;
    }
    
    public void setMoney(final String money) {
        this.money = money;
    }
    
    public String getFee() {
        return this.fee;
    }
    
    public void setFee(final String fee) {
        this.fee = fee;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
}
