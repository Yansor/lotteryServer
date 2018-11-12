package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "activity_red_packet_rain_time", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "date", "hour" }) })
public class ActivityRedPacketRainTime implements Serializable
{
    private int id;
    private String date;
    private String hour;
    private String startTime;
    private String endTime;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "date", nullable = false, length = 10)
    public String getDate() {
        return this.date;
    }
    
    public void setDate(final String date) {
        this.date = date;
    }
    
    @Column(name = "hour", nullable = false, length = 2)
    public String getHour() {
        return this.hour;
    }
    
    public void setHour(final String hour) {
        this.hour = hour;
    }
    
    @Column(name = "start_time", nullable = false, length = 20)
    public String getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }
    
    @Column(name = "end_time", nullable = false, length = 20)
    public String getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }
}
