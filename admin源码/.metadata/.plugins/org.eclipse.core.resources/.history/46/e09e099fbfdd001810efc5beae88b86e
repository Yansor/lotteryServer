package lottery.domains.content.vo.lottery;

import lottery.domains.content.entity.LotteryType;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.LotteryPlayRulesGroup;

public class LotteryPlayRulesGroupSimpleVO
{
    private String typeName;
    private int typeId;
    private int groupId;
    private String name;
    
    public LotteryPlayRulesGroupSimpleVO(final LotteryPlayRulesGroup group, final LotteryDataFactory dataFactory) {
        final LotteryType lotteryType = dataFactory.getLotteryType(group.getTypeId());
        if (lotteryType != null) {
            this.typeName = lotteryType.getName();
        }
        this.typeId = group.getTypeId();
        this.groupId = group.getId();
        this.name = group.getName();
    }
    
    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(final String typeName) {
        this.typeName = typeName;
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
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
}
