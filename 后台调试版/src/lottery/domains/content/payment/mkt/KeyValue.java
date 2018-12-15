package lottery.domains.content.payment.mkt;

public class KeyValue implements Comparable
{
  private String key;
  private String val;
  
  public KeyValue(String k, String v)
  {
    this.key = k;
    this.val = v;
  }
  
  public int compare(KeyValue other)
  {
    return this.key.compareTo(other.key);
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public String getVal()
  {
    return this.val;
  }

  @Override
  public int compareTo(Object o) {

    {
      if(o instanceof KeyValue){
        return this.key.compareTo(((KeyValue) o).key);
      }
    }
    return 0;

  }
}
