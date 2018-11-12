package admin.domains.content.vo;

public class AdminUserBaseVO
{
    private int id;
    private String username;
    
    public AdminUserBaseVO() {
    }
    
    public AdminUserBaseVO(final int id, final String username) {
        this.id = id;
        this.username = username;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
}
