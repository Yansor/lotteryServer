package lottery.domains.content.vo.activity;

import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.ActivityPacketInfo;
import lottery.domains.content.entity.ActivityPacketBill;

public class ActivityPacketVO
{
    private String username;
    public ActivityPacketBill bean;
    public ActivityPacketInfo info;
    
    public ActivityPacketVO() {
    }
    
    public ActivityPacketVO(final ActivityPacketBill bean, final LotteryDataFactory df) {
        this.bean = bean;
        final UserVO user = df.getUser(bean.getUserId());
        if (user != null) {
            this.username = user.getUsername();
        }
    }
    
    public ActivityPacketVO(final ActivityPacketInfo info, final LotteryDataFactory df) {
        this.info = info;
        if (info.getUserId() == -1) {
            this.username = "系统红包";
        }
        else {
            final UserVO user = df.getUser(info.getUserId());
            if (user != null) {
                this.username = user.getUsername();
            }
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public ActivityPacketBill getBean() {
        return this.bean;
    }
    
    public void setBean(final ActivityPacketBill bean) {
        this.bean = bean;
    }
    
    public ActivityPacketInfo getInfo() {
        return this.info;
    }
    
    public void setInfo(final ActivityPacketInfo info) {
        this.info = info;
    }
}
