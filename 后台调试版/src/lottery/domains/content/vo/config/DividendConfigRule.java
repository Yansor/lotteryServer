package lottery.domains.content.vo.config;

public class DividendConfigRule
{
  private double from;
  private double to;
  private double scale;
  
  public DividendConfigRule(double from, double to, double scale)
  {
    this.from = from;
    this.to = to;
    this.scale = scale;
  }
  
  public double getFrom()
  {
    return this.from;
  }
  
  public void setFrom(double from)
  {
    this.from = from;
  }
  
  public double getTo()
  {
    return this.to;
  }
  
  public void setTo(double to)
  {
    this.to = to;
  }
  
  public double getScale()
  {
    return this.scale;
  }
  
  public void setScale(double scale)
  {
    this.scale = scale;
  }
}
