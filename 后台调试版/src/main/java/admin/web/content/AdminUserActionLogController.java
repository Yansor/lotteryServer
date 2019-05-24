package admin.web.content;

import admin.domains.content.biz.AdminUserActionLogService;
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
public class AdminUserActionLogController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogService adUserActionLogService;
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  
  @RequestMapping(value={"/admin-user-action-log/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_ACTION_LOG_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user-action-log/list";
    
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String actionId = request.getParameter("actionId");
        String error = request.getParameter("error");
        String sTime = request.getParameter("sTime");
        String eTime = request.getParameter("eTime");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.adUserActionLogService.search(username, actionId, error, sTime, eTime, start, limit);
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
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
