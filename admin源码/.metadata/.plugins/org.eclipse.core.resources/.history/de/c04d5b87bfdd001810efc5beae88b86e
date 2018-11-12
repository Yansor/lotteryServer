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
@Table(name = "sys_platform", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class SysPlatform implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private int upid;
    private int status;
    
    public SysPlatform() {
    }
    
    public SysPlatform(final String name, final int upid, final int status) {
        this.name = name;
        this.upid = upid;
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
    
    @Column(name = "name", unique = true, nullable = false, length = 128)
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    @Column(name = "upid", nullable = false)
    public int getUpid() {
        return this.upid;
    }
    
    public void setUpid(final int upid) {
        this.upid = upid;
    }
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
}
