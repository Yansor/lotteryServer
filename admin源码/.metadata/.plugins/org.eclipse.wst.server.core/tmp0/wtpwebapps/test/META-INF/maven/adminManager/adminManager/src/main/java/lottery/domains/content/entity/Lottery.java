package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "lottery", catalog = "ecai")
public class Lottery implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String showName;
    private String shortName;
    private int type;
    private int times;
    private int sort;
    private int status;
    private int self;
    private int dantiaoMaxPrize;
    private int expectMaxPrize;
    private int expectTrans;
    private String allowModels;
    private int display;
    
    public Lottery() {
    }
    
    public Lottery(final String showName, final int type, final int times, final int sort, final int status, final int self, final int dantiaoMaxPrize, final int expectMaxPrize) {
        this.showName = showName;
        this.type = type;
        this.times = times;
        this.sort = sort;
        this.status = status;
        this.self = self;
        this.dantiaoMaxPrize = dantiaoMaxPrize;
        this.expectMaxPrize = expectMaxPrize;
    }
    
    public Lottery(final String showName, final String shortName, final int type, final int times, final int sort, final int status, final int self, final int dantiaoMaxPrize, final int expectMaxPrize) {
        this.showName = showName;
        this.shortName = shortName;
        this.type = type;
        this.times = times;
        this.sort = sort;
        this.status = status;
        this.self = self;
        this.dantiaoMaxPrize = dantiaoMaxPrize;
        this.expectMaxPrize = expectMaxPrize;
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
    
    @Column(name = "show_name", nullable = false, length = 32)
    public String getShowName() {
        return this.showName;
    }
    
    public void setShowName(final String showName) {
        this.showName = showName;
    }
    
    @Column(name = "short_name", length = 32, nullable = false)
    public String getShortName() {
        return this.shortName;
    }
    
    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }
    
    @Column(name = "type", nullable = false)
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    @Column(name = "times", nullable = false)
    public int getTimes() {
        return this.times;
    }
    
    public void setTimes(final int times) {
        this.times = times;
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
    
    @Column(name = "self", nullable = false)
    public int getSelf() {
        return this.self;
    }
    
    public void setSelf(final int self) {
        this.self = self;
    }
    
    @Column(name = "dantiao_max_prize", nullable = false)
    public int getDantiaoMaxPrize() {
        return this.dantiaoMaxPrize;
    }
    
    public void setDantiaoMaxPrize(final int dantiaoMaxPrize) {
        this.dantiaoMaxPrize = dantiaoMaxPrize;
    }
    
    @Column(name = "expect_max_prize", nullable = false)
    public int getExpectMaxPrize() {
        return this.expectMaxPrize;
    }
    
    public void setExpectMaxPrize(final int expectMaxPrize) {
        this.expectMaxPrize = expectMaxPrize;
    }
    
    @Column(name = "expect_trans", nullable = false)
    public int getExpectTrans() {
        return this.expectTrans;
    }
    
    public void setExpectTrans(final int expectTrans) {
        this.expectTrans = expectTrans;
    }
    
    @Column(name = "allow_models", nullable = false)
    public String getAllowModels() {
        return this.allowModels;
    }
    
    public void setAllowModels(final String allowModels) {
        this.allowModels = allowModels;
    }
    
    @Column(name = "display", nullable = false)
    public int getDisplay() {
        return this.display;
    }
    
    public void setDisplay(final int display) {
        this.display = display;
    }
}
