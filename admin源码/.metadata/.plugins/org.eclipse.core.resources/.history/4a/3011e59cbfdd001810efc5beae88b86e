package lottery.web.content;

import javautils.jdbc.PageList;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import lottery.domains.content.entity.ActivityRedPacketRainConfig;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import admin.domains.jobs.AdminUserLogJob;
import admin.domains.jobs.AdminUserActionLogJob;
import lottery.domains.content.biz.ActivityRedPacketRainBillService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.biz.ActivityRedPacketRainConfigService;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class ActivityRedPacketRainController extends AbstractActionController
{
    @Autowired
    private ActivityRedPacketRainConfigService configService;
    @Autowired
    private ActivityRedPacketRainBillService billService;
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private AdminUserLogJob adminUserLogJob;
    
    @RequestMapping(value = { "/activity-red-packet-rain/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_RED_PACKET_RAIN_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-red-packet-rain/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final ActivityRedPacketRainConfig config = this.configService.getConfig();
                if (config != null) {
                    json.accumulate("data", config);
                }
                else {
                    json.accumulate("data", "{}");
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
    
    @RequestMapping(value = { "/activity-red-packet-rain/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_RED_PACKET_RAIN_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-red-packet-rain/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String rules = request.getParameter("rules");
                final String hours = request.getParameter("hours");
                final int durationMinutes = HttpUtil.getIntParameter(request, "durationMinutes");
                final boolean result = this.configService.updateConfig(id, rules, hours, durationMinutes);
                if (result) {
                    this.adminUserLogJob.logEditRedPacketRainConfig(uEntity, request);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
                }
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
    
    @RequestMapping(value = { "/activity-red-packet-rain/update-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_RED_PACKET_RAIN_UPDATE_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-red-packet-rain/update-status";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int status = HttpUtil.getIntParameter(request, "status");
                final boolean result = this.configService.updateStatus(id, status);
                if (result) {
                    this.adminUserLogJob.logUpdateStatusRedPacketRain(uEntity, request, status);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
                }
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
    
    @RequestMapping(value = { "/activity-red-packet-rain/bill" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_RED_PACKET_RAIN_BILL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-red-packet-rain/bill";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                String minTime = request.getParameter("minTime");
                if (StringUtils.isNotEmpty(minTime)) {
                    minTime = String.valueOf(minTime) + " 00:00:00";
                }
                String maxTime = request.getParameter("maxTime");
                if (StringUtils.isNotEmpty(maxTime)) {
                    maxTime = String.valueOf(maxTime) + " 00:00:00";
                }
                final String ip = request.getParameter("ip");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.billService.find(username, minTime, maxTime, ip, start, limit);
                if (pList != null) {
                    final double totalAmount = this.billService.sumAmount(username, minTime, maxTime, ip);
                    json.accumulate("totalAmount", totalAmount);
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
