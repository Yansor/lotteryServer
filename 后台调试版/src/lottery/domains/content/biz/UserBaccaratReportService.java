package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.vo.bill.UserBaccaratReportVO;

public abstract interface UserBaccaratReportService
{
  public abstract boolean update(int paramInt1, int paramInt2, double paramDouble, String paramString);
  
  public abstract List<UserBaccaratReportVO> report(String paramString1, String paramString2);
  
  public abstract List<UserBaccaratReportVO> report(int paramInt, String paramString1, String paramString2);
}
