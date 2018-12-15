package lottery.domains.content.biz.impl;

import java.util.Random;
import javautils.date.Moment;
import lottery.domains.content.biz.ActivityRedPacketRainTimeService;
import lottery.domains.content.dao.ActivityRedPacketRainConfigDao;
import lottery.domains.content.dao.ActivityRedPacketRainTimeDao;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.entity.ActivityRedPacketRainConfig;
import lottery.domains.content.entity.ActivityRedPacketRainTime;
import lottery.domains.content.global.DbServerSyncEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityRedPacketRainTimeServiceImpl
  implements ActivityRedPacketRainTimeService
{
  @Autowired
  private ActivityRedPacketRainTimeDao timeDao;
  @Autowired
  private ActivityRedPacketRainConfigDao configDao;
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  
  public boolean add(ActivityRedPacketRainTime time)
  {
    return this.timeDao.add(time);
  }
  
  public ActivityRedPacketRainTime getByDateAndHour(String date, String hour)
  {
    return this.timeDao.getByDateAndHour(date, hour);
  }
  
  public synchronized boolean initTimes(int days)
  {
    ActivityRedPacketRainConfig config = this.configDao.getConfig();
    if ((config == null) || (config.getStatus() == 0)) {
      return false;
    }
    int durationMinutes = config.getDurationMinutes();
    int maxEndMinute = 60 - durationMinutes;
    if (maxEndMinute <= 0) {
      maxEndMinute = 50;
    }
    int addedCount = 0;
    for (int i = 0; i < days; i++)
    {
      String date = new Moment().add(i, "days").format("yyyy-MM-dd");
      String hours = config.getHours();
      String[] hoursArr = hours.split(",");
      String[] arrayOfString1;
      int j = (arrayOfString1 = hoursArr).length;
      for ( i = 0; i < j; i++)
      {
        String hour = arrayOfString1[i];
        String _hour = String.format("%02d", new Object[] { Integer.valueOf(hour) });
        ActivityRedPacketRainTime rainTime = this.timeDao.getByDateAndHour(date, _hour);
        if (rainTime == null)
        {
          rainTime = new ActivityRedPacketRainTime();
          
          Random random = new Random();
          int minute = random.nextInt(maxEndMinute);
          if (minute <= 0) {
            minute = 1;
          }
          if (minute >= 60) {
            minute = 10;
          }
          String _minute = String.format("%02d", new Object[] { Integer.valueOf(minute) });
          String _second = "00";
          
          String _startTime = date + " " + _hour + ":" + _minute + ":" + _second;
          String _endTime = new Moment().fromTime(_startTime).add(durationMinutes, "minutes").toSimpleTime();
          
          rainTime.setDate(date);
          rainTime.setHour(_hour);
          rainTime.setStartTime(_startTime);
          rainTime.setEndTime(_endTime);
          this.timeDao.add(rainTime);
          addedCount++;
        }
      }
    }
    if (addedCount > 0) {
      this.dbServerSyncDao.update(DbServerSyncEnum.ACTIVITY);
    }
    return true;
  }
}
