package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "user_message", catalog = "ecai")
public class UserMessage implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int toUid;
    private int fromUid;
    private int type;
    private String subject;
    private String content;
    private String time;
    private int toStatus;
    private int fromStatus;
    
    public UserMessage() {
    }
    
    public UserMessage(final int toUid, final int fromUid, final int type, final String subject, final String content, final String time, final int toStatus, final int fromStatus) {
        this.toUid = toUid;
        this.fromUid = fromUid;
        this.type = type;
        this.subject = subject;
        this.content = content;
        this.time = time;
        this.toStatus = toStatus;
        this.fromStatus = fromStatus;
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
    
    @Column(name = "to_uid", nullable = false)
    public int getToUid() {
        return this.toUid;
    }
    
    public void setToUid(final int toUid) {
        this.toUid = toUid;
    }
    
    @Column(name = "from_uid", nullable = false)
    public int getFromUid() {
        return this.fromUid;
    }
    
    public void setFromUid(final int fromUid) {
        this.fromUid = fromUid;
    }
    
    @Column(name = "type", nullable = false)
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    @Column(name = "subject", nullable = false)
    public String getSubject() {
        return this.subject;
    }
    
    public void setSubject(final String subject) {
        this.subject = subject;
    }
    
    @Column(name = "content", nullable = false, length = 16777215)
    public String getContent() {
        return this.content;
    }
    
    public void setContent(final String content) {
        this.content = content;
    }
    
    @Column(name = "time", nullable = false, length = 19)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    @Column(name = "to_status", nullable = false)
    public int getToStatus() {
        return this.toStatus;
    }
    
    public void setToStatus(final int toStatus) {
        this.toStatus = toStatus;
    }
    
    @Column(name = "from_status", nullable = false)
    public int getFromStatus() {
        return this.fromStatus;
    }
    
    public void setFromStatus(final int fromStatus) {
        this.fromStatus = fromStatus;
    }
}
