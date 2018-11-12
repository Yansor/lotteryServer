package lottery.domains.content.biz;

import lottery.domains.content.vo.chart.ChartPieVO;
import lottery.domains.content.vo.chart.ChartLineVO;
import java.util.List;
import lottery.domains.content.vo.chart.RechargeWithdrawTotal;

public interface LotteryStatService
{
    int getTotalUserRegist(final String p0, final String p1);
    
    long getTotalBetsMoney(final String p0, final String p1);
    
    int getTotalOrderCount(final String p0, final String p1);
    
    long getTotalProfitMoney(final String p0, final String p1);
    
    RechargeWithdrawTotal getTotalRechargeWithdrawData(final String p0, final String p1, final Integer p2, final Integer p3);
    
    List<ChartLineVO> getRechargeWithdrawDataChart(final String p0, final String p1, final Integer p2, final Integer p3);
    
    ChartLineVO getUserRegistChart(final String p0, final String p1);
    
    ChartLineVO getUserLoginChart(final String p0, final String p1);
    
    ChartLineVO getUserBetsChart(final Integer p0, final Integer p1, final String p2, final String p3);
    
    ChartLineVO getUserCashChart(final String p0, final String p1);
    
    ChartLineVO getUserComplexChart(final Integer p0, final Integer p1, final String p2, final String p3);
    
    ChartPieVO getLotteryHotChart(final Integer p0, final String p1, final String p2);
}
