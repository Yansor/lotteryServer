package lottery.domains.content.vo.user;

import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.Game;

public class GameVO
{
    private String platformName;
    private String typeName;
    private Game bean;
    
    public GameVO(final Game bean, final LotteryDataFactory dataFactory) {
        this.bean = bean;
        this.platformName = dataFactory.getSysPlatform(bean.getPlatformId()).getName();
        this.typeName = dataFactory.getGameType(bean.getTypeId()).getTypeName();
    }
    
    public String getPlatformName() {
        return this.platformName;
    }
    
    public void setPlatformName(final String platformName) {
        this.platformName = platformName;
    }
    
    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }
    
    public Game getBean() {
        return this.bean;
    }
    
    public void setBean(final Game bean) {
        this.bean = bean;
    }
}
