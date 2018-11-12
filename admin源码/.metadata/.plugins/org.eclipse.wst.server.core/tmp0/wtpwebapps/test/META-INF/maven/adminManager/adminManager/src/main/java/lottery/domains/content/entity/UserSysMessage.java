package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "user_sys_message", catalog = "ecai")
public class UserSysMessage implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private int type;
    private String content;
    private String time;
    private int status;
    
    public UserSysMessage() {
    }
    
    public UserSysMessage(final int userId, final int type, final String time, final int status) {
        this.userId = userId;
        this.type = type;
        this.time = time;
        this.status = status;
    }
    
    public UserSysMessage(final int userId, final int type, final String content, final String time, final int status) {
        this.userId = userId;
        this.type = type;
        this.content = content;
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
    
    @Column(name = "userId", nullable = false)
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
    
    @Column(name = "content", length = 65535)
    public String getContent() {
        return this.content;
    }
    
    public void setContent(final String content) {
        this.content = content;
    }
    
    @Column(name = "time", nullable = false, length = 20)
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
}
