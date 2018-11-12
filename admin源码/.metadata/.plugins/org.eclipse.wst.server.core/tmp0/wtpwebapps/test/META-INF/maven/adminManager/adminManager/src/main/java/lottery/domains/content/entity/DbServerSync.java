package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "db_server_sync", catalog = "ecai")
public class DbServerSync implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String key;
    private String lastModTime;
    private String desc;
    
    public DbServerSync() {
    }
    
    public DbServerSync(final String lastModTime, final String desc) {
        this.lastModTime = lastModTime;
        this.desc = desc;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`key`", unique = true, nullable = false, length = 128)
    public String getKey() {
        return this.key;
    }
    
    public void setKey(final String key) {
        this.key = key;
    }
    
    @Column(name = "last_mod_time", length = 19)
    public String getLastModTime() {
        return this.lastModTime;
    }
    
    public void setLastModTime(final String lastModTime) {
        this.lastModTime = lastModTime;
    }
    
    @Column(name = "`desc`")
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(final String desc) {
        this.desc = desc;
    }
}
