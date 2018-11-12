package lottery.domains.content.payment.lepay.request;

import java.util.Map;

public class FCSOpenApiMobilePayResendRequest extends FCSOpenApiRequest
{
    private String tradeId;
    
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
        return (Map<String, String>)params;
    }
}
