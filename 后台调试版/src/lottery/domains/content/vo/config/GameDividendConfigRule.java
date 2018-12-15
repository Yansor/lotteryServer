package lottery.domains.content.vo.config;

public class GameDividendConfigRule
{
  private double fromLoss;
  private double toLoss;
  private double scale;
  
  public GameDividendConfigRule(double fromLoss, double toLoss, double scale)
  {
    this.fromLoss = fromLoss;
    this.toLoss = toLoss;
    this.scale = scale;
  }
  
  public double getFromLoss()
  {
    return this.fromLoss;
  }
  
  public void setFromLoss(double fromLoss)
  {
    this.fromLoss = fromLoss;
  }
  
  public double getToLoss()
  {
    return this.toLoss;
  }
  
  public void setToLoss(double toLoss)
  {
    this.toLoss = toLoss;
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
