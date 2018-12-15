package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import lottery.domains.content.biz.LotteryPlayRulesService;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.LotteryPlayRulesConfigDao;
import lottery.domains.content.dao.LotteryPlayRulesDao;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryPlayRules;
import lottery.domains.content.entity.LotteryPlayRulesConfig;
import lottery.domains.content.entity.LotteryType;
import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.vo.lottery.LotteryPlayRulesSimpleVO;
import lottery.domains.content.vo.lottery.LotteryPlayRulesVO;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryPlayRulesServiceImpl
  implements LotteryPlayRulesService
{
  @Autowired
  private LotteryPlayRulesDao rulesDao;
  @Autowired
  private LotteryPlayRulesConfigDao configDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  
  public List<LotteryPlayRulesVO> list(int lotteryId, Integer groupId)
  {
    Lottery lottery = this.lotteryDataFactory.getLottery(lotteryId);
    if (lottery == null) {
      return new ArrayList();
    }
    List<LotteryPlayRulesConfig> configs;
    List<LotteryPlayRules> rules;
    if (groupId == null)
    {
      rules = this.rulesDao.listByType(lottery.getType());
      configs = this.configDao.listByLottery(lotteryId);
    }
    else
    {
      rules = this.rulesDao.listByTypeAndGroup(lottery.getType(), groupId.intValue());
      List<Integer> ruleIds = new ArrayList();
      for (LotteryPlayRules rule : rules) {
        ruleIds.add(Integer.valueOf(rule.getId()));
      }
      configs = this.configDao.listByLotteryAndRule(lotteryId, ruleIds);
    }
    List<LotteryPlayRulesVO> results = new ArrayList();
    for (LotteryPlayRules rule : rules) {
      if ((groupId == null) || (rule.getGroupId() == groupId.intValue()))
      {
        LotteryPlayRulesConfig config = null;
        for (LotteryPlayRulesConfig rulesConfig : configs) {
          if (rulesConfig.getRuleId() == rule.getId())
          {
            config = rulesConfig;
            break;
          }
        }
        LotteryPlayRulesVO vo = new LotteryPlayRulesVO(rule, config, this.lotteryDataFactory);
        if (config == null)
        {
          vo.setLotteryName(lottery.getShowName());
          vo.setLotteryId(lotteryId);
        }
        results.add(vo);
      }
    }
    return results;
  }
  
  public List<LotteryPlayRulesSimpleVO> listSimple(int typeId, Integer groupId)
  {
    LotteryType lotteryType = this.lotteryDataFactory.getLotteryType(typeId);
    if (lotteryType == null) {
      return new ArrayList();
    }
    List<LotteryPlayRules> rules;
    if (groupId == null) {
      rules = this.rulesDao.listByType(typeId);
    } else {
      rules = this.rulesDao.listByTypeAndGroup(typeId, groupId.intValue());
    }
    List<LotteryPlayRulesSimpleVO> results = new ArrayList();
    for (LotteryPlayRules rule : rules) {
      if ((groupId == null) || (rule.getGroupId() == groupId.intValue()))
      {
        LotteryPlayRulesSimpleVO vo = new LotteryPlayRulesSimpleVO(rule, this.lotteryDataFactory);
        results.add(vo);
      }
    }
    return results;
  }
  
  public LotteryPlayRulesVO get(int lotteryId, int ruleId)
  {
    Lottery lottery = this.lotteryDataFactory.getLottery(lotteryId);
    if (lottery == null) {
      return null;
    }
    LotteryPlayRules rule = this.rulesDao.getById(ruleId);
    if (rule == null) {
      return null;
    }
    LotteryPlayRulesConfig config = this.configDao.get(lotteryId, ruleId);
    
    LotteryPlayRulesVO result = new LotteryPlayRulesVO(rule, config, this.lotteryDataFactory);
    if (config == null)
    {
      result.setLotteryName(lottery.getShowName());
      result.setLotteryId(lotteryId);
    }
    return result;
  }
  
  public boolean edit(int ruleId, Integer lotteryId, String minNum, String maxNum)
  {
    if (!checkEditParams(ruleId, minNum, maxNum)) {
      return false;
    }
    if (lotteryId != null)
    {
      LotteryPlayRulesConfig config = this.configDao.get(lotteryId.intValue(), ruleId);
      if (config == null)
      {
        LotteryPlayRules rule = this.rulesDao.getById(ruleId);
        if (rule == null) {
          return false;
        }
        config = new LotteryPlayRulesConfig();
        config.setRuleId(ruleId);
        config.setLotteryId(lotteryId.intValue());
        config.setMinNum(minNum);
        config.setMaxNum(maxNum);
        config.setStatus(rule.getStatus());
        config.setPrize(rule.getPrize());
        this.configDao.save(config);
      }
      else
      {
        config.setMinNum(minNum);
        config.setMaxNum(maxNum);
        this.configDao.update(config);
      }
    }
    else
    {
      this.rulesDao.update(ruleId, minNum, maxNum);
      this.configDao.update(ruleId, minNum, maxNum);
    }
    this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY_PLAY_RULES);
    
    return true;
  }
  
  private boolean checkEditParams(int ruleId, String minNum, String maxNum)
  {
    if ((StringUtils.isEmpty(minNum)) || (StringUtils.isEmpty(maxNum))) {
      return false;
    }
    LotteryPlayRules originalRule = this.rulesDao.getById(ruleId);
    if (originalRule == null) {
      return false;
    }
    String[] minNums = minNum.split(",");
    String[] maxNums = maxNum.split(",");
    String[] arrayOfString1;
    int j = (arrayOfString1 = minNums).length;
    for (int i = 0; i < j; i++)
    {
      String num = arrayOfString1[i];
      if ((!NumberUtils.isDigits(num)) || (Integer.valueOf(num).intValue() < 0)) {
        return false;
      }
    }
    j = (arrayOfString1 = maxNums).length;
    for (int i = 0; i < j; i++)
    {
      String num = arrayOfString1[i];
      if ((!NumberUtils.isDigits(num)) || (Integer.valueOf(num).intValue() < 0)) {
        return false;
      }
    }
    String[] originalMinNums = originalRule.getMinNum().split(",");
    String[] originalMaxNums = originalRule.getMaxNum().split(",");
    if (minNums.length != originalMinNums.length) {
      return false;
    }
    if (maxNums.length != originalMaxNums.length) {
      return false;
    }
    return true;
  }
  
  public boolean updateStatus(int ruleId, Integer lotteryId, boolean enable)
  {
    int _status = enable ? 0 : -1;
    if (lotteryId != null)
    {
      LotteryPlayRulesConfig config = this.configDao.get(lotteryId.intValue(), ruleId);
      if (config == null)
      {
        LotteryPlayRules rule = this.rulesDao.getById(ruleId);
        if (rule == null) {
          return false;
        }
        config = new LotteryPlayRulesConfig();
        config.setRuleId(ruleId);
        config.setLotteryId(lotteryId.intValue());
        config.setMinNum(rule.getMinNum());
        config.setMaxNum(rule.getMaxNum());
        config.setStatus(_status);
        config.setPrize(rule.getPrize());
        this.configDao.save(config);
      }
      else
      {
        this.configDao.updateStatus(lotteryId.intValue(), ruleId, _status);
      }
    }
    else
    {
      this.rulesDao.updateStatus(ruleId, _status);
      this.configDao.updateStatus(ruleId, _status);
    }
    this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY_PLAY_RULES);
    
    return true;
  }
}
