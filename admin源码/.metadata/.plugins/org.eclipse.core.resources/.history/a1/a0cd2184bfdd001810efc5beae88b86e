package lottery.domains.content.payment.lepay.request;

import java.util.Map;

public class FCSOpenApiRefundRequest extends FCSOpenApiRequest
{
    private String tradeId;
    private String amountStr;
    
    public String getAmountStr() {
        return this.amountStr;
    }
    
    public void setAmountStr(final String amountStr) {
        this.amountStr = amountStr;
    }
    
    public String getTradeId() {
        return this.tradeId;
    }
    
    public void setTradeId(final String tradeId) {
        this.tradeId = tradeId;
    }
    
    @Override
    public Map<String, String> getTextParams() {
        final Map params = this.getBaseTextParams();
        params.put("trade_id", this.tradeId);
        params.put("amount_str", this.amountStr);
        return (Map<String, String>)params;
    }
}
