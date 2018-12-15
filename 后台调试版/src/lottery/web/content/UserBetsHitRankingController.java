package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserBetsHitRankingService;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.entity.UserBetsHitRanking;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserBetsHitRankingController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private UserBetsHitRankingService uBetsHitRankingService;
  @Autowired
  private UserSysMessageService mUserSysMessageService;
  
  @RequestMapping(value={"/user-bets-hit-ranking/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void USER_BETS_HIT_RANKING_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/user-bets-hit-ranking/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.uBetsHitRankingService.search(start, limit);
        json.accumulate("totalCount", Integer.valueOf(pList.getCount()));
        json.accumulate("data", pList.getList());
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
  
  @RequestMapping(value={"/user-bets-hit-ranking/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void USER_BETS_HIT_RANKING_ADD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/user-bets-hit-ranking/add";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        int prizeMoney = HttpUtil.getIntParameter(request, "prizeMoney").intValue();
        String time = request.getParameter("time");
        String code = request.getParameter("code");
        String type = request.getParameter("type");
        int platform = HttpUtil.getIntParameter(request, "platform").intValue();
        if (StringUtils.isEmpty(name))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if (StringUtils.isEmpty(username))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if ((prizeMoney < 0) || (prizeMoney > 99999999))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if (StringUtils.isEmpty(time))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if (StringUtils.isEmpty(code))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if (StringUtils.isEmpty(type))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if ((platform != 2) && (platform != 4) && (platform != 4))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        boolean result = this.uBetsHitRankingService.add(name, username, prizeMoney, time, code, type, platform);
        if (result) {
          json.set(Integer.valueOf(0), "0-6");
        } else {
          json.set(Integer.valueOf(1), "1-6");
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
  
  @RequestMapping(value={"/user-bets-hit-ranking/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void USER_BETS_HIT_RANKING_EDIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/user-bets-hit-ranking/edit";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        int prizeMoney = HttpUtil.getIntParameter(request, "prizeMoney").intValue();
        String time = request.getParameter("time");
        String code = request.getParameter("code");
        String type = request.getParameter("type");
        int platform = HttpUtil.getIntParameter(request, "platform").intValue();
        if (StringUtils.isEmpty(name))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if (StringUtils.isEmpty(username))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if ((prizeMoney < 0) || (prizeMoney > 99999999))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if (StringUtils.isEmpty(time))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if (StringUtils.isEmpty(code))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if (StringUtils.isEmpty(type))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if ((platform != 2) && (platform != 4) && (platform != 4))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        boolean result = this.uBetsHitRankingService.edit(id, name, username, prizeMoney, time, code, type, platform);
        if (result) {
          json.set(Integer.valueOf(0), "0-6");
        } else {
          json.set(Integer.valueOf(1), "1-6");
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
  
  @RequestMapping(value={"/user-bets-hit-ranking/del"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void USER_BETS_HIT_RANKING_DEL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/user-bets-hit-ranking/del";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        boolean result = this.uBetsHitRankingService.delete(id);
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
  
  @RequestMapping(value={"/user-bets-hit-ranking/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void USER_BETS_HIT_RANKING_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    int id = HttpUtil.getIntParameter(request, "id").intValue();
    UserBetsHitRanking bean = this.uBetsHitRankingService.getById(id);
    JSONObject json = JSONObject.fromObject(bean);
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
