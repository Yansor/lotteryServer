package lottery.domains.content.jobs;

import java.util.Iterator;
import java.util.List;
import lottery.domains.content.entity.UserWithdrawLog;
import javautils.date.Moment;
import lottery.domains.content.global.RemitStatusConstants.Status;
import lottery.domains.content.entity.UserWithdraw;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.LoggerFactory;
import lottery.domains.content.biz.UserWithdrawLogService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.biz.UserWithdrawService;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class APIPayOrderTimeoutJob
{
    private static final Logger log;
    private static volatile boolean isRunning;
    private static final int[] PROCESSING_STATUSES;
    @Autowired
    private UserWithdrawService uWithdrawService;
    @Autowired
    private UserWithdrawLogService userWithdrawLogService;
    
    static {
        log = LoggerFactory.getLogger((Class)APIPayOrderTimeoutJob.class);
        APIPayOrderTimeoutJob.isRunning = false;
        PROCESSING_STATUSES = new int[] { 1, 3, -3 };
    }
    
    @Scheduled(cron = "0 0/10 * * * ?")
    public void scheduler() {
        synchronized (APIPayOrderTimeoutJob.class) {
            if (APIPayOrderTimeoutJob.isRunning) {
                // monitorexit(APIPayOrderTimeoutJob.class)
                return;
            }
            APIPayOrderTimeoutJob.isRunning = true;
        }
        // monitorexit(APIPayOrderTimeoutJob.class)
        try {
            this.process();
        }
        catch (Exception e) {
            APIPayOrderTimeoutJob.log.error("处理API代付单超时任务异常", (Throwable)e);
            return;
        }
        finally {
            APIPayOrderTimeoutJob.isRunning = false;
        }
        APIPayOrderTimeoutJob.isRunning = false;
    }
    
    private void process() {
        final List<UserWithdraw> withdrawOrders = this.getWithdrawOrders();
        if (CollectionUtils.isEmpty((Collection)withdrawOrders)) {
            return;
        }
        for (final UserWithdraw withdrawOrder : withdrawOrders) {
            withdrawOrder.setRemitStatus(-4);
            this.uWithdrawService.update(withdrawOrder);
            final String content = Status.getTypeByContent(-4);
            final String time = new Moment().toSimpleTime();
            final String action = String.format("%s；操作人：系统", content);
            this.userWithdrawLogService.add(new UserWithdrawLog(withdrawOrder.getBillno(), withdrawOrder.getUserId(), -1, action, time));
        }
    }
    
    private List<UserWithdraw> getWithdrawOrders() {
        final String sTime = new Moment().subtract(7, "days").toSimpleDate();
        final String eTime = new Moment().subtract(1, "days").toSimpleDate();
        return this.uWithdrawService.listByRemitStatus(APIPayOrderTimeoutJob.PROCESSING_STATUSES, true, sTime, eTime);
    }
}
