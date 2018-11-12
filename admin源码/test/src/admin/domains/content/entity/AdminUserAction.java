package admin.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "admin_user_action", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "key" }), @UniqueConstraint(columnNames = { "name" }) })
public class AdminUserAction implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String key;
    private String description;
    private int sort;
    private int status;
    
    public AdminUserAction() {
    }
    
    public AdminUserAction(final String name, final String key, final int sort, final int status) {
        this.name = name;
        this.key = key;
        this.sort = sort;
        this.status = status;
    }
    
    public AdminUserAction(final String name, final String key, final String description, final int sort, final int status) {
        this.name = name;
        this.key = key;
        this.description = description;
        this.sort = sort;
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
    
    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    @Column(name = "key", unique = true, nullable = false)
    public String getKey() {
        return this.key;
    }
    
    public void setKey(final String key) {
        this.key = key;
    }
    
    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    @Column(name = "sort", nullable = false)
    public int getSort() {
        return this.sort;
    }
    
    public void setSort(final int sort) {
        this.sort = sort;
    }
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
}
