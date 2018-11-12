package admin.web.content;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import javautils.jdbc.PageList;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.biz.AdminUserActionLogService;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class AdminUserActionLogController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogService adUserActionLogService;
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    
    @RequestMapping(value = { "/admin-user-action-log/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_ACTION_LOG_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user-action-log/list";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String actionId = request.getParameter("actionId");
                final String error = request.getParameter("error");
                final String sTime = request.getParameter("sTime");
                final String eTime = request.getParameter("eTime");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.adUserActionLogService.search(username, actionId, error, sTime, eTime, start, limit);
                if (pList != null) {
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                json.set(0, "0-3");
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
