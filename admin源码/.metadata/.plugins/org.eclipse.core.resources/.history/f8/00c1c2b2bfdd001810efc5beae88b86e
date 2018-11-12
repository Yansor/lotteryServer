package admin.web;

import net.sf.json.JSONObject;
import admin.domains.pool.AdminDataFactory;

public class WebJSONObject
{
    private AdminDataFactory df;
    private String message;
    private Integer error;
    private String code;
    private JSONObject json;
    
    public WebJSONObject(final AdminDataFactory df) {
        this.json = new JSONObject();
        this.df = df;
    }
    
    public void set(final Integer error, final String code) {
        this.message = this.df.getSysMessage(code);
        this.error = error;
        this.code = code;
    }
    
    public void setWithParams(final Integer error, final String code, final Object... args) {
        this.message = this.df.getSysMessage(code);
        this.error = error;
        this.code = code;
        if (args != null && args.length > 0) {
            this.message = String.format(this.message, args);
        }
    }
    
    @Override
    public String toString() {
        this.json.put((Object)"error", (Object)this.error);
        this.json.put((Object)"message", (Object)this.message);
        this.json.put((Object)"code", (Object)this.code);
        return this.json.toString();
    }
    
    public Integer getError() {
        return this.error;
    }
    
    public void setError(final Integer error) {
        this.error = error;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public void setCode(final String code) {
        this.code = code;
    }
    
    public JSONObject accumulate(final String key, final Object value) {
        return this.json.accumulate(key, value);
    }
}
