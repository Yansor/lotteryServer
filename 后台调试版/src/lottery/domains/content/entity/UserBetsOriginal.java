package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_bets_original", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"billno", "user_id"})})
public class UserBetsOriginal
  implements Serializable, Cloneable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private String billno;
  private int userId;
  private int type;
  private int lotteryId;
  private String expect;
  private int ruleId;
  private String codes;
  private int nums;
  private String model;
  private int multiple;
  private int code;
  private double point;
  private double money;
  private String time;
  private String stopTime;
  private String openTime;
  private int status;
  private String openCode;
  private Double prizeMoney;
  private String prizeTime;
  private String chaseBillno;
  private Integer chaseStop;
  private String planBillno;
  private Double rewardMoney;
  private String identification;
  private String certification;
  private int compressed;
  
  public UserBetsOriginal clone()
  {
    try
    {
      return (UserBetsOriginal)super.clone();
    }
    catch (Exception localException) {}
    return null;
  }
  
  public UserBetsOriginal() {}
  
  public UserBetsOriginal(String billno, int userId, int type, int lotteryId, String expect, int ruleId, String codes, int nums, String model, int multiple, int code, double point, double money, String time, String stopTime, String openTime, int status)
  {
    this.billno = billno;
    this.userId = userId;
    this.type = type;
    this.lotteryId = lotteryId;
    this.expect = expect;
    this.ruleId = ruleId;
    this.codes = codes;
    this.nums = nums;
    this.model = model;
    this.multiple = multiple;
    this.code = code;
    this.point = point;
    this.money = money;
    this.time = time;
    this.stopTime = stopTime;
    this.openTime = openTime;
    this.status = status;
  }
  
  public UserBetsOriginal(String billno, int userId, int type, int lotteryId, String expect, int ruleId, String codes, int nums, String model, int multiple, int code, double point, double money, String time, String stopTime, String openTime, int status, String openCode, Double prizeMoney, String prizeTime, String chaseBillno, Integer chaseStop, String planBillno, Double rewardMoney)
  {
    this.billno = billno;
    this.userId = userId;
    this.type = type;
    this.lotteryId = lotteryId;
    this.expect = expect;
    this.ruleId = ruleId;
    this.codes = codes;
    this.nums = nums;
    this.model = model;
    this.multiple = multiple;
    this.code = code;
    this.point = point;
    this.money = money;
    this.time = time;
    this.stopTime = stopTime;
    this.openTime = openTime;
    this.status = status;
    this.openCode = openCode;
    this.prizeMoney = prizeMoney;
    this.prizeTime = prizeTime;
    this.chaseBillno = chaseBillno;
    this.chaseStop = chaseStop;
    this.planBillno = planBillno;
    this.rewardMoney = rewardMoney;
  }
  
  @Id
  @Column(name="id", unique=true, nullable=false)
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  @Column(name="billno", nullable=false, length=32, updatable=false)
  public String getBillno()
  {
    return this.billno;
  }
  
  public void setBillno(String billno)
  {
    this.billno = billno;
  }
  
  @Column(name="user_id", nullable=false, updatable=false)
  public int getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(int userId)
  {
    this.userId = userId;
  }
  
  @Column(name="type", nullable=false, updatable=false)
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int type)
  {
    this.type = type;
  }
  
  @Column(name="lottery_id", nullable=false, updatable=false)
  public int getLotteryId()
  {
    return this.lotteryId;
  }
  
  public void setLotteryId(int lotteryId)
  {
    this.lotteryId = lotteryId;
  }
  
  @Column(name="expect", nullable=false, length=32, updatable=false)
  public String getExpect()
  {
    return this.expect;
  }
  
  public void setExpect(String expect)
  {
    this.expect = expect;
  }
  
  @Column(name="rule_id", nullable=false, updatable=false)
  public int getRuleId()
  {
    return this.ruleId;
  }
  
  public void setRuleId(int ruleId)
  {
    this.ruleId = ruleId;
  }
  
  @Column(name="codes", nullable=false, length=16777215, updatable=false)
  public String getCodes()
  {
    return this.codes;
  }
  
  public void setCodes(String codes)
  {
    this.codes = codes;
  }
  
  @Column(name="nums", nullable=false, updatable=false)
  public int getNums()
  {
    return this.nums;
  }
  
  public void setNums(int nums)
  {
    this.nums = nums;
  }
  
  @Column(name="model", nullable=false, length=16, updatable=false)
  public String getModel()
  {
    return this.model;
  }
  
  public void setModel(String model)
  {
    this.model = model;
  }
  
  @Column(name="multiple", nullable=false, updatable=false)
  public int getMultiple()
  {
    return this.multiple;
  }
  
  public void setMultiple(int multiple)
  {
    this.multiple = multiple;
  }
  
  @Column(name="code", nullable=false, updatable=false)
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int code)
  {
    this.code = code;
  }
  
  @Column(name="point", nullable=false, precision=11, scale=1, updatable=false)
  public double getPoint()
  {
    return this.point;
  }
  
  public void setPoint(double point)
  {
    this.point = point;
  }
  
  @Column(name="money", nullable=false, precision=16, scale=5, updatable=false)
  public double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(double money)
  {
    this.money = money;
  }
  
  @Column(name="time", nullable=false, length=19, updatable=false)
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
  
  @Column(name="stop_time", nullable=false, length=19, updatable=false)
  public String getStopTime()
  {
    return this.stopTime;
  }
  
  public void setStopTime(String stopTime)
  {
    this.stopTime = stopTime;
  }
  
  @Column(name="open_time", nullable=false, length=19, updatable=false)
  public String getOpenTime()
  {
    return this.openTime;
  }
  
  public void setOpenTime(String openTime)
  {
    this.openTime = openTime;
  }
  
  @Column(name="status", nullable=false, updatable=false)
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  @Column(name="open_code", length=128, updatable=false)
  public String getOpenCode()
  {
    return this.openCode;
  }
  
  public void setOpenCode(String openCode)
  {
    this.openCode = openCode;
  }
  
  @Column(name="prize_money", precision=16, scale=5, updatable=false)
  public Double getPrizeMoney()
  {
    return this.prizeMoney;
  }
  
  public void setPrizeMoney(Double prizeMoney)
  {
    this.prizeMoney = prizeMoney;
  }
  
  @Column(name="prize_time", length=19, updatable=false)
  public String getPrizeTime()
  {
    return this.prizeTime;
  }
  
  public void setPrizeTime(String prizeTime)
  {
    this.prizeTime = prizeTime;
  }
  
  @Column(name="chase_billno", length=64, updatable=false)
  public String getChaseBillno()
  {
    return this.chaseBillno;
  }
  
  public void setChaseBillno(String chaseBillno)
  {
    this.chaseBillno = chaseBillno;
  }
  
  @Column(name="chase_stop", updatable=false)
  public Integer getChaseStop()
  {
    return this.chaseStop;
  }
  
  public void setChaseStop(Integer chaseStop)
  {
    this.chaseStop = chaseStop;
  }
  
  @Column(name="plan_billno", length=64, updatable=false)
  public String getPlanBillno()
  {
    return this.planBillno;
  }
  
  public void setPlanBillno(String planBillno)
  {
    this.planBillno = planBillno;
  }
  
  @Column(name="reward_money", precision=16, scale=5, updatable=false)
  public Double getRewardMoney()
  {
    return this.rewardMoney;
  }
  
  public void setRewardMoney(Double rewardMoney)
  {
    this.rewardMoney = rewardMoney;
  }
  
  @Column(name="identification", length=128, nullable=false)
  public String getIdentification()
  {
    return this.identification;
  }
  
  public void setIdentification(String identification)
  {
    this.identification = identification;
  }
  
  @Column(name="certification", length=128, nullable=false)
  public String getCertification()
  {
    return this.certification;
  }
  
  public void setCertification(String certification)
  {
    this.certification = certification;
  }
  
  @Column(name="compressed")
  public int getCompressed()
  {
    return this.compressed;
  }
  
  public void setCompressed(int compressed)
  {
    this.compressed = compressed;
  }
}
