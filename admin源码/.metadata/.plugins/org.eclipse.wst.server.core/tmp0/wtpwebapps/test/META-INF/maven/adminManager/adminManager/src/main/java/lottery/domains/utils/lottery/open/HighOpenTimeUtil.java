package lottery.domains.utils.lottery.open;

import javautils.date.Moment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryOpenTime;
import javautils.date.DateUtil;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.stereotype.Component;

@Component
public class HighOpenTimeUtil implements OpenTimeUtil
{
    @Autowired
    private LotteryDataFactory df;
    
    @Override
    public OpenTime getCurrOpenTime(final int lotteryId, final String currTime) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery == null) {
            return null;
        }
        if ("tw5fc".equals(lottery.getShortName())) {
            return this.getCurrOpenTimeForNext(lotteryId, currTime);
        }
        if (lottery != null) {
            final List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
            if (CollectionUtils.isEmpty((Collection)list)) {
                return null;
            }
            final String currDate = currTime.substring(0, 10);
            final String nextDate = DateUtil.calcNextDay(currDate);
            final String lastDate = DateUtil.calcLastDay(currDate);
            for (int i = 0, j = list.size(); i < j; ++i) {
                LotteryOpenTime tmpBean = list.get(i);
                String startDate = currDate;
                String stopDate = currDate;
                String openDate = currDate;
                String expectDate = currDate;
                String startTime = tmpBean.getStartTime();
                String stopTime = tmpBean.getStopTime();
                String openTime = tmpBean.getOpenTime();
                String expect = tmpBean.getExpect();
                if (i == 0) {
                    if (startTime.compareTo(stopTime) > 0) {
                        startDate = lastDate;
                    }
                }
                else if (i == j - 1) {
                    if (startTime.compareTo(stopTime) > 0) {
                        stopDate = nextDate;
                    }
                    if (startTime.compareTo(openTime) > 0) {
                        openDate = nextDate;
                    }
                    if (currTime.compareTo(String.valueOf(stopDate) + " " + stopTime) >= 0) {
                        tmpBean = list.get(0);
                        startDate = nextDate;
                        stopDate = nextDate;
                        openDate = nextDate;
                        expectDate = nextDate;
                        startTime = tmpBean.getStartTime();
                        stopTime = tmpBean.getStopTime();
                        openTime = tmpBean.getOpenTime();
                        expect = tmpBean.getExpect();
                        if (startTime.compareTo(stopTime) > 0) {
                            startDate = currDate;
                        }
                    }
                }
                else {
                    if (startTime.compareTo(stopTime) > 0) {
                        stopDate = nextDate;
                    }
                    if (startTime.compareTo(openTime) > 0) {
                        openDate = nextDate;
                    }
                }
                if (!tmpBean.getIsTodayExpect()) {
                    if (startTime.compareTo(stopTime) > 0) {
                        if (currTime.substring(11).compareTo(startTime) < 0 || currTime.substring(11).compareTo("24:00:00") >= 0) {
                            startDate = lastDate;
                            stopDate = currDate;
                            expectDate = lastDate;
                        }
                    }
                    else {
                        expectDate = lastDate;
                    }
                }
                startTime = String.valueOf(startDate) + " " + startTime;
                stopTime = String.valueOf(stopDate) + " " + stopTime;
                openTime = String.valueOf(openDate) + " " + openTime;
                expect = String.valueOf(expectDate.replace("-", "")) + "-" + expect;
                if (currTime.compareTo(startTime) >= 0 && currTime.compareTo(stopTime) < 0) {
                    final OpenTime bean = new OpenTime();
                    bean.setExpect(expect);
                    bean.setStartTime(startTime);
                    bean.setStopTime(stopTime);
                    bean.setOpenTime(openTime);
                    return bean;
                }
            }
        }
        return null;
    }
    
    private OpenTime getCurrOpenTimeForNext(final int lotteryId, final String currTime) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery == null) {
            return null;
        }
        final List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
        final String currDate = currTime.substring(0, 10);
        final String currTimeHMS = currTime.substring(11);
        final String nextDate = DateUtil.calcNextDay(currDate);
        final String lastDate = DateUtil.calcLastDay(currDate);
        String startTime = null;
        String stopTime = null;
        String openTime = null;
        String expect = null;
        boolean found = false;
        for (final LotteryOpenTime lotteryOpenTime : list) {
            startTime = lotteryOpenTime.getStartTime();
            stopTime = lotteryOpenTime.getStopTime();
            openTime = lotteryOpenTime.getOpenTime();
            expect = lotteryOpenTime.getExpect();
            if (lotteryOpenTime.getStartTime().compareTo(lotteryOpenTime.getStopTime()) > 0) {
                if (currTimeHMS.compareTo(lotteryOpenTime.getStopTime()) < 0) {
                    startTime = String.valueOf(lastDate) + " " + startTime;
                    stopTime = String.valueOf(currDate) + " " + stopTime;
                    openTime = String.valueOf(currDate) + " " + openTime;
                }
                else {
                    startTime = String.valueOf(currDate) + " " + startTime;
                    stopTime = String.valueOf(nextDate) + " " + stopTime;
                    openTime = String.valueOf(nextDate) + " " + openTime;
                }
            }
            else {
                startTime = String.valueOf(currDate) + " " + startTime;
                stopTime = String.valueOf(currDate) + " " + stopTime;
                openTime = String.valueOf(currDate) + " " + openTime;
            }
            if (currTime.compareTo(startTime) >= 0 && currTime.compareTo(stopTime) < 0) {
                found = true;
                if (lotteryOpenTime.getIsTodayExpect()) {
                    expect = String.valueOf(currDate.replace("-", "")) + "-" + expect;
                    break;
                }
                if (lotteryOpenTime.getStartTime().compareTo(lotteryOpenTime.getStopTime()) <= 0) {
                    expect = String.valueOf(nextDate.replace("-", "")) + "-" + expect;
                    break;
                }
                if (currTimeHMS.compareTo(lotteryOpenTime.getStopTime()) < 0) {
                    expect = String.valueOf(currDate.replace("-", "")) + "-" + expect;
                    break;
                }
                expect = String.valueOf(nextDate.replace("-", "")) + "-" + expect;
                break;
            }
        }
        if (!found) {
            return null;
        }
        final OpenTime bean = new OpenTime();
        bean.setExpect(expect);
        bean.setStartTime(startTime);
        bean.setStopTime(stopTime);
        bean.setOpenTime(openTime);
        return bean;
    }
    
    @Override
    public OpenTime getLastOpenTime(final int lotteryId, final String currTime) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery == null) {
            return null;
        }
        final OpenTime currOpenTime = this.getCurrOpenTime(lotteryId, currTime);
        if (currOpenTime == null) {
            return null;
        }
        final String tmpExpect = currOpenTime.getExpect();
        final String tmpDate = tmpExpect.substring(0, 8);
        final String currDate = DateUtil.formatTime(tmpDate, "yyyyMMdd", "yyyy-MM-dd");
        final String currExpect = tmpExpect.substring(9);
        String lastDate = currDate;
        int lastExpect = Integer.parseInt(currExpect);
        final int times = lottery.getTimes();
        if (lastExpect == 1) {
            lastDate = DateUtil.calcLastDay(currDate);
            lastExpect = times;
        }
        else {
            --lastExpect;
        }
        int formatCount = 3;
        if (lottery.getTimes() >= 1000) {
            formatCount = 4;
        }
        final String expect = String.valueOf(lastDate.replaceAll("-", "")) + "-" + String.format("%0" + formatCount + "d", lastExpect);
        if ("tw5fc".equals(lottery.getShortName())) {
            return this.getOpenTimeForNext(lotteryId, expect);
        }
        return this.getOpenTime(lotteryId, expect);
    }
    
    @Override
    public List<OpenTime> getOpenTimeList(final int lotteryId, final int count) {
        final List<OpenTime> list = new ArrayList<OpenTime>();
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery != null) {
            final String currTime = DateUtil.getCurrentTime();
            final OpenTime currOpenTime = this.getCurrOpenTime(lotteryId, currTime);
            final String tmpExpect = currOpenTime.getExpect();
            final String tmpDate = tmpExpect.substring(0, 8);
            String currDate = DateUtil.formatTime(tmpDate, "yyyyMMdd", "yyyy-MM-dd");
            int currExpect = Integer.parseInt(tmpExpect.substring(9));
            for (int i = 0; i < count; ++i) {
                int formatCount = 3;
                if (lottery.getTimes() >= 1000) {
                    formatCount = 4;
                }
                final String expect = String.valueOf(currDate.replaceAll("-", "")) + "-" + String.format("%0" + formatCount + "d", currExpect);
                OpenTime tmpBean;
                if ("tw5fc".equals(lottery.getShortName())) {
                    tmpBean = this.getOpenTimeForNext(lotteryId, expect);
                }
                else {
                    tmpBean = this.getOpenTime(lotteryId, expect);
                }
                if (tmpBean != null) {
                    list.add(tmpBean);
                }
                String nextDate = currDate;
                int nextExpect = currExpect;
                final int times = lottery.getTimes();
                if (nextExpect == times) {
                    nextDate = DateUtil.calcNextDay(currDate);
                    nextExpect = 1;
                }
                else {
                    ++nextExpect;
                }
                currDate = nextDate;
                currExpect = nextExpect;
            }
        }
        return list;
    }
    
    @Override
    public List<OpenTime> getOpenDateList(final int lotteryId, final String date) {
        final List<OpenTime> list = new ArrayList<OpenTime>();
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery != null) {
            for (int times = lottery.getTimes(), i = 0; i < times; ++i) {
                int formatCount = 3;
                if (lottery.getTimes() >= 1000) {
                    formatCount = 4;
                }
                final String expect = String.valueOf(date.replaceAll("-", "")) + "-" + String.format("%0" + formatCount + "d", i + 1);
                final OpenTime tmpBean = this.getOpenTime(lotteryId, expect);
                if (tmpBean != null) {
                    list.add(tmpBean);
                }
            }
        }
        return list;
    }
    
    @Override
    public OpenTime getOpenTime(final int lotteryId, final String expect) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery == null) {
            return null;
        }
        if ("tw5fc".equals(lottery.getShortName())) {
            return this.getOpenTimeForNext(lotteryId, expect);
        }
        if (lottery != null) {
            final List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
            final String date = expect.substring(0, 8);
            final String currDate = DateUtil.formatTime(date, "yyyyMMdd", "yyyy-MM-dd");
            final String nextDate = DateUtil.calcNextDay(currDate);
            final String lastDate = DateUtil.calcLastDay(currDate);
            final String currExpect = expect.substring(9);
            for (int i = 0, j = list.size(); i < j; ++i) {
                final LotteryOpenTime tmpBean = list.get(i);
                if (tmpBean.getExpect().equals(currExpect)) {
                    String startDate = currDate;
                    String startTime = tmpBean.getStartTime();
                    final String stopTime = tmpBean.getStopTime();
                    if (i == 0 && startTime.compareTo(stopTime) > 0) {
                        startDate = lastDate;
                    }
                    if (!tmpBean.getIsTodayExpect()) {
                        if ("xjssc".equals(lottery.getShortName())) {
                            if (startTime.compareTo(stopTime) > 0) {
                                final String currTime = new Moment().format("HH:mm:ss");
                                if (currTime.compareTo(startTime) < 0 || currTime.compareTo("24:00:00") >= 0) {
                                    startDate = currDate;
                                }
                            }
                            else {
                                startDate = nextDate;
                            }
                        }
                        else {
                            startDate = nextDate;
                        }
                    }
                    startTime = String.valueOf(startDate) + " " + startTime;
                    return this.getCurrOpenTime(lotteryId, startTime);
                }
            }
        }
        return null;
    }
    
    private OpenTime getOpenTimeForNext(final int lotteryId, final String expect) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery == null) {
            return null;
        }
        final List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
        final String date = expect.substring(0, 8);
        final String currDate = DateUtil.formatTime(date, "yyyyMMdd", "yyyy-MM-dd");
        final String lastDate = DateUtil.calcLastDay(currDate);
        final String currExpect = expect.substring(9);
        for (final LotteryOpenTime lotteryOpenTime : list) {
            if (!lotteryOpenTime.getExpect().equals(currExpect)) {
                continue;
            }
            String startTime;
            if (lotteryOpenTime.getIsTodayExpect()) {
                startTime = String.valueOf(currDate) + " " + lotteryOpenTime.getStartTime();
            }
            else {
                startTime = String.valueOf(lastDate) + " " + lotteryOpenTime.getStartTime();
            }
            return this.getCurrOpenTimeForNext(lotteryId, startTime);
        }
        return null;
    }
}
