package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "user_login_log", catalog = "ecaibackup")
public class HistoryUserLoginLog implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private String ip;
    private String address;
    private String userAgent;
    private String time;
    private String loginLine;
    
    public HistoryUserLoginLog() {
    }
    
    public HistoryUserLoginLog(final int userId, final String ip, final String time, final String loginLine) {
        this.userId = userId;
        this.ip = ip;
        this.time = time;
        this.loginLine = loginLine;
    }
    
    public HistoryUserLoginLog(final int userId, final String ip, final String address, final String userAgent, final String time, final String loginLine) {
        this.userId = userId;
        this.ip = ip;
        this.address = address;
        this.userAgent = userAgent;
        this.time = time;
        this.loginLine = loginLine;
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
    
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    @Column(name = "ip", nullable = false, length = 128)
    public String getIp() {
        return this.ip;
    }
    
    public void setIp(final String ip) {
        this.ip = ip;
    }
    
    @Column(name = "address")
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(final String address) {
        this.address = address;
    }
    
    @Column(name = "user_agent")
    public String getUserAgent() {
        return this.userAgent;
    }
    
    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }
    
    @Column(name = "time", nullable = false, length = 19)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    @Column(name = "login_line")
    public String getLoginLine() {
        return this.loginLine;
    }
    
    public void setLoginLine(final String loginLine) {
        this.loginLine = loginLine;
    }
}
