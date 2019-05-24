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
import lottery.domains.content.biz.UserMessageService;
import lottery.domains.content.vo.user.UserMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserMessageController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private UserMessageService uMessageService;
  
  @RequestMapping(value={"/lottery-user-message/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MESSAGE_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-message/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String toUser = request.getParameter("toUser");
        String fromUser = request.getParameter("fromUser");
        String sTime = request.getParameter("sTime");
        String eTime = request.getParameter("eTime");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        int sortColoum = HttpUtil.getIntParameter(request, "sortColoum").intValue();
        PageList pList = this.uMessageService.search(toUser, fromUser, sTime, eTime, sortColoum, start, limit);
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
  
  @RequestMapping(value={"/lottery-user-message/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MESSAGE_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-message/get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        UserMessageVO result = this.uMessageService.getById(id);
        json.accumulate("data", result);
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
  
  @RequestMapping(value={"/lottery-user-message/delete"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MESSAGE_DELETE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-message/delete";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        boolean result = this.uMessageService.delete(id);
        if (result) {
          json.set(Integer.valueOf(0), "0-5");
        } else {
          json.set(Integer.valueOf(0), "0-6");
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
  
  @RequestMapping(value={"/lottery-user-message/reply"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MESSAGE_REPLY(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-message/reply";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String content = request.getParameter("content");
        boolean result = this.uMessageService.save(id, content);
        if (result) {
          json.set(Integer.valueOf(0), "0-5");
        } else {
          json.set(Integer.valueOf(0), "0-6");
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
