package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.vo.chart.ChartLineVO;
import lottery.domains.content.vo.chart.ChartPieVO;
import lottery.domains.content.vo.chart.RechargeWithdrawTotal;

public abstract interface LotteryStatService
{
  public abstract int getTotalUserRegist(String paramString1, String paramString2);
  
  public abstract long getTotalBetsMoney(String paramString1, String paramString2);
  
  public abstract int getTotalOrderCount(String paramString1, String paramString2);
  
  public abstract double getTotalProfitMoney(String paramString1, String paramString2);
  
  public abstract RechargeWithdrawTotal getTotalRechargeWithdrawData(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2);
  
  public abstract List<ChartLineVO> getRechargeWithdrawDataChart(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2);
  
  public abstract ChartLineVO getUserRegistChart(String paramString1, String paramString2);
  
  public abstract ChartLineVO getUserLoginChart(String paramString1, String paramString2);
  
  public abstract ChartLineVO getUserBetsChart(Integer paramInteger1, Integer paramInteger2, String paramString1, String paramString2);
  
  public abstract ChartLineVO getUserCashChart(String paramString1, String paramString2);
  
  public abstract ChartLineVO getUserComplexChart(Integer paramInteger1, Integer paramInteger2, String paramString1, String paramString2);
  
  public abstract ChartPieVO getLotteryHotChart(Integer paramInteger, String paramString1, String paramString2);
}
