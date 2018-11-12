package lottery.domains.utils.lottery.open;

import java.util.Date;
import javautils.date.DateUtil;

public class OpenTimeTransUtil
{
    public static OpenTime trans(final OpenTime calcOpenTime, final String refDate, final int refExpect, final int times) {
        final String calcExpect = calcOpenTime.getExpect();
        final Date currentDate = DateUtil.stringToDate(calcExpect.substring(0, 8), "yyyyMMdd");
        final int currentTimes = Integer.valueOf(calcExpect.substring(9));
        final Date refDateDate = DateUtil.stringToDate(refDate, "yyyy-MM-dd");
        final int disDays = DateUtil.calcDays(currentDate, refDateDate);
        final int disTimes = disDays * times + currentTimes;
        final int finalExpect = refExpect + disTimes;
        calcOpenTime.setExpect(String.valueOf(finalExpect));
        return calcOpenTime;
    }
    
    public static String trans(final int realExpect, final String refDate, final int refExpect, final int times) {
        final int disTimes = realExpect - refExpect;
        final int disDays = disTimes / times;
        final int remainTimes = disTimes % times;
        final String currentDate = DateUtil.calcNewDay(refDate, disDays);
        return String.valueOf(currentDate.replace("-", "")) + "-" + String.format("%03d", remainTimes);
    }
}
