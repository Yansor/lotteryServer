package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.domains.jobs.AdminUserCriticalLogJob;
import admin.domains.jobs.AdminUserLogJob;
import admin.domains.jobs.MailJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import javautils.StringUtil;
import javautils.encrypt.PasswordUtil;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.PaymentChannelService;
import lottery.domains.content.biz.UserRechargeService;
import lottery.domains.content.vo.user.HistoryUserRechargeVO;
import lottery.domains.content.vo.user.UserRechargeVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserRechargeController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private AdminUserLogJob adminUserLogJob;
  @Autowired
  private UserRechargeService uRechargeService;
  @Autowired
  private MailJob mailJob;
  @Autowired
  private PaymentChannelService paymentChannelService;
  @Autowired
  private AdminUserCriticalLogJob adminUserCriticalLogJob;
  
  @RequestMapping(value={"/lottery-user-recharge/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_RECHARGE_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-recharge/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String billno = request.getParameter("billno");
        String username = request.getParameter("username");
        String minTime = request.getParameter("minTime");
        if (StringUtil.isNotNull(minTime)) {
          minTime = minTime + " 00:00:00";
        }
        String maxTime = request.getParameter("maxTime");
        if (StringUtil.isNotNull(maxTime)) {
          maxTime = maxTime + " 00:00:00";
        }
        String minPayTime = request.getParameter("minPayTime");
        if (StringUtil.isNotNull(minPayTime)) {
          minPayTime = minPayTime + " 00:00:00";
        }
        String maxPayTime = request.getParameter("maxPayTime");
        if (StringUtil.isNotNull(maxPayTime)) {
          maxPayTime = maxPayTime + " 00:00:00";
        }
        Double minMoney = HttpUtil.getDoubleParameter(request, "minMoney");
        Double maxMoney = HttpUtil.getDoubleParameter(request, "maxMoney");
        Integer status = HttpUtil.getIntParameter(request, "status");
        Integer channelId = HttpUtil.getIntParameter(request, "channelId");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        Integer type = HttpUtil.getIntParameter(request, "type");
        PageList pList = this.uRechargeService.search(type, billno, username, minTime, maxTime, minPayTime, maxPayTime, minMoney, maxMoney, status, channelId, start, limit);
        if (pList != null)
        {
          double totalRecharge = this.uRechargeService.getTotalRecharge(type, billno, username, minTime, maxTime, minPayTime, maxPayTime, minMoney, maxMoney, status, channelId);
          json.accumulate("totalRecharge", Double.valueOf(totalRecharge));
          json.accumulate("totalCount", Integer.valueOf(pList.getCount()));
          json.accumulate("data", pList.getList());
        }
        else
        {
          json.accumulate("totalRecharge", Integer.valueOf(0));
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
  
  @RequestMapping(value={"/history-lottery-user-recharge/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void HISTORY_LOTTERY_USER_RECHARGE_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/history-lottery-user-recharge/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String billno = request.getParameter("billno");
        String username = request.getParameter("username");
        String minTime = request.getParameter("minTime");
        if (StringUtil.isNotNull(minTime)) {
          minTime = minTime + " 00:00:00";
        }
        String maxTime = request.getParameter("maxTime");
        if (StringUtil.isNotNull(maxTime)) {
          maxTime = maxTime + " 00:00:00";
        }
        String minPayTime = request.getParameter("minPayTime");
        if (StringUtil.isNotNull(minPayTime)) {
          minPayTime = minPayTime + " 00:00:00";
        }
        String maxPayTime = request.getParameter("maxPayTime");
        if (StringUtil.isNotNull(maxPayTime)) {
          maxPayTime = maxPayTime + " 00:00:00";
        }
        Double minMoney = HttpUtil.getDoubleParameter(request, "minMoney");
        Double maxMoney = HttpUtil.getDoubleParameter(request, "maxMoney");
        Integer status = HttpUtil.getIntParameter(request, "status");
        Integer channelId = HttpUtil.getIntParameter(request, "channelId");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.uRechargeService.searchHistory(billno, username, minTime, maxTime, minPayTime, maxPayTime, minMoney, maxMoney, status, channelId, start, limit);
        if (pList != null)
        {
          double totalRecharge = this.uRechargeService.getHistoryTotalRecharge(billno, username, minTime, maxTime, minPayTime, maxPayTime, minMoney, maxMoney, status, channelId);
          json.accumulate("totalRecharge", Double.valueOf(totalRecharge));
          json.accumulate("totalCount", Integer.valueOf(pList.getCount()));
          json.accumulate("data", pList.getList());
        }
        else
        {
          json.accumulate("totalRecharge", Integer.valueOf(0));
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
  
  @RequestMapping(value={"/lottery-user-recharge/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_RECHARGE_ADD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-recharge/add";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        int type = HttpUtil.getIntParameter(request, "type").intValue();
        int account = HttpUtil.getIntParameter(request, "account").intValue();
        double amount = HttpUtil.getDoubleParameter(request, "amount").doubleValue();
        String withdrawPwd = request.getParameter("withdrawPwd");
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        String remarks = request.getParameter("remarks");
        if ((remarks == null) || (StringUtils.isEmpty(remarks.trim())))
        {
          json.set(Integer.valueOf(2), "2-30");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
        {
          if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
          {
            if (isUnlockedWithdrawPwd(session))
            {
              boolean result = this.uRechargeService.addSystemRecharge(username, type, account, amount, limit, remarks);
              if (result)
              {
                this.adminUserLogJob.logRecharge(uEntity, request, username, type, account, amount, limit, remarks);
                this.mailJob.sendSystemRecharge(username, uEntity.getUsername(), type, account, amount, remarks);
                json.set(Integer.valueOf(0), "0-5");
              }
              else
              {
                json.set(Integer.valueOf(1), "1-5");
              }
            }
            else
            {
              json.set(Integer.valueOf(2), "2-43");
            }
          }
          else {
            json.set(Integer.valueOf(2), "2-41");
          }
        }
        else {
          json.set(Integer.valueOf(2), "2-12");
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
  
  @RequestMapping(value={"/lottery-user-recharge/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_RECHARGE_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-recharge/get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        UserRechargeVO result = this.uRechargeService.getById(id);
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
  
  @RequestMapping(value={"/history-lottery-user-recharge/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void HISTORY_LOTTERY_USER_RECHARGE_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/history-lottery-user-recharge/get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        HistoryUserRechargeVO result = this.uRechargeService.getHistoryById(id);
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
  
  @RequestMapping(value={"/lottery-user-recharge/patch"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_RECHARGE_PATCH(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-recharge/patch";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String billno = request.getParameter("billno");
        String payBillno = request.getParameter("paybillno");
        String remarks = request.getParameter("remarks");
        String withdrawPwd = request.getParameter("withdrawPwd");
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
        {
          if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
          {
            if (isUnlockedWithdrawPwd(session))
            {
              boolean result = this.uRechargeService.patchOrder(billno, payBillno, remarks);
              if (result)
              {
                this.adminUserLogJob.logPatchRecharge(uEntity, request, billno, payBillno, remarks);
                json.set(Integer.valueOf(0), "0-3");
              }
              else
              {
                json.set(Integer.valueOf(1), "1-3");
              }
            }
            else
            {
              json.set(Integer.valueOf(2), "2-43");
            }
          }
          else {
            json.set(Integer.valueOf(2), "2-41");
          }
        }
        else {
          json.set(Integer.valueOf(2), "2-12");
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
  
  @RequestMapping(value={"/lottery-user-recharge/cancel"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_RECHARGE_CANCEL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-recharge/cancel";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String billno = request.getParameter("billno");
        boolean result = this.uRechargeService.cancelOrder(billno);
        if (result)
        {
          this.adminUserLogJob.logCancelRecharge(uEntity, request, billno);
          json.set(Integer.valueOf(0), "0-3");
        }
        else
        {
          json.set(Integer.valueOf(1), "1-3");
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
