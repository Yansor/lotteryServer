package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "user_regist_link", catalog = "ecai")
public class UserRegistLink implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private int type;
    private String code;
    private double locatePoint;
    private String expTime;
    private int amount;
    private String time;
    private int status;
    private int deviceType;
    private String qrCode;
    
    public UserRegistLink() {
    }
    
    public UserRegistLink(final int userId, final int type, final String code, final double locatePoint, final int amount, final String time, final int status) {
        this.userId = userId;
        this.type = type;
        this.code = code;
        this.locatePoint = locatePoint;
        this.amount = amount;
        this.time = time;
        this.status = status;
    }
    
    public UserRegistLink(final int userId, final int type, final String code, final double locatePoint, final String expTime, final int amount, final String time, final int status) {
        this.userId = userId;
        this.type = type;
        this.code = code;
        this.locatePoint = locatePoint;
        this.expTime = expTime;
        this.amount = amount;
        this.time = time;
        this.status = status;
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
    
    @Column(name = "type", nullable = false)
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    @Column(name = "code", nullable = false, length = 64)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(final String code) {
        this.code = code;
    }
    
    @Column(name = "locate_point", nullable = false, precision = 11, scale = 2)
    public double getLocatePoint() {
        return this.locatePoint;
    }
    
    public void setLocatePoint(final double locatePoint) {
        this.locatePoint = locatePoint;
    }
    
    @Column(name = "exp_time", length = 19)
    public String getExpTime() {
        return this.expTime;
    }
    
    public void setExpTime(final String expTime) {
        this.expTime = expTime;
    }
    
    @Column(name = "amount", nullable = false)
    public int getAmount() {
        return this.amount;
    }
    
    public void setAmount(final int amount) {
        this.amount = amount;
    }
    
    @Column(name = "time", nullable = false, length = 19)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "device_type")
    public int getDeviceType() {
        return this.deviceType;
    }
    
    public void setDeviceType(final int deviceType) {
        this.deviceType = deviceType;
    }
    
    @Column(name = "qr_code", length = 1024)
    public String getQrCode() {
        return this.qrCode;
    }
    
    public void setQrCode(final String qrCode) {
        this.qrCode = qrCode;
    }
}
