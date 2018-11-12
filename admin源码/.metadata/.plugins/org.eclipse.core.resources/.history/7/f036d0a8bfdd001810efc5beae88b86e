package lottery.domains.content.biz.impl;

import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryPlayRulesGroupConfig;
import lottery.domains.content.vo.lottery.LotteryPlayRulesGroupVO;
import java.util.Iterator;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import lottery.domains.content.vo.lottery.LotteryPlayRulesGroupSimpleVO;
import java.util.List;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.LotteryPlayRulesGroupConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.LotteryPlayRulesGroupDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.LotteryPlayRulesGroupService;

@Service
public class LotteryPlayRulesGroupServiceImpl implements LotteryPlayRulesGroupService
{
    @Autowired
    private LotteryPlayRulesGroupDao groupDao;
    @Autowired
    private LotteryPlayRulesGroupConfigDao configDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Override
    public List<LotteryPlayRulesGroupSimpleVO> listSimpleByType(final int typeId) {
        final List<LotteryPlayRulesGroup> groups = this.groupDao.listByType(typeId);
        if (CollectionUtils.isEmpty((Collection)groups)) {
            return new ArrayList<LotteryPlayRulesGroupSimpleVO>();
        }
        final List<LotteryPlayRulesGroupSimpleVO> simpleVOS = new ArrayList<LotteryPlayRulesGroupSimpleVO>();
        for (final LotteryPlayRulesGroup group : groups) {
            simpleVOS.add(new LotteryPlayRulesGroupSimpleVO(group, this.lotteryDataFactory));
        }
        return simpleVOS;
    }
    
    @Override
    public List<LotteryPlayRulesGroupVO> list(final int lotteryId) {
        final Lottery lottery = this.lotteryDataFactory.getLottery(lotteryId);
        if (lottery == null) {
            return new ArrayList<LotteryPlayRulesGroupVO>();
        }
        final List<LotteryPlayRulesGroup> groups = this.groupDao.listByType(lottery.getType());
        final List<LotteryPlayRulesGroupConfig> configs = this.configDao.listByLottery(lotteryId);
        final List<LotteryPlayRulesGroupVO> results = new ArrayList<LotteryPlayRulesGroupVO>();
        for (final LotteryPlayRulesGroup group : groups) {
            LotteryPlayRulesGroupConfig config = null;
            for (final LotteryPlayRulesGroupConfig groupConfig : configs) {
                if (groupConfig.getGroupId() == group.getId()) {
                    config = groupConfig;
                    break;
                }
            }
            final LotteryPlayRulesGroupVO vo = new LotteryPlayRulesGroupVO(group, config, this.lotteryDataFactory);
            if (config == null) {
                vo.setLotteryName(lottery.getShowName());
                vo.setLotteryId(lotteryId);
            }
            results.add(vo);
        }
        return results;
    }
    
    @Override
    public boolean updateStatus(final int groupId, final Integer lotteryId, final boolean enable) {
        final int _status = enable ? 0 : -1;
        if (lotteryId != null) {
            LotteryPlayRulesGroupConfig config = this.configDao.get(lotteryId, groupId);
            if (config == null) {
                config = new LotteryPlayRulesGroupConfig();
                config.setGroupId(groupId);
                config.setLotteryId(lotteryId);
                config.setStatus(_status);
                this.configDao.save(config);
            }
            else {
                this.configDao.updateStatus(lotteryId, groupId, _status);
            }
        }
        else {
            this.groupDao.updateStatus(groupId, _status);
            this.configDao.updateStatus(groupId, _status);
        }
        this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY_PLAY_RULES);
        return true;
    }
}
