package lottery.domains.content.api.sb;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class Win88SBResult
{
    @JSONField(name = "error_code")
    private String errorCode;
    @JSONField(name = "message")
    private String message;
    
    public String getErrorCode() {
        return this.errorCode;
    }
    
    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
}
