package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "payment_card", catalog = "ecai")
public class PaymentCard implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int bankId;
    private String cardName;
    private String cardId;
    private String branchName;
    private double totalCredits;
    private double usedCredits;
    private double minTotalRecharge;
    private double maxTotalRecharge;
    private String startTime;
    private String endTime;
    private double minUnitRecharge;
    private double maxUnitRecharge;
    private int status;
    
    public PaymentCard() {
    }
    
    public PaymentCard(final int bankId, final String cardName, final String cardId, final String branchName, final double totalCredits, final double usedCredits, final double minTotalRecharge, final double maxTotalRecharge, final String startTime, final String endTime, final double minUnitRecharge, final double maxUnitRecharge, final int status) {
        this.bankId = bankId;
        this.cardName = cardName;
        this.cardId = cardId;
        this.branchName = branchName;
        this.totalCredits = totalCredits;
        this.usedCredits = usedCredits;
        this.minTotalRecharge = minTotalRecharge;
        this.maxTotalRecharge = maxTotalRecharge;
        this.startTime = startTime;
        this.endTime = endTime;
        this.minUnitRecharge = minUnitRecharge;
        this.maxUnitRecharge = maxUnitRecharge;
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
    
    @Column(name = "bank_id", nullable = false)
    public int getBankId() {
        return this.bankId;
    }
    
    public void setBankId(final int bankId) {
        this.bankId = bankId;
    }
    
    @Column(name = "card_name", nullable = false)
    public String getCardName() {
        return this.cardName;
    }
    
    public void setCardName(final String cardName) {
        this.cardName = cardName;
    }
    
    @Column(name = "card_id", nullable = false, length = 512)
    public String getCardId() {
        return this.cardId;
    }
    
    public void setCardId(final String cardId) {
        this.cardId = cardId;
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
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "branch_name")
    public String getBranchName() {
        return this.branchName;
    }
    
    public void setBranchName(final String branchName) {
        this.branchName = branchName;
    }
}
