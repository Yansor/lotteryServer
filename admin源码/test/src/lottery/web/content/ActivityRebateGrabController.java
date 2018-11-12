package lottery.web.content;

import java.util.Map;
import net.sf.json.JSONObject;
import lottery.domains.content.entity.activity.RebateRulesGrab;
import net.sf.json.JSONArray;
import java.util.List;
import lottery.domains.content.entity.ActivityRebate;
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
import lottery.domains.content.biz.ActivityGrabService;
import lottery.domains.content.biz.ActivityRebateService;
import lottery.domains.content.dao.ActivityRebateDao;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class ActivityRebateGrabController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private ActivityRebateDao activityRebateDao;
    @Autowired
    private ActivityRebateService activityRebateService;
    @Autowired
    private ActivityGrabService activityGrabService;
    
    @RequestMapping(value = { "/activity-rebate-grab/bill" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_GRAB_PACKAGE_BILL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-grab/bill";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String date = request.getParameter("date");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.activityGrabService.searchBill(username, date, start, limit);
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
    
    @RequestMapping(value = { "/activity-rebate-grab/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_GRAB_PACKAGE_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-grab/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final ActivityRebate bean = this.activityRebateDao.getByType(10);
                json.accumulate("data", bean);
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
    
    @RequestMapping(value = { "/activity-rebate-grab/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_GRAB_PACKAGE_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-grab/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String rules = request.getParameter("rules");
                final List<RebateRulesGrab> rewardRules = (List<RebateRulesGrab>)JSONArray.toCollection(JSONArray.fromObject((Object)rules), (Class)RebateRulesGrab.class);
                if (rewardRules != null && rewardRules.size() > 0) {
                    final boolean result = this.activityRebateService.edit(id, rules, null, null);
                    if (result) {
                        json.set(0, "0-5");
                    }
                    else {
                        json.set(1, "1-5");
                    }
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
    
    @RequestMapping(value = { "/activity-rebate-grab/update-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_GRAB_PACKAGE_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-grab/update-status";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int status = HttpUtil.getIntParameter(request, "status");
                final boolean result = this.activityRebateService.updateStatus(id, status);
                if (result) {
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
    
    @RequestMapping(value = { "/activity-rebate-grab/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_GRAB_PACKAGE_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final Integer id = HttpUtil.getIntParameter(request, "id");
        final ActivityRebate bean = this.activityRebateDao.getById(id);
        final JSONObject json = JSONObject.fromObject((Object)bean);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/activity-rebate-grab/out-total" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_GRAB_PACKAGE_OUTTOTAL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final Map<String, Double> outTotal = this.activityGrabService.getOutTotalInfo();
        json.accumulate("outTotal", outTotal);
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
