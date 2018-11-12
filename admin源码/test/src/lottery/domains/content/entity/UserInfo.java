package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "user_info", catalog = "ecai")
public class UserInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int userId;
    private Integer gender;
    private String cellphone;
    private String birthday;
    private String qq;
    private String email;
    private String postscript;
    
    public UserInfo() {
    }
    
    public UserInfo(final Integer gender, final String cellphone, final String birthday, final String qq, final String email, final String postscript) {
        this.gender = gender;
        this.cellphone = cellphone;
        this.birthday = birthday;
        this.qq = qq;
        this.email = email;
        this.postscript = postscript;
    }
    
    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    @Column(name = "gender")
    public Integer getGender() {
        return this.gender;
    }
    
    public void setGender(final Integer gender) {
        this.gender = gender;
    }
    
    @Column(name = "cellphone", length = 32)
    public String getCellphone() {
        return this.cellphone;
    }
    
    public void setCellphone(final String cellphone) {
        this.cellphone = cellphone;
    }
    
    @Column(name = "birthday", length = 10)
    public String getBirthday() {
        return this.birthday;
    }
    
    public void setBirthday(final String birthday) {
        this.birthday = birthday;
    }
    
    @Column(name = "qq", length = 16)
    public String getQq() {
        return this.qq;
    }
    
    public void setQq(final String qq) {
        this.qq = qq;
    }
    
    @Column(name = "email", length = 128)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(final String email) {
        this.email = email;
    }
    
    @Column(name = "postscript", length = 128)
    public String getPostscript() {
        return this.postscript;
    }
    
    public void setPostscript(final String postscript) {
        this.postscript = postscript;
    }
}
