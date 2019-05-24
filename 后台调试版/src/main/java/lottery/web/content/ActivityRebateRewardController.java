package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.io.PrintStream;
import java.util.List;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.ActivityRebateService;
import lottery.domains.content.biz.ActivityRewardService;
import lottery.domains.content.dao.ActivityRebateDao;
import lottery.domains.content.entity.ActivityRebate;
import lottery.domains.content.entity.activity.RebateRulesReward;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ActivityRebateRewardController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private ActivityRebateDao activityRebateDao;
  @Autowired
  private ActivityRebateService activityRebateService;
  @Autowired
  private ActivityRewardService activityRewardService;
  
  @RequestMapping(value={"/activity-rebate-reward/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ACTIVITY_REBATE_REWARD_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/activity-rebate-reward/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String type = request.getParameter("type");
        if ("xf".equals(type))
        {
          ActivityRebate bean = this.activityRebateDao.getByType(1);
          json.accumulate("data", bean);
        }
        if ("yk".equals(type))
        {
          ActivityRebate bean = this.activityRebateDao.getByType(2);
          json.accumulate("data", bean);
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
  
  @RequestMapping(value={"/activity-rebate-reward/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public synchronized void ACTIVITY_REBATE_REWARD_EDIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/activity-rebate-reward/edit";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String rules = request.getParameter("rules");
        
        List<RebateRulesReward> rewardRules = (List)JSONArray.toCollection(JSONArray.fromObject(rules), RebateRulesReward.class);
        if ((rewardRules != null) && (rewardRules.size() > 0))
        {
          boolean result = this.activityRebateService.edit(id, rules, null, null);
          if (result) {
            json.set(Integer.valueOf(0), "0-5");
          } else {
            json.set(Integer.valueOf(1), "1-5");
          }
        }
        else
        {
          json.set(Integer.valueOf(1), "1-5");
        }
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
  
  @RequestMapping(value={"/activity-rebate-reward/update-status"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public synchronized void ACTIVITY_REBATE_REWARD_UPDATE_STATUS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/activity-rebate-reward/update-status";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        boolean result = this.activityRebateService.updateStatus(id, status);
        if (result) {
          json.set(Integer.valueOf(0), "0-5");
        } else {
          json.set(Integer.valueOf(1), "1-5");
        }
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
  
  @RequestMapping(value={"/activity-rebate-reward-bill/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public void ACTIVITY_REBATE_REWARD_BILL_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/activity-rebate-reward-bill/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String date = request.getParameter("date");
        Integer type = HttpUtil.getIntParameter(request, "type");
        Integer status = HttpUtil.getIntParameter(request, "status");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.activityRewardService.search(username, date, type, status, start, limit);
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
  
  @RequestMapping(value={"/activity-rebate-reward-bill/calculate"}, method={org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public void ACTIVITY_REBATE_REWARD_BILL_CALCULATE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/activity-rebate-reward-bill/calculate";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String date = request.getParameter("date");
        boolean xfResult = false;
        try
        {
          xfResult = this.activityRewardService.calculate(1, date);
        }
        catch (Exception e)
        {
          System.out.println("消费佣金计算失败。");
        }
        boolean ykResult = false;
        try
        {
          ykResult = this.activityRewardService.calculate(2, date);
        }
        catch (Exception e)
        {
          System.out.println("盈亏佣金计算失败。");
        }
        json.accumulate("xfResult", Boolean.valueOf(xfResult));
        json.accumulate("ykResult", Boolean.valueOf(ykResult));
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
  
  @RequestMapping(value={"/activity-rebate-reward-bill/confirm"}, method={org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public void ACTIVITY_REBATE_REWARD_BILL_CONFIRM(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/activity-rebate-reward-bill/confirm";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String date = request.getParameter("date");
        boolean result = this.activityRewardService.agreeAll(date);
        if (result) {
          json.set(Integer.valueOf(0), "0-5");
        } else {
          json.set(Integer.valueOf(1), "1-5");
        }
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
  
  @RequestMapping(value={"/activity-rebate-reward/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ACTIVITY_REBATE_REWARD_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    Integer id = HttpUtil.getIntParameter(request, "id");
    ActivityRebate bean = this.activityRebateDao.getById(id.intValue());
    JSONObject json = JSONObject.fromObject(bean);
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
