package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javautils.date.DateRangeUtil;
import javautils.date.Moment;
import javautils.math.MathUtil;
import lottery.domains.content.biz.LotteryStatService;
import lottery.domains.content.biz.UserLotteryReportService;
import lottery.domains.content.dao.UserBetsDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserLoginLogDao;
import lottery.domains.content.dao.UserRechargeDao;
import lottery.domains.content.dao.UserWithdrawDao;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import lottery.domains.content.vo.chart.ChartLineVO;
import lottery.domains.content.vo.chart.ChartPieVO;
import lottery.domains.content.vo.chart.RechargeWithdrawTotal;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryStatServiceImpl
  implements LotteryStatService
{
  private static final Logger log = LoggerFactory.getLogger(LotteryStatServiceImpl.class);
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserBetsDao uBetsDao;
  @Autowired
  private UserRechargeDao uRechargeDao;
  @Autowired
  private UserWithdrawDao uWithdrawDao;
  @Autowired
  private UserLoginLogDao uLoginLogDao;
  @Autowired
  private UserLotteryReportService uLotteryReportService;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public int getTotalUserRegist(String sTime, String eTime)
  {
    return this.uDao.getTotalUserRegist(sTime, eTime);
  }
  
  public long getTotalBetsMoney(String sTime, String eTime)
  {
    return this.uBetsDao.getTotalBetsMoney(sTime, eTime);
  }
  
  public int getTotalOrderCount(String sTime, String eTime)
  {
    return this.uBetsDao.getTotalOrderCount(sTime, eTime);
  }
  
  public double getTotalProfitMoney(String sTime, String eTime)
  {
    UserLotteryReportVO rBean = (UserLotteryReportVO)this.uLotteryReportService.report(sTime, eTime).get(0);
    return (rBean.getPrize() + rBean.getSpendReturn() + rBean.getProxyReturn() + rBean.getActivity() - rBean.getBillingOrder());
  }
  
  public RechargeWithdrawTotal getTotalRechargeWithdrawData(String sDate, String eDate, Integer type, Integer subtype)
  {
    String sTime = new Moment().fromDate(sDate).toSimpleDate();
    String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
    
    Object[] rechargeData = this.uRechargeDao.getTotalRechargeData(sTime, eTime, type, subtype);
    
    int totalRechargeCount = rechargeData == null ? 0 : Integer.valueOf(rechargeData[0].toString()).intValue();
    
    double totalRechargeMoney = rechargeData == null ? 0.0D : ((Double)rechargeData[1]).doubleValue();
    
    double totalReceiveFeeMoney = rechargeData == null ? 0.0D : ((Double)rechargeData[2]).doubleValue();
    
    Object[] withdrawData = this.uWithdrawDao.getTotalWithdrawData(sTime, eTime);
    
    int totalWithdrawCount = withdrawData == null ? 0 : Integer.valueOf(withdrawData[0].toString()).intValue();
    
    double totalWithdrawMoney = withdrawData == null ? 0.0D : ((Double)withdrawData[1]).doubleValue();
    
    double totalActualReceiveMoney = MathUtil.subtract(totalRechargeMoney, totalReceiveFeeMoney);
    
    double totalRechargeWithdrawDiff = MathUtil.subtract(totalWithdrawMoney, totalActualReceiveMoney);
    
    return new RechargeWithdrawTotal(totalRechargeCount, totalRechargeMoney, totalReceiveFeeMoney, 
      totalWithdrawCount, totalWithdrawMoney, totalActualReceiveMoney, totalRechargeWithdrawDiff);
  }
  
  public List<ChartLineVO> getRechargeWithdrawDataChart(String sDate, String eDate, Integer type, Integer subtype)
  {
    String sTime = new Moment().fromDate(sDate).toSimpleDate();
    String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
    
    String[] dates = DateRangeUtil.listDate(sDate, eDate);
    List<ChartLineVO> lineVOs = new LinkedList();
    try
    {
      List<?> rechargeList = this.uRechargeDao.getDayRecharge2(sTime, eTime, type, subtype);
      List<?> withdrawList = this.uWithdrawDao.getDayWithdraw2(sTime, eTime);
      
      Map<String, Object[]> rechargeMap = new HashMap();
      Map<String, Object[]> withdrawMap = new HashMap();
      if (CollectionUtils.isNotEmpty(rechargeList)) {
        for (Object o : rechargeList)
        {
          Object[] arr = (Object[])o;
          String date = (String)arr[0];
          rechargeMap.put(date, arr);
        }
      }
      if (CollectionUtils.isNotEmpty(withdrawList)) {
        for (Object o : withdrawList)
        {
          Object[] arr = (Object[])o;
          String date = (String)arr[0];
          withdrawMap.put(date, arr);
        }
      }
      ChartLineVO receiveFeeMoneyLineVO = new ChartLineVO();
      receiveFeeMoneyLineVO.setxAxis(dates);
      Number[] receiveFeeMoneyYAxis = new Number[dates.length];
      
      ChartLineVO rechargeWithdrawDiffLineVO = new ChartLineVO();
      rechargeWithdrawDiffLineVO.setxAxis(dates);
      Number[] rechargeWithdrawDiffYAxis = new Number[dates.length];
      
      ChartLineVO actualReceiveMoneyLineVO = new ChartLineVO();
      actualReceiveMoneyLineVO.setxAxis(dates);
      Number[] actualReceiveMoneyYAxis = new Number[dates.length];
      
      ChartLineVO rechargeMoneyLineVO = new ChartLineVO();
      rechargeMoneyLineVO.setxAxis(dates);
      Number[] rechargeMoneyYAxis = new Number[dates.length];
      
      ChartLineVO withdrawMoneyLineVO = new ChartLineVO();
      withdrawMoneyLineVO.setxAxis(dates);
      Number[] withdrawMoneyYAxis = new Number[dates.length];
      
      ChartLineVO rechargeCountLineVO = new ChartLineVO();
      rechargeCountLineVO.setxAxis(dates);
      Number[] rechargeCountYAxis = new Number[dates.length];
      
      ChartLineVO withdrawCountLineVO = new ChartLineVO();
      withdrawCountLineVO.setxAxis(dates);
      Number[] withdrawCountYAxis = new Number[dates.length];
      for (int i = 0; i < dates.length; i++)
      {
        String date = dates[i];
        Number receiveFeeMoney = Integer.valueOf(0);
        Number rechargeWithdrawDiff = Integer.valueOf(0);
        Number actualReceiveMoney = Integer.valueOf(0);
        Number rechargeMoney = Integer.valueOf(0);
        Number withdrawMoney = Integer.valueOf(0);
        Number rechargeCount = Integer.valueOf(0);
        Number withdrawCount = Integer.valueOf(0);
        if (rechargeMap.containsKey(date))
        {
          Object[] arr = (Object[])rechargeMap.get(date);
          rechargeCount = Integer.valueOf(((Number)arr[1]).intValue());
          rechargeMoney = Integer.valueOf(((Number)arr[2]).intValue());
          receiveFeeMoney = Integer.valueOf(((Number)arr[3]).intValue());
          actualReceiveMoney = Integer.valueOf(rechargeMoney.intValue() - receiveFeeMoney.intValue());
          rechargeWithdrawDiff = Integer.valueOf(-actualReceiveMoney.intValue());
        }
        if (withdrawMap.containsKey(date))
        {
          Object[] arr = (Object[])withdrawMap.get(date);
          withdrawCount = Integer.valueOf(((Number)arr[1]).intValue());
          withdrawMoney = Integer.valueOf(((Number)arr[2]).intValue());
          rechargeWithdrawDiff = Integer.valueOf(withdrawMoney.intValue() + rechargeWithdrawDiff.intValue());
        }
        receiveFeeMoneyYAxis[i] = receiveFeeMoney;
        rechargeWithdrawDiffYAxis[i] = rechargeWithdrawDiff;
        actualReceiveMoneyYAxis[i] = actualReceiveMoney;
        rechargeMoneyYAxis[i] = rechargeMoney;
        withdrawMoneyYAxis[i] = withdrawMoney;
        rechargeCountYAxis[i] = rechargeCount;
        withdrawCountYAxis[i] = withdrawCount;
      }
      receiveFeeMoneyLineVO.getyAxis().add(receiveFeeMoneyYAxis);
      rechargeWithdrawDiffLineVO.getyAxis().add(rechargeWithdrawDiffYAxis);
      actualReceiveMoneyLineVO.getyAxis().add(actualReceiveMoneyYAxis);
      rechargeMoneyLineVO.getyAxis().add(rechargeMoneyYAxis);
      withdrawMoneyLineVO.getyAxis().add(withdrawMoneyYAxis);
      rechargeCountLineVO.getyAxis().add(rechargeCountYAxis);
      withdrawCountLineVO.getyAxis().add(withdrawCountYAxis);
      
      lineVOs.add(receiveFeeMoneyLineVO);
      lineVOs.add(rechargeWithdrawDiffLineVO);
      lineVOs.add(actualReceiveMoneyLineVO);
      lineVOs.add(rechargeMoneyLineVO);
      lineVOs.add(withdrawMoneyLineVO);
      lineVOs.add(rechargeCountLineVO);
      lineVOs.add(withdrawCountLineVO);
    }
    catch (Exception e)
    {
      log.error("统计充提报表时出错", e);
    }
    return lineVOs;
  }
  
  public ChartLineVO getUserRegistChart(String sDate, String eDate)
  {
    String sTime = new Moment().fromDate(sDate).toSimpleDate();
    String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
    String[] xAxis = DateRangeUtil.listDate(sDate, eDate);
    ChartLineVO lineVO = new ChartLineVO();
    lineVO.setxAxis(xAxis);
    
    List<?> list = this.uDao.getDayUserRegist(sTime, eTime);
    Map<String, Number> tmpMap = new HashMap();
    if (list != null) {
      for (Object o : list)
      {
        Object[] arr = (Object[])o;
        String date = (String)arr[0];
        Number count = (Number)arr[1];
        tmpMap.put(date, count);
      }
    }
    Number[] yAxis = new Number[xAxis.length];
    for (int i = 0; i < xAxis.length; i++) {
      if (tmpMap.containsKey(xAxis[i])) {
        yAxis[i] = ((Number)tmpMap.get(xAxis[i]));
      } else {
        yAxis[i] = Integer.valueOf(0);
      }
    }
    lineVO.getyAxis().add(yAxis);
    return lineVO;
  }
  
  public ChartLineVO getUserLoginChart(String sDate, String eDate)
  {
    String sTime = new Moment().fromDate(sDate).toSimpleDate();
    String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
    String[] xAxis = DateRangeUtil.listDate(sDate, eDate);
    ChartLineVO lineVO = new ChartLineVO();
    lineVO.setxAxis(xAxis);
    
    List<?> list = this.uLoginLogDao.getDayUserLogin(sTime, eTime);
    Map<String, Number> tmpMap = new HashMap();
    if (list != null) {
      for (Object o : list)
      {
        Object[] arr = (Object[])o;
        String date = (String)arr[0];
        Number count = (Number)arr[1];
        tmpMap.put(date, count);
      }
    }
    Number[] yAxis = new Number[xAxis.length];
    for (int i = 0; i < xAxis.length; i++) {
      if (tmpMap.containsKey(xAxis[i])) {
        yAxis[i] = ((Number)tmpMap.get(xAxis[i]));
      } else {
        yAxis[i] = Integer.valueOf(0);
      }
    }
    lineVO.getyAxis().add(yAxis);
    return lineVO;
  }
  
  public ChartLineVO getUserBetsChart(Integer type, Integer id, String sDate, String eDate)
  {
    String sTime = new Moment().fromDate(sDate).toSimpleDate();
    String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
    
    List<Lottery> lotteries = new ArrayList();
    if (id != null)
    {
      Lottery tmpLottery = this.lotteryDataFactory.getLottery(id.intValue());
      if (tmpLottery != null) {
        lotteries.add(tmpLottery);
      }
    }
    else if (type != null)
    {
      lotteries = this.lotteryDataFactory.listLottery(type.intValue());
    }
    int[] lids = new int[lotteries.size()];
    for (int i = 0; i < lotteries.size(); i++) {
      lids[i] = ((Lottery)lotteries.get(i)).getId();
    }
    String[] xAxis = DateRangeUtil.listDate(sDate, eDate);
    ChartLineVO lineVO = new ChartLineVO();
    lineVO.setxAxis(xAxis);
    
    List<?> list = this.uBetsDao.getDayUserBets(lids, sTime, eTime);
    Map<String, Number> tmpMap = new HashMap();
    if (list != null) {
      for (Object o : list)
      {
        Object[] arr = (Object[])o;
        String date = (String)arr[0];
        Number count = (Number)arr[1];
        tmpMap.put(date, count);
      }
    }
    Number[] yAxis = new Number[xAxis.length];
    for (int i = 0; i < xAxis.length; i++) {
      if (tmpMap.containsKey(xAxis[i])) {
        yAxis[i] = ((Number)tmpMap.get(xAxis[i]));
      } else {
        yAxis[i] = Integer.valueOf(0);
      }
    }
    lineVO.getyAxis().add(yAxis);
    return lineVO;
  }
  
  public ChartLineVO getUserCashChart(String sDate, String eDate)
  {
    String sTime = new Moment().fromDate(sDate).toSimpleDate();
    String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
    String[] xAxis = DateRangeUtil.listDate(sDate, eDate);
    ChartLineVO lineVO = new ChartLineVO();
    lineVO.setxAxis(xAxis);
    Number[] yAxis;
    try
    {
      List<?> list = this.uRechargeDao.getDayRecharge(sTime, eTime);
      Map<String, Number> tmpMap = new HashMap();
      
      Map<String, Number> receiveFeeMap = new HashMap();
      if (list != null) {
        for (Object o : list)
        {
          Object[] arr = (Object[])o;
          String date = (String)arr[0];
          Number count = Integer.valueOf(((Number)arr[1]).intValue());
          Number receiveFee = Integer.valueOf(((Number)arr[2]).intValue());
          tmpMap.put(date, count);
          receiveFeeMap.put(date, receiveFee);
        }
      }
      yAxis = new Number[xAxis.length];
      for (int i = 0; i < xAxis.length; i++) {
        if (tmpMap.containsKey(xAxis[i])) {
          yAxis[i] = ((Number)tmpMap.get(xAxis[i]));
        } else {
          yAxis[i] = Integer.valueOf(0);
        }
      }
      lineVO.getyAxis().add(yAxis);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    try
    {
      List<?> list = this.uWithdrawDao.getDayWithdraw(sTime, eTime);
      Map<String, Number> tmpMap = new HashMap();
      if (list != null) {
        for (Object o : list)
        {
          Object[] arr = (Object[])o;
          String date = (String)arr[0];
          Number count = Integer.valueOf(((Number)arr[1]).intValue());
          tmpMap.put(date, count);
        }
      }
      yAxis = new Number[xAxis.length];
      for (int i = 0; i < xAxis.length; i++) {
        if (tmpMap.containsKey(xAxis[i])) {
          yAxis[i] = ((Number)tmpMap.get(xAxis[i]));
        } else {
          yAxis[i] = Integer.valueOf(0);
        }
      }
      lineVO.getyAxis().add(yAxis);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return lineVO;
  }
  
  public ChartLineVO getUserComplexChart(Integer type, Integer id, String sDate, String eDate)
  {
    String sTime = new Moment().fromDate(sDate).toSimpleDate();
    String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
    
    List<Lottery> lotteries = new ArrayList();
    if (id != null)
    {
      Lottery tmpLottery = this.lotteryDataFactory.getLottery(id.intValue());
      if (tmpLottery != null) {
        lotteries.add(tmpLottery);
      }
    }
    else if (type != null)
    {
      lotteries = this.lotteryDataFactory.listLottery(type.intValue());
    }
    int[] lids = new int[lotteries.size()];
    for (int i = 0; i < lotteries.size(); i++) {
      lids[i] = ((Lottery)lotteries.get(i)).getId();
    }
    String[] xAxis = DateRangeUtil.listDate(sDate, eDate);
    ChartLineVO lineVO = new ChartLineVO();
    lineVO.setxAxis(xAxis);
    int i;
    try
    {
      List<?> list = this.uBetsDao.getDayBetsMoney(lids, sTime, eTime);
      Map<String, Number> tmpMap = new HashMap();
      if (list != null) {
        for (Object o : list)
        {
          Object[] arr = (Object[])o;
          String date = (String)arr[0];
          Number count = Integer.valueOf(((Number)arr[1]).intValue());
          tmpMap.put(date, count);
        }
      }
      Number[] yAxis = new Number[xAxis.length];
      for (i = 0; i < xAxis.length; i++) {
        if (tmpMap.containsKey(xAxis[i])) {
          yAxis[i] = ((Number)tmpMap.get(xAxis[i]));
        } else {
          yAxis[i] = Integer.valueOf(0);
        }
      }
      lineVO.getyAxis().add(yAxis);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    try
    {
      List<?> list = this.uBetsDao.getDayPrizeMoney(lids, sTime, eTime);
      Map<String, Number> tmpMap = new HashMap();
      if (list != null) {
        for (Object o : list)
        {
          Object[] arr = (Object[])o;
          String date = (String)arr[0];
          Number count = Integer.valueOf(arr[1] != null ? ((Number)arr[1]).intValue() : 0);
          tmpMap.put(date, count);
        }
      }
      Number[] yAxis = new Number[xAxis.length];
      for ( i = 0; i < xAxis.length; i++) {
        if (tmpMap.containsKey(xAxis[i])) {
          yAxis[i] = ((Number)tmpMap.get(xAxis[i]));
        } else {
          yAxis[i] = Integer.valueOf(0);
        }
      }
      lineVO.getyAxis().add(yAxis);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return lineVO;
  }
  
  public ChartPieVO getLotteryHotChart(Integer type, String sTime, String eTime)
  {
    ChartPieVO pieVO = new ChartPieVO();
    List<Lottery> lotteries = new ArrayList();
    List<?> list = new ArrayList();
    if (type != null)
    {
      lotteries = this.lotteryDataFactory.listLottery(type.intValue());
      int[] lids = new int[lotteries.size()];
      for (int i = 0; i < lotteries.size(); i++) {
        lids[i] = ((Lottery)lotteries.get(i)).getId();
      }
      list = this.uBetsDao.getLotteryHot(lids, sTime, eTime);
    }
    else
    {
      lotteries = this.lotteryDataFactory.listLottery();
      list = this.uBetsDao.getLotteryHot(null, sTime, eTime);
    }
    String[] legend = new String[lotteries.size()];
    for (int i = 0; i < lotteries.size(); i++) {
      legend[i] = ((Lottery)lotteries.get(i)).getShowName();
    }
    pieVO.setLegend(legend);
    Map<String, Number> tmpMap = new HashMap();
    if (list != null) {
      for (Object o : list)
      {
        Object[] arr = (Object[])o;
        int lotteryId = ((Number)arr[0]).intValue();
        Number count = (Number)arr[1];
        Lottery tmpLottery = this.lotteryDataFactory.getLottery(lotteryId);
        if (tmpLottery != null) {
          tmpMap.put(tmpLottery.getShowName(), count);
        }
      }
    }
    ChartPieVO.PieValue[] series = new ChartPieVO.PieValue[legend.length];
    for (int i = 0; i < legend.length; i++) {
      if (tmpMap.containsKey(legend[i]))
      {
        ChartPieVO tmp346_344 = pieVO;tmp346_344.getClass();series[i] = new ChartPieVO.PieValue(tmp346_344, legend[i], (Number)tmpMap.get(legend[i]));
      }
      else
      {
        ChartPieVO tmp388_386 = pieVO;tmp388_386.getClass();series[i] = new ChartPieVO.PieValue(tmp388_386, legend[i], Integer.valueOf(0));
      }
    }
    pieVO.setSeries(series);
    return pieVO;
  }
}
