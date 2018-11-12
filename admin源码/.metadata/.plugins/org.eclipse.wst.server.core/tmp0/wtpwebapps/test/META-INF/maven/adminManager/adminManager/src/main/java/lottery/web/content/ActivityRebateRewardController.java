package lottery.web.content;

import net.sf.json.JSONObject;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.activity.RebateRulesReward;
import net.sf.json.JSONArray;
import java.util.List;
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
import lottery.domains.content.biz.ActivityRewardService;
import lottery.domains.content.biz.ActivityRebateService;
import lottery.domains.content.dao.ActivityRebateDao;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class ActivityRebateRewardController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private ActivityRebateDao activityRebateDao;
    @Autowired
    private ActivityRebateService activityRebateService;
    @Autowired
    private ActivityRewardService activityRewardService;
    
    @RequestMapping(value = { "/activity-rebate-reward/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_REWARD_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-reward/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String type = request.getParameter("type");
                if ("xf".equals(type)) {
                    final ActivityRebate bean = this.activityRebateDao.getByType(1);
                    json.accumulate("data", bean);
                }
                if ("yk".equals(type)) {
                    final ActivityRebate bean = this.activityRebateDao.getByType(2);
                    json.accumulate("data", bean);
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
    
    @RequestMapping(value = { "/activity-rebate-reward/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public synchronized void ACTIVITY_REBATE_REWARD_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-reward/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String rules = request.getParameter("rules");
                final List<RebateRulesReward> rewardRules = (List<RebateRulesReward>)JSONArray.toCollection(JSONArray.fromObject((Object)rules), (Class)RebateRulesReward.class);
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
    
    @RequestMapping(value = { "/activity-rebate-reward/update-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public synchronized void ACTIVITY_REBATE_REWARD_UPDATE_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-reward/update-status";
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
    
    @RequestMapping(value = { "/activity-rebate-reward-bill/list" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public void ACTIVITY_REBATE_REWARD_BILL_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-reward-bill/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String date = request.getParameter("date");
                final Integer type = HttpUtil.getIntParameter(request, "type");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.activityRewardService.search(username, date, type, status, start, limit);
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
    
    @RequestMapping(value = { "/activity-rebate-reward-bill/calculate" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public void ACTIVITY_REBATE_REWARD_BILL_CALCULATE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-reward-bill/calculate";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String date = request.getParameter("date");
                boolean xfResult = false;
                try {
                    xfResult = this.activityRewardService.calculate(1, date);
                }
                catch (Exception e) {
                    System.out.println("消费佣金计算失败。");
                }
                boolean ykResult = false;
                try {
                    ykResult = this.activityRewardService.calculate(2, date);
                }
                catch (Exception e2) {
                    System.out.println("盈亏佣金计算失败。");
                }
                json.accumulate("xfResult", xfResult);
                json.accumulate("ykResult", ykResult);
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
    
    @RequestMapping(value = { "/activity-rebate-reward-bill/confirm" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public void ACTIVITY_REBATE_REWARD_BILL_CONFIRM(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/activity-rebate-reward-bill/confirm";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String date = request.getParameter("date");
                final boolean result = this.activityRewardService.agreeAll(date);
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
    
    @RequestMapping(value = { "/activity-rebate-reward/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ACTIVITY_REBATE_REWARD_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final Integer id = HttpUtil.getIntParameter(request, "id");
        final ActivityRebate bean = this.activityRebateDao.getById(id);
        final JSONObject json = JSONObject.fromObject((Object)bean);
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
