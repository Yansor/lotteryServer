package admin.web;

import admin.domains.pool.AdminDataFactory;
import net.sf.json.JSONObject;

public class WebJSONObject
{
  private AdminDataFactory df;
  private String message;
  private Integer error;
  private String code;
  private JSONObject json = new JSONObject();
  
  public WebJSONObject(AdminDataFactory df)
  {
    this.df = df;
  }
  
  public void set(Integer error, String code)
  {
    this.message = this.df.getSysMessage(code);
    this.error = error;
    this.code = code;
  }
  
  public void setWithParams(Integer error, String code, Object... args)
  {
    this.message = this.df.getSysMessage(code);
    this.error = error;
    this.code = code;
    if ((args != null) && (args.length > 0)) {
      this.message = String.format(this.message, args);
    }
  }
  
  public String toString()
  {
    this.json.put("error", this.error);
    this.json.put("message", this.message);
    this.json.put("code", this.code);
    return this.json.toString();
  }
  
  public Integer getError()
  {
    return this.error;
  }
  
  public void setError(Integer error)
  {
    this.error = error;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public JSONObject accumulate(String key, Object value)
  {
    return this.json.accumulate(key, value);
  }
}
