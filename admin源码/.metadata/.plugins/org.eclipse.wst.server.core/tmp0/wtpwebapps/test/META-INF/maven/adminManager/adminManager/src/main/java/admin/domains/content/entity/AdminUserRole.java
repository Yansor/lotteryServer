package admin.domains.content.entity;

import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "admin_user_role", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class AdminUserRole implements Serializable, Cloneable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private int upid;
    private String description;
    private int sort;
    private int status;
    private String menus;
    private String actions;
    private ArrayList<AdminUserRole> items;
    
    public AdminUserRole clone() {
        try {
            final AdminUserRole cloneBean = (AdminUserRole)super.clone();
            cloneBean.items = (ArrayList<AdminUserRole>)this.items.clone();
            return cloneBean;
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public AdminUserRole() {
        this.items = new ArrayList<AdminUserRole>();
    }
    
    public AdminUserRole(final String name, final int upid, final int sort, final int status) {
        this.items = new ArrayList<AdminUserRole>();
        this.name = name;
        this.upid = upid;
        this.sort = sort;
        this.status = status;
    }
    
    public AdminUserRole(final String name, final int upid, final String description, final int sort, final int status, final String menus, final String actions) {
        this.items = new ArrayList<AdminUserRole>();
        this.name = name;
        this.upid = upid;
        this.description = description;
        this.sort = sort;
        this.status = status;
        this.menus = menus;
        this.actions = actions;
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
    
    @Column(name = "upid", nullable = false)
    public int getUpid() {
        return this.upid;
    }
    
    public void setUpid(final int upid) {
        this.upid = upid;
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
    
    @Column(name = "menus", length = 65535)
    public String getMenus() {
        return this.menus;
    }
    
    public void setMenus(final String menus) {
        this.menus = menus;
    }
    
    @Column(name = "actions", length = 65535)
    public String getActions() {
        return this.actions;
    }
    
    public void setActions(final String actions) {
        this.actions = actions;
    }
    
    @Transient
    public ArrayList<AdminUserRole> getItems() {
        return this.items;
    }
    
    public void setItems(final ArrayList<AdminUserRole> items) {
        this.items = items;
    }
}
