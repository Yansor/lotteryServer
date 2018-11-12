package lottery.domains.content.payment.lepay.request;

import java.util.Map;

public class FCSOpenApiTradeRecordRequest extends FCSOpenApiRequest
{
    private String tradeId;
    private String outTradeNo;
    
    public String getTradeId() {
        return this.tradeId;
    }
    
    public void setTradeId(final String tradeId) {
        this.tradeId = tradeId;
    }
    
    public String getOutTradeNo() {
        return this.outTradeNo;
    }
    
    public void setOutTradeNo(final String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
    
    @Override
    public Map<String, String> getTextParams() {
        final Map map = this.getBaseTextParams();
        map.put("trade_id", this.tradeId);
        map.put("out_trade_no", this.outTradeNo);
        return (Map<String, String>)map;
    }
}
