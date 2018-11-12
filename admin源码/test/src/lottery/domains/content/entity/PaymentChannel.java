package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "payment_channel", catalog = "ecai")
public class PaymentChannel implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String mobileName;
    private String frontName;
    private String channelCode;
    private String merCode;
    private double totalCredits;
    private double usedCredits;
    private double minTotalRecharge;
    private double maxTotalRecharge;
    private double minUnitRecharge;
    private double maxUnitRecharge;
    private String payUrl;
    private String armourUrl;
    private int status;
    private int sequence;
    private String maxRegisterTime;
    private double thirdFee;
    private int thirdFeeFixed;
    private String qrUrlCode;
    private int fixedQRAmount;
    private int type;
    private int subType;
    private double consumptionPercent;
    private String whiteUsernames;
    private String startTime;
    private String endTime;
    private int addMoneyType;
    private String md5Key;
    private String rsaPublicKey;
    private String rsaPrivateKey;
    private String rsaPlatformPublicKey;
    private String apiPayBankChannelCode;
    private int apiPayStatus;
    private String ext1;
    private String ext2;
    private String ext3;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    @Column(name = "mobile_name", nullable = false, length = 64)
    public String getMobileName() {
        return this.mobileName;
    }
    
    public void setMobileName(final String mobileName) {
        this.mobileName = mobileName;
    }
    
    @Column(name = "front_name", nullable = false, length = 64)
    public String getFrontName() {
        return this.frontName;
    }
    
    public void setFrontName(final String frontName) {
        this.frontName = frontName;
    }
    
    @Column(name = "channel_code", nullable = false, length = 64)
    public String getChannelCode() {
        return this.channelCode;
    }
    
    public void setChannelCode(final String channelCode) {
        this.channelCode = channelCode;
    }
    
    @Column(name = "mer_code", nullable = false)
    public String getMerCode() {
        return this.merCode;
    }
    
    public void setMerCode(final String merCode) {
        this.merCode = merCode;
    }
    
    @Column(name = "total_credits", nullable = false, precision = 12, scale = 3)
    public double getTotalCredits() {
        return this.totalCredits;
    }
    
    public void setTotalCredits(final double totalCredits) {
        this.totalCredits = totalCredits;
    }
    
    @Column(name = "used_credits", nullable = false, precision = 12, scale = 3)
    public double getUsedCredits() {
        return this.usedCredits;
    }
    
    public void setUsedCredits(final double usedCredits) {
        this.usedCredits = usedCredits;
    }
    
    @Column(name = "min_total_recharge", nullable = false, precision = 12, scale = 3)
    public double getMinTotalRecharge() {
        return this.minTotalRecharge;
    }
    
    public void setMinTotalRecharge(final double minTotalRecharge) {
        this.minTotalRecharge = minTotalRecharge;
    }
    
    @Column(name = "max_total_recharge", nullable = false, precision = 12, scale = 3)
    public double getMaxTotalRecharge() {
        return this.maxTotalRecharge;
    }
    
    public void setMaxTotalRecharge(final double maxTotalRecharge) {
        this.maxTotalRecharge = maxTotalRecharge;
    }
    
    @Column(name = "min_unit_recharge", nullable = false, precision = 12, scale = 3)
    public double getMinUnitRecharge() {
        return this.minUnitRecharge;
    }
    
    public void setMinUnitRecharge(final double minUnitRecharge) {
        this.minUnitRecharge = minUnitRecharge;
    }
    
    @Column(name = "max_unit_recharge", nullable = false, precision = 12, scale = 3)
    public double getMaxUnitRecharge() {
        return this.maxUnitRecharge;
    }
    
    public void setMaxUnitRecharge(final double maxUnitRecharge) {
        this.maxUnitRecharge = maxUnitRecharge;
    }
    
    @Column(name = "pay_url")
    public String getPayUrl() {
        return this.payUrl;
    }
    
    public void setPayUrl(final String payUrl) {
        this.payUrl = payUrl;
    }
    
    @Column(name = "armour_url")
    public String getArmourUrl() {
        return this.armourUrl;
    }
    
    public void setArmourUrl(final String armourUrl) {
        this.armourUrl = armourUrl;
    }
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "sequence", nullable = false)
    public int getSequence() {
        return this.sequence;
    }
    
    public void setSequence(final int sequence) {
        this.sequence = sequence;
    }
    
    @Column(name = "max_register_time", length = 20)
    public String getMaxRegisterTime() {
        return this.maxRegisterTime;
    }
    
    public void setMaxRegisterTime(final String maxRegisterTime) {
        this.maxRegisterTime = maxRegisterTime;
    }
    
    @Column(name = "third_fee", nullable = false, precision = 4, scale = 2)
    public double getThirdFee() {
        return this.thirdFee;
    }
    
    public void setThirdFee(final double thirdFee) {
        this.thirdFee = thirdFee;
    }
    
    @Column(name = "third_fee_fixed")
    public int getThirdFeeFixed() {
        return this.thirdFeeFixed;
    }
    
    public void setThirdFeeFixed(final int thirdFeeFixed) {
        this.thirdFeeFixed = thirdFeeFixed;
    }
    
    @Column(name = "qr_url_code", length = 3072)
    public String getQrUrlCode() {
        return this.qrUrlCode;
    }
    
    public void setQrUrlCode(final String qrUrlCode) {
        this.qrUrlCode = qrUrlCode;
    }
    
    @Column(name = "fixed_qr_amount")
    public int getFixedQRAmount() {
        return this.fixedQRAmount;
    }
    
    public void setFixedQRAmount(final int fixedQRAmount) {
        this.fixedQRAmount = fixedQRAmount;
    }
    
    @Column(name = "type", nullable = false)
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    @Column(name = "sub_type", nullable = false)
    public int getSubType() {
        return this.subType;
    }
    
    public void setSubType(final int subType) {
        this.subType = subType;
    }
    
    @Column(name = "consumption_percent", nullable = false, precision = 4, scale = 2)
    public double getConsumptionPercent() {
        return this.consumptionPercent;
    }
    
    public void setConsumptionPercent(final double consumptionPercent) {
        this.consumptionPercent = consumptionPercent;
    }
    
    @Column(name = "white_usernames", length = 2048)
    public String getWhiteUsernames() {
        return this.whiteUsernames;
    }
    
    public void setWhiteUsernames(final String whiteUsernames) {
        this.whiteUsernames = whiteUsernames;
    }
    
    @Column(name = "start_time", length = 20)
    public String getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }
    
    @Column(name = "end_time", length = 20)
    public String getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }
    
    @Column(name = "add_money_type", nullable = false)
    public int getAddMoneyType() {
        return this.addMoneyType;
    }
    
    public void setAddMoneyType(final int addMoneyType) {
        this.addMoneyType = addMoneyType;
    }
    
    @Column(name = "md5_key", nullable = false, length = 512)
    public String getMd5Key() {
        return this.md5Key;
    }
    
    public void setMd5Key(final String md5Key) {
        this.md5Key = md5Key;
    }
    
    @Column(name = "rsa_public_key", length = 3072)
    public String getRsaPublicKey() {
        return this.rsaPublicKey;
    }
    
    public void setRsaPublicKey(final String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }
    
    @Column(name = "rsa_private_key", length = 3072)
    public String getRsaPrivateKey() {
        return this.rsaPrivateKey;
    }
    
    public void setRsaPrivateKey(final String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }
    
    @Column(name = "rsa_platform_public_key", length = 3072)
    public String getRsaPlatformPublicKey() {
        return this.rsaPlatformPublicKey;
    }
    
    public void setRsaPlatformPublicKey(final String rsaPlatformPublicKey) {
        this.rsaPlatformPublicKey = rsaPlatformPublicKey;
    }
    
    @Column(name = "api_pay_bank_channel_code", length = 64)
    public String getApiPayBankChannelCode() {
        return this.apiPayBankChannelCode;
    }
    
    public void setApiPayBankChannelCode(final String apiPayBankChannelCode) {
        this.apiPayBankChannelCode = apiPayBankChannelCode;
    }
    
    @Column(name = "api_pay_status")
    public int getApiPayStatus() {
        return this.apiPayStatus;
    }
    
    public void setApiPayStatus(final int apiPayStatus) {
        this.apiPayStatus = apiPayStatus;
    }
    
    @Column(name = "ext1", length = 512)
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(final String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name = "ext2", length = 512)
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(final String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name = "ext3", length = 3072)
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(final String ext3) {
        this.ext3 = ext3;
    }
}
