package admin.domains.jobs;

import admin.domains.content.entity.AdminUserAction;
import javautils.date.DateUtil;
import net.sf.json.JSONObject;
import admin.web.WebJSONObject;
import admin.domains.content.entity.AdminUser;
import javax.servlet.http.HttpServletRequest;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.List;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;
import admin.domains.content.entity.AdminUserActionLog;
import java.util.concurrent.BlockingQueue;
import admin.domains.content.dao.AdminUserActionLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.pool.AdminDataFactory;
import org.springframework.stereotype.Component;

@Component
public class AdminUserActionLogJob
{
    @Autowired
    private AdminDataFactory adminDataFactory;
    @Autowired
    private AdminUserActionLogDao adminUserActionLogDao;
    private BlockingQueue<AdminUserActionLog> logQueue;
    
    public AdminUserActionLogJob() {
        this.logQueue = new LinkedBlockingDeque<AdminUserActionLog>();
    }
    
    @Scheduled(cron = "0/5 * * * * *")
    public void run() {
        if (this.logQueue != null && this.logQueue.size() > 0) {
            final List<AdminUserActionLog> list = new LinkedList<AdminUserActionLog>();
            this.logQueue.drainTo(list, 1000);
            this.adminUserActionLogDao.save(list);
        }
    }
    
    public void add(final AdminUserActionLog entity) {
        this.logQueue.offer(entity);
    }
    
    public void add(final HttpServletRequest request, final String actionKey, final AdminUser uEntity, final WebJSONObject json, final long millisecond) {
        if (uEntity != null && uEntity.getId() != 0) {
            final AdminUserAction adminUserAction = this.adminDataFactory.getAdminUserAction(actionKey);
            if (adminUserAction != null) {
                final AdminUserActionLog adminUserActionLog = new AdminUserActionLog();
                final String dataString = JSONObject.fromObject((Object)request.getParameterMap()).toString();
                adminUserActionLog.setData(dataString);
                adminUserActionLog.setActionId(adminUserAction.getId());
                adminUserActionLog.setUserId(uEntity.getId());
                adminUserActionLog.setTime(DateUtil.getCurrentTime());
                adminUserActionLog.setError((json == null) ? 2 : ((int)json.getError()));
                adminUserActionLog.setMillisecond(millisecond);
                adminUserActionLog.setMessage((json == null) ? "2-1" : json.getMessage());
                final String userAgent = request.getHeader("user-agent");
                adminUserActionLog.setUserAgent(userAgent);
                this.add(adminUserActionLog);
            }
        }
    }
}
