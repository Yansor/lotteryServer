package lottery.domains.content.vo.user;

import lottery.domains.content.entity.User;

public class UserBaseVO
{
  private int id;
  private String username;
  private String nickname;
  private double totalMoney;
  private double lotteryMoney;
  private double baccaratMoney;
  private double freezeMoney;
  private double dividendMoney;
  private int type;
  private int code;
  private double locatePoint;
  private double notLocatePoint;
  private double extraPoint;
  private String registTime;
  private String loginTime;
  private int AStatus;
  private int BStatus;
  private int onlineStatus;
  private int isCjZhaoShang;
  
  public UserBaseVO(User bean)
  {
    this.id = bean.getId();
    this.username = bean.getUsername();
    this.nickname = bean.getNickname();
    this.totalMoney = bean.getTotalMoney();
    this.lotteryMoney = bean.getLotteryMoney();
    this.baccaratMoney = bean.getBaccaratMoney();
    this.freezeMoney = bean.getFreezeMoney();
    this.dividendMoney = bean.getDividendMoney();
    this.type = bean.getType();
    this.code = bean.getCode();
    this.locatePoint = bean.getLocatePoint();
    this.notLocatePoint = bean.getNotLocatePoint();
    this.extraPoint = bean.getExtraPoint();
    this.registTime = bean.getRegistTime();
    this.loginTime = bean.getLoginTime();
    this.AStatus = bean.getAStatus();
    this.BStatus = bean.getBStatus();
    this.onlineStatus = bean.getOnlineStatus();
    this.isCjZhaoShang = bean.getIsCjZhaoShang();
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getNickname()
  {
    return this.nickname;
  }
  
  public void setNickname(String nickname)
  {
    this.nickname = nickname;
  }
  
  public double getTotalMoney()
  {
    return this.totalMoney;
  }
  
  public void setTotalMoney(double totalMoney)
  {
    this.totalMoney = totalMoney;
  }
  
  public double getLotteryMoney()
  {
    return this.lotteryMoney;
  }
  
  public void setLotteryMoney(double lotteryMoney)
  {
    this.lotteryMoney = lotteryMoney;
  }
  
  public double getBaccaratMoney()
  {
    return this.baccaratMoney;
  }
  
  public void setBaccaratMoney(double baccaratMoney)
  {
    this.baccaratMoney = baccaratMoney;
  }
  
  public double getFreezeMoney()
  {
    return this.freezeMoney;
  }
  
  public void setFreezeMoney(double freezeMoney)
  {
    this.freezeMoney = freezeMoney;
  }
  
  public double getDividendMoney()
  {
    return this.dividendMoney;
  }
  
  public void setDividendMoney(double dividendMoney)
  {
    this.dividendMoney = dividendMoney;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int type)
  {
    this.type = type;
  }
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int code)
  {
    this.code = code;
  }
  
  public double getLocatePoint()
  {
    return this.locatePoint;
  }
  
  public void setLocatePoint(double locatePoint)
  {
    this.locatePoint = locatePoint;
  }
  
  public double getNotLocatePoint()
  {
    return this.notLocatePoint;
  }
  
  public void setNotLocatePoint(double notLocatePoint)
  {
    this.notLocatePoint = notLocatePoint;
  }
  
  public double getExtraPoint()
  {
    return this.extraPoint;
  }
  
  public void setExtraPoint(double extraPoint)
  {
    this.extraPoint = extraPoint;
  }
  
  public String getRegistTime()
  {
    return this.registTime;
  }
  
  public void setRegistTime(String registTime)
  {
    this.registTime = registTime;
  }
  
  public String getLoginTime()
  {
    return this.loginTime;
  }
  
  public void setLoginTime(String loginTime)
  {
    this.loginTime = loginTime;
  }
  
  public int getAStatus()
  {
    return this.AStatus;
  }
  
  public void setAStatus(int aStatus)
  {
    this.AStatus = aStatus;
  }
  
  public int getBStatus()
  {
    return this.BStatus;
  }
  
  public void setBStatus(int bStatus)
  {
    this.BStatus = bStatus;
  }
  
  public int getOnlineStatus()
  {
    return this.onlineStatus;
  }
  
  public void setOnlineStatus(int onlineStatus)
  {
    this.onlineStatus = onlineStatus;
  }
  
  public int getIsCjZhaoShang()
  {
    return this.isCjZhaoShang;
  }
  
  public void setIsCjZhaoShang(int isCjZhaoShang)
  {
    this.isCjZhaoShang = isCjZhaoShang;
  }
}
