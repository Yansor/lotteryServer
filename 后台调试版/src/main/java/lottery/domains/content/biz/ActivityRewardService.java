package lottery.domains.content.biz;

import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.vo.activity.ActivityRewardBillVO;

public abstract interface ActivityRewardService
{
  public abstract PageList search(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, int paramInt1, int paramInt2);
  
  public abstract boolean add(int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, String paramString);
  
  public abstract List<ActivityRewardBillVO> getLatest(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract boolean agreeAll(String paramString);
  
  public abstract boolean calculate(int paramInt, String paramString);
}
