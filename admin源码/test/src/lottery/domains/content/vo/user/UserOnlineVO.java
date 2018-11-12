package lottery.domains.content.vo.user;

import javautils.StringUtil;
import java.util.ArrayList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.User;
import java.util.List;

public class UserOnlineVO
{
    private String username;
    private double totalMoney;
    private double lotteryMoney;
    private double baccaratMoney;
    private List<String> levelUsers;
    private String loginTime;
    
    public UserOnlineVO(final User bean, final LotteryDataFactory lotteryDataFactory) {
        this.levelUsers = new ArrayList<String>();
        this.username = bean.getUsername();
        this.totalMoney = bean.getTotalMoney();
        this.lotteryMoney = bean.getLotteryMoney();
        this.baccaratMoney = bean.getBaccaratMoney();
        this.loginTime = bean.getLoginTime();
        if (StringUtil.isNotNull(bean.getUpids())) {
            final String[] ids = bean.getUpids().replaceAll("\\[|\\]", "").split(",");
            String[] array;
            for (int length = (array = ids).length, i = 0; i < length; ++i) {
                final String id = array[i];
                final UserVO thisUser = lotteryDataFactory.getUser(Integer.parseInt(id));
                if (thisUser != null) {
                    this.levelUsers.add(thisUser.getUsername());
                }
            }
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public double getTotalMoney() {
        return this.totalMoney;
    }
    
    public void setTotalMoney(final double totalMoney) {
        this.totalMoney = totalMoney;
    }
    
    public double getLotteryMoney() {
        return this.lotteryMoney;
    }
    
    public void setLotteryMoney(final double lotteryMoney) {
        this.lotteryMoney = lotteryMoney;
    }
    
    public double getBaccaratMoney() {
        return this.baccaratMoney;
    }
    
    public void setBaccaratMoney(final double baccaratMoney) {
        this.baccaratMoney = baccaratMoney;
    }
    
    public List<String> getLevelUsers() {
        return this.levelUsers;
    }
    
    public void setLevelUsers(final List<String> levelUsers) {
        this.levelUsers = levelUsers;
    }
    
    public String getLoginTime() {
        return this.loginTime;
    }
    
    public void setLoginTime(final String loginTime) {
        this.loginTime = loginTime;
    }
}
