package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "payment_channel_qr_code", catalog = "ecai")
public class PaymentChannelQrCode implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int channelId;
    private double money;
    private String qrUrlCode;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "channel_id", nullable = false)
    public int getChannelId() {
        return this.channelId;
    }
    
    public void setChannelId(final int channelId) {
        this.channelId = channelId;
    }
    
    @Column(name = "money", nullable = false, precision = 12, scale = 3)
    public double getMoney() {
        return this.money;
    }
    
    public void setMoney(final double money) {
        this.money = money;
    }
    
    @Column(name = "qr_url_code")
    public String getQrUrlCode() {
        return this.qrUrlCode;
    }
    
    public void setQrUrlCode(final String qrUrlCode) {
        this.qrUrlCode = qrUrlCode;
    }
}
