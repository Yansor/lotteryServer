package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="activity_red_packet_rain_ip", catalog="ecai")
public class ActivityRedPacketRainIp
  implements Serializable
{
  private int id;
  private String ip;
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id", unique=true, nullable=false)
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  @Column(name="ip", nullable=false, length=25, unique=true)
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
}
