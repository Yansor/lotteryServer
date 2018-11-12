package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "activity_packet_info", catalog = "ecai")
public class ActivityPacketInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private String time;
    private double amount;
    private int count;
    private int type;
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
    
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    @Column(name = "amount", nullable = false)
    public double getAmount() {
        return this.amount;
    }
    
    public void setAmount(final double amount) {
        this.amount = amount;
    }
    
    @Column(name = "count", nullable = false)
    public int getCount() {
        return this.count;
    }
    
    public void setCount(final int count) {
        this.count = count;
    }
    
    @Column(name = "type", nullable = false)
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "time", nullable = false, length = 19)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    public enum PacketStatus
    {
        AVALIABLE("AVALIABLE", 0, 0), 
        FINISH("FINISH", 1, 1);
        
        private Integer status;
        
        private PacketStatus(final String s, final int n, final int status) {
            this.status = status;
        }
        
        public int get() {
            return this.status;
        }
    }
    
    public enum PacketType
    {
        SYSTEM_PACKET("SYSTEM_PACKET", 0, 0), 
        USER_PACKET("USER_PACKET", 1, 1);
        
        private Integer type;
        
        private PacketType(final String s, final int n, final int type) {
            this.type = type;
        }
        
        public int get() {
            return this.type;
        }
    }
}
