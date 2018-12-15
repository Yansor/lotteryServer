package admin.domains.jobs;

import admin.domains.content.dao.AdminUserActionLogDao;
import admin.domains.content.entity.AdminUser;
import admin.domains.content.entity.AdminUserAction;
import admin.domains.content.entity.AdminUserActionLog;
import admin.domains.pool.AdminDataFactory;
import admin.web.WebJSONObject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import javautils.date.DateUtil;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AdminUserActionLogJob
{
  @Autowired
  private AdminDataFactory adminDataFactory;
  @Autowired
  private AdminUserActionLogDao adminUserActionLogDao;
  private BlockingQueue<AdminUserActionLog> logQueue = new LinkedBlockingDeque();
  
  @Scheduled(cron="0/5 * * * * *")
  public void run()
  {
    if ((this.logQueue != null) && (this.logQueue.size() > 0))
    {
      List<AdminUserActionLog> list = new LinkedList();
      this.logQueue.drainTo(list, 1000);
      this.adminUserActionLogDao.save(list);
    }
  }
  
  public void add(AdminUserActionLog entity)
  {
    this.logQueue.offer(entity);
  }
  
  public void add(HttpServletRequest request, String actionKey, AdminUser uEntity, WebJSONObject json, long millisecond)
  {
    if ((uEntity != null) && (uEntity.getId() != 0))
    {
      AdminUserAction adminUserAction = this.adminDataFactory.getAdminUserAction(actionKey);
      if (adminUserAction != null)
      {
        AdminUserActionLog adminUserActionLog = new AdminUserActionLog();
        String dataString = JSONObject.fromObject(request.getParameterMap()).toString();
        adminUserActionLog.setData(dataString);
        adminUserActionLog.setActionId(adminUserAction.getId());
        adminUserActionLog.setUserId(uEntity.getId());
        adminUserActionLog.setTime(DateUtil.getCurrentTime());
        adminUserActionLog.setError(json == null ? 2 : json.getError().intValue());
        adminUserActionLog.setMillisecond(millisecond);
        adminUserActionLog.setMessage(json == null ? "2-1" : json.getMessage());
        String userAgent = request.getHeader("user-agent");
        adminUserActionLog.setUserAgent(userAgent);
        add(adminUserActionLog);
      }
    }
  }
}
