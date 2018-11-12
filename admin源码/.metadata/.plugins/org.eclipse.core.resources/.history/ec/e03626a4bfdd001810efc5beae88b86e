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
@Table(name = "lottery_open_code", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "lottery", "expect", "user_id" }) })
public class LotteryOpenCode implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer userId;
    private String lottery;
    private String expect;
    private String code;
    private String time;
    private String interfaceTime;
    private Integer openStatus;
    private String openTime;
    private String remarks;
    
    public LotteryOpenCode() {
    }
    
    public LotteryOpenCode(final String lottery, final String expect, final String code, final String time, final Integer openStatus) {
        this.lottery = lottery;
        this.expect = expect;
        this.code = code;
        this.time = time;
        this.openStatus = openStatus;
    }
    
    public LotteryOpenCode(final String lottery, final String expect, final String code, final String time, final Integer openStatus, final String openTime, final String remarks) {
        this.lottery = lottery;
        this.expect = expect;
        this.code = code;
        this.time = time;
        this.openStatus = openStatus;
        this.openTime = openTime;
        this.remarks = remarks;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    @Column(name = "user_id")
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(final Integer userId) {
        this.userId = userId;
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
    
    @Column(name = "code", nullable = false, length = 128)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(final String code) {
        this.code = code;
    }
    
    @Column(name = "time", nullable = false, length = 19)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    @Column(name = "interface_time", nullable = false, length = 19)
    public String getInterfaceTime() {
        return this.interfaceTime;
    }
    
    public void setInterfaceTime(final String interfaceTime) {
        this.interfaceTime = interfaceTime;
    }
    
    @Column(name = "open_status", nullable = false)
    public Integer getOpenStatus() {
        return this.openStatus;
    }
    
    public void setOpenStatus(final Integer openStatus) {
        this.openStatus = openStatus;
    }
    
    @Column(name = "open_time")
    public String getOpenTime() {
        return this.openTime;
    }
    
    public void setOpenTime(final String openTime) {
        this.openTime = openTime;
    }
    
    @Column(name = "remarks", length = 128)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }
}
