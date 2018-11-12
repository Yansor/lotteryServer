package lottery.domains.content.vo.user;

import org.apache.commons.lang.StringUtils;
import lottery.domains.content.entity.UserBetsSameIpLog;

public class UserBetsSameIpLogVO
{
    private int id;
    private String ip;
    private String address;
    private String users;
    private int usersCount;
    private String lastTime;
    private String lastUser;
    private int lastUserBetsId;
    private int times;
    private double amount;
    
    public UserBetsSameIpLogVO(final UserBetsSameIpLog userBetsSameIpLog) {
        this.setId(userBetsSameIpLog.getId());
        this.setIp(userBetsSameIpLog.getIp());
        this.setAddress(userBetsSameIpLog.getAddress());
        if (StringUtils.isNotEmpty(userBetsSameIpLog.getUsers())) {
            this.setUsers(userBetsSameIpLog.getUsers().replaceAll("\\[", "").replaceAll("\\]", ""));
        }
        this.setUsersCount(userBetsSameIpLog.getUsersCount());
        this.setLastTime(userBetsSameIpLog.getLastTime());
        this.setLastUser(userBetsSameIpLog.getLastUser());
        this.setLastUserBetsId(userBetsSameIpLog.getLastUserBetsId());
        this.setTimes(userBetsSameIpLog.getTimes());
        this.setAmount(userBetsSameIpLog.getAmount());
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getIp() {
        return this.ip;
    }
    
    public void setIp(final String ip) {
        this.ip = ip;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(final String address) {
        this.address = address;
    }
    
    public String getUsers() {
        return this.users;
    }
    
    public void setUsers(final String users) {
        this.users = users;
    }
    
    public int getUsersCount() {
        return this.usersCount;
    }
    
    public void setUsersCount(final int usersCount) {
        this.usersCount = usersCount;
    }
    
    public String getLastTime() {
        return this.lastTime;
    }
    
    public void setLastTime(final String lastTime) {
        this.lastTime = lastTime;
    }
    
    public String getLastUser() {
        return this.lastUser;
    }
    
    public void setLastUser(final String lastUser) {
        this.lastUser = lastUser;
    }
    
    public int getLastUserBetsId() {
        return this.lastUserBetsId;
    }
    
    public void setLastUserBetsId(final int lastUserBetsId) {
        this.lastUserBetsId = lastUserBetsId;
    }
    
    public int getTimes() {
        return this.times;
    }
    
    public void setTimes(final int times) {
        this.times = times;
    }
    
    public double getAmount() {
        return this.amount;
    }
    
    public void setAmount(final double amount) {
        this.amount = amount;
    }
}
