package lottery.domains.content.vo.lottery;

import lottery.domains.content.entity.LotteryPlayRulesGroup;
import lottery.domains.content.entity.LotteryType;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.LotteryPlayRules;

public class LotteryPlayRulesSimpleVO
{
    private String typeName;
    private String groupName;
    private int typeId;
    private int groupId;
    private int ruleId;
    private String name;
    private String code;
    
    public LotteryPlayRulesSimpleVO(final LotteryPlayRules rule, final LotteryDataFactory dataFactory) {
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
}
