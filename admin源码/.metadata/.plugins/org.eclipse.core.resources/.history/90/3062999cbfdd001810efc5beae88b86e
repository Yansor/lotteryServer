package lottery.web.content;

import javautils.jdbc.PageList;
import net.sf.json.JSONObject;
import lottery.domains.content.entity.activity.RebateRulesRechargeLoop;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import lottery.domains.content.entity.ActivityRebate;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.ActivityRechargeLoopService;
import lottery.domains.content.biz.ActivityRebateService;
import lottery.domains.content.dao.ActivityRebateDao;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class ActivityRebateRechargeLoopController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private ActivityRebateDao activityRebateDao;
    @Autowired
    private ActivityRebateService activityRebateService;
    @Autowired
    private ActivityRechargeLoopService activityRechargeLoopService;
    
    @RequestMapping(value = { "/activity-rebate-recharge-loop/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_RECHARGE_LOOP_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-recharge-loop/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final ActivityRebate bean = this.activityRebateDao.getByType(7);
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
    
    @RequestMapping(value = { "/activity-rebate-recharge-loop/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public synchronized void ACTIVITY_REBATE_RECHARGE_LOOP_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-recharge-loop/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String rules = request.getParameter("rules");
                final RebateRulesRechargeLoop rewardRules = (RebateRulesRechargeLoop)JSONObject.toBean(JSONObject.fromObject((Object)rules), (Class)RebateRulesRechargeLoop.class);
                if (rewardRules != null) {
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
    
    @RequestMapping(value = { "/activity-rebate-recharge-loop/update-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public synchronized void ACTIVITY_REBATE_RECHARGE_LOOP_UPDATE_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-recharge-loop/update-status";
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
    
    @RequestMapping(value = { "/activity-rebate-recharge-loop-bill/list" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public void ACTIVITY_REBATE_RECHARGE_LOOP_BILL_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-recharge-loop-bill/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String date = request.getParameter("date");
                final Integer step = HttpUtil.getIntParameter(request, "step");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.activityRechargeLoopService.search(username, date, step, start, limit);
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
    
    @RequestMapping(value = { "/activity-rebate-recharge-loop/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_RECHARGE_LOOP_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final Integer id = HttpUtil.getIntParameter(request, "id");
        final ActivityRebate bean = this.activityRebateDao.getById(id);
        final JSONObject json = JSONObject.fromObject((Object)bean);
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
