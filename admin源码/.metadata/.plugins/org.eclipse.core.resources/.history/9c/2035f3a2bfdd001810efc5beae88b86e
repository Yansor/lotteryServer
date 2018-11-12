package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "game", catalog = "ecai")
public class Game implements Serializable
{
    private int id;
    private String gameName;
    private String gameCode;
    private int typeId;
    private int platformId;
    private String imgUrl;
    private int sequence;
    private int display;
    private int flashSupport;
    private int h5Support;
    private int progressiveSupport;
    private String progressiveCode;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "game_name", nullable = false, length = 128)
    public String getGameName() {
        return this.gameName;
    }
    
    public void setGameName(final String gameName) {
        this.gameName = gameName;
    }
    
    @Column(name = "game_code", nullable = false, length = 128)
    public String getGameCode() {
        return this.gameCode;
    }
    
    public void setGameCode(final String gameCode) {
        this.gameCode = gameCode;
    }
    
    @Column(name = "type_id", nullable = false)
    public int getTypeId() {
        return this.typeId;
    }
    
    public void setTypeId(final int typeId) {
        this.typeId = typeId;
    }
    
    @Column(name = "platform_id", nullable = false)
    public int getPlatformId() {
        return this.platformId;
    }
    
    public void setPlatformId(final int platformId) {
        this.platformId = platformId;
    }
    
    @Column(name = "img_url", nullable = false, length = 128)
    public String getImgUrl() {
        return this.imgUrl;
    }
    
    public void setImgUrl(final String imgUrl) {
        this.imgUrl = imgUrl;
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
    
    @Column(name = "flash_support", nullable = false)
    public int getFlashSupport() {
        return this.flashSupport;
    }
    
    public void setFlashSupport(final int flashSupport) {
        this.flashSupport = flashSupport;
    }
    
    @Column(name = "h5_support", nullable = false)
    public int getH5Support() {
        return this.h5Support;
    }
    
    public void setH5Support(final int h5Support) {
        this.h5Support = h5Support;
    }
    
    @Column(name = "progressive_support", nullable = false)
    public int getProgressiveSupport() {
        return this.progressiveSupport;
    }
    
    public void setProgressiveSupport(final int progressiveSupport) {
        this.progressiveSupport = progressiveSupport;
    }
    
    @Column(name = "progressive_code", length = 128)
    public String getProgressiveCode() {
        return this.progressiveCode;
    }
    
    public void setProgressiveCode(final String progressiveCode) {
        this.progressiveCode = progressiveCode;
    }
}
