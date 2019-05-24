package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.util.List;
import java.util.Map;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.ActivityGrabService;
import lottery.domains.content.biz.ActivityRebateService;
import lottery.domains.content.dao.ActivityRebateDao;
import lottery.domains.content.entity.ActivityRebate;
import lottery.domains.content.entity.activity.RebateRulesGrab;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ActivityRebateGrabController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private ActivityRebateDao activityRebateDao;
  @Autowired
  private ActivityRebateService activityRebateService;
  @Autowired
  private ActivityGrabService activityGrabService;
  
  @RequestMapping(value={"/activity-rebate-grab/bill"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ACTIVITY_REBATE_GRAB_PACKAGE_BILL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/activity-rebate-grab/bill";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String date = request.getParameter("date");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.activityGrabService.searchBill(username, date, start, limit);
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
  
  @RequestMapping(value={"/activity-rebate-grab/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ACTIVITY_REBATE_GRAB_PACKAGE_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/activity-rebate-grab/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        ActivityRebate bean = this.activityRebateDao.getByType(10);
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
  
  @RequestMapping(value={"/activity-rebate-grab/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ACTIVITY_REBATE_GRAB_PACKAGE_EDIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/activity-rebate-grab/edit";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String rules = request.getParameter("rules");
        
        List<RebateRulesGrab> rewardRules = (List)JSONArray.toCollection(JSONArray.fromObject(rules), RebateRulesGrab.class);
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
  
  @RequestMapping(value={"/activity-rebate-grab/update-status"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ACTIVITY_REBATE_GRAB_PACKAGE_STATUS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/activity-rebate-grab/update-status";
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
  
  @RequestMapping(value={"/activity-rebate-grab/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ACTIVITY_REBATE_GRAB_PACKAGE_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    Integer id = HttpUtil.getIntParameter(request, "id");
    ActivityRebate bean = this.activityRebateDao.getById(id.intValue());
    JSONObject json = JSONObject.fromObject(bean);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/activity-rebate-grab/out-total"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ACTIVITY_REBATE_GRAB_PACKAGE_OUTTOTAL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    Map<String, Double> outTotal = this.activityGrabService.getOutTotalInfo();
    json.accumulate("outTotal", outTotal);
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
