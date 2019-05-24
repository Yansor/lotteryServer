package admin.web.content;

import admin.domains.content.biz.AdminUserCriticalLogService;
import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminUserCriticalLogController
  extends AbstractActionController
{
  @Autowired
  private AdminUserCriticalLogService adminUserCriticalLogService;
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  
  @RequestMapping(value={"/admin-user-critical-log/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_CRITICAL_LOG_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user-critical-log/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = HttpUtil.getStringParameterTrim(request, "username");
        String ip = HttpUtil.getStringParameterTrim(request, "ip");
        String keyword = HttpUtil.getStringParameterTrim(request, "keyword");
        String sDate = HttpUtil.getStringParameterTrim(request, "sDate");
        String eDate = HttpUtil.getStringParameterTrim(request, "eDate");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        Integer actionId = HttpUtil.getIntParameter(request, "actionId");
        PageList pList = this.adminUserCriticalLogService.search(actionId, username, ip, keyword, sDate, eDate, start, limit);
        if (pList != null)
        {
          json.accumulate("totalCount", Integer.valueOf(pList.getCount()));
          json.accumulate("data", pList.getList());
        }
        else
        {
          json.accumulate("totalCount", Integer.valueOf(0));
          json.accumulate("data", "[]");
        }
        json.set(Integer.valueOf(0), "0-3");
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
