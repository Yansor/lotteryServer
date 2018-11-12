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
@Table(name = "lottery_play_rules", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "type_id", "code" }) })
public class LotteryPlayRules implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int groupId;
    private int typeId;
    private String name;
    private String code;
    private String minNum;
    private String maxNum;
    private String totalNum;
    private int status;
    private int fixed;
    private String prize;
    private String desc;
    private String dantiao;
    private int isLocate;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "group_id", nullable = false)
    public int getGroupId() {
        return this.groupId;
    }
    
    public void setGroupId(final int groupId) {
        this.groupId = groupId;
    }
    
    @Column(name = "type_id", nullable = false)
    public int getTypeId() {
        return this.typeId;
    }
    
    public void setTypeId(final int typeId) {
        this.typeId = typeId;
    }
    
    @Column(name = "name", nullable = false, length = 128)
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    @Column(name = "code", nullable = false, length = 128)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(final String code) {
        this.code = code;
    }
    
    @Column(name = "min_num", length = 128)
    public String getMinNum() {
        return this.minNum;
    }
    
    public void setMinNum(final String minNum) {
        this.minNum = minNum;
    }
    
    @Column(name = "max_num", length = 128)
    public String getMaxNum() {
        return this.maxNum;
    }
    
    public void setMaxNum(final String maxNum) {
        this.maxNum = maxNum;
    }
    
    @Column(name = "total_num", length = 128)
    public String getTotalNum() {
        return this.totalNum;
    }
    
    public void setTotalNum(final String totalNum) {
        this.totalNum = totalNum;
    }
    
    @Column(name = "status")
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "fixed", nullable = false)
    public int getFixed() {
        return this.fixed;
    }
    
    public void setFixed(final int fixed) {
        this.fixed = fixed;
    }
    
    @Column(name = "prize", nullable = false, length = 512)
    public String getPrize() {
        return this.prize;
    }
    
    public void setPrize(final String prize) {
        this.prize = prize;
    }
    
    @Column(name = "desc", length = 256)
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(final String desc) {
        this.desc = desc;
    }
    
    @Column(name = "dantiao", length = 128)
    public String getDantiao() {
        return this.dantiao;
    }
    
    public void setDantiao(final String dantiao) {
        this.dantiao = dantiao;
    }
    
    @Column(name = "is_locate", nullable = false)
    public int getIsLocate() {
        return this.isLocate;
    }
    
    public void setIsLocate(final int isLocate) {
        this.isLocate = isLocate;
    }
}
