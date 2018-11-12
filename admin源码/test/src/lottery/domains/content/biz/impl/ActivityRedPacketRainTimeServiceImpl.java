package lottery.domains.content.biz.impl;

import lottery.domains.content.entity.ActivityRedPacketRainConfig;
import lottery.domains.content.global.DbServerSyncEnum;
import java.util.Random;
import javautils.date.Moment;
import lottery.domains.content.entity.ActivityRedPacketRainTime;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.ActivityRedPacketRainConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.ActivityRedPacketRainTimeDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.ActivityRedPacketRainTimeService;

@Service
public class ActivityRedPacketRainTimeServiceImpl implements ActivityRedPacketRainTimeService
{
    @Autowired
    private ActivityRedPacketRainTimeDao timeDao;
    @Autowired
    private ActivityRedPacketRainConfigDao configDao;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Override
    public boolean add(final ActivityRedPacketRainTime time) {
        return this.timeDao.add(time);
    }
    
    @Override
    public ActivityRedPacketRainTime getByDateAndHour(final String date, final String hour) {
        return this.timeDao.getByDateAndHour(date, hour);
    }
    
    @Override
    public synchronized boolean initTimes(final int days) {
        final ActivityRedPacketRainConfig config = this.configDao.getConfig();
        if (config == null || config.getStatus() == 0) {
            return false;
        }
        final int durationMinutes = config.getDurationMinutes();
        int maxEndMinute = 60 - durationMinutes;
        if (maxEndMinute <= 0) {
            maxEndMinute = 50;
        }
        int addedCount = 0;
        for (int i = 0; i < days; ++i) {
            final String date = new Moment().add(i, "days").format("yyyy-MM-dd");
            final String hours = config.getHours();
            final String[] hoursArr = hours.split(",");
            String[] array;
            for (int length = (array = hoursArr).length, j = 0; j < length; ++j) {
                final String hour = array[j];
                final String _hour = String.format("%02d", Integer.valueOf(hour));
                ActivityRedPacketRainTime rainTime = this.timeDao.getByDateAndHour(date, _hour);
                if (rainTime == null) {
                    rainTime = new ActivityRedPacketRainTime();
                    final Random random = new Random();
                    int minute = random.nextInt(maxEndMinute);
                    if (minute <= 0) {
                        minute = 1;
                    }
                    if (minute >= 60) {
                        minute = 10;
                    }
                    final String _minute = String.format("%02d", minute);
                    final String _second = "00";
                    final String _startTime = String.valueOf(date) + " " + _hour + ":" + _minute + ":" + _second;
                    final String _endTime = new Moment().fromTime(_startTime).add(durationMinutes, "minutes").toSimpleTime();
                    rainTime.setDate(date);
                    rainTime.setHour(_hour);
                    rainTime.setStartTime(_startTime);
                    rainTime.setEndTime(_endTime);
                    this.timeDao.add(rainTime);
                    ++addedCount;
                }
            }
        }
        if (addedCount > 0) {
            this.dbServerSyncDao.update(DbServerSyncEnum.ACTIVITY);
        }
        return true;
    }
}
