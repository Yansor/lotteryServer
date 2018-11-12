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
import admin.domains.content.biz.AdminUserCriticalLogService;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class AdminUserCriticalLogController extends AbstractActionController
{
    @Autowired
    private AdminUserCriticalLogService adminUserCriticalLogService;
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    
    @RequestMapping(value = { "/admin-user-critical-log/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_CRITICAL_LOG_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user-critical-log/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                final String ip = HttpUtil.getStringParameterTrim(request, "ip");
                final String keyword = HttpUtil.getStringParameterTrim(request, "keyword");
                final String sDate = HttpUtil.getStringParameterTrim(request, "sDate");
                final String eDate = HttpUtil.getStringParameterTrim(request, "eDate");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final Integer actionId = HttpUtil.getIntParameter(request, "actionId");
                final PageList pList = this.adminUserCriticalLogService.search(actionId, username, ip, keyword, sDate, eDate, start, limit);
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
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
