package lottery.domains.content.vo.lottery;

import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryPlayRules;
import lottery.domains.content.entity.LotteryPlayRulesConfig;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import lottery.domains.content.entity.LotteryType;
import lottery.domains.pool.LotteryDataFactory;

public class LotteryPlayRulesVO
{
  private String typeName;
  private String groupName;
  private String lotteryName;
  private int typeId;
  private int groupId;
  private int lotteryId;
  private int ruleId;
  private String name;
  private String code;
  private String minNum;
  private String maxNum;
  private String totalNum;
  private int status;
  private int fixed;
  private String prize;
  private String desc;
  private String dantiao;
  private int isLocate;
  
  public LotteryPlayRulesVO(LotteryPlayRules rule, LotteryPlayRulesConfig config, LotteryDataFactory dataFactory)
  {
    LotteryType lotteryType = dataFactory.getLotteryType(rule.getTypeId());
    if (lotteryType != null) {
      this.typeName = lotteryType.getName();
    }
    LotteryPlayRulesGroup lotteryPlayRulesGroup = dataFactory.getLotteryPlayRulesGroup(rule.getGroupId());
    if (lotteryPlayRulesGroup != null) {
      this.groupName = lotteryPlayRulesGroup.getName();
    }
    this.typeId = rule.getTypeId();
    this.groupId = rule.getGroupId();
    this.ruleId = rule.getId();
    this.name = rule.getName();
    this.code = rule.getCode();
    this.totalNum = rule.getTotalNum();
    this.fixed = rule.getFixed();
    this.desc = rule.getDesc();
    this.dantiao = rule.getDantiao();
    this.isLocate = rule.getIsLocate();
    if (config != null)
    {
      this.minNum = config.getMinNum();
      this.maxNum = config.getMaxNum();
      this.status = config.getStatus();
      this.prize = config.getPrize();
      
      Lottery lottery = dataFactory.getLottery(config.getLotteryId());
      if (lottery != null)
      {
        this.lotteryId = lottery.getId();
        this.lotteryName = lottery.getShowName();
      }
    }
    else
    {
      this.minNum = rule.getMinNum();
      this.maxNum = rule.getMaxNum();
      this.status = rule.getStatus();
      this.prize = rule.getPrize();
    }
  }
  
  public String getTypeName()
  {
    return this.typeName;
  }
  
  public void setTypeName(String typeName)
  {
    this.typeName = typeName;
  }
  
  public String getGroupName()
  {
    return this.groupName;
  }
  
  public void setGroupName(String groupName)
  {
    this.groupName = groupName;
  }
  
  public String getLotteryName()
  {
    return this.lotteryName;
  }
  
  public void setLotteryName(String lotteryName)
  {
    this.lotteryName = lotteryName;
  }
  
  public int getTypeId()
  {
    return this.typeId;
  }
  
  public void setTypeId(int typeId)
  {
    this.typeId = typeId;
  }
  
  public int getGroupId()
  {
    return this.groupId;
  }
  
  public void setGroupId(int groupId)
  {
    this.groupId = groupId;
  }
  
  public int getLotteryId()
  {
    return this.lotteryId;
  }
  
  public void setLotteryId(int lotteryId)
  {
    this.lotteryId = lotteryId;
  }
  
  public int getRuleId()
  {
    return this.ruleId;
  }
  
  public void setRuleId(int ruleId)
  {
    this.ruleId = ruleId;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public String getMinNum()
  {
    return this.minNum;
  }
  
  public void setMinNum(String minNum)
  {
    this.minNum = minNum;
  }
  
  public String getMaxNum()
  {
    return this.maxNum;
  }
  
  public void setMaxNum(String maxNum)
  {
    this.maxNum = maxNum;
  }
  
  public String getTotalNum()
  {
    return this.totalNum;
  }
  
  public void setTotalNum(String totalNum)
  {
    this.totalNum = totalNum;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public int getFixed()
  {
    return this.fixed;
  }
  
  public void setFixed(int fixed)
  {
    this.fixed = fixed;
  }
  
  public String getPrize()
  {
    return this.prize;
  }
  
  public void setPrize(String prize)
  {
    this.prize = prize;
  }
  
  public String getDesc()
  {
    return this.desc;
  }
  
  public void setDesc(String desc)
  {
    this.desc = desc;
  }
  
  public String getDantiao()
  {
    return this.dantiao;
  }
  
  public void setDantiao(String dantiao)
  {
    this.dantiao = dantiao;
  }
  
  public int getIsLocate()
  {
    return this.isLocate;
  }
  
  public void setIsLocate(int isLocate)
  {
    this.isLocate = isLocate;
  }
}
