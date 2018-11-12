package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "user_security", catalog = "ecai")
public class UserSecurity implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private String key;
    private String value;
    
    public UserSecurity() {
    }
    
    public UserSecurity(final int userId, final String key, final String value) {
        this.userId = userId;
        this.key = key;
        this.value = value;
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
    
    @Column(name = "`key`", nullable = false, length = 128)
    public String getKey() {
        return this.key;
    }
    
    public void setKey(final String key) {
        this.key = key;
    }
    
    @Column(name = "`value`", nullable = false, length = 128)
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
}
