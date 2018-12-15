package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import lottery.domains.content.biz.LotteryPlayRulesGroupService;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.LotteryPlayRulesGroupConfigDao;
import lottery.domains.content.dao.LotteryPlayRulesGroupDao;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import lottery.domains.content.entity.LotteryPlayRulesGroupConfig;
import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.vo.lottery.LotteryPlayRulesGroupSimpleVO;
import lottery.domains.content.vo.lottery.LotteryPlayRulesGroupVO;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryPlayRulesGroupServiceImpl
  implements LotteryPlayRulesGroupService
{
  @Autowired
  private LotteryPlayRulesGroupDao groupDao;
  @Autowired
  private LotteryPlayRulesGroupConfigDao configDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  
  public List<LotteryPlayRulesGroupSimpleVO> listSimpleByType(int typeId)
  {
    List<LotteryPlayRulesGroup> groups = this.groupDao.listByType(typeId);
    if (CollectionUtils.isEmpty(groups)) {
      return new ArrayList();
    }
    List<LotteryPlayRulesGroupSimpleVO> simpleVOS = new ArrayList();
    for (LotteryPlayRulesGroup group : groups) {
      simpleVOS.add(new LotteryPlayRulesGroupSimpleVO(group, this.lotteryDataFactory));
    }
    return simpleVOS;
  }
  
  public List<LotteryPlayRulesGroupVO> list(int lotteryId)
  {
    Lottery lottery = this.lotteryDataFactory.getLottery(lotteryId);
    if (lottery == null) {
      return new ArrayList();
    }
    List<LotteryPlayRulesGroup> groups = this.groupDao.listByType(lottery.getType());
    List<LotteryPlayRulesGroupConfig> configs = this.configDao.listByLottery(lotteryId);
    
    List<LotteryPlayRulesGroupVO> results = new ArrayList();
    for (LotteryPlayRulesGroup group : groups)
    {
      LotteryPlayRulesGroupConfig config = null;
      for (LotteryPlayRulesGroupConfig groupConfig : configs) {
        if (groupConfig.getGroupId() == group.getId())
        {
          config = groupConfig;
          break;
        }
      }
      LotteryPlayRulesGroupVO vo = new LotteryPlayRulesGroupVO(group, config, this.lotteryDataFactory);
      if (config == null)
      {
        vo.setLotteryName(lottery.getShowName());
        vo.setLotteryId(lotteryId);
      }
      results.add(vo);
    }
    return results;
  }
  
  public boolean updateStatus(int groupId, Integer lotteryId, boolean enable)
  {
    int _status = enable ? 0 : -1;
    if (lotteryId != null)
    {
      LotteryPlayRulesGroupConfig config = this.configDao.get(lotteryId.intValue(), groupId);
      if (config == null)
      {
        config = new LotteryPlayRulesGroupConfig();
        config.setGroupId(groupId);
        config.setLotteryId(lotteryId.intValue());
        config.setStatus(_status);
        this.configDao.save(config);
      }
      else
      {
        this.configDao.updateStatus(lotteryId.intValue(), groupId, _status);
      }
    }
    else
    {
      this.groupDao.updateStatus(groupId, _status);
      this.configDao.updateStatus(groupId, _status);
    }
    this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY_PLAY_RULES);
    
    return true;
  }
}
