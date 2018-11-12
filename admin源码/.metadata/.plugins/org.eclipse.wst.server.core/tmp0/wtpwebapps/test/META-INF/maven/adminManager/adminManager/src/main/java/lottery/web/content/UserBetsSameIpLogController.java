package lottery.web.content;

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
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.biz.UserBetsSameIpLogService;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserBetsSameIpLogController extends AbstractActionController
{
    @Autowired
    private UserBetsSameIpLogService uBetsSameIpLogService;
    
    @RequestMapping(value = { "/user-bets-same-ip-log/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_BETS_SAME_IP_LOG_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-bets-same-ip-log/list";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String ip = HttpUtil.getStringParameterTrim(request, "ip");
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                final String sortColumn = HttpUtil.getStringParameterTrim(request, "sortColumn");
                final String sortType = HttpUtil.getStringParameterTrim(request, "sortType");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.uBetsSameIpLogService.search(ip, username, sortColumn, sortType, start, limit);
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
