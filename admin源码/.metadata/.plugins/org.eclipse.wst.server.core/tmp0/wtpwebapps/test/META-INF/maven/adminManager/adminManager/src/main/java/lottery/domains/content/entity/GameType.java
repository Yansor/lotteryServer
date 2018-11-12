package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "game_type", catalog = "ecai")
public class GameType implements Serializable
{
    private int id;
    private String typeName;
    private int platformId;
    private int sequence;
    private int display;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "type_name", nullable = false, length = 50)
    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }
    
    @Column(name = "platform_id", nullable = false)
    public int getPlatformId() {
        return this.platformId;
    }
    
    public void setPlatformId(final int platformId) {
        this.platformId = platformId;
    }
    
    @Column(name = "sequence", nullable = false)
    public int getSequence() {
        return this.sequence;
    }
    
    public void setSequence(final int sequence) {
        this.sequence = sequence;
    }
    
    @Column(name = "display", nullable = false)
    public int getDisplay() {
        return this.display;
    }
    
    public void setDisplay(final int display) {
        this.display = display;
    }
}
