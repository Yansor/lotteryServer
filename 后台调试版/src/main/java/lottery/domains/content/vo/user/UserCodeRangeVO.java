package lottery.domains.content.vo.user;

public class UserCodeRangeVO
{
  private int code;
  private double maxLocatePoint;
  private double maxNotLocatePoint;
  
  public UserCodeRangeVO() {}
  
  public UserCodeRangeVO(int code, double maxLocatePoint, double maxNotLocatePoint)
  {
    this.code = code;
    this.maxLocatePoint = maxLocatePoint;
    this.maxNotLocatePoint = maxNotLocatePoint;
  }
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int code)
  {
    this.code = code;
  }
  
  public double getMaxLocatePoint()
  {
    return this.maxLocatePoint;
  }
  
  public void setMaxLocatePoint(double maxLocatePoint)
  {
    this.maxLocatePoint = maxLocatePoint;
  }
  
  public double getMaxNotLocatePoint()
  {
    return this.maxNotLocatePoint;
  }
  
  public void setMaxNotLocatePoint(double maxNotLocatePoint)
  {
    this.maxNotLocatePoint = maxNotLocatePoint;
  }
}
