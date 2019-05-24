package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import com.alibaba.fastjson.JSON;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.ActivityRebateService;
import lottery.domains.content.biz.ActivityRebateWheelBillService;
import lottery.domains.content.entity.ActivityRebate;
import lottery.domains.content.entity.activity.RebateRulesWheel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ActivityRebateWheelController
  extends AbstractActionController
{
  @Autowired
  private ActivityRebateWheelBillService billService;
  @Autowired
  private ActivityRebateService activityRebateService;
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  
  @RequestMapping(value={"/activity-rebate-wheel/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ACTIVITY_REBATE_WHEEL_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/activity-rebate-wheel/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        ActivityRebate bean = this.activityRebateService.getByType(16);
        json.accumulate("data", bean);
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
  
  @RequestMapping(value={"/activity-rebate-wheel/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ACTIVITY_REBATE_WHEEL_EDIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/activity-rebate-sign/edit";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String rule = request.getParameter("rule");
        RebateRulesWheel rulesWheel = (RebateRulesWheel)JSON.parseObject(rule, RebateRulesWheel.class);
        if (rulesWheel != null)
        {
          boolean result = this.activityRebateService.edit(16, rule, null, null);
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
  
  @RequestMapping(value={"/activity-rebate-wheel/update-status"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ACTIVITY_REBATE_WHEEL_UPDATE_STATUS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/activity-rebate-sign/update-status";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        boolean result = this.activityRebateService.updateStatus(16, status);
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
  
  @RequestMapping(value={"/activity-rebate-wheel/bill"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ACTIVITY_REBATE_WHEEL_BILL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/activity-rebate-wheel/bill";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = HttpUtil.getStringParameterTrim(request, "username");
        String minTime = HttpUtil.getStringParameterTrim(request, "minTime");
        String maxTime = HttpUtil.getStringParameterTrim(request, "maxTime");
        String ip = HttpUtil.getStringParameterTrim(request, "ip");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.billService.find(username, minTime, maxTime, ip, start, limit);
        if (pList != null)
        {
          double totalAmount = this.billService.sumAmount(username, minTime, maxTime, ip);
          json.accumulate("totalAmount", Double.valueOf(totalAmount));
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
}
