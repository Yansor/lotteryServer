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
@Table(name = "user", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String password;
    private String imgPassword;
    private String nickname;
    private double totalMoney;
    private double lotteryMoney;
    private double baccaratMoney;
    private double freezeMoney;
    private double dividendMoney;
    private int type;
    private int upid;
    private String upids;
    private int code;
    private double locatePoint;
    private double notLocatePoint;
    private int codeType;
    private double extraPoint;
    private String withdrawName;
    private String withdrawPassword;
    private String registTime;
    private String loginTime;
    private String lockTime;
    private int AStatus;
    private int BStatus;
    private String message;
    private String sessionId;
    private int onlineStatus;
    private int allowEqualCode;
    private int allowTransfers;
    private int allowPlatformTransfers;
    private int allowWithdraw;
    private int loginValidate;
    private int bindStatus;
    private int vipLevel;
    private double integral;
    private String secretKey;
    private int isBindGoogle;
    private int relatedUpid;
    private String relatedLowers;
    private double relatedPoint;
    private String relatedUsers;
    private int isCjZhaoShang;
    
    public User() {
    }
    
    public User(final String username, final String password, final String nickname, final double totalMoney, final double lotteryMoney, final double baccaratMoney, final double freezeMoney, final double dividendMoney, final int type, final int upid, final int code, final double locatePoint, final double notLocatePoint, final int codeType, final double extraPoint, final String registTime, final int AStatus, final int BStatus, final int onlineStatus, final int allowEqualCode, final int allowTransfers, final int loginValidate, final int bindStatus, final int vipLevel, final double integral, final int allowWithdraw, final int allowPlatformTransfers) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.totalMoney = totalMoney;
        this.lotteryMoney = lotteryMoney;
        this.baccaratMoney = baccaratMoney;
        this.freezeMoney = freezeMoney;
        this.dividendMoney = dividendMoney;
        this.type = type;
        this.upid = upid;
        this.code = code;
        this.locatePoint = locatePoint;
        this.notLocatePoint = notLocatePoint;
        this.codeType = codeType;
        this.extraPoint = extraPoint;
        this.registTime = registTime;
        this.AStatus = AStatus;
        this.BStatus = BStatus;
        this.onlineStatus = onlineStatus;
        this.allowEqualCode = allowEqualCode;
        this.allowTransfers = allowTransfers;
        this.loginValidate = loginValidate;
        this.bindStatus = bindStatus;
        this.vipLevel = vipLevel;
        this.integral = integral;
        this.allowWithdraw = allowWithdraw;
        this.allowPlatformTransfers = allowPlatformTransfers;
    }
    
    public User(final String username, final String password, final String imgPassword, final String nickname, final double totalMoney, final double lotteryMoney, final double baccaratMoney, final double freezeMoney, final double dividendMoney, final int type, final int upid, final String upids, final int code, final double locatePoint, final double notLocatePoint, final int codeType, final double extraPoint, final String withdrawName, final String withdrawPassword, final String registTime, final String loginTime, final String lockTime, final int AStatus, final int BStatus, final String message, final String sessionId, final int onlineStatus, final int allowEqualCode, final int allowTransfers, final int loginValidate, final int bindStatus, final int vipLevel, final double integral, final int allowWithdraw, final int allowPlatformTransfers) {
        this.username = username;
        this.password = password;
        this.imgPassword = imgPassword;
        this.nickname = nickname;
        this.totalMoney = totalMoney;
        this.lotteryMoney = lotteryMoney;
        this.baccaratMoney = baccaratMoney;
        this.freezeMoney = freezeMoney;
        this.dividendMoney = dividendMoney;
        this.type = type;
        this.upid = upid;
        this.upids = upids;
        this.code = code;
        this.locatePoint = locatePoint;
        this.notLocatePoint = notLocatePoint;
        this.codeType = codeType;
        this.extraPoint = extraPoint;
        this.withdrawName = withdrawName;
        this.withdrawPassword = withdrawPassword;
        this.registTime = registTime;
        this.loginTime = loginTime;
        this.lockTime = lockTime;
        this.AStatus = AStatus;
        this.BStatus = BStatus;
        this.message = message;
        this.sessionId = sessionId;
        this.onlineStatus = onlineStatus;
        this.allowEqualCode = allowEqualCode;
        this.allowTransfers = allowTransfers;
        this.loginValidate = loginValidate;
        this.bindStatus = bindStatus;
        this.vipLevel = vipLevel;
        this.integral = integral;
        this.allowWithdraw = allowWithdraw;
        this.allowPlatformTransfers = allowPlatformTransfers;
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
    
    @Column(name = "username", unique = true, nullable = false, length = 20)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    @Column(name = "password", nullable = false, length = 32)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    @Column(name = "img_password", length = 32)
    public String getImgPassword() {
        return this.imgPassword;
    }
    
    public void setImgPassword(final String imgPassword) {
        this.imgPassword = imgPassword;
    }
    
    @Column(name = "nickname", nullable = false, length = 20)
    public String getNickname() {
        return this.nickname;
    }
    
    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }
    
    @Column(name = "total_money", nullable = false, precision = 16, scale = 5)
    public double getTotalMoney() {
        return this.totalMoney;
    }
    
    public void setTotalMoney(final double totalMoney) {
        this.totalMoney = totalMoney;
    }
    
    @Column(name = "lottery_money", nullable = false, precision = 16, scale = 5)
    public double getLotteryMoney() {
        return this.lotteryMoney;
    }
    
    public void setLotteryMoney(final double lotteryMoney) {
        this.lotteryMoney = lotteryMoney;
    }
    
    @Column(name = "baccarat_money", nullable = false, precision = 16, scale = 5)
    public double getBaccaratMoney() {
        return this.baccaratMoney;
    }
    
    public void setBaccaratMoney(final double baccaratMoney) {
        this.baccaratMoney = baccaratMoney;
    }
    
    @Column(name = "freeze_money", nullable = false, precision = 16, scale = 5)
    public double getFreezeMoney() {
        return this.freezeMoney;
    }
    
    public void setFreezeMoney(final double freezeMoney) {
        this.freezeMoney = freezeMoney;
    }
    
    @Column(name = "dividend_money", nullable = false, precision = 16, scale = 5)
    public double getDividendMoney() {
        return this.dividendMoney;
    }
    
    public void setDividendMoney(final double dividendMoney) {
        this.dividendMoney = dividendMoney;
    }
    
    @Column(name = "type", nullable = false)
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    @Column(name = "upid", nullable = false)
    public int getUpid() {
        return this.upid;
    }
    
    public void setUpid(final int upid) {
        this.upid = upid;
    }
    
    @Column(name = "upids")
    public String getUpids() {
        return this.upids;
    }
    
    public void setUpids(final String upids) {
        this.upids = upids;
    }
    
    @Column(name = "code", nullable = false)
    public int getCode() {
        return this.code;
    }
    
    public void setCode(final int code) {
        this.code = code;
    }
    
    @Column(name = "locate_point", nullable = false, precision = 11, scale = 2)
    public double getLocatePoint() {
        return this.locatePoint;
    }
    
    public void setLocatePoint(final double locatePoint) {
        this.locatePoint = locatePoint;
    }
    
    @Column(name = "not_locate_point", nullable = false, precision = 11, scale = 2)
    public double getNotLocatePoint() {
        return this.notLocatePoint;
    }
    
    public void setNotLocatePoint(final double notLocatePoint) {
        this.notLocatePoint = notLocatePoint;
    }
    
    @Column(name = "code_type", nullable = false)
    public int getCodeType() {
        return this.codeType;
    }
    
    public void setCodeType(final int codeType) {
        this.codeType = codeType;
    }
    
    @Column(name = "extra_point", nullable = false, precision = 11, scale = 3)
    public double getExtraPoint() {
        return this.extraPoint;
    }
    
    public void setExtraPoint(final double extraPoint) {
        this.extraPoint = extraPoint;
    }
    
    @Column(name = "withdraw_name", length = 32)
    public String getWithdrawName() {
        return this.withdrawName;
    }
    
    public void setWithdrawName(final String withdrawName) {
        this.withdrawName = withdrawName;
    }
    
    @Column(name = "withdraw_password", length = 32)
    public String getWithdrawPassword() {
        return this.withdrawPassword;
    }
    
    public void setWithdrawPassword(final String withdrawPassword) {
        this.withdrawPassword = withdrawPassword;
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
    
    @Column(name = "lock_time", length = 19)
    public String getLockTime() {
        return this.lockTime;
    }
    
    public void setLockTime(final String lockTime) {
        this.lockTime = lockTime;
    }
    
    @Column(name = "a_status", nullable = false)
    public int getAStatus() {
        return this.AStatus;
    }
    
    public void setAStatus(final int AStatus) {
        this.AStatus = AStatus;
    }
    
    @Column(name = "b_status", nullable = false)
    public int getBStatus() {
        return this.BStatus;
    }
    
    public void setBStatus(final int BStatus) {
        this.BStatus = BStatus;
    }
    
    @Column(name = "message")
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    @Column(name = "session_id", length = 128)
    public String getSessionId() {
        return this.sessionId;
    }
    
    public void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }
    
    @Column(name = "online_status", nullable = false)
    public int getOnlineStatus() {
        return this.onlineStatus;
    }
    
    public void setOnlineStatus(final int onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
    
    @Column(name = "allow_equal_code", nullable = false)
    public int getAllowEqualCode() {
        return this.allowEqualCode;
    }
    
    public void setAllowEqualCode(final int allowEqualCode) {
        this.allowEqualCode = allowEqualCode;
    }
    
    @Column(name = "allow_transfers", nullable = false)
    public int getAllowTransfers() {
        return this.allowTransfers;
    }
    
    public void setAllowTransfers(final int allowTransfers) {
        this.allowTransfers = allowTransfers;
    }
    
    @Column(name = "login_validate", nullable = false)
    public int getLoginValidate() {
        return this.loginValidate;
    }
    
    public void setLoginValidate(final int loginValidate) {
        this.loginValidate = loginValidate;
    }
    
    @Column(name = "bind_status", nullable = false)
    public int getBindStatus() {
        return this.bindStatus;
    }
    
    public void setBindStatus(final int bindStatus) {
        this.bindStatus = bindStatus;
    }
    
    @Column(name = "vip_level", nullable = false)
    public int getVipLevel() {
        return this.vipLevel;
    }
    
    public void setVipLevel(final int vipLevel) {
        this.vipLevel = vipLevel;
    }
    
    @Column(name = "integral", nullable = false, precision = 16, scale = 5)
    public double getIntegral() {
        return this.integral;
    }
    
    public void setIntegral(final double integral) {
        this.integral = integral;
    }
    
    @Column(name = "secret_key")
    public String getSecretKey() {
        return this.secretKey;
    }
    
    public void setSecretKey(final String secretKey) {
        this.secretKey = secretKey;
    }
    
    @Column(name = "is_bind_google", nullable = false)
    public int getIsBindGoogle() {
        return this.isBindGoogle;
    }
    
    public void setIsBindGoogle(final int isBindGoogle) {
        this.isBindGoogle = isBindGoogle;
    }
    
    @Column(name = "related_upid")
    public int getRelatedUpid() {
        return this.relatedUpid;
    }
    
    public void setRelatedUpid(final int relatedUpid) {
        this.relatedUpid = relatedUpid;
    }
    
    @Column(name = "related_lowers")
    public String getRelatedLowers() {
        return this.relatedLowers;
    }
    
    public void setRelatedLowers(final String relatedLowers) {
        this.relatedLowers = relatedLowers;
    }
    
    @Column(name = "related_point", nullable = false, precision = 3, scale = 2)
    public double getRelatedPoint() {
        return this.relatedPoint;
    }
    
    public void setRelatedPoint(final double relatedPoint) {
        this.relatedPoint = relatedPoint;
    }
    
    @Column(name = "related_users")
    public String getRelatedUsers() {
        return this.relatedUsers;
    }
    
    public void setRelatedUsers(final String relatedUsers) {
        this.relatedUsers = relatedUsers;
    }
    
    @Column(name = "allow_withdraw", nullable = false)
    public int getAllowWithdraw() {
        return this.allowWithdraw;
    }
    
    public void setAllowWithdraw(final int allowWithdraw) {
        this.allowWithdraw = allowWithdraw;
    }
    
    @Column(name = "allow_platform_transfers", nullable = false)
    public int getAllowPlatformTransfers() {
        return this.allowPlatformTransfers;
    }
    
    public void setAllowPlatformTransfers(final int allowPlatformTransfers) {
        this.allowPlatformTransfers = allowPlatformTransfers;
    }
    
    @Column(name = "is_cj_zhao_shang")
    public int getIsCjZhaoShang() {
        return this.isCjZhaoShang;
    }
    
    public void setIsCjZhaoShang(final int isCjZhaoShang) {
        this.isCjZhaoShang = isCjZhaoShang;
    }
}
