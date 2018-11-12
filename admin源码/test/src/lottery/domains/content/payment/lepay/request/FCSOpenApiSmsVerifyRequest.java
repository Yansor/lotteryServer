package lottery.domains.content.payment.lepay.request;

import java.util.Map;

public class FCSOpenApiSmsVerifyRequest extends FCSOpenApiRequest
{
    private String tradeId;
    private String veriCode;
    private String bankMobileNo;
    
    public String getTradeId() {
        return this.tradeId;
    }
    
    public void setTradeId(final String tradeId) {
        this.tradeId = tradeId;
    }
    
    public String getVeriCode() {
        return this.veriCode;
    }
    
    public void setVeriCode(final String veriCode) {
        this.veriCode = veriCode;
    }
    
    public String getBankMobileNo() {
        return this.bankMobileNo;
    }
    
    public void setBankMobileNo(final String bankMobileNo) {
        this.bankMobileNo = bankMobileNo;
    }
    
    @Override
    public Map<String, String> getTextParams() {
        final Map params = this.getBaseTextParams();
        params.put("trade_id", this.tradeId);
        params.put("veri_code", this.veriCode);
        params.put("bank_mobile_no", this.bankMobileNo);
        return (Map<String, String>)params;
    }
}
