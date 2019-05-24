package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.vo.bill.UserMainReportVO;

public abstract interface UserMainReportService
{
  public abstract boolean update(int paramInt1, int paramInt2, double paramDouble, String paramString);
  
  public abstract List<UserMainReportVO> report(String paramString1, String paramString2);
  
  public abstract List<UserMainReportVO> report(int paramInt, String paramString1, String paramString2);
}
