package lottery.domains.content.entity;

import java.io.Serializable;
import javautils.date.Moment;
import javautils.math.MathUtil;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lottery.domains.content.api.ag.AGAPI;
import lottery.domains.content.api.ag.AGBetRecord;
import lottery.domains.content.api.pt.PTPlayerGameResult;
import lottery.domains.content.api.sb.Win88SBAPI;
import lottery.domains.content.api.sb.Win88SBSportBetLogResult;
//import lottery.domains.content.api.sb.Win88SBSportBetLogResult.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;

@Entity
@Table(name="game_bets", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"user_id", "platform_id", "bets_id", "game_code"})})
public class GameBets
  implements Serializable
{
  private int id;
  private int userId;
  private int platformId;
  private String betsId;
  private String gameCode;
  private String gameType;
  private String gameName;
  private double money;
  private double prizeMoney;
  private double progressiveMoney;
  private double progressivePrize;
  private double balance;
  private int status;
  private String time;
  private String prizeTime;
  private String ext1;
  private String ext2;
  private String ext3;
  
  public GameBets() {}
  
  public GameBets(PTPlayerGameResult result, UserGameAccount account)
  {
    setUserId(account.getUserId());
    setPlatformId(account.getPlatformId());
    setBetsId(result.getGAMEID());
    setGameCode(result.getGAMECODE());
    setGameType(result.getGAMETYPE());
    setGameName(result.getGAMENAME());
    setMoney(Double.valueOf(result.getBET()).doubleValue());
    setProgressiveMoney(Double.valueOf(result.getPROGRESSIVEBET()).doubleValue());
    if ((getMoney() > 0.0D) && (getProgressiveMoney() > 0.0D)) {
      setProgressiveMoney(0.0D);
    }
    setPrizeMoney(Double.valueOf(result.getWIN()).doubleValue());
    setProgressivePrize(Double.valueOf(result.getPROGRESSIVEWIN()).doubleValue());
    setBalance(Double.valueOf(result.getBALANCE()).doubleValue());
    setTime(result.getGAMEDATE());
    setPrizeTime(result.getGAMEDATE());
    setStatus(1);
  }
  
  public GameBets(AGBetRecord record, UserGameAccount account)
  {
    setUserId(account.getUserId());
    setPlatformId(account.getPlatformId());
    setBetsId(record.getBillNo());
    setGameCode(record.getGameCode());
    setGameType(AGAPI.transRound(record.getRound()));
    setGameName(AGAPI.transGameType(record.getGameType()));
    setMoney(Double.valueOf(record.getBetAmount()).doubleValue());
    
    record.getGameType();
    if (("BR".equals(record.getDataType())) || ("EBR".equals(record.getDataType())))
    {
      Double netAmount = Double.valueOf(record.getNetAmount());
      double prizeMoney = MathUtil.add(getMoney(), netAmount.doubleValue());
      setPrizeMoney(prizeMoney);
    }
    else
    {
      double prizeMoney = Double.valueOf(record.getNetAmount()).doubleValue();
      setPrizeMoney(prizeMoney);
    }
    setProgressiveMoney(0.0D);
    setProgressivePrize(0.0D);
    double beforeCredit = StringUtils.isEmpty(record.getBeforeCredit()) ? 0.0D : Double.valueOf(record.getBeforeCredit()).doubleValue();
    double balance = beforeCredit - getMoney() + this.prizeMoney;
    if (balance < 0.0D) {
      balance = 0.0D;
    }
    setBalance(balance);
    setStatus(1);
    setTime(record.getBetTime());
    setPrizeTime(record.getBetTime());
    if (getPrizeMoney() < 0.0D) {
      setPrizeMoney(0.0D);
    }
    if (getMoney() < 0.0D) {
      setMoney(0.0D);
    }
  }
  
//  public GameBets(Win88SBSportBetLogResult.Data record, UserGameAccount account)
//  {
//    setUserId(account.getUserId());
//    setPlatformId(13);
//    setBetsId(record.getTransId());
//    setGameCode(record.getMatchId());
//    if ("29".equals(record.getBetType()))
//    {
//      setGameType("串关");
//      setGameName("混合赛事");
//    }
//    else
//    {
//      setGameType(Win88SBAPI.transSportType(record.getSportType()));
//      setGameName(StringUtils.isEmpty(record.getLeagueName()) ? "未知" : record.getLeagueName());
//    }
//    setMoney(Double.valueOf(record.getStake()).doubleValue());
//
//    Double winLoseAmount = Double.valueOf(record.getWinLoseAmount());
//    double prizeMoney = MathUtil.add(getMoney(), winLoseAmount.doubleValue());
//    setPrizeMoney(prizeMoney);
//
//    setProgressiveMoney(0.0D);
//    setProgressivePrize(0.0D);
//    setBalance(0.0D);
//    setStatus(Win88SBAPI.transTicketStatus(record.getTicketStatus()));
//
//    String transactionTime = record.getTransactionTime();
//    transactionTime = transactionTime.replaceAll("T", " ");
//    if (transactionTime.lastIndexOf(".") > -1) {
//      transactionTime = transactionTime.substring(0, transactionTime.lastIndexOf("."));
//    }
//    transactionTime = new Moment().fromTime(transactionTime).add(12, "hours").toSimpleTime();
//    setTime(transactionTime);
//    if (StringUtils.isNotEmpty(record.getWinLostDateTime()))
//    {
//      String winLostDateTime = record.getWinLostDateTime();
//
//      winLostDateTime = winLostDateTime.replaceAll("T", " ");
//      if (winLostDateTime.lastIndexOf(".") > -1) {
//        winLostDateTime = winLostDateTime.substring(0, winLostDateTime.lastIndexOf("."));
//      }
//      winLostDateTime = new Moment().fromTime(winLostDateTime).add(12, "hours").toSimpleTime();
//
//      setPrizeTime(winLostDateTime);
//    }
//    setExt1(record.getVersionKey());
//  }
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id", unique=true, nullable=false)
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  @Column(name="user_id", nullable=false)
  public int getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(int userId)
  {
    this.userId = userId;
  }
  
  @Column(name="platform_id", nullable=false)
  public int getPlatformId()
  {
    return this.platformId;
  }
  
  public void setPlatformId(int platformId)
  {
    this.platformId = platformId;
  }
  
  @Column(name="bets_id", nullable=false, length=128)
  public String getBetsId()
  {
    return this.betsId;
  }
  
  public void setBetsId(String betsId)
  {
    this.betsId = betsId;
  }
  
  @Column(name="game_code", nullable=false, length=128)
  public String getGameCode()
  {
    return this.gameCode;
  }
  
  public void setGameCode(String gameCode)
  {
    this.gameCode = gameCode;
  }
  
  @Column(name="game_type", nullable=false, length=128)
  public String getGameType()
  {
    return this.gameType;
  }
  
  public void setGameType(String gameType)
  {
    this.gameType = gameType;
  }
  
  @Column(name="game_name", nullable=false, length=128)
  public String getGameName()
  {
    return this.gameName;
  }
  
  public void setGameName(String gameName)
  {
    this.gameName = gameName;
  }
  
  @Column(name="money", nullable=false, precision=12, scale=4)
  public double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(double money)
  {
    this.money = money;
  }
  
  @Column(name="prize_money", nullable=false, precision=12, scale=4)
  public double getPrizeMoney()
  {
    return this.prizeMoney;
  }
  
  public void setPrizeMoney(double prizeMoney)
  {
    this.prizeMoney = prizeMoney;
  }
  
  @Column(name="progressive_money", precision=12, scale=4)
  public double getProgressiveMoney()
  {
    return this.progressiveMoney;
  }
  
  public void setProgressiveMoney(double progressiveMoney)
  {
    this.progressiveMoney = progressiveMoney;
  }
  
  @Column(name="progressive_prize", precision=12, scale=4)
  public double getProgressivePrize()
  {
    return this.progressivePrize;
  }
  
  public void setProgressivePrize(double progressivePrize)
  {
    this.progressivePrize = progressivePrize;
  }
  
  @Column(name="balance", precision=12, scale=4)
  public double getBalance()
  {
    return this.balance;
  }
  
  public void setBalance(double balance)
  {
    this.balance = balance;
  }
  
  @Column(name="status")
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  @Column(name="time", nullable=false, length=50)
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
  
  @Column(name="prize_time", length=50)
  public String getPrizeTime()
  {
    return this.prizeTime;
  }
  
  public void setPrizeTime(String prizeTime)
  {
    this.prizeTime = prizeTime;
  }
  
  @Column(name="ext1", length=128)
  public String getExt1()
  {
    return this.ext1;
  }
  
  public void setExt1(String ext1)
  {
    this.ext1 = ext1;
  }
  
  @Column(name="ext2", length=128)
  public String getExt2()
  {
    return this.ext2;
  }
  
  public void setExt2(String ext2)
  {
    this.ext2 = ext2;
  }
  
  @Column(name="ext3", length=128)
  public String getExt3()
  {
    return this.ext3;
  }
  
  public void setExt3(String ext3)
  {
    this.ext3 = ext3;
  }
  
  public boolean equals(Object obj)
  {
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof GameBets)) {
      return false;
    }
    GameBets other = (GameBets)obj;
    
    EqualsBuilder builder = new EqualsBuilder();
    return builder
      .append(getUserId(), other.getUserId())
      .append(getPlatformId(), other.getPlatformId())
      .append(getBetsId(), other.getBetsId())
      .append(getGameCode(), other.getGameCode())
      .append(getGameType(), other.getGameType())
      .append(getGameName(), other.getGameName())
      .append(getMoney(), other.getMoney())
      .append(getPrizeMoney(), other.getPrizeMoney())
      .append(getProgressiveMoney(), other.getProgressiveMoney())
      .append(getProgressivePrize(), other.getProgressivePrize())
      .append(getBalance(), other.getBalance())
      .append(getStatus(), other.getStatus())
      .append(getTime(), other.getTime())
      .append(getPrizeTime(), other.getPrizeTime())
      .append(getExt1(), other.getExt1())
      .append(getExt2(), other.getExt2())
      .append(getExt3(), other.getExt3())
      .isEquals();
  }
}
