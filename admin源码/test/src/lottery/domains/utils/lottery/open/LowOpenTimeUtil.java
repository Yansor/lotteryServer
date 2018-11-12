package lottery.domains.utils.lottery.open;

import javautils.date.DateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryOpenTime;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.stereotype.Component;

@Component
public class LowOpenTimeUtil implements OpenTimeUtil
{
    @Autowired
    private LotteryDataFactory df;
    
    @Override
    public OpenTime getCurrOpenTime(final int lotteryId, final String currTime) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery != null) {
            final List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
            for (final LotteryOpenTime tmpBean : list) {
                final String startTime = tmpBean.getStartTime();
                final String stopTime = tmpBean.getStopTime();
                if (currTime.compareTo(startTime) >= 0 && currTime.compareTo(stopTime) < 0) {
                    final OpenTime bean = new OpenTime();
                    bean.setExpect(tmpBean.getExpect());
                    bean.setStartTime(tmpBean.getStartTime());
                    bean.setStopTime(tmpBean.getStopTime());
                    bean.setOpenTime(tmpBean.getOpenTime());
                    return bean;
                }
            }
        }
        return null;
    }
    
    @Override
    public OpenTime getLastOpenTime(final int lotteryId, final String currTime) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery != null) {
            final List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
            int i = 0;
            final int j = list.size();
            while (i < j) {
                LotteryOpenTime tmpBean = list.get(i);
                final String startTime = tmpBean.getStartTime();
                final String stopTime = tmpBean.getStopTime();
                if (currTime.compareTo(startTime) >= 0 && currTime.compareTo(stopTime) < 0) {
                    if (i == 0) {
                        return null;
                    }
                    tmpBean = list.get(i - 1);
                    final OpenTime bean = new OpenTime();
                    bean.setExpect(tmpBean.getExpect());
                    bean.setStartTime(tmpBean.getStartTime());
                    bean.setStopTime(tmpBean.getStopTime());
                    bean.setOpenTime(tmpBean.getOpenTime());
                    return bean;
                }
                else {
                    ++i;
                }
            }
        }
        return null;
    }
    
    @Override
    public List<OpenTime> getOpenTimeList(final int lotteryId, final int count) {
        final List<OpenTime> list = new ArrayList<OpenTime>();
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery != null) {
            final String currTime = DateUtil.getCurrentTime();
            final List<LotteryOpenTime> opList = this.df.listLotteryOpenTime(lottery.getShortName());
            for (final LotteryOpenTime tmpBean : opList) {
                final String stopTime = tmpBean.getStopTime();
                if (currTime.compareTo(stopTime) < 0) {
                    final OpenTime bean = new OpenTime();
                    bean.setExpect(tmpBean.getExpect());
                    bean.setStartTime(tmpBean.getStartTime());
                    bean.setStopTime(tmpBean.getStopTime());
                    bean.setOpenTime(tmpBean.getOpenTime());
                    list.add(bean);
                    if (list.size() == count) {
                        break;
                    }
                    continue;
                }
            }
        }
        return list;
    }
    
    @Override
    public List<OpenTime> getOpenDateList(final int lotteryId, final String date) {
        final List<OpenTime> list = new ArrayList<OpenTime>();
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery != null) {
            final List<LotteryOpenTime> opList = this.df.listLotteryOpenTime(lottery.getShortName());
            for (final LotteryOpenTime tmpBean : opList) {
                final String openTime = tmpBean.getOpenTime();
                if (openTime.indexOf(date) != -1) {
                    final OpenTime bean = new OpenTime();
                    bean.setExpect(tmpBean.getExpect());
                    bean.setStartTime(tmpBean.getStartTime());
                    bean.setStopTime(tmpBean.getStopTime());
                    bean.setOpenTime(tmpBean.getOpenTime());
                    list.add(bean);
                }
            }
        }
        return list;
    }
    
    @Override
    public OpenTime getOpenTime(final int lotteryId, final String expect) {
        final Lottery lottery = this.df.getLottery(lotteryId);
        if (lottery != null) {
            final List<LotteryOpenTime> opList = this.df.listLotteryOpenTime(lottery.getShortName());
            for (final LotteryOpenTime tmpBean : opList) {
                final String thisExpect = tmpBean.getExpect();
                if (thisExpect.equals(expect)) {
                    final OpenTime bean = new OpenTime();
                    bean.setExpect(tmpBean.getExpect());
                    bean.setStartTime(tmpBean.getStartTime());
                    bean.setStopTime(tmpBean.getStopTime());
                    bean.setOpenTime(tmpBean.getOpenTime());
                    return bean;
                }
            }
        }
        return null;
    }
}
