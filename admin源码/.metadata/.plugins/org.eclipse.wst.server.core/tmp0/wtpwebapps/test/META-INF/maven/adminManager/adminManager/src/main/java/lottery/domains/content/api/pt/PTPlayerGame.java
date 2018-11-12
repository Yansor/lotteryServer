package lottery.domains.content.api.pt;

import java.util.List;

public class PTPlayerGame
{
    private List<PTPlayerGameResult> result;
    private PTPlayerGamePagination pagination;
    
    public List<PTPlayerGameResult> getResult() {
        return this.result;
    }
    
    public void setResult(final List<PTPlayerGameResult> result) {
        this.result = result;
    }
    
    public PTPlayerGamePagination getPagination() {
        return this.pagination;
    }
    
    public void setPagination(final PTPlayerGamePagination pagination) {
        this.pagination = pagination;
    }
}
