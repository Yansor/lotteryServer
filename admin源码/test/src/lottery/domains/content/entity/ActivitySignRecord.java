package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "activity_sign_record", catalog = "ecai")
public class ActivitySignRecord implements Serializable
{
    private int id;
    private int userId;
    private int days;
    private String startTime;
    private String lastSignTime;
    private String lastCollectTime;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "user_id", nullable = false, unique = true)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    @Column(name = "days", nullable = false)
    public int getDays() {
        return this.days;
    }
    
    public void setDays(final int days) {
        this.days = days;
    }
    
    @Column(name = "start_time", length = 19)
    public String getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }
    
    @Column(name = "last_sign_time", length = 19)
    public String getLastSignTime() {
        return this.lastSignTime;
    }
    
    public void setLastSignTime(final String lastSignTime) {
        this.lastSignTime = lastSignTime;
    }
    
    @Column(name = "last_collect_time", length = 19)
    public String getLastCollectTime() {
        return this.lastCollectTime;
    }
    
    public void setLastCollectTime(final String lastCollectTime) {
        this.lastCollectTime = lastCollectTime;
    }
}
