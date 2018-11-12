package lottery.domains.content.payment.tgf;

import com.alibaba.fastjson.annotation.JSONField;

public class TGFDaifuQueryResult
{
    @JSONField(name = "result_code")
    private String result_code;
    @JSONField(name = "result_msg")
    private String result_msg;
    @JSONField(name = "order_no")
    private String order_no;
    @JSONField(name = "merchant_no")
    private String merchant_no;
    @JSONField(name = "result")
    private String result;
    @JSONField(name = "amount")
    private String amount;
    @JSONField(name = "withdraw_fee")
    private String withdraw_fee;
    @JSONField(name = "sign")
    private String sign;
    
    public String getResult_code() {
        return this.result_code;
    }
    
    public void setResult_code(final String result_code) {
        this.result_code = result_code;
    }
    
    public String getResult_msg() {
        return this.result_msg;
    }
    
    public void setResult_msg(final String result_msg) {
        this.result_msg = result_msg;
    }
    
    public String getOrder_no() {
        return this.order_no;
    }
    
    public void setOrder_no(final String order_no) {
        this.order_no = order_no;
    }
    
    public String getMerchant_no() {
        return this.merchant_no;
    }
    
    public void setMerchant_no(final String merchant_no) {
        this.merchant_no = merchant_no;
    }
    
    public String getResult() {
        return this.result;
    }
    
    public void setResult(final String result) {
        this.result = result;
    }
    
    public String getAmount() {
        return this.amount;
    }
    
    public void setAmount(final String amount) {
        this.amount = amount;
    }
    
    public String getWithdraw_fee() {
        return this.withdraw_fee;
    }
    
    public void setWithdraw_fee(final String withdraw_fee) {
        this.withdraw_fee = withdraw_fee;
    }
    
    public String getSign() {
        return this.sign;
    }
    
    public void setSign(final String sign) {
        this.sign = sign;
    }
}
