package lottery.domains.content.payment.mkt;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class KeyValues
{
  private List<KeyValue> keyValues = new LinkedList();
  
  public List<KeyValue> items()
  {
    return this.keyValues;
  }
  
  public void add(KeyValue kv)
  {
    if ((kv != null) && (!StringUtils.isEmpty(kv.getVal()))) {
      this.keyValues.add(kv);
    }
  }
  
  public String sign(String key, String inputCharset)
  {
    StringBuilder sb = new StringBuilder();
    Collections.sort(this.keyValues);
    for (KeyValue kv : this.keyValues) {
      URLUtils.appendParam(sb, kv.getKey(), kv.getVal());
    }
    URLUtils.appendParam(sb, "key", key);
    String s = sb.toString();
    s = s.substring(1, s.length());
    return MD5Encoder.encode(s, inputCharset);
  }
}
