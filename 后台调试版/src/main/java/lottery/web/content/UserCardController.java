package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.domains.jobs.AdminUserCriticalLogJob;
import admin.domains.jobs.AdminUserLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserBankcardUnbindService;
import lottery.domains.content.biz.UserCardService;
import lottery.domains.content.dao.UserCardDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.vo.user.UserCardVO;
import lottery.web.content.validate.UserCardValidate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserCardController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private AdminUserLogJob adminUserLogJob;
  @Autowired
  private AdminUserCriticalLogJob adminUserCriticalLogJob;
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserCardDao uCardDao;
  @Autowired
  private UserCardService uCardService;
  @Autowired
  private UserBankcardUnbindService uBankcardUnbindService;
  @Autowired
  private UserCardValidate uCardValidate;
  
  @RequestMapping(value={"/lottery-user-card/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_CARD_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-card/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String keyword = request.getParameter("keyword");
        Integer status = HttpUtil.getIntParameter(request, "status");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.uCardService.search(username, keyword, status, start, limit);
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
  
  @RequestMapping(value={"/lottery-user-card/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_CARD_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-card/get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        UserCardVO result = this.uCardService.getById(id);
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
  
  @RequestMapping(value={"/lottery-user-card/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_CARD_EDIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-card/edit";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        int bankId = HttpUtil.getIntParameter(request, "bankId").intValue();
        String bankBranch = request.getParameter("bankBranch");
        String cardId = request.getParameter("cardId");
        UserCard cBean = this.uCardDao.getById(id);
        if (cBean != null)
        {
          User targetUser = this.uDao.getById(cBean.getUserId());
          if (targetUser != null)
          {
            String cardName = targetUser.getWithdrawName();
            if (this.uCardValidate.required(json, Integer.valueOf(bankId), cardName, cardId)) {
              if (this.uCardValidate.checkCardId(cardId))
              {
                UserCard exBean = this.uCardDao.getByCardId(cardId);
                if ((exBean == null) || (exBean.getId() == id))
                {
                  boolean result = this.uCardService.edit(id, bankId, bankBranch, cardId);
                  if (result)
                  {
                    this.adminUserLogJob.logModUserCard(uEntity, request, targetUser.getUsername(), bankId, bankBranch, cardId);
                    this.adminUserCriticalLogJob.logModUserCard(uEntity, request, targetUser.getUsername(), bankId, bankBranch, cardId, actionKey);
                    json.set(Integer.valueOf(0), "0-6");
                  }
                  else
                  {
                    json.set(Integer.valueOf(1), "1-6");
                  }
                }
                else
                {
                  json.set(Integer.valueOf(2), "2-1015");
                }
              }
              else
              {
                json.set(Integer.valueOf(2), "2-1014");
              }
            }
          }
          else
          {
            json.set(Integer.valueOf(2), "2-3");
          }
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user-card/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_CARD_ADD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-card/add";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        int bankId = HttpUtil.getIntParameter(request, "bankId").intValue();
        String bankBranch = request.getParameter("bankBranch");
        String cardId = request.getParameter("cardId");
        User targetUser = this.uDao.getByUsername(username);
        if (targetUser != null)
        {
          if (targetUser.getBindStatus() == 1)
          {
            String cardName = targetUser.getWithdrawName();
            if (this.uCardValidate.required(json, Integer.valueOf(bankId), cardName, cardId)) {
              if (this.uCardValidate.checkCardId(cardId))
              {
                if (this.uCardDao.getByCardId(cardId) == null)
                {
                  boolean result = this.uCardService.add(username, bankId, bankBranch, cardName, cardId, 0);
                  if (result)
                  {
                    this.adminUserLogJob.logAddUserCard(uEntity, request, username, bankId, bankBranch, cardId);
                    this.adminUserCriticalLogJob.logAddUserCard(uEntity, request, username, bankId, bankBranch, cardId, actionKey);
                    json.set(Integer.valueOf(0), "0-6");
                  }
                  else
                  {
                    json.set(Integer.valueOf(1), "1-6");
                  }
                }
                else
                {
                  json.set(Integer.valueOf(2), "2-1015");
                }
              }
              else {
                json.set(Integer.valueOf(2), "2-1014");
              }
            }
          }
          else
          {
            json.set(Integer.valueOf(2), "2-1016");
          }
        }
        else {
          json.set(Integer.valueOf(2), "2-3");
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
  
  @RequestMapping(value={"/lottery-user-card/lock-status"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_CARD_LOCK_STATUS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-card/lock-status";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        boolean result = this.uCardService.updateStatus(id, status);
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
  
  @RequestMapping(value={"/lottery-user-card/unbid-list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_UNBIND_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-card/unbid-list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      String userName = request.getParameter("username");
      String cardId = request.getParameter("cardId");
      String unbindTime = request.getParameter("unbindTime");
      int start = HttpUtil.getIntParameter(request, "start").intValue();
      int limit = HttpUtil.getIntParameter(request, "limit").intValue();
      PageList pList = this.uBankcardUnbindService.search(userName, cardId, 
        unbindTime, start, limit);
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
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-user-card/unbid-del"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_UNBIND_DEL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-card/unbid-del";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String cardId = HttpUtil.getStringParameterTrim(request, "cardId");
        String remark = HttpUtil.getStringParameterTrim(request, "remark");
        if (StringUtils.isEmpty(cardId))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if (StringUtils.isEmpty(remark))
        {
          json.set(Integer.valueOf(2), "2-30");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if (remark.length() > 128)
        {
          json.set(Integer.valueOf(2), "2-35");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        boolean result = this.uBankcardUnbindService.delByCardId(cardId);
        if (result)
        {
          this.adminUserLogJob.logDelUserCardUnbindRecord(uEntity, request, cardId, remark);
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
