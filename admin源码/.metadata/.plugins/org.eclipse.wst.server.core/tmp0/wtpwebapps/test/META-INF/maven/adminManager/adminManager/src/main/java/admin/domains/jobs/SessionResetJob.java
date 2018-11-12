package admin.domains.jobs;

import java.util.Iterator;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;
import org.springframework.scheduling.annotation.Scheduled;
import lottery.domains.content.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import javautils.redis.JedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SessionResetJob
{
    @Autowired
    private JedisTemplate jedisTemplate;
    @Autowired
    private UserDao uDao;
    private static boolean isRunning;
    
    static {
        SessionResetJob.isRunning = false;
    }
    
    @Scheduled(cron = "0 0/1 * * * *")
    public void reset() {
        synchronized (SessionResetJob.class) {
            if (SessionResetJob.isRunning) {
                // monitorexit(SessionResetJob.class)
                return;
            }
            SessionResetJob.isRunning = true;
        }
        // monitorexit(SessionResetJob.class)
        try {
            this.check();
        }
        finally {
            SessionResetJob.isRunning = false;
        }
        SessionResetJob.isRunning = false;
    }
    
    private void check() {
        final Set<String> sessionIds = this.jedisTemplate.keys("spring:session:sessions:*");
        if (sessionIds == null || sessionIds.isEmpty()) {
            this.uDao.updateAllOffline();
            return;
        }
        final Set<String> processIds = new HashSet<String>(sessionIds.size());
        for (final String sessionId : sessionIds) {
            processIds.add(sessionId.replaceAll("spring:session:sessions:", ""));
        }
        this.uDao.updateOnlineStatusNotIn(processIds);
    }
}
