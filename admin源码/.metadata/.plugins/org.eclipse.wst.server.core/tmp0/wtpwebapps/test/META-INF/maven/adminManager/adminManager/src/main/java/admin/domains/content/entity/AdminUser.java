package admin.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "admin_user", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class AdminUser implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String password;
    private String withdrawPwd;
    private int roleId;
    private String registTime;
    private String loginTime;
    private int status;
    private String ips;
    private int pwd_error;
    private String secretKey;
    private int isValidate;
    
    public AdminUser() {
    }
    
    public AdminUser(final String username, final String password, final String withdrawPwd, final int roleId, final String registTime) {
        this.username = username;
        this.password = password;
        this.withdrawPwd = withdrawPwd;
        this.roleId = roleId;
        this.registTime = registTime;
    }
    
    public AdminUser(final String username, final String password, final String withdrawPwd, final int roleId, final String registTime, final String loginTime, final int status, final String ips, final int pwd_error) {
        this.username = username;
        this.password = password;
        this.withdrawPwd = withdrawPwd;
        this.roleId = roleId;
        this.registTime = registTime;
        this.loginTime = loginTime;
        this.status = status;
        this.ips = ips;
        this.pwd_error = pwd_error;
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
    
    @Column(name = "username", unique = true, nullable = false, length = 128)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    @Column(name = "password", nullable = false, length = 128)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    @Column(name = "withdraw_pwd", nullable = false, length = 128)
    public String getWithdrawPwd() {
        return this.withdrawPwd;
    }
    
    public void setWithdrawPwd(final String withdrawPwd) {
        this.withdrawPwd = withdrawPwd;
    }
    
    @Column(name = "role_id", nullable = false)
    public int getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(final int roleId) {
        this.roleId = roleId;
    }
    
    @Column(name = "regist_time", nullable = false, length = 19)
    public String getRegistTime() {
        return this.registTime;
    }
    
    public void setRegistTime(final String registTime) {
        this.registTime = registTime;
    }
    
    @Column(name = "login_time", length = 19)
    public String getLoginTime() {
        return this.loginTime;
    }
    
    public void setLoginTime(final String loginTime) {
        this.loginTime = loginTime;
    }
    
    @Column(name = "status")
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "ips", length = 255)
    public String getIps() {
        return this.ips;
    }
    
    public void setIps(final String ips) {
        this.ips = ips;
    }
    
    @Column(name = "pwd_error")
    public int getPwd_error() {
        return this.pwd_error;
    }
    
    public void setPwd_error(final int pwd_error) {
        this.pwd_error = pwd_error;
    }
    
    @Column(name = "secret_key")
    public String getSecretKey() {
        return this.secretKey;
    }
    
    public void setSecretKey(final String secretKey) {
        this.secretKey = secretKey;
    }
    
    @Column(name = "is_validate")
    public int getIsValidate() {
        return this.isValidate;
    }
    
    public void setIsValidate(final int isValidate) {
        this.isValidate = isValidate;
    }
}
