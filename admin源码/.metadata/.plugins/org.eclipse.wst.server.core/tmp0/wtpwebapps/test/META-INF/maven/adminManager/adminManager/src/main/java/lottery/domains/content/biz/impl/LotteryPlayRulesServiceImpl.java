package lottery.domains.content.biz.impl;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.StringUtils;
import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.entity.LotteryType;
import lottery.domains.content.vo.lottery.LotteryPlayRulesSimpleVO;
import java.util.Iterator;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryPlayRulesConfig;
import lottery.domains.content.entity.LotteryPlayRules;
import java.util.ArrayList;
import lottery.domains.content.vo.lottery.LotteryPlayRulesVO;
import java.util.List;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.LotteryPlayRulesConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.LotteryPlayRulesDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.LotteryPlayRulesService;

@Service
public class LotteryPlayRulesServiceImpl implements LotteryPlayRulesService
{
    @Autowired
    private LotteryPlayRulesDao rulesDao;
    @Autowired
    private LotteryPlayRulesConfigDao configDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Override
    public List<LotteryPlayRulesVO> list(final int lotteryId, final Integer groupId) {
        final Lottery lottery = this.lotteryDataFactory.getLottery(lotteryId);
        if (lottery == null) {
            return new ArrayList<LotteryPlayRulesVO>();
        }
        List<LotteryPlayRules> rules;
        List<LotteryPlayRulesConfig> configs;
        if (groupId == null) {
            rules = this.rulesDao.listByType(lottery.getType());
            configs = this.configDao.listByLottery(lotteryId);
        }
        else {
            rules = this.rulesDao.listByTypeAndGroup(lottery.getType(), groupId);
            final List<Integer> ruleIds = new ArrayList<Integer>();
            for (final LotteryPlayRules rule : rules) {
                ruleIds.add(rule.getId());
            }
            configs = this.configDao.listByLotteryAndRule(lotteryId, ruleIds);
        }
        final List<LotteryPlayRulesVO> results = new ArrayList<LotteryPlayRulesVO>();
        for (final LotteryPlayRules rule : rules) {
            if (groupId != null && rule.getGroupId() != groupId) {
                continue;
            }
            LotteryPlayRulesConfig config = null;
            for (final LotteryPlayRulesConfig rulesConfig : configs) {
                if (rulesConfig.getRuleId() == rule.getId()) {
                    config = rulesConfig;
                    break;
                }
            }
            final LotteryPlayRulesVO vo = new LotteryPlayRulesVO(rule, config, this.lotteryDataFactory);
            if (config == null) {
                vo.setLotteryName(lottery.getShowName());
                vo.setLotteryId(lotteryId);
            }
            results.add(vo);
        }
        return results;
    }
    
    @Override
    public List<LotteryPlayRulesSimpleVO> listSimple(final int typeId, final Integer groupId) {
        final LotteryType lotteryType = this.lotteryDataFactory.getLotteryType(typeId);
        if (lotteryType == null) {
            return new ArrayList<LotteryPlayRulesSimpleVO>();
        }
        List<LotteryPlayRules> rules;
        if (groupId == null) {
            rules = this.rulesDao.listByType(typeId);
        }
        else {
            rules = this.rulesDao.listByTypeAndGroup(typeId, groupId);
        }
        final List<LotteryPlayRulesSimpleVO> results = new ArrayList<LotteryPlayRulesSimpleVO>();
        for (final LotteryPlayRules rule : rules) {
            if (groupId != null && rule.getGroupId() != groupId) {
                continue;
            }
            final LotteryPlayRulesSimpleVO vo = new LotteryPlayRulesSimpleVO(rule, this.lotteryDataFactory);
            results.add(vo);
        }
        return results;
    }
    
    @Override
    public LotteryPlayRulesVO get(final int lotteryId, final int ruleId) {
        final Lottery lottery = this.lotteryDataFactory.getLottery(lotteryId);
        if (lottery == null) {
            return null;
        }
        final LotteryPlayRules rule = this.rulesDao.getById(ruleId);
        if (rule == null) {
            return null;
        }
        final LotteryPlayRulesConfig config = this.configDao.get(lotteryId, ruleId);
        final LotteryPlayRulesVO result = new LotteryPlayRulesVO(rule, config, this.lotteryDataFactory);
        if (config == null) {
            result.setLotteryName(lottery.getShowName());
            result.setLotteryId(lotteryId);
        }
        return result;
    }
    
    @Override
    public boolean edit(final int ruleId, final Integer lotteryId, final String minNum, final String maxNum) {
        if (!this.checkEditParams(ruleId, minNum, maxNum)) {
            return false;
        }
        if (lotteryId != null) {
            LotteryPlayRulesConfig config = this.configDao.get(lotteryId, ruleId);
            if (config == null) {
                final LotteryPlayRules rule = this.rulesDao.getById(ruleId);
                if (rule == null) {
                    return false;
                }
                config = new LotteryPlayRulesConfig();
                config.setRuleId(ruleId);
                config.setLotteryId(lotteryId);
                config.setMinNum(minNum);
                config.setMaxNum(maxNum);
                config.setStatus(rule.getStatus());
                config.setPrize(rule.getPrize());
                this.configDao.save(config);
            }
            else {
                config.setMinNum(minNum);
                config.setMaxNum(maxNum);
                this.configDao.update(config);
            }
        }
        else {
            this.rulesDao.update(ruleId, minNum, maxNum);
            this.configDao.update(ruleId, minNum, maxNum);
        }
        this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY_PLAY_RULES);
        return true;
    }
    
    private boolean checkEditParams(final int ruleId, final String minNum, final String maxNum) {
        if (StringUtils.isEmpty(minNum) || StringUtils.isEmpty(maxNum)) {
            return false;
        }
        final LotteryPlayRules originalRule = this.rulesDao.getById(ruleId);
        if (originalRule == null) {
            return false;
        }
        final String[] minNums = minNum.split(",");
        final String[] maxNums = maxNum.split(",");
        String[] array;
        for (int length = (array = minNums).length, i = 0; i < length; ++i) {
            final String num = array[i];
            if (!NumberUtils.isDigits(num) || Integer.valueOf(num) < 0) {
                return false;
            }
        }
        String[] array2;
        for (int length2 = (array2 = maxNums).length, j = 0; j < length2; ++j) {
            final String num = array2[j];
            if (!NumberUtils.isDigits(num) || Integer.valueOf(num) < 0) {
                return false;
            }
        }
        final String[] originalMinNums = originalRule.getMinNum().split(",");
        final String[] originalMaxNums = originalRule.getMaxNum().split(",");
        return minNums.length == originalMinNums.length && maxNums.length == originalMaxNums.length;
    }
    
    @Override
    public boolean updateStatus(final int ruleId, final Integer lotteryId, final boolean enable) {
        final int _status = enable ? 0 : -1;
        if (lotteryId != null) {
            LotteryPlayRulesConfig config = this.configDao.get(lotteryId, ruleId);
            if (config == null) {
                final LotteryPlayRules rule = this.rulesDao.getById(ruleId);
                if (rule == null) {
                    return false;
                }
                config = new LotteryPlayRulesConfig();
                config.setRuleId(ruleId);
                config.setLotteryId(lotteryId);
                config.setMinNum(rule.getMinNum());
                config.setMaxNum(rule.getMaxNum());
                config.setStatus(_status);
                config.setPrize(rule.getPrize());
                this.configDao.save(config);
            }
            else {
                this.configDao.updateStatus(lotteryId, ruleId, _status);
            }
        }
        else {
            this.rulesDao.updateStatus(ruleId, _status);
            this.configDao.updateStatus(ruleId, _status);
        }
        this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY_PLAY_RULES);
        return true;
    }
}
