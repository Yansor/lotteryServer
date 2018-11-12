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
@Table(name = "lottery_crawler_status", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "show_name" }), @UniqueConstraint(columnNames = { "short_name" }) })
public class LotteryCrawlerStatus implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String showName;
    private String shortName;
    private int times;
    private String lastExpect;
    private String lastUpdate;
    
    public LotteryCrawlerStatus() {
    }
    
    public LotteryCrawlerStatus(final String showName, final String shortName, final int times, final String lastExpect, final String lastUpdate) {
        this.showName = showName;
        this.shortName = shortName;
        this.times = times;
        this.lastExpect = lastExpect;
        this.lastUpdate = lastUpdate;
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
    
    @Column(name = "show_name", unique = true, nullable = false, length = 128)
    public String getShowName() {
        return this.showName;
    }
    
    public void setShowName(final String showName) {
        this.showName = showName;
    }
    
    @Column(name = "short_name", unique = true, nullable = false, length = 32)
    public String getShortName() {
        return this.shortName;
    }
    
    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }
    
    @Column(name = "times", nullable = false)
    public int getTimes() {
        return this.times;
    }
    
    public void setTimes(final int times) {
        this.times = times;
    }
    
    @Column(name = "last_expect", nullable = false, length = 32)
    public String getLastExpect() {
        return this.lastExpect;
    }
    
    public void setLastExpect(final String lastExpect) {
        this.lastExpect = lastExpect;
    }
    
    @Column(name = "last_update", nullable = false, length = 19)
    public String getLastUpdate() {
        return this.lastUpdate;
    }
    
    public void setLastUpdate(final String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
