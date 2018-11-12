package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "lottery_type", catalog = "ecai")
public class LotteryType implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private int sort;
    private int status;
    
    public LotteryType() {
    }
    
    public LotteryType(final String name, final int sort, final int status) {
        this.name = name;
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
    
    @Column(name = "name", nullable = false, length = 128)
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    @Column(name = "`sort`", nullable = false)
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
