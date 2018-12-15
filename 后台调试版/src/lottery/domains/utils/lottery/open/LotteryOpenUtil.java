package lottery.domains.utils.lottery.open;

import java.util.ArrayList;
import java.util.List;
import javautils.date.DateUtil;
import javautils.date.Moment;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryOpenTime;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LotteryOpenUtil
{
  @Autowired
  private LotteryDataFactory df;
  @Autowired
  @Qualifier("highOpenTimeUtil")
  private OpenTimeUtil highOpenTimeUtil;
  @Autowired
  @Qualifier("lowOpenTimeUtil")
  private OpenTimeUtil lowOpenTimeUtil;
  
  public OpenTime getOpentime(int lotteryId, String expect)
  {
    Lottery lottery = this.df.getLottery(lotteryId);
    if (lottery != null)
    {
      if (lottery.getExpectTrans() == 1)
      {
        expect = trans(lotteryId, Integer.parseInt(expect));
        OpenTime bean = this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
        return trans(lotteryId, bean);
      }
      switch (lottery.getType())
      {
      case 1: 
      case 2: 
      case 3: 
      case 7: 
        if ("bjk3".equals(lottery.getShortName()))
        {
          expect = trans(lotteryId, Integer.parseInt(expect));
          OpenTime bean = this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
          return trans(lotteryId, bean);
        }
        return this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
      case 4: 
        return this.lowOpenTimeUtil.getOpenTime(lotteryId, expect);
      case 5: 
      case 6: 
        if ("bjpk10".equals(lottery.getShortName()))
        {
          expect = trans(lotteryId, Integer.parseInt(expect));
          OpenTime bean = this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
          return trans(lotteryId, bean);
        }
        return this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
      }
    }
    return null;
  }
  
  public List<OpenTime> getOpenTimeList(int lotteryId, int count)
  {
    Lottery lottery = this.df.getLottery(lotteryId);
    if (lottery != null)
    {
      if (lottery.getExpectTrans() == 1)
      {
        List<OpenTime> list = this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
        return trans(lotteryId, list);
      }
      switch (lottery.getType())
      {
      case 1: 
      case 2: 
      case 3: 
      case 7: 
        if ("bjk3".equals(lottery.getShortName()))
        {
          List<OpenTime> list = this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
          return trans(lotteryId, list);
        }
        return this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
      case 4: 
        return this.lowOpenTimeUtil.getOpenTimeList(lotteryId, count);
      case 5: 
      case 6: 
        if ("bjpk10".equals(lottery.getShortName()))
        {
          List<OpenTime> list = this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
          return trans(lotteryId, list);
        }
        return this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
      }
    }
    return null;
  }
  
  public List<OpenTime> getOpenDateList(int lotteryId, String date)
  {
    Lottery lottery = this.df.getLottery(lotteryId);
    if (lottery != null)
    {
      if (lottery.getExpectTrans() == 1)
      {
        List<OpenTime> list = this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
        return trans(lotteryId, list);
      }
      switch (lottery.getType())
      {
      case 1: 
      case 2: 
      case 3: 
      case 7: 
        if ("bjk3".equals(lottery.getShortName()))
        {
          List<OpenTime> list = this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
          return trans(lotteryId, list);
        }
        return this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
      case 4: 
        return this.lowOpenTimeUtil.getOpenDateList(lotteryId, date);
      case 5: 
      case 6: 
        if ("bjpk10".equals(lottery.getShortName()))
        {
          List<OpenTime> list = this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
          return trans(lotteryId, list);
        }
        return this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
      }
    }
    return null;
  }
  
  public OpenTime getCurrOpenTime(int lotteryId)
  {
    Lottery lottery = this.df.getLottery(lotteryId);
    if (lottery != null)
    {
      String currTime = DateUtil.getCurrentTime();
      if (lottery.getExpectTrans() == 1)
      {
        OpenTime bean = this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
        return trans(lotteryId, bean);
      }
      switch (lottery.getType())
      {
      case 1: 
      case 2: 
      case 3: 
      case 7: 
        if ("bjk3".equals(lottery.getShortName()))
        {
          OpenTime bean = this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
          return trans(lotteryId, bean);
        }
        return this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
      case 4: 
        return this.lowOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
      case 5: 
      case 6: 
        if ("bjpk10".equals(lottery.getShortName()))
        {
          OpenTime bean = this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
          return trans(lotteryId, bean);
        }
        return this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
      }
    }
    return null;
  }
  
  public OpenTime getLastOpenTime(int lotteryId)
  {
    Lottery lottery = this.df.getLottery(lotteryId);
    if (lottery != null)
    {
      String currTime = DateUtil.getCurrentTime();
      if (lottery.getExpectTrans() == 1)
      {
        OpenTime bean = this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
        return trans(lotteryId, bean);
      }
      switch (lottery.getType())
      {
      case 1: 
      case 2: 
      case 3: 
      case 7: 
        if ("bjk3".equals(lottery.getShortName()))
        {
          OpenTime bean = this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
          return trans(lotteryId, bean);
        }
        return this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
      case 4: 
        return this.lowOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
      case 5: 
      case 6: 
        if ("bjpk10".equals(lottery.getShortName()))
        {
          OpenTime bean = this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
          return trans(lotteryId, bean);
        }
        return this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
      }
    }
    return null;
  }
  
  public OpenTime trans(int lotteryId, OpenTime bean)
  {
    Lottery lottery = this.df.getLottery(lotteryId);
    String refLotteryName = lottery.getShortName() + "_ref";
    List<LotteryOpenTime> opList = this.df.listLotteryOpenTime(refLotteryName);
    LotteryOpenTime lotteryOpenTime = (LotteryOpenTime)opList.get(0);
    String refDate = lotteryOpenTime.getOpenTime();
    int refExpect = Integer.parseInt(lotteryOpenTime.getExpect());
    int times = lottery.getTimes();
    return OpenTimeTransUtil.trans(bean, refDate, refExpect, times);
  }
  
  public String trans(int lotteryId, int expect)
  {
    Lottery lottery = this.df.getLottery(lotteryId);
    String refLotteryName = lottery.getShortName() + "_ref";
    List<LotteryOpenTime> opList = this.df.listLotteryOpenTime(refLotteryName);
    LotteryOpenTime lotteryOpenTime = (LotteryOpenTime)opList.get(0);
    String refDate = lotteryOpenTime.getOpenTime();
    int refExpect = Integer.parseInt(lotteryOpenTime.getExpect());
    int times = lottery.getTimes();
    return OpenTimeTransUtil.trans(expect, refDate, refExpect, times);
  }
  
  public List<OpenTime> trans(int lotteryId, List<OpenTime> list)
  {
    List<OpenTime> nList = new ArrayList();
    for (OpenTime bean : list) {
      nList.add(trans(lotteryId, bean));
    }
    return nList;
  }
  
  public String subtractExpect(String lotteryShortName, String expect)
  {
    Lottery lottery = this.df.getLottery(lotteryShortName);
    if (lottery == null) {
      return null;
    }
    String subExpect;
    if (expect.indexOf("-") <= -1)
    {
      Integer integerExpect = Integer.valueOf(expect);
      integerExpect = Integer.valueOf(integerExpect.intValue() - 1);
      if (integerExpect.toString().length() >= expect.length()) {
        subExpect = integerExpect.toString();
      } else {
        subExpect = String.format("%0" + expect.length() + "d", new Object[] { integerExpect });
      }
    }
    else
    {
      String[] split = expect.split("-");
      int formatCount = split[1].length();
      String date = split[0];
      String currExpect = split[1];
      if ((currExpect.equals("001")) || (currExpect.equals("0001")))
      {
        date = new Moment().fromDate(date).subtract(1, "days").format("yyyyMMdd");
        subExpect = String.format("%0" + formatCount + "d", new Object[] { Integer.valueOf(lottery.getTimes()) });
      }
      else
      {
        Integer integer = Integer.valueOf(currExpect);
        integer = Integer.valueOf(integer.intValue() - 1);
        if (integer.toString().length() >= formatCount) {
          subExpect = integer.toString();
        } else {
          subExpect = String.format("%0" + formatCount + "d", new Object[] { integer });
        }
      }
      subExpect = date + "-" + subExpect;
    }
    return subExpect;
  }
}
