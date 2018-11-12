package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "lottery_open_time", catalog = "ecai")
public class LotteryOpenTime implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String lottery;
    private String expect;
    private String startTime;
    private String stopTime;
    private String openTime;
    private int play;
    private boolean isTodayExpect;
    
    public LotteryOpenTime() {
    }
    
    public LotteryOpenTime(final String lottery, final String expect, final String startTime, final String stopTime, final String openTime, final boolean isTodayExpect) {
        this.lottery = lottery;
        this.expect = expect;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.openTime = openTime;
        this.isTodayExpect = isTodayExpect;
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
    
    @Column(name = "lottery", nullable = false, length = 32)
    public String getLottery() {
        return this.lottery;
    }
    
    public void setLottery(final String lottery) {
        this.lottery = lottery;
    }
    
    @Column(name = "expect", nullable = false, length = 32)
    public String getExpect() {
        return this.expect;
    }
    
    public void setExpect(final String expect) {
        this.expect = expect;
    }
    
    @Column(name = "start_time", nullable = false, length = 19)
    public String getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }
    
    @Column(name = "stop_time", nullable = false, length = 19)
    public String getStopTime() {
        return this.stopTime;
    }
    
    public void setStopTime(final String stopTime) {
        this.stopTime = stopTime;
    }
    
    @Column(name = "open_time", nullable = false, length = 19)
    public String getOpenTime() {
        return this.openTime;
    }
    
    public void setOpenTime(final String openTime) {
        this.openTime = openTime;
    }
    
    @Column(name = "is_today_expect", nullable = false)
    public boolean getIsTodayExpect() {
        return this.isTodayExpect;
    }
    
    public void setIsTodayExpect(final boolean isTodayExpect) {
        this.isTodayExpect = isTodayExpect;
    }
    
    @Column(name = "play", nullable = false, length = 11)
    public int getPlay() {
        return this.play;
    }
    
    public void setPlay(final int play) {
        this.play = play;
    }
}
