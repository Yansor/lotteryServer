package admin.domains.content.entity;

import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "admin_user_menu", catalog = "ecai")
public class AdminUserMenu implements Serializable, Cloneable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String icon;
    private String link;
    private int upid;
    private int sort;
    private int status;
    private int baseAction;
    private String allActions;
    private ArrayList<AdminUserMenu> items;
    
    public AdminUserMenu clone() {
        try {
            final AdminUserMenu cloneBean = (AdminUserMenu)super.clone();
            cloneBean.items = (ArrayList<AdminUserMenu>)this.items.clone();
            return cloneBean;
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public AdminUserMenu() {
        this.items = new ArrayList<AdminUserMenu>();
    }
    
    public AdminUserMenu(final String name, final String icon, final String link, final int upid, final int sort, final int status) {
        this.items = new ArrayList<AdminUserMenu>();
        this.name = name;
        this.icon = icon;
        this.link = link;
        this.upid = upid;
        this.sort = sort;
        this.status = status;
    }
    
    public AdminUserMenu(final String name, final String icon, final String link, final int upid, final int sort, final int status, final int baseAction, final String allActions) {
        this.items = new ArrayList<AdminUserMenu>();
        this.name = name;
        this.icon = icon;
        this.link = link;
        this.upid = upid;
        this.sort = sort;
        this.status = status;
        this.baseAction = baseAction;
        this.allActions = allActions;
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
    
    @Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    @Column(name = "icon", nullable = false)
    public String getIcon() {
        return this.icon;
    }
    
    public void setIcon(final String icon) {
        this.icon = icon;
    }
    
    @Column(name = "link", nullable = false)
    public String getLink() {
        return this.link;
    }
    
    public void setLink(final String link) {
        this.link = link;
    }
    
    @Column(name = "upid", nullable = false)
    public int getUpid() {
        return this.upid;
    }
    
    public void setUpid(final int upid) {
        this.upid = upid;
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
    
    @Column(name = "base_action", nullable = false)
    public int getBaseAction() {
        return this.baseAction;
    }
    
    public void setBaseAction(final int baseAction) {
        this.baseAction = baseAction;
    }
    
    @Column(name = "all_actions", length = 65535)
    public String getAllActions() {
        return this.allActions;
    }
    
    public void setAllActions(final String allActions) {
        this.allActions = allActions;
    }
    
    @Transient
    public ArrayList<AdminUserMenu> getItems() {
        return this.items;
    }
    
    public void setItems(final ArrayList<AdminUserMenu> items) {
        this.items = items;
    }
}
