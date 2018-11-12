package lottery.web.content;

import javautils.jdbc.PageList;
import lottery.domains.content.entity.activity.RebateRulesCost;
import net.sf.json.JSONArray;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import lottery.domains.content.entity.ActivityRebate;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.ActivityCostService;
import lottery.domains.content.biz.ActivityRebateService;
import lottery.domains.content.dao.ActivityRebateDao;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class ActivityRebateCostController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private ActivityRebateDao activityRebateDao;
    @Autowired
    private ActivityRebateService activityRebateService;
    @Autowired
    private ActivityCostService activityCostService;
    
    @RequestMapping(value = { "/activity-rebate-cost/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_COST_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-cost/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final ActivityRebate bean = this.activityRebateDao.getByType(15);
                json.accumulate("data", bean);
                json.set(0, "0-3");
            }
            else {
                final ActivityRebate bean = new ActivityRebate();
                bean.setId(15);
                bean.setType(15);
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
    
    @RequestMapping(value = { "/activity-rebate-cost/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_COST_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final Integer id = HttpUtil.getIntParameter(request, "id");
        final ActivityRebate bean = this.activityRebateDao.getById(id);
        final JSONObject json = JSONObject.fromObject((Object)bean);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/activity-rebate-cost/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_COST_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-cost/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String rules = request.getParameter("rules");
                final List<RebateRulesCost> rewardRules = (List<RebateRulesCost>)JSONArray.toCollection(JSONArray.fromObject((Object)rules), (Class)RebateRulesCost.class);
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
    
    @RequestMapping(value = { "/activity-rebate-cost/update-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_COST_UPDATE_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-cost/update-status";
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
    
    @RequestMapping(value = { "/activity-rebate-cost/bill" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_COST_BILL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-cost/bill";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String date = request.getParameter("date");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.activityCostService.searchBill(username, date, start, limit);
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
