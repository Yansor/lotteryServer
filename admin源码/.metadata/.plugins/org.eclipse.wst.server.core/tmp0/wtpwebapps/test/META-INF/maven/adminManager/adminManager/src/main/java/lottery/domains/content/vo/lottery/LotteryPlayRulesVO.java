package lottery.domains.content.vo.lottery;

import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import lottery.domains.content.entity.LotteryType;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.LotteryPlayRulesConfig;
import lottery.domains.content.entity.LotteryPlayRules;

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
    
    public LotteryPlayRulesVO(final LotteryPlayRules rule, final LotteryPlayRulesConfig config, final LotteryDataFactory dataFactory) {
        final LotteryType lotteryType = dataFactory.getLotteryType(rule.getTypeId());
        if (lotteryType != null) {
            this.typeName = lotteryType.getName();
        }
        final LotteryPlayRulesGroup lotteryPlayRulesGroup = dataFactory.getLotteryPlayRulesGroup(rule.getGroupId());
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
        if (config != null) {
            this.minNum = config.getMinNum();
            this.maxNum = config.getMaxNum();
            this.status = config.getStatus();
            this.prize = config.getPrize();
            final Lottery lottery = dataFactory.getLottery(config.getLotteryId());
            if (lottery != null) {
                this.lotteryId = lottery.getId();
                this.lotteryName = lottery.getShowName();
            }
        }
        else {
            this.minNum = rule.getMinNum();
            this.maxNum = rule.getMaxNum();
            this.status = rule.getStatus();
            this.prize = rule.getPrize();
        }
    }
    
    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }
    
    public String getGroupName() {
        return this.groupName;
    }
    
    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }
    
    public String getLotteryName() {
        return this.lotteryName;
    }
    
    public void setLotteryName(final String lotteryName) {
        this.lotteryName = lotteryName;
    }
    
    public int getTypeId() {
        return this.typeId;
    }
    
    public void setTypeId(final int typeId) {
        this.typeId = typeId;
    }
    
    public int getGroupId() {
        return this.groupId;
    }
    
    public void setGroupId(final int groupId) {
        this.groupId = groupId;
    }
    
    public int getLotteryId() {
        return this.lotteryId;
    }
    
    public void setLotteryId(final int lotteryId) {
        this.lotteryId = lotteryId;
    }
    
    public int getRuleId() {
        return this.ruleId;
    }
    
    public void setRuleId(final int ruleId) {
        this.ruleId = ruleId;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public void setCode(final String code) {
        this.code = code;
    }
    
    public String getMinNum() {
        return this.minNum;
    }
    
    public void setMinNum(final String minNum) {
        this.minNum = minNum;
    }
    
    public String getMaxNum() {
        return this.maxNum;
    }
    
    public void setMaxNum(final String maxNum) {
        this.maxNum = maxNum;
    }
    
    public String getTotalNum() {
        return this.totalNum;
    }
    
    public void setTotalNum(final String totalNum) {
        this.totalNum = totalNum;
    }
    
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    public int getFixed() {
        return this.fixed;
    }
    
    public void setFixed(final int fixed) {
        this.fixed = fixed;
    }
    
    public String getPrize() {
        return this.prize;
    }
    
    public void setPrize(final String prize) {
        this.prize = prize;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(final String desc) {
        this.desc = desc;
    }
    
    public String getDantiao() {
        return this.dantiao;
    }
    
    public void setDantiao(final String dantiao) {
        this.dantiao = dantiao;
    }
    
    public int getIsLocate() {
        return this.isLocate;
    }
    
    public void setIsLocate(final int isLocate) {
        this.isLocate = isLocate;
    }
}
