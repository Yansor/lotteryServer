package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "user_bets_hit_ranking", catalog = "ecai")
public class UserBetsHitRanking implements Serializable, Comparable<UserBetsHitRanking>
{
    private static final long serialVersionUID = -2265717841611668255L;
    @JSONField(serialize = false)
    private int id;
    private String name;
    private String username;
    private int prizeMoney;
    private String time;
    private String code;
    private String type;
    private int platform;
    
    public UserBetsHitRanking() {
    }
    
    public UserBetsHitRanking(final int id, final String name, final String username, final int prizeMoney, final String time, final String code, final String type, final int platform) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.prizeMoney = prizeMoney;
        this.time = time;
        this.code = code;
        this.type = type;
        this.platform = platform;
    }
    
    public UserBetsHitRanking(final String name, final String username, final int prizeMoney, final String time, final String code, final String type, final int platform) {
        this.name = name;
        this.username = username;
        this.prizeMoney = prizeMoney;
        this.time = time;
        this.code = code;
        this.type = type;
        this.platform = platform;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "name", nullable = false, length = 256)
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    @Column(name = "username", nullable = false, length = 256)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    @Column(name = "prize_money", nullable = false)
    public int getPrizeMoney() {
        return this.prizeMoney;
    }
    
    public void setPrizeMoney(final int prizeMoney) {
        this.prizeMoney = prizeMoney;
    }
    
    @Column(name = "time", nullable = false, length = 20)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    @Column(name = "code")
    public String getCode() {
        return this.code;
    }
    
    public void setCode(final String code) {
        this.code = code;
    }
    
    @Column(name = "type")
    public String getType() {
        return this.type;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    @Column(name = "platform", nullable = false)
    public int getPlatform() {
        return this.platform;
    }
    
    public void setPlatform(final int platform) {
        this.platform = platform;
    }
    
    @Override
    public int compareTo(final UserBetsHitRanking o) {
        if (this.getPrizeMoney() == o.getPrizeMoney()) {
            return 1;
        }
        return (o.getPrizeMoney() > this.getPrizeMoney()) ? 1 : -1;
    }
}
