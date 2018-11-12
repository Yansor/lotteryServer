package lottery.domains.content.payment.lepay.request;

import java.util.Map;

public class FCSOpenApiBalanceRequest extends FCSOpenApiRequest
{
    private String tradeDate;
    private String pageDisplayNo;
    private String pageNo;
    
    public String getTradeDate() {
        return this.tradeDate;
    }
    
    public void setTradeDate(final String tradeDate) {
        this.tradeDate = tradeDate;
    }
    
    public String getPageDisplayNo() {
        return this.pageDisplayNo;
    }
    
    public void setPageDisplayNo(final String pageDisplayNo) {
        this.pageDisplayNo = pageDisplayNo;
    }
    
    public String getPageNo() {
        return this.pageNo;
    }
    
    public void setPageNo(final String pageNo) {
        this.pageNo = pageNo;
    }
    
    @Override
    public Map<String, String> getTextParams() {
        final Map map = this.getBaseTextParams();
        map.put("trade_date", this.tradeDate);
        map.put("page_display_no", this.pageDisplayNo);
        map.put("page_no", this.pageNo);
        return (Map<String, String>)map;
    }
}
