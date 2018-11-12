package lottery.domains.content.biz.impl;

import lottery.domains.content.vo.chart.ChartPieVO.PieValue;
import lottery.domains.content.vo.chart.ChartPieVO;
import lottery.domains.content.entity.Lottery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import java.util.HashMap;
import java.util.LinkedList;
import javautils.date.DateRangeUtil;
import lottery.domains.content.vo.chart.ChartLineVO;
import java.util.List;
import javautils.math.MathUtil;
import javautils.date.Moment;
import lottery.domains.content.vo.chart.RechargeWithdrawTotal;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import org.slf4j.LoggerFactory;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.biz.UserLotteryReportService;
import lottery.domains.content.dao.UserLoginLogDao;
import lottery.domains.content.dao.UserWithdrawDao;
import lottery.domains.content.dao.UserRechargeDao;
import lottery.domains.content.dao.UserBetsDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.LotteryStatService;

@Service
public class LotteryStatServiceImpl implements LotteryStatService
{
    private static final Logger log;
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
    
    static {
        log = LoggerFactory.getLogger((Class)LotteryStatServiceImpl.class);
    }
    
    @Override
    public int getTotalUserRegist(final String sTime, final String eTime) {
        return this.uDao.getTotalUserRegist(sTime, eTime);
    }
    
    @Override
    public long getTotalBetsMoney(final String sTime, final String eTime) {
        return this.uBetsDao.getTotalBetsMoney(sTime, eTime);
    }
    
    @Override
    public int getTotalOrderCount(final String sTime, final String eTime) {
        return this.uBetsDao.getTotalOrderCount(sTime, eTime);
    }
    
    @Override
    public long getTotalProfitMoney(final String sTime, final String eTime) {
        final UserLotteryReportVO rBean = this.uLotteryReportService.report(sTime, eTime).get(0);
        return (long)(rBean.getPrize() + rBean.getSpendReturn() + rBean.getProxyReturn() + rBean.getActivity() - rBean.getBillingOrder());
    }
    
    @Override
    public RechargeWithdrawTotal getTotalRechargeWithdrawData(final String sDate, final String eDate, final Integer type, final Integer subtype) {
        final String sTime = new Moment().fromDate(sDate).toSimpleDate();
        final String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
        final Object[] rechargeData = this.uRechargeDao.getTotalRechargeData(sTime, eTime, type, subtype);
        final int totalRechargeCount = (rechargeData == null) ? 0 : Integer.valueOf(rechargeData[0].toString());
        final double totalRechargeMoney = (double)((rechargeData == null) ? 0.0 : rechargeData[1]);
        final double totalReceiveFeeMoney = (double)((rechargeData == null) ? 0.0 : rechargeData[2]);
        final Object[] withdrawData = this.uWithdrawDao.getTotalWithdrawData(sTime, eTime);
        final int totalWithdrawCount = (withdrawData == null) ? 0 : Integer.valueOf(withdrawData[0].toString());
        final double totalWithdrawMoney = (double)((withdrawData == null) ? 0.0 : withdrawData[1]);
        final double totalActualReceiveMoney = MathUtil.subtract(totalRechargeMoney, totalReceiveFeeMoney);
        final double totalRechargeWithdrawDiff = MathUtil.subtract(totalWithdrawMoney, totalActualReceiveMoney);
        return new RechargeWithdrawTotal(totalRechargeCount, totalRechargeMoney, totalReceiveFeeMoney, totalWithdrawCount, totalWithdrawMoney, totalActualReceiveMoney, totalRechargeWithdrawDiff);
    }
    
    @Override
    public List<ChartLineVO> getRechargeWithdrawDataChart(final String sDate, final String eDate, final Integer type, final Integer subtype) {
        final String sTime = new Moment().fromDate(sDate).toSimpleDate();
        final String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
        final String[] dates = DateRangeUtil.listDate(sDate, eDate);
        final List<ChartLineVO> lineVOs = new LinkedList<ChartLineVO>();
        try {
            final List<?> rechargeList = this.uRechargeDao.getDayRecharge2(sTime, eTime, type, subtype);
            final List<?> withdrawList = this.uWithdrawDao.getDayWithdraw2(sTime, eTime);
            final Map<String, Object[]> rechargeMap = new HashMap<String, Object[]>();
            final Map<String, Object[]> withdrawMap = new HashMap<String, Object[]>();
            if (CollectionUtils.isNotEmpty((Collection)rechargeList)) {
                for (final Object o : rechargeList) {
                    final Object[] arr = (Object[])o;
                    final String date = (String)arr[0];
                    rechargeMap.put(date, arr);
                }
            }
            if (CollectionUtils.isNotEmpty((Collection)withdrawList)) {
                for (final Object o : withdrawList) {
                    final Object[] arr = (Object[])o;
                    final String date = (String)arr[0];
                    withdrawMap.put(date, arr);
                }
            }
            final ChartLineVO receiveFeeMoneyLineVO = new ChartLineVO();
            receiveFeeMoneyLineVO.setxAxis(dates);
            final Number[] receiveFeeMoneyYAxis = new Number[dates.length];
            final ChartLineVO rechargeWithdrawDiffLineVO = new ChartLineVO();
            rechargeWithdrawDiffLineVO.setxAxis(dates);
            final Number[] rechargeWithdrawDiffYAxis = new Number[dates.length];
            final ChartLineVO actualReceiveMoneyLineVO = new ChartLineVO();
            actualReceiveMoneyLineVO.setxAxis(dates);
            final Number[] actualReceiveMoneyYAxis = new Number[dates.length];
            final ChartLineVO rechargeMoneyLineVO = new ChartLineVO();
            rechargeMoneyLineVO.setxAxis(dates);
            final Number[] rechargeMoneyYAxis = new Number[dates.length];
            final ChartLineVO withdrawMoneyLineVO = new ChartLineVO();
            withdrawMoneyLineVO.setxAxis(dates);
            final Number[] withdrawMoneyYAxis = new Number[dates.length];
            final ChartLineVO rechargeCountLineVO = new ChartLineVO();
            rechargeCountLineVO.setxAxis(dates);
            final Number[] rechargeCountYAxis = new Number[dates.length];
            final ChartLineVO withdrawCountLineVO = new ChartLineVO();
            withdrawCountLineVO.setxAxis(dates);
            final Number[] withdrawCountYAxis = new Number[dates.length];
            for (int i = 0; i < dates.length; ++i) {
                final String date2 = dates[i];
                Number receiveFeeMoney = 0;
                Number rechargeWithdrawDiff = 0;
                Number actualReceiveMoney = 0;
                Number rechargeMoney = 0;
                Number withdrawMoney = 0;
                Number rechargeCount = 0;
                Number withdrawCount = 0;
                if (rechargeMap.containsKey(date2)) {
                    final Object[] arr2 = rechargeMap.get(date2);
                    rechargeCount = ((Number)arr2[1]).intValue();
                    rechargeMoney = ((Number)arr2[2]).intValue();
                    receiveFeeMoney = ((Number)arr2[3]).intValue();
                    actualReceiveMoney = rechargeMoney.intValue() - receiveFeeMoney.intValue();
                    rechargeWithdrawDiff = -actualReceiveMoney.intValue();
                }
                if (withdrawMap.containsKey(date2)) {
                    final Object[] arr2 = withdrawMap.get(date2);
                    withdrawCount = ((Number)arr2[1]).intValue();
                    withdrawMoney = ((Number)arr2[2]).intValue();
                    rechargeWithdrawDiff = withdrawMoney.intValue() + rechargeWithdrawDiff.intValue();
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
        catch (Exception e) {
            LotteryStatServiceImpl.log.error("统计充提报表时出错", (Throwable)e);
        }
        return lineVOs;
    }
    
    @Override
    public ChartLineVO getUserRegistChart(final String sDate, final String eDate) {
        final String sTime = new Moment().fromDate(sDate).toSimpleDate();
        final String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
        final String[] xAxis = DateRangeUtil.listDate(sDate, eDate);
        final ChartLineVO lineVO = new ChartLineVO();
        lineVO.setxAxis(xAxis);
        final List<?> list = this.uDao.getDayUserRegist(sTime, eTime);
        final Map<String, Number> tmpMap = new HashMap<String, Number>();
        if (list != null) {
            for (final Object o : list) {
                final Object[] arr = (Object[])o;
                final String date = (String)arr[0];
                final Number count = (Number)arr[1];
                tmpMap.put(date, count);
            }
        }
        final Number[] yAxis = new Number[xAxis.length];
        for (int i = 0; i < xAxis.length; ++i) {
            if (tmpMap.containsKey(xAxis[i])) {
                yAxis[i] = tmpMap.get(xAxis[i]);
            }
            else {
                yAxis[i] = 0;
            }
        }
        lineVO.getyAxis().add(yAxis);
        return lineVO;
    }
    
    @Override
    public ChartLineVO getUserLoginChart(final String sDate, final String eDate) {
        final String sTime = new Moment().fromDate(sDate).toSimpleDate();
        final String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
        final String[] xAxis = DateRangeUtil.listDate(sDate, eDate);
        final ChartLineVO lineVO = new ChartLineVO();
        lineVO.setxAxis(xAxis);
        final List<?> list = this.uLoginLogDao.getDayUserLogin(sTime, eTime);
        final Map<String, Number> tmpMap = new HashMap<String, Number>();
        if (list != null) {
            for (final Object o : list) {
                final Object[] arr = (Object[])o;
                final String date = (String)arr[0];
                final Number count = (Number)arr[1];
                tmpMap.put(date, count);
            }
        }
        final Number[] yAxis = new Number[xAxis.length];
        for (int i = 0; i < xAxis.length; ++i) {
            if (tmpMap.containsKey(xAxis[i])) {
                yAxis[i] = tmpMap.get(xAxis[i]);
            }
            else {
                yAxis[i] = 0;
            }
        }
        lineVO.getyAxis().add(yAxis);
        return lineVO;
    }
    
    @Override
    public ChartLineVO getUserBetsChart(final Integer type, final Integer id, final String sDate, final String eDate) {
        final String sTime = new Moment().fromDate(sDate).toSimpleDate();
        final String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
        List<Lottery> lotteries = new ArrayList<Lottery>();
        if (id != null) {
            final Lottery tmpLottery = this.lotteryDataFactory.getLottery(id);
            if (tmpLottery != null) {
                lotteries.add(tmpLottery);
            }
        }
        else if (type != null) {
            lotteries = this.lotteryDataFactory.listLottery(type);
        }
        final int[] lids = new int[lotteries.size()];
        for (int i = 0; i < lotteries.size(); ++i) {
            lids[i] = lotteries.get(i).getId();
        }
        final String[] xAxis = DateRangeUtil.listDate(sDate, eDate);
        final ChartLineVO lineVO = new ChartLineVO();
        lineVO.setxAxis(xAxis);
        final List<?> list = this.uBetsDao.getDayUserBets(lids, sTime, eTime);
        final Map<String, Number> tmpMap = new HashMap<String, Number>();
        if (list != null) {
            for (final Object o : list) {
                final Object[] arr = (Object[])o;
                final String date = (String)arr[0];
                final Number count = (Number)arr[1];
                tmpMap.put(date, count);
            }
        }
        final Number[] yAxis = new Number[xAxis.length];
        for (int j = 0; j < xAxis.length; ++j) {
            if (tmpMap.containsKey(xAxis[j])) {
                yAxis[j] = tmpMap.get(xAxis[j]);
            }
            else {
                yAxis[j] = 0;
            }
        }
        lineVO.getyAxis().add(yAxis);
        return lineVO;
    }
    
    @Override
    public ChartLineVO getUserCashChart(final String sDate, final String eDate) {
        final String sTime = new Moment().fromDate(sDate).toSimpleDate();
        final String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
        final String[] xAxis = DateRangeUtil.listDate(sDate, eDate);
        final ChartLineVO lineVO = new ChartLineVO();
        lineVO.setxAxis(xAxis);
        try {
            final List<?> list = this.uRechargeDao.getDayRecharge(sTime, eTime);
            final Map<String, Number> tmpMap = new HashMap<String, Number>();
            final Map<String, Number> receiveFeeMap = new HashMap<String, Number>();
            if (list != null) {
                for (final Object o : list) {
                    final Object[] arr = (Object[])o;
                    final String date = (String)arr[0];
                    final Number count = ((Number)arr[1]).intValue();
                    final Number receiveFee = ((Number)arr[2]).intValue();
                    tmpMap.put(date, count);
                    receiveFeeMap.put(date, receiveFee);
                }
            }
            final Number[] yAxis = new Number[xAxis.length];
            for (int i = 0; i < xAxis.length; ++i) {
                if (tmpMap.containsKey(xAxis[i])) {
                    yAxis[i] = tmpMap.get(xAxis[i]);
                }
                else {
                    yAxis[i] = 0;
                }
            }
            lineVO.getyAxis().add(yAxis);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            final List<?> list = this.uWithdrawDao.getDayWithdraw(sTime, eTime);
            final Map<String, Number> tmpMap = new HashMap<String, Number>();
            if (list != null) {
                for (final Object o2 : list) {
                    final Object[] arr2 = (Object[])o2;
                    final String date2 = (String)arr2[0];
                    final Number count2 = ((Number)arr2[1]).intValue();
                    tmpMap.put(date2, count2);
                }
            }
            final Number[] yAxis2 = new Number[xAxis.length];
            for (int j = 0; j < xAxis.length; ++j) {
                if (tmpMap.containsKey(xAxis[j])) {
                    yAxis2[j] = tmpMap.get(xAxis[j]);
                }
                else {
                    yAxis2[j] = 0;
                }
            }
            lineVO.getyAxis().add(yAxis2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return lineVO;
    }
    
    @Override
    public ChartLineVO getUserComplexChart(final Integer type, final Integer id, final String sDate, final String eDate) {
        final String sTime = new Moment().fromDate(sDate).toSimpleDate();
        final String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
        List<Lottery> lotteries = new ArrayList<Lottery>();
        if (id != null) {
            final Lottery tmpLottery = this.lotteryDataFactory.getLottery(id);
            if (tmpLottery != null) {
                lotteries.add(tmpLottery);
            }
        }
        else if (type != null) {
            lotteries = this.lotteryDataFactory.listLottery(type);
        }
        final int[] lids = new int[lotteries.size()];
        for (int i = 0; i < lotteries.size(); ++i) {
            lids[i] = lotteries.get(i).getId();
        }
        final String[] xAxis = DateRangeUtil.listDate(sDate, eDate);
        final ChartLineVO lineVO = new ChartLineVO();
        lineVO.setxAxis(xAxis);
        try {
            final List<?> list = this.uBetsDao.getDayBetsMoney(lids, sTime, eTime);
            final Map<String, Number> tmpMap = new HashMap<String, Number>();
            if (list != null) {
                for (final Object o : list) {
                    final Object[] arr = (Object[])o;
                    final String date = (String)arr[0];
                    final Number count = ((Number)arr[1]).intValue();
                    tmpMap.put(date, count);
                }
            }
            final Number[] yAxis = new Number[xAxis.length];
            for (int j = 0; j < xAxis.length; ++j) {
                if (tmpMap.containsKey(xAxis[j])) {
                    yAxis[j] = tmpMap.get(xAxis[j]);
                }
                else {
                    yAxis[j] = 0;
                }
            }
            lineVO.getyAxis().add(yAxis);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            final List<?> list = this.uBetsDao.getDayPrizeMoney(lids, sTime, eTime);
            final Map<String, Number> tmpMap = new HashMap<String, Number>();
            if (list != null) {
                for (final Object o : list) {
                    final Object[] arr = (Object[])o;
                    final String date = (String)arr[0];
                    final Number count = (arr[1] != null) ? ((Number)arr[1]).intValue() : 0;
                    tmpMap.put(date, count);
                }
            }
            final Number[] yAxis = new Number[xAxis.length];
            for (int j = 0; j < xAxis.length; ++j) {
                if (tmpMap.containsKey(xAxis[j])) {
                    yAxis[j] = tmpMap.get(xAxis[j]);
                }
                else {
                    yAxis[j] = 0;
                }
            }
            lineVO.getyAxis().add(yAxis);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return lineVO;
    }
    
    @Override
    public ChartPieVO getLotteryHotChart(final Integer type, final String sTime, final String eTime) {
        final ChartPieVO pieVO = new ChartPieVO();
        List<Lottery> lotteries = new ArrayList<Lottery>();
        List<?> list = new ArrayList<Object>();
        if (type != null) {
            lotteries = this.lotteryDataFactory.listLottery(type);
            final int[] lids = new int[lotteries.size()];
            for (int i = 0; i < lotteries.size(); ++i) {
                lids[i] = lotteries.get(i).getId();
            }
            list = this.uBetsDao.getLotteryHot(lids, sTime, eTime);
        }
        else {
            lotteries = this.lotteryDataFactory.listLottery();
            list = this.uBetsDao.getLotteryHot(null, sTime, eTime);
        }
        final String[] legend = new String[lotteries.size()];
        for (int i = 0; i < lotteries.size(); ++i) {
            legend[i] = lotteries.get(i).getShowName();
        }
        pieVO.setLegend(legend);
        final Map<String, Number> tmpMap = new HashMap<String, Number>();
        if (list != null) {
            for (final Object o : list) {
                final Object[] arr = (Object[])o;
                final int lotteryId = ((Number)arr[0]).intValue();
                final Number count = (Number)arr[1];
                final Lottery tmpLottery = this.lotteryDataFactory.getLottery(lotteryId);
                if (tmpLottery != null) {
                    tmpMap.put(tmpLottery.getShowName(), count);
                }
            }
        }
        final PieValue[] series = new PieValue[legend.length];
        for (int j = 0; j < legend.length; ++j) {
            if (tmpMap.containsKey(legend[j])) {
                series[j] = pieVO.new PieValue(legend[j], tmpMap.get(legend[j]));
            }
            else {
                series[j] = pieVO.new PieValue(legend[j], 0);
            }
        }
        pieVO.setSeries(series);
        return pieVO;
    }
}
