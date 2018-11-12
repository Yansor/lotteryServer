package lottery.domains.content.jobs;

import lottery.domains.content.entity.UserBalanceSnapshot;
import javautils.date.Moment;
import javautils.ObjectUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.LoggerFactory;
import lottery.domains.content.biz.UserBalanceSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class UserBalanceSnapshotJob
{
    private static final Logger log;
    private static volatile boolean isRunning;
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserBalanceSnapshotService uBalanceSnapshotService;
    
    static {
        log = LoggerFactory.getLogger((Class)UserBalanceSnapshotJob.class);
        UserBalanceSnapshotJob.isRunning = false;
    }
    
    @Scheduled(cron = "59 59 23 * * ?")
    public void syncOrder() {
        synchronized (UserBalanceSnapshotJob.class) {
            if (UserBalanceSnapshotJob.isRunning) {
                // monitorexit(UserBalanceSnapshotJob.class)
                return;
            }
            UserBalanceSnapshotJob.isRunning = true;
        }
        // monitorexit(UserBalanceSnapshotJob.class)
        try {
            UserBalanceSnapshotJob.log.debug("开始执行平台余额快照");
            this.start();
            UserBalanceSnapshotJob.log.debug("完成执行平台余额快照");
        }
        catch (Exception e) {
            UserBalanceSnapshotJob.log.error("执行平台余额快照出错", (Throwable)e);
            return;
        }
        finally {
            UserBalanceSnapshotJob.isRunning = false;
        }
        UserBalanceSnapshotJob.isRunning = false;
    }
    
    private void start() {
        final Object[] balance = this.uDao.getTotalMoney();
        final double totalMoney = ObjectUtil.toDouble(balance[0]);
        final double lotteryMoney = ObjectUtil.toDouble(balance[1]);
        final String time = new Moment().toSimpleTime();
        this.save(totalMoney, lotteryMoney, time);
    }
    
    private void save(final double totalMoney, final double lotteryMoney, final String time) {
        final UserBalanceSnapshot snapshot = new UserBalanceSnapshot();
        snapshot.setTotalMoney(totalMoney);
        snapshot.setLotteryMoney(lotteryMoney);
        snapshot.setTime(time);
        this.uBalanceSnapshotService.add(snapshot);
    }
}
