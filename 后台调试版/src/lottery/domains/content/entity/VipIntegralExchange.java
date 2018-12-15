package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vip_integral_exchange", catalog="ecai")
public class VipIntegralExchange
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private int userId;
  private double integral;
  private double money;
  private String time;
  
  public VipIntegralExchange() {}
  
  public VipIntegralExchange(int userId, double integral, double money, String time)
  {
    this.userId = userId;
    this.integral = integral;
    this.money = money;
    this.time = time;
  }
  
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
  
  @Column(name="user_id", nullable=false)
  public int getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(int userId)
  {
    this.userId = userId;
  }
  
  @Column(name="integral", nullable=false, precision=16, scale=5)
  public double getIntegral()
  {
    return this.integral;
  }
  
  public void setIntegral(double integral)
  {
    this.integral = integral;
  }
  
  @Column(name="money", nullable=false, precision=16, scale=5)
  public double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(double money)
  {
    this.money = money;
  }
  
  @Column(name="time", nullable=false, length=19)
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
}
