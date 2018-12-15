package lottery.domains.content.vo.config;

public class LotteryConfig
{
  private int bUnitMoney;
  private int fenModelDownCode;
  private int liModelDownCode;
  private boolean autoHitRanking;
  private int hitRankingSize;
  private int notBetPoint;
  private int notBetPointAccount;
  
  public int getbUnitMoney()
  {
    return this.bUnitMoney;
  }
  
  public void setbUnitMoney(int bUnitMoney)
  {
    this.bUnitMoney = bUnitMoney;
  }
  
  public int getFenModelDownCode()
  {
    return this.fenModelDownCode;
  }
  
  public void setFenModelDownCode(int fenModelDownCode)
  {
    this.fenModelDownCode = fenModelDownCode;
  }
  
  public int getLiModelDownCode()
  {
    return this.liModelDownCode;
  }
  
  public void setLiModelDownCode(int liModelDownCode)
  {
    this.liModelDownCode = liModelDownCode;
  }
  
  public boolean isAutoHitRanking()
  {
    return this.autoHitRanking;
  }
  
  public void setAutoHitRanking(boolean autoHitRanking)
  {
    this.autoHitRanking = autoHitRanking;
  }
  
  public int getHitRankingSize()
  {
    return this.hitRankingSize;
  }
  
  public void setHitRankingSize(int hitRankingSize)
  {
    this.hitRankingSize = hitRankingSize;
  }
  
  public int getNotBetPoint()
  {
    return this.notBetPoint;
  }
  
  public void setNotBetPoint(int notBetPoint)
  {
    this.notBetPoint = notBetPoint;
  }
  
  public int getNotBetPointAccount()
  {
    return this.notBetPointAccount;
  }
  
  public void setNotBetPointAccount(int notBetPointAccount)
  {
    this.notBetPointAccount = notBetPointAccount;
  }
}
