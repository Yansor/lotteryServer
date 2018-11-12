package admin.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "admin_user_critical_log", catalog = "ecai")
public class AdminUserCriticalLog implements Serializable
{
    private static final long serialVersionUID = -7560535410474004055L;
    private int id;
    private int userId;
    private int adminUserId;
    private String ip;
    private String address;
    private String action;
    private String time;
    private String userAgent;
    private int actionId;
    
    public AdminUserCriticalLog() {
    }
    
    public AdminUserCriticalLog(final int userId, final int actionId, final String ip, final String action, final String time) {
        this.userId = userId;
        this.actionId = actionId;
        this.ip = ip;
        this.action = action;
        this.time = time;
    }
    
    public AdminUserCriticalLog(final int adminUserId, final int userId, final int actionId, final String ip, final String address, final String action, final String time) {
        this.userId = userId;
        this.adminUserId = adminUserId;
        this.ip = ip;
        this.actionId = actionId;
        this.address = address;
        this.action = action;
        this.time = time;
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
    
    @Column(name = "action", nullable = false, length = 65535)
    public String getAction() {
        return this.action;
    }
    
    public void setAction(final String action) {
        this.action = action;
    }
    
    @Column(name = "time", nullable = false, length = 20)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    @Column(name = "user_agent", nullable = false, length = 255)
    public String getUserAgent() {
        return this.userAgent;
    }
    
    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }
    
    @Column(name = "action_id", nullable = false)
    public int getActionId() {
        return this.actionId;
    }
    
    public void setActionId(final int actionId) {
        this.actionId = actionId;
    }
    
    @Column(name = "admin_user_id")
    public int getAdminUserId() {
        return this.adminUserId;
    }
    
    public void setAdminUserId(final int adminUserId) {
        this.adminUserId = adminUserId;
    }
}
