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
@Table(name = "payment_channel_bank", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "channel_code", "bank_id" }) })
public class PaymentChannelBank implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String channelCode;
    private int bankId;
    private String code;
    private int status;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "channel_code", nullable = false, length = 64)
    public String getChannelCode() {
        return this.channelCode;
    }
    
    public void setChannelCode(final String channelCode) {
        this.channelCode = channelCode;
    }
    
    @Column(name = "bank_id", nullable = false)
    public int getBankId() {
        return this.bankId;
    }
    
    public void setBankId(final int bankId) {
        this.bankId = bankId;
    }
    
    @Column(name = "code", nullable = false, length = 32)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(final String code) {
        this.code = code;
    }
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
}
