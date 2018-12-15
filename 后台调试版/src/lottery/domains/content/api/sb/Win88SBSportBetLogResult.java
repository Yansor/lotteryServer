package lottery.domains.content.api.sb;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

public class Win88SBSportBetLogResult
  extends Win88SBResult
{
  @JSONField(name="LastVersionKey")
  private String LastVersionKey;
  @JSONField(name="TotalRecord")
  private int TotalRecord;
  @JSONField(name="Data")
  private List Data;
  
  public String getLastVersionKey()
  {
    return this.LastVersionKey;
  }
  
  public void setLastVersionKey(String lastVersionKey)
  {
    this.LastVersionKey = lastVersionKey;
  }
  
  public int getTotalRecord()
  {
    return this.TotalRecord;
  }
  
  public void setTotalRecord(int totalRecord)
  {
    this.TotalRecord = totalRecord;
  }
  
  public List getData()
  {
    return this.Data;
  }
  
  public void setData(List data)
  {
    this.Data = data;
  }
}
