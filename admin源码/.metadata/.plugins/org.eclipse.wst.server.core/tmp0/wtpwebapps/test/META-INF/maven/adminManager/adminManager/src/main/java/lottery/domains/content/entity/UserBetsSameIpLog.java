package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "user_bets_same_ip_log", catalog = "ecai")
public class UserBetsSameIpLog implements Serializable
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
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "ip", unique = true, nullable = false, length = 20)
    public String getIp() {
        return this.ip;
    }
    
    public void setIp(final String ip) {
        this.ip = ip;
    }
    
    @Column(name = "address", nullable = false, length = 256)
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(final String address) {
        this.address = address;
    }
    
    @Column(name = "users", nullable = false, length = 4096)
    public String getUsers() {
        return this.users;
    }
    
    public void setUsers(final String users) {
        this.users = users;
    }
    
    @Column(name = "users_count", nullable = false, length = 11)
    public int getUsersCount() {
        return this.usersCount;
    }
    
    public void setUsersCount(final int usersCount) {
        this.usersCount = usersCount;
    }
    
    @Column(name = "last_time", nullable = false, length = 20)
    public String getLastTime() {
        return this.lastTime;
    }
    
    public void setLastTime(final String lastTime) {
        this.lastTime = lastTime;
    }
    
    @Column(name = "times", length = 11)
    public int getTimes() {
        return this.times;
    }
    
    public void setTimes(final int times) {
        this.times = times;
    }
    
    @Column(name = "amount", nullable = false, precision = 16, scale = 5)
    public double getAmount() {
        return this.amount;
    }
    
    public void setAmount(final double amount) {
        this.amount = amount;
    }
    
    @Column(name = "last_user", nullable = false, length = 20)
    public String getLastUser() {
        return this.lastUser;
    }
    
    public void setLastUser(final String lastUser) {
        this.lastUser = lastUser;
    }
    
    @Column(name = "last_user_bets_id", nullable = false, length = 11)
    public int getLastUserBetsId() {
        return this.lastUserBetsId;
    }
    
    public void setLastUserBetsId(final int lastUserBetsId) {
        this.lastUserBetsId = lastUserBetsId;
    }
}
