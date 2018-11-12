package lottery.domains.content.payment.lepay.request;

import java.util.Map;

public class FCSOpenApiWxPayRequest extends FCSOpenApiRequest
{
    private String amount;
    private String remark;
    private String outTradeNo;
    private String wxPayType;
    private String subject;
    private String subBody;
    
    public String getAmount() {
        return this.amount;
    }
    
    public void setAmount(final String amount) {
        this.amount = amount;
    }
    
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(final String remark) {
        this.remark = remark;
    }
    
    public String getOutTradeNo() {
        return this.outTradeNo;
    }
    
    public void setOutTradeNo(final String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
    
    public String getWxPayType() {
        return this.wxPayType;
    }
    
    public void setWxPayType(final String wxPayType) {
        this.wxPayType = wxPayType;
    }
    
    public String getSubject() {
        return this.subject;
    }
    
    public void setSubject(final String subject) {
        this.subject = subject;
    }
    
    public String getSubBody() {
        return this.subBody;
    }
    
    public void setSubBody(final String subBody) {
        this.subBody = subBody;
    }
    
    @Override
    public Map<String, String> getTextParams() {
        final Map map = this.getBaseTextParams();
        map.put("remark", this.remark);
        map.put("out_trade_no", this.outTradeNo);
        map.put("wx_pay_type", this.wxPayType);
        map.put("subject", this.subject);
        map.put("sub_body", this.subBody);
        map.put("amount_str", this.amount.toString());
        return (Map<String, String>)map;
    }
}
