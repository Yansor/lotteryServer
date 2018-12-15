package lottery.domains.content.biz;

import admin.web.WebJSONObject;
import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.LotteryOpenCode;
import lottery.domains.content.vo.lottery.LotteryOpenCodeVO;

public abstract interface LotteryOpenCodeService
{
  public abstract PageList search(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract LotteryOpenCodeVO get(String paramString1, String paramString2);
  
  public abstract boolean add(WebJSONObject paramWebJSONObject, String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract boolean delete(LotteryOpenCode paramLotteryOpenCode);
  
  public abstract int countByInterfaceTime(String paramString1, String paramString2, String paramString3);
  
  public abstract LotteryOpenCode getFirstExpectByInterfaceTime(String paramString1, String paramString2, String paramString3);
  
  public abstract List<LotteryOpenCode> getLatest(String paramString, int paramInt);
}
