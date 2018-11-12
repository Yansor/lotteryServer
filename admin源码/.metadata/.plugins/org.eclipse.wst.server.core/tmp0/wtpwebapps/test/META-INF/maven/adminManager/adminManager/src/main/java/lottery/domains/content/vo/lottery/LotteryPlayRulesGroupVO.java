package lottery.domains.content.vo.lottery;

import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryType;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.LotteryPlayRulesGroupConfig;
import lottery.domains.content.entity.LotteryPlayRulesGroup;

public class LotteryPlayRulesGroupVO
{
    private String typeName;
    private String lotteryName;
    private int typeId;
    private int lotteryId;
    private int groupId;
    private String name;
    private String code;
    private int status;
    
    public LotteryPlayRulesGroupVO(final LotteryPlayRulesGroup group, final LotteryPlayRulesGroupConfig config, final LotteryDataFactory dataFactory) {
        final LotteryType lotteryType = dataFactory.getLotteryType(group.getTypeId());
        if (lotteryType != null) {
            this.typeName = lotteryType.getName();
        }
        this.typeId = group.getTypeId();
        this.groupId = group.getId();
        this.name = group.getName();
        this.code = group.getCode();
        if (config != null) {
            this.status = config.getStatus();
            this.lotteryId = config.getLotteryId();
            final Lottery lottery = dataFactory.getLottery(config.getLotteryId());
            if (lottery != null) {
                this.lotteryName = lottery.getShowName();
            }
        }
        else {
            this.status = group.getStatus();
        }
    }
    
    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(final String typeName) {
        this.typeName = typeName;
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
    
    public int getLotteryId() {
        return this.lotteryId;
    }
    
    public void setLotteryId(final int lotteryId) {
        this.lotteryId = lotteryId;
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
    
    public String getCode() {
        return this.code;
    }
    
    public void setCode(final String code) {
        this.code = code;
    }
    
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
}
