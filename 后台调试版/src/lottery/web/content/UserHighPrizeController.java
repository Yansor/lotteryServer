package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.domains.jobs.AdminUserLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserHighPrizeService;
import lottery.web.content.utils.UserCodePointUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserHighPrizeController
  extends AbstractActionController
{
  @Autowired
  private UserHighPrizeService highPrizeService;
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private AdminUserLogJob adminUserLogJob;
  @Autowired
  private UserCodePointUtil uCodePointUtil;
  
  @RequestMapping(value={"/user-high-prize/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void USER_HIGH_PRIZE_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/user-high-prize/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        Integer platform = HttpUtil.getIntParameter(request, "platform");
        String nameId = request.getParameter("nameId");
        String subName = request.getParameter("subName");
        String refId = request.getParameter("refId");
        Integer type = HttpUtil.getIntParameter(request, "type");
        Double minMoney = HttpUtil.getDoubleParameter(request, "minMoney");
        Double maxMoney = HttpUtil.getDoubleParameter(request, "maxMoney");
        Double minPrizeMoney = HttpUtil.getDoubleParameter(request, "minPrizeMoney");
        Double maxPrizeMoney = HttpUtil.getDoubleParameter(request, "maxPrizeMoney");
        Double minTimes = HttpUtil.getDoubleParameter(request, "minTimes");
        Double maxTimes = HttpUtil.getDoubleParameter(request, "maxTimes");
        String minTime = request.getParameter("minTime");
        String maxTime = request.getParameter("maxTime");
        if (StringUtils.isNotEmpty(minTime)) {
          minTime = minTime + " 00:00:00";
        }
        if (StringUtils.isNotEmpty(maxTime)) {
          maxTime = maxTime + " 23:59:59";
        }
        Integer status = HttpUtil.getIntParameter(request, "status");
        String confirmUsername = request.getParameter("confirmUsername");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.highPrizeService.search(type, username, platform, nameId, subName, refId, minMoney, maxMoney, minPrizeMoney, maxPrizeMoney, 
          minTimes, maxTimes, minTime, maxTime, status, confirmUsername, start, limit);
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
  
  @RequestMapping(value={"/user-high-prize/lock"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void USER_HIGH_PRIZE_LOCK(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/user-high-prize/lock";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        
        boolean result = this.highPrizeService.lock(id, uEntity.getUsername());
        if (result)
        {
          this.adminUserLogJob.logLockHighPrize(uEntity, request, id);
          json.set(Integer.valueOf(0), "0-5");
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
  
  @RequestMapping(value={"/user-high-prize/unlock"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void USER_HIGH_PRIZE_UNLOCK(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/user-high-prize/unlock";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        
        boolean result = this.highPrizeService.unlock(id, uEntity.getUsername());
        if (result)
        {
          this.adminUserLogJob.logUnLockHighPrize(uEntity, request, id);
          json.set(Integer.valueOf(0), "0-5");
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
  
  @RequestMapping(value={"/user-high-prize/confirm"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void USER_HIGH_PRIZE_CONFIRM(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/user-high-prize/confirm";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        
        boolean result = this.highPrizeService.confirm(id, uEntity.getUsername());
        if (result)
        {
          this.adminUserLogJob.logConfirmHighPrize(uEntity, request, id);
          json.set(Integer.valueOf(0), "0-5");
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
}
