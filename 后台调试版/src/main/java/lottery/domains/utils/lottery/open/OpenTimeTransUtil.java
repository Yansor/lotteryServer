package lottery.domains.utils.lottery.open;

import java.util.Date;
import javautils.date.DateUtil;

public class OpenTimeTransUtil
{
  public static OpenTime trans(OpenTime calcOpenTime, String refDate, int refExpect, int times)
  {
    String calcExpect = calcOpenTime.getExpect();
    
    Date currentDate = DateUtil.stringToDate(calcExpect.substring(0, 8), "yyyyMMdd");
    
    int currentTimes = Integer.valueOf(calcExpect.substring(9)).intValue();
    
    Date refDateDate = DateUtil.stringToDate(refDate, "yyyy-MM-dd");
    
    int disDays = DateUtil.calcDays(currentDate, refDateDate);
    
    int disTimes = disDays * times + currentTimes;
    
    int finalExpect = refExpect + disTimes;
    
    calcOpenTime.setExpect(String.valueOf(finalExpect));
    
    return calcOpenTime;
  }
  
  public static String trans(int realExpect, String refDate, int refExpect, int times)
  {
    int disTimes = realExpect - refExpect;
    
    int disDays = disTimes / times;
    
    int remainTimes = disTimes % times;
    
    String currentDate = DateUtil.calcNewDay(refDate, disDays);
    
    return currentDate.replace("-", "") + "-" + String.format("%03d", new Object[] { Integer.valueOf(remainTimes) });
  }
}
