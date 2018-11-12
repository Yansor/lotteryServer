package lottery.domains.content.vo.config;

import java.util.ArrayList;
import lottery.domains.content.vo.user.SysCodeRangeVO;
import java.util.List;

public class CodeConfig
{
    private int sysCode;
    private double sysLp;
    private double sysNlp;
    private int notCreateAccount;
    private List<SysCodeRangeVO> sysCodeRange;
    
    public CodeConfig() {
        this.sysCodeRange = new ArrayList<SysCodeRangeVO>();
    }
    
    public int getSysCode() {
        return this.sysCode;
    }
    
    public void setSysCode(final int sysCode) {
        this.sysCode = sysCode;
    }
    
    public double getSysLp() {
        return this.sysLp;
    }
    
    public void setSysLp(final double sysLp) {
        this.sysLp = sysLp;
    }
    
    public double getSysNlp() {
        return this.sysNlp;
    }
    
    public void setSysNlp(final double sysNlp) {
        this.sysNlp = sysNlp;
    }
    
    public List<SysCodeRangeVO> getSysCodeRange() {
        return this.sysCodeRange;
    }
    
    public void setSysCodeRange(final List<SysCodeRangeVO> sysCodeRange) {
        this.sysCodeRange = sysCodeRange;
    }
    
    public int getNotCreateAccount() {
        return this.notCreateAccount;
    }
    
    public void setNotCreateAccount(final int notCreateAccount) {
        this.notCreateAccount = notCreateAccount;
    }
}
