package lottery.domains.content.vo.config;

import java.util.ArrayList;
import java.util.List;
import lottery.domains.content.vo.user.SysCodeRangeVO;

public class CodeConfig
{
  private int sysCode;
  private double sysLp;
  private double sysNlp;
  private int notCreateAccount;
  private List<SysCodeRangeVO> sysCodeRange = new ArrayList();
  
  public int getSysCode()
  {
    return this.sysCode;
  }
  
  public void setSysCode(int sysCode)
  {
    this.sysCode = sysCode;
  }
  
  public double getSysLp()
  {
    return this.sysLp;
  }
  
  public void setSysLp(double sysLp)
  {
    this.sysLp = sysLp;
  }
  
  public double getSysNlp()
  {
    return this.sysNlp;
  }
  
  public void setSysNlp(double sysNlp)
  {
    this.sysNlp = sysNlp;
  }
  
  public List<SysCodeRangeVO> getSysCodeRange()
  {
    return this.sysCodeRange;
  }
  
  public void setSysCodeRange(List<SysCodeRangeVO> sysCodeRange)
  {
    this.sysCodeRange = sysCodeRange;
  }
  
  public int getNotCreateAccount()
  {
    return this.notCreateAccount;
  }
  
  public void setNotCreateAccount(int notCreateAccount)
  {
    this.notCreateAccount = notCreateAccount;
  }
}
