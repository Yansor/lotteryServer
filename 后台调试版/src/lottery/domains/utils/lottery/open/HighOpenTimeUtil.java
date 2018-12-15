package lottery.domains.utils.lottery.open;

import java.util.ArrayList;
import java.util.List;
import javautils.date.DateUtil;
import javautils.date.Moment;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryOpenTime;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HighOpenTimeUtil
  implements OpenTimeUtil
{
  @Autowired
  private LotteryDataFactory df;
  
  public OpenTime getCurrOpenTime(int lotteryId, String currTime)
  {
    Lottery lottery = this.df.getLottery(lotteryId);
    if (lottery == null) {
      return null;
    }
    if ("tw5fc".equals(lottery.getShortName())) {
      return getCurrOpenTimeForNext(lotteryId, currTime);
    }
    if (lottery != null)
    {
      List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
      if (CollectionUtils.isEmpty(list)) {
        return null;
      }
      String currDate = currTime.substring(0, 10);
      String nextDate = DateUtil.calcNextDay(currDate);
      String lastDate = DateUtil.calcLastDay(currDate);
      int i = 0;
      for (int j = list.size(); i < j; i++)
      {
        LotteryOpenTime tmpBean = (LotteryOpenTime)list.get(i);
        String startDate = currDate;
        String stopDate = currDate;
        String openDate = currDate;
        String expectDate = currDate;
        String startTime = tmpBean.getStartTime();
        String stopTime = tmpBean.getStopTime();
        String openTime = tmpBean.getOpenTime();
        String expect = tmpBean.getExpect();
        if (i == 0)
        {
          if (startTime.compareTo(stopTime) > 0) {
            startDate = lastDate;
          }
        }
        else if (i == j - 1)
        {
          if (startTime.compareTo(stopTime) > 0) {
            stopDate = nextDate;
          }
          if (startTime.compareTo(openTime) > 0) {
            openDate = nextDate;
          }
          if (currTime.compareTo(stopDate + " " + stopTime) >= 0)
          {
            tmpBean = (LotteryOpenTime)list.get(0);
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
        else
        {
          if (startTime.compareTo(stopTime) > 0) {
            stopDate = nextDate;
          }
          if (startTime.compareTo(openTime) > 0) {
            openDate = nextDate;
          }
        }
        if (!tmpBean.getIsTodayExpect()) {
          if (startTime.compareTo(stopTime) > 0)
          {
            if ((currTime.substring(11).compareTo(startTime) < 0) || (currTime.substring(11).compareTo("24:00:00") >= 0))
            {
              startDate = lastDate;
              stopDate = currDate;
              expectDate = lastDate;
            }
          }
          else {
            expectDate = lastDate;
          }
        }
        startTime = startDate + " " + startTime;
        stopTime = stopDate + " " + stopTime;
        openTime = openDate + " " + openTime;
        expect = expectDate.replace("-", "") + "-" + expect;
        if ((currTime.compareTo(startTime) >= 0) && (currTime.compareTo(stopTime) < 0))
        {
          OpenTime bean = new OpenTime();
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
  
  private OpenTime getCurrOpenTimeForNext(int lotteryId, String currTime)
  {
    Lottery lottery = this.df.getLottery(lotteryId);
    if (lottery == null) {
      return null;
    }
    List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
    String currDate = currTime.substring(0, 10);
    String currTimeHMS = currTime.substring(11);
    String nextDate = DateUtil.calcNextDay(currDate);
    String lastDate = DateUtil.calcLastDay(currDate);
    
    String startTime = null;
    String stopTime = null;
    String openTime = null;
    String expect = null;
    boolean found = false;
    for (LotteryOpenTime lotteryOpenTime : list)
    {
      startTime = lotteryOpenTime.getStartTime();
      stopTime = lotteryOpenTime.getStopTime();
      openTime = lotteryOpenTime.getOpenTime();
      expect = lotteryOpenTime.getExpect();
      if (lotteryOpenTime.getStartTime().compareTo(lotteryOpenTime.getStopTime()) > 0)
      {
        if (currTimeHMS.compareTo(lotteryOpenTime.getStopTime()) < 0)
        {
          startTime = lastDate + " " + startTime;
          stopTime = currDate + " " + stopTime;
          openTime = currDate + " " + openTime;
        }
        else
        {
          startTime = currDate + " " + startTime;
          stopTime = nextDate + " " + stopTime;
          openTime = nextDate + " " + openTime;
        }
      }
      else
      {
        startTime = currDate + " " + startTime;
        stopTime = currDate + " " + stopTime;
        openTime = currDate + " " + openTime;
      }
      if ((currTime.compareTo(startTime) >= 0) && (currTime.compareTo(stopTime) < 0))
      {
        found = true;
        if (lotteryOpenTime.getIsTodayExpect())
        {
          expect = currDate.replace("-", "") + "-" + expect;
          break;
        }
        if (lotteryOpenTime.getStartTime().compareTo(lotteryOpenTime.getStopTime()) > 0)
        {
          if (currTimeHMS.compareTo(lotteryOpenTime.getStopTime()) < 0)
          {
            expect = currDate.replace("-", "") + "-" + expect;
            break;
          }
          expect = nextDate.replace("-", "") + "-" + expect;
          
          break;
        }
        expect = nextDate.replace("-", "") + "-" + expect;
        
        break;
      }
    }
    if (!found) {
      return null;
    }
    OpenTime bean = new OpenTime();
    bean.setExpect(expect);
    bean.setStartTime(startTime);
    bean.setStopTime(stopTime);
    bean.setOpenTime(openTime);
    return bean;
  }
  
  public OpenTime getLastOpenTime(int lotteryId, String currTime)
  {
    Lottery lottery = this.df.getLottery(lotteryId);
    if (lottery != null)
    {
      OpenTime currOpenTime = getCurrOpenTime(lotteryId, currTime);
      if (currOpenTime == null) {
        return null;
      }
      String tmpExpect = currOpenTime.getExpect();
      String tmpDate = tmpExpect.substring(0, 8);
      String currDate = DateUtil.formatTime(tmpDate, "yyyyMMdd", "yyyy-MM-dd");
      String currExpect = tmpExpect.substring(9);
      String lastDate = currDate;
      int lastExpect = Integer.parseInt(currExpect);
      int times = lottery.getTimes();
      if (lastExpect == 1)
      {
        lastDate = DateUtil.calcLastDay(currDate);
        lastExpect = times;
      }
      else
      {
        lastExpect--;
      }
      int formatCount = 3;
      if (lottery.getTimes() >= 1000) {
        formatCount = 4;
      }
      String expect = lastDate.replaceAll("-", "") + "-" + String.format(new StringBuilder("%0").append(formatCount).append("d").toString(), new Object[] { Integer.valueOf(lastExpect) });
      if ("tw5fc".equals(lottery.getShortName())) {
        return getOpenTimeForNext(lotteryId, expect);
      }
      return getOpenTime(lotteryId, expect);
    }
    return null;
  }
  
  public List<OpenTime> getOpenTimeList(int lotteryId, int count)
  {
    List<OpenTime> list = new ArrayList();
    Lottery lottery = this.df.getLottery(lotteryId);
    if (lottery != null)
    {
      String currTime = DateUtil.getCurrentTime();
      
      OpenTime currOpenTime = getCurrOpenTime(lotteryId, currTime);
      String tmpExpect = currOpenTime.getExpect();
      String tmpDate = tmpExpect.substring(0, 8);
      String currDate = DateUtil.formatTime(tmpDate, "yyyyMMdd", "yyyy-MM-dd");
      int currExpect = Integer.parseInt(tmpExpect.substring(9));
      for (int i = 0; i < count; i++)
      {
        int formatCount = 3;
        if (lottery.getTimes() >= 1000) {
          formatCount = 4;
        }
        String expect = currDate.replaceAll("-", "") + "-" + String.format(new StringBuilder("%0").append(formatCount).append("d").toString(), new Object[] { Integer.valueOf(currExpect) });
        OpenTime tmpBean;
        if ("tw5fc".equals(lottery.getShortName())) {
          tmpBean = getOpenTimeForNext(lotteryId, expect);
        } else {
          tmpBean = getOpenTime(lotteryId, expect);
        }
        if (tmpBean != null) {
          list.add(tmpBean);
        }
        String nextDate = currDate;
        int nextExpect = currExpect;
        int times = lottery.getTimes();
        if (nextExpect == times)
        {
          nextDate = DateUtil.calcNextDay(currDate);
          nextExpect = 1;
        }
        else
        {
          nextExpect++;
        }
        currDate = nextDate;
        currExpect = nextExpect;
      }
    }
    return list;
  }
  
  public List<OpenTime> getOpenDateList(int lotteryId, String date)
  {
    List<OpenTime> list = new ArrayList();
    Lottery lottery = this.df.getLottery(lotteryId);
    if (lottery != null)
    {
      int times = lottery.getTimes();
      for (int i = 0; i < times; i++)
      {
        int formatCount = 3;
        if (lottery.getTimes() >= 1000) {
          formatCount = 4;
        }
        String expect = date.replaceAll("-", "") + "-" + String.format(new StringBuilder("%0").append(formatCount).append("d").toString(), new Object[] { Integer.valueOf(i + 1) });
        OpenTime tmpBean = getOpenTime(lotteryId, expect);
        if (tmpBean != null) {
          list.add(tmpBean);
        }
      }
    }
    return list;
  }
  
  public OpenTime getOpenTime(int lotteryId, String expect)
  {
    Lottery lottery = this.df.getLottery(lotteryId);
    if (lottery == null) {
      return null;
    }
    if ("tw5fc".equals(lottery.getShortName())) {
      return getOpenTimeForNext(lotteryId, expect);
    }
    if (lottery != null)
    {
      List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
      String date = expect.substring(0, 8);
      String currDate = DateUtil.formatTime(date, "yyyyMMdd", "yyyy-MM-dd");
      String nextDate = DateUtil.calcNextDay(currDate);
      String lastDate = DateUtil.calcLastDay(currDate);
      String currExpect = expect.substring(9);
      
      int i = 0;
      for (int j = list.size(); i < j; i++)
      {
        LotteryOpenTime tmpBean = (LotteryOpenTime)list.get(i);
        if (tmpBean.getExpect().equals(currExpect))
        {
          String startDate = currDate;
          String startTime = tmpBean.getStartTime();
          String stopTime = tmpBean.getStopTime();
          if (i == 0) {
            if (startTime.compareTo(stopTime) > 0) {
              startDate = lastDate;
            }
          }
          if (!tmpBean.getIsTodayExpect()) {
            if ("xjssc".equals(lottery.getShortName()))
            {
              if (startTime.compareTo(stopTime) > 0)
              {
                String currTime = new Moment().format("HH:mm:ss");
                if ((currTime.compareTo(startTime) < 0) || (currTime.compareTo("24:00:00") >= 0)) {
                  startDate = currDate;
                }
              }
              else
              {
                startDate = nextDate;
              }
            }
            else {
              startDate = nextDate;
            }
          }
          startTime = startDate + " " + startTime;
          return getCurrOpenTime(lotteryId, startTime);
        }
      }
    }
    return null;
  }
  
  private OpenTime getOpenTimeForNext(int lotteryId, String expect)
  {
    Lottery lottery = this.df.getLottery(lotteryId);
    if (lottery == null) {
      return null;
    }
    List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
    String date = expect.substring(0, 8);
    String currDate = DateUtil.formatTime(date, "yyyyMMdd", "yyyy-MM-dd");
    String lastDate = DateUtil.calcLastDay(currDate);
    String currExpect = expect.substring(9);
    for (LotteryOpenTime lotteryOpenTime : list) {
      if (lotteryOpenTime.getExpect().equals(currExpect))
      {
        String startTime;
        if (lotteryOpenTime.getIsTodayExpect()) {
          startTime = currDate + " " + lotteryOpenTime.getStartTime();
        } else {
          startTime = lastDate + " " + lotteryOpenTime.getStartTime();
        }
        return getCurrOpenTimeForNext(lotteryId, startTime);
      }
    }
    return null;
  }
}
