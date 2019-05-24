package lottery.domains.content.vo.user;

public class SysCodeRangeVO
{
  private double point;
  private double minPoint;
  private double maxPoint;
  private int defaultQuantity;
  
  public double getMinPoint()
  {
    return this.minPoint;
  }
  
  public void setMinPoint(double minPoint)
  {
    this.minPoint = minPoint;
  }
  
  public double getMaxPoint()
  {
    return this.maxPoint;
  }
  
  public void setMaxPoint(double maxPoint)
  {
    this.maxPoint = maxPoint;
  }
  
  public double getPoint()
  {
    return this.point;
  }
  
  public void setPoint(double point)
  {
    this.point = point;
  }
  
  public int getDefaultQuantity()
  {
    return this.defaultQuantity;
  }
  
  public void setDefaultQuantity(int defaultQuantity)
  {
    this.defaultQuantity = defaultQuantity;
  }
}
