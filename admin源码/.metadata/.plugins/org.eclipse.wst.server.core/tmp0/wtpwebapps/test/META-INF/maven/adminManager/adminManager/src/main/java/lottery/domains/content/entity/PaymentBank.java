package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "payment_bank", catalog = "ecai")
public class PaymentBank implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String url;
    
    public PaymentBank() {
    }
    
    public PaymentBank(final String name, final String url) {
        this.name = name;
        this.url = url;
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
    
    @Column(name = "url", nullable = false)
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
}
